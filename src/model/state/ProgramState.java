package model.state;

import model.exceptions.EmptyExecutionStackException;
import model.statement.Statement;
import model.value.Value;

public class ProgramState {
    private final ExecutionStack executionStack;
    private final SymbolTable symbolTable;
    private final Out out;
    private final FileTable fileTable;
    private final MapHeap<Integer, Value> heap;

    static int CURRENT_ID = 0;
    private int id;

    public ProgramState(ExecutionStack executionStack, SymbolTable symbolTable, Out out, FileTable fileTable, MapHeap<Integer, Value> heap) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;

        this.id = getNewId();
    }

    private ProgramState(ExecutionStack executionStack, SymbolTable symbolTable, Out out, FileTable fileTable, MapHeap<Integer, Value> heap, boolean newId) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;

        if(newId) {
            this.id = getNewId();
        }
    }

    private static synchronized int getNewId() {
        return CURRENT_ID++;
    }

    public Boolean isNotCompleted() { return !executionStack.isEmpty(); }

    public Out getOut() {
        return out;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public ExecutionStack getExecutionStack() {
        return executionStack;
    }

    public FileTable getFileTable() {
        return fileTable;
    }

    public MapHeap<Integer, Value> getHeap() {
        return heap;
    }

    public ProgramState oneStep() {
        if(executionStack.isEmpty()) throw new EmptyExecutionStackException();

        Statement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

    public ProgramState deepCopy() {
        ProgramState clonedState = null;
        try {
            clonedState = new ProgramState(executionStack.deepCopy(), symbolTable.deepCopy(), out.deepCopy(), fileTable.deepCopy(), heap.deepCopy(), false);

        } catch(Exception _) {
            clonedState = new ProgramState(new ListExecutionStack(), new MapSymbolTable(), new ListOut(), new FileTable(), new MapHeap<Integer, Value>());
        }

        clonedState.id = this.id;
        return clonedState;
    }

    public String toLogString() {
        return "====== START ======\nPROGRAM ID: %d |\nExeStack:\n%s\n\nSymTable:\n%s\n\nOut:\n%s\n\nFileTable:\n%s\n\nHeap:\n%s\n\n===== END =====\n\n".formatted(
                id,
                executionStack.toLogString(),
                symbolTable.toLogString(),
                out.toLogString(),
                fileTable.toLogString(),
                heap.toLogString()
        );
    }

    @Override
    public String toString() {
        return "PROGRAM ID: %d |\nHeap: %s\nSymbolTable: %s\nOut: %s\nNext Statement: %s\n".formatted(
                id,
                heap.toString(),
                symbolTable.toString(),
                out.toString(),
                executionStack.top() != null ? executionStack.top().toString() : "EOF"
        );
    }

}
