package model.expression;

import model.Type;
import model.state.MapHeap;
import model.state.SymbolTable;
import model.state.structures.m_HashMap;
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
    public Type typeCheck(m_HashMap<String, Type> typeEnv) {
        return value.getType();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
