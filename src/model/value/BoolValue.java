package model.value;

import model.BoolType;
import model.Type;

public record BoolValue(Boolean val) implements Value {

    public BoolValue(Boolean val) {
        this.val = val;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Object getValue() {
        return val;
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(val);
    }

    @Override
    public String toString() {
        return "%s %s".formatted(
                getType().toString(),
                getValue().toString()
        );
    }
}
