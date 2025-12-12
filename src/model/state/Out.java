package model.state;

import model.value.Value;

public interface Out {
    void append(Value value);

    String toLogString();

    Out deepCopy();
}
