package model.state;

import model.Type;
import model.exceptions.VariableAlreadyDefinedException;
import model.exceptions.VariableNotDeclaredException;
import model.value.Value;

import java.util.HashMap;
import java.util.Map;

public class MapSymbolTable implements SymbolTable {
    private MyHashMap<String, Value> symbolTable = new MyHashMap<>();

    @Override
    public boolean isDefined(String variableName) {
        return symbolTable.isDefined(variableName);
    }

    @Override
    public void declareVariable(Type type, String variableName) {
        if(isDefined(variableName)) {
            throw new VariableAlreadyDefinedException();
        }
        symbolTable.put(variableName, type.getDefaultValue());
    }

    @Override
    public Type getVariableType(String variableName) {
        if(!isDefined(variableName)) {
            throw new VariableNotDeclaredException();
        }
        return symbolTable.getValue(variableName).getType();
    }

    @Override
    public String toLogString() {
        final String[] ret = {""};
        symbolTable.getContent().forEach((k, v) -> {
            ret[0] += k + " -> " + v.toString();
            ret[0] += "\n";
        });
        return ret[0];
    }

    @Override
    public Value getValue(String variableName) {
        if(!isDefined(variableName)) {
            throw new VariableNotDeclaredException();
        }
        return symbolTable.getValue(variableName);
    }

    @Override
    public void put(String key, Value value) {
        if(!isDefined(key)) {
            throw new VariableNotDeclaredException();
        }
        symbolTable.put(key, value);
    }

    @Override
    public void setContent(Map<String, Value> newContent) {
        symbolTable.setContent(newContent);
    }

    @Override
    public Map<String, Value> getContent() {
        return symbolTable.getContent();
    }

    @Override
    public void removeKey(String key) {
        symbolTable.removeKey(key);
    }

    @Override
    public SymbolTable deepCopy() {
        MapSymbolTable newTable = new MapSymbolTable();

        symbolTable.getContent().forEach((k, v) -> {
            newTable.symbolTable.put(k, v.deepCopy());
        });

        return newTable;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append('{');

        symbolTable.getContent().forEach((k, v) -> {
            sb.append('[');
            sb.append(k.toCharArray());
            sb.append(':');
            sb.append(v.toString().toCharArray());
            sb.append(']');
            sb.append(',');
        });

        // sb.deleteCharAt(sb.length() - 1);

        sb.append('}');
        return sb.toString();
    }
}
