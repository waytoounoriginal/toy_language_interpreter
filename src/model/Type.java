package model;

import model.value.Value;

public interface Type {
    boolean equals(Object another);

    String toString();

    Value getDefaultValue();
}