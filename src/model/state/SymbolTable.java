package model.state;

import model.Type;
import model.state.structures.m_HashMap;
import model.value.Value;

public interface SymbolTable extends m_HashMap<String, Value> {

    void declareVariable(Type type, String variableName);
    Type getVariableType(String variableName);

    String toLogString();

    SymbolTable deepCopy();
}
