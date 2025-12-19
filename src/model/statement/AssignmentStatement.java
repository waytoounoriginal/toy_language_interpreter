package model.statement;

import model.Type;
import model.exceptions.InvalidArithmeticExpception;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.state.structures.m_HashMap;

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
    public m_HashMap<String, Type> typeCheck(m_HashMap<String, Type> typeEnv) {
        Type typeVar = typeEnv.getValue(variableName);
        Type typeExp = value.typeCheck(typeEnv);

        if(typeExp.equals(typeVar)) {
            return typeEnv;
        }

        throw new InvalidArithmeticExpception("Assignment: RHS and LHS have different types");
    }

    @Override
    public String toString() {
        return "[ ASSIGNMENT: " + variableName + " = " + value.toString() + " ]";
    }
}
