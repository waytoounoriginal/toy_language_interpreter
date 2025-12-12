package model.expression;

import model.state.MapHeap;
import model.state.SymbolTable;
import model.value.Value;

public class ValueExpression implements Expression{
    private final Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public Value evaluate(SymbolTable symbolTable, MapHeap<Integer, Value> heap) {
        return value;
    }

    @Override
    public Expression deepCopy() {
        return new ValueExpression(value.deepCopy());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
