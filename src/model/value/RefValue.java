package model.value;

import model.RefType;
import model.Type;

public class RefValue implements Value {
    Integer address;
    Type locationType;

    public RefValue(Integer address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public Integer getAddress() {
        return address;
    }

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public String toString() {
        return "Ref: ( %d, %s )".formatted(
                address,
                locationType.toString()
        );
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public Object getValue() {
        return address;
    }

    @Override
    public Value deepCopy() {
        return new RefValue(address, locationType);
    }
}
