package model.value;

import model.StringType;
import model.Type;

public record StringValue(String val) implements Value {
    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Object getValue() {
        return val;
    }

    @Override
    public Value deepCopy() {
        return new StringValue(val);
    }
}
