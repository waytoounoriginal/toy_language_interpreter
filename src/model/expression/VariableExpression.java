package model.expression;

import model.Type;
import model.state.MapHeap;
import model.state.SymbolTable;
import model.state.structures.m_HashMap;
import model.value.Value;

public record VariableExpression(String variableName ) implements Expression {
    @Override
    public Value evaluate(SymbolTable symbolTable, MapHeap<Integer, Value> heap) {
        return symbolTable.getValue(variableName);
    }

    @Override
    public Expression deepCopy() {
        return new VariableExpression(variableName);
    }

    @Override
    public Type typeCheck(m_HashMap<String, Type> typeEnv) {
        return typeEnv.getValue(variableName);
    }

    @Override
    public String toString() {
        return "( " + variableName + " )";
    }
}
