package model.expression;

import model.RefType;
import model.Type;
import model.exceptions.HeapException;
import model.exceptions.InvalidValueException;
import model.state.MapHeap;
import model.state.SymbolTable;
import model.state.structures.m_HashMap;
import model.value.RefValue;
import model.value.Value;

public record HeapReadingExpression(Expression expr) implements Expression{
    @Override
    public Value evaluate(SymbolTable symbolTable, MapHeap<Integer, Value> heap) {
        var result = expr.evaluate(symbolTable, heap);

        if(!(result instanceof RefValue)) {
            throw new InvalidValueException("Expected a refValue");
        }

        // Get the address of the result
        Integer address = ((RefValue)result).getAddress();
        return heap.getValue(address);
    }

    @Override
    public Expression deepCopy() {
        return new HeapReadingExpression(expr.deepCopy());
    }

    @Override
    public Type typeCheck(m_HashMap<String, Type> typeEnv) {
        Type type;
        type = expr.typeCheck(typeEnv);

        if(type instanceof RefType) {
            RefType refType = (RefType) type;
            return refType.getInner();
        }

        throw new HeapException("The readHeap argument is not a Ref Type");
    }

    @Override
    public String toString() {
        return "rh( " + expr.toString() + " )";
    }
}
