package repository;

import model.state.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MultithreadedRepository implements Repository{
    private List<ProgramState> programStates;

    public MultithreadedRepository() {
        programStates = new ArrayList<>();
        programStates.addFirst(null);
    }


    @Override
    public void setMainProgramState(ProgramState state) {
        programStates.set(0, state);
    }

    @Override
    public void logProgramState(String fileName, ProgramState state) throws IOException {
        PrintWriter printWriter = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(fileName, true)));

        printWriter.print(state.toLogString());
        printWriter.close();
    }

    @Override
    public List<ProgramState> getProgramStatesList() {
        return programStates;
    }

    @Override
    public void setProgramStatesList(List<ProgramState> list) {
        programStates = list;
    }

    @Override
    public Repository deepCopy() {
        MultithreadedRepository newRepo = new MultithreadedRepository();
        newRepo.setProgramStatesList(programStates.stream().map(ProgramState::deepCopy).toList());
        return newRepo;
    }
}
