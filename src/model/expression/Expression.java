package model.expression;

import model.state.MapHeap;
import model.state.SymbolTable;
import model.value.Value;

public interface Expression {
    Value evaluate(SymbolTable symbolTable, MapHeap<Integer, Value> heap);
    Expression deepCopy();
}
