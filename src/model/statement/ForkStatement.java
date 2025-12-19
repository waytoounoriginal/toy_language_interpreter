package model.statement;

import model.Type;
import model.state.ExecutionStack;
import model.state.ListExecutionStack;
import model.state.ProgramState;
import model.state.structures.m_HashMap;

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
    public m_HashMap<String, Type> typeCheck(m_HashMap<String, Type> typeEnv) {
        return statement.typeCheck(typeEnv.deepCopy());
    }

    @Override
    public String toString() {
        return "fork( " + statement.toString() + " )";
    }
}
