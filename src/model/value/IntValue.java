package model.value;

import model.IntType;
import model.Type;

public record IntValue(Integer val) implements Value {

    public IntValue(Integer val) {
        this.val = val;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Object getValue() {
        return val;
    }

    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }

    @Override
    public String toString() {
        return "%s %s".formatted(getType().toString(), getValue().toString());
    }
}
