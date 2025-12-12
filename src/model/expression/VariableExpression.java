package model.expression;

import model.state.MapHeap;
import model.state.SymbolTable;
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
    public String toString() {
        return "( " + variableName + " )";
    }
}
