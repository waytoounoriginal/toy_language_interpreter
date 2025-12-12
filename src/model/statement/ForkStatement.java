package model.statement;

import model.state.ExecutionStack;
import model.state.ListExecutionStack;
import model.state.ProgramState;

public record ForkStatement(Statement statement) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        // This will basically create a new id with the cloned symtable and the rest are references and a statement as the execution stack
        ExecutionStack newExeStack = new ListExecutionStack();
        newExeStack.push(statement);

        ProgramState forkedState = new ProgramState(
                newExeStack,
                state.getSymbolTable().deepCopy(),
                state.getOut(),
                state.getFileTable(),
                state.getHeap()
        );

        return forkedState;
    }

    @Override
    public Statement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public String toString() {
        return "fork( " + statement.toString() + " )";
    }
}
