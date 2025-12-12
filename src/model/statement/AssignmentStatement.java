package model.statement;

import model.expression.Expression;
import model.state.ProgramState;
import model.state.SymbolTable;

public record AssignmentStatement(String variableName, Expression value) implements Statement {


    @Override
    public ProgramState execute(ProgramState state) {
        SymbolTable symbolTable = state.getSymbolTable();

        symbolTable.put(variableName, value.evaluate(symbolTable, state.getHeap()));

        return null;
    }

    @Override
    public Statement deepCopy() {
        return new AssignmentStatement(variableName, value.deepCopy());
    }

    @Override
    public String toString() {
        return "[ ASSIGNMENT: " + variableName + " = " + value.toString() + " ]";
    }
}
