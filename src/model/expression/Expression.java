package model.expression;

import model.Type;
import model.state.MapHeap;
import model.state.SymbolTable;
import model.state.structures.m_HashMap;
import model.value.Value;

public interface Expression {
    Value evaluate(SymbolTable symbolTable, MapHeap<Integer, Value> heap);
    Expression deepCopy();

    Type typeCheck(m_HashMap<String, Type> typeEnv);
}
