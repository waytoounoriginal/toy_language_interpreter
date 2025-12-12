package controller;

import model.state.ProgramState;
import model.statement.Statement;
import model.value.RefValue;
import model.value.Value;
import repository.Repository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private Repository repository, originalRepository;
    private ExecutorService executor;
    private String logFileName;

    public Controller(Repository repository, String programName) {
        this.originalRepository = repository;
        this.repository = originalRepository.deepCopy();

        logFileName = "log-" + programName + ".txt";
    }

    public void reset() {
        this.repository = originalRepository.deepCopy();
    }


    List<ProgramState> removeCompletedPrograms(List<ProgramState> inputList) {
        return inputList.stream().filter(ProgramState::isNotCompleted).collect(Collectors.toList());
    }

    public void oneStepForAllPrograms(List<ProgramState> programStates) {
        programStates.forEach(p -> {
            try {
                repository.logProgramState(logFileName, p);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Run concurrently all the program states
        List<Callable<ProgramState>> callList = programStates.stream().map(
                p -> (Callable<ProgramState>)(p::oneStep)
        ).toList();

        // Start the execution of the callables
        List<ProgramState> newProgramList = new ArrayList<>();
        try {
            newProgramList = executor.invokeAll(callList)
                    .stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e.getCause());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        programStates.addAll(newProgramList);

        // Log into files
        programStates.forEach(prg -> {
            try {
                repository.logProgramState(logFileName, prg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        repository.setProgramStatesList(programStates);
    }

    public void allSteps() {
        executor = Executors.newFixedThreadPool(2);

        // Remove the completed programs
        var programs = removeCompletedPrograms(repository.getProgramStatesList());
        while(!programs.isEmpty()) {
            // The heap is shared, I can get and set any heap
            programs.getFirst().getHeap().setContent(
                    conservativeGarbageCollector(programs)
            );

            oneStepForAllPrograms(programs);
            programs = removeCompletedPrograms(repository.getProgramStatesList());
        }

        executor.shutdownNow();

        // Now the repo should contain at least one completed program

        // update the rpo
        repository.setProgramStatesList(programs);
    }

    public Set<Integer> getAddressesFromSymbolTable(Collection<Value> symbolTable) {
        return symbolTable.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue refValue = (RefValue) v; return refValue.getAddress(); })
                .collect(Collectors.toSet());
    }

    public Set<Integer> computeReachableAddressesInHeap(Set<Integer> initialAddresses, Map<Integer, Value> heap) {
        Set<Integer> reachableAddresses = new HashSet<>(initialAddresses);
        Stack<Integer> reachableAddressesStack = new Stack<>();
        reachableAddressesStack.addAll(initialAddresses);

        // For each addrress in the heap we're searching if it points to a refValue
        while (!reachableAddressesStack.isEmpty()) {
            int address = reachableAddressesStack.pop();
            Value v =  heap.get(address);

            // If the current address points to a ref value and it wasn't already seen by our collector, check it as well
            // It is a DFS approach
            if (v instanceof RefValue refValue) {
                int nextAddress = refValue.getAddress();
                if ( !reachableAddresses.contains(nextAddress) ) {
                    reachableAddresses.add(nextAddress);
                    reachableAddressesStack.push(nextAddress);
                }
            }
        }

        return reachableAddresses;
    }

    public Map<Integer, Value> safeGarbageCollector(Collection<Value> symbolTableValues, Map<Integer, Value> heap) {
        Set<Integer> initialAddresses = getAddressesFromSymbolTable(symbolTableValues);
        Set<Integer> reachableAddresses = computeReachableAddressesInHeap(initialAddresses, heap);
        return heap.entrySet().stream()
                .filter(elem -> reachableAddresses.contains(elem.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Integer, Value> conservativeGarbageCollector(List<ProgramState> programStates) {
        // The main change is that we want the references from all the possible programs
        Set<Integer> initialAddresses = new HashSet<>();
        programStates.forEach((state) -> {
            initialAddresses.addAll(
                    getAddressesFromSymbolTable(state.getSymbolTable().getContent().values())
            );
        });

        // The heap is shared, take it from the first program
        Map<Integer, Value> heap = programStates.getFirst().getHeap().getContent();

        Set<Integer> reachableAddresses = computeReachableAddressesInHeap(initialAddresses, heap);
        return heap.entrySet().stream()
                .filter(elem -> reachableAddresses.contains(elem.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
