package model.expression;

import model.exceptions.InvalidValueException;
import model.state.MapHeap;
import model.state.SymbolTable;
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
    public String toString() {
        return "rh( " + expr.toString() + " )";
    }
}
