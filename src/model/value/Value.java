package model.value;

import model.Type;

public interface Value {
    Type getType();
    Object getValue();
    Value deepCopy();
}
