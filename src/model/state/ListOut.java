package model.state;

import model.value.Value;

import java.util.ArrayList;
import java.util.List;

public class ListOut implements Out {
    private final List<Value> values = new ArrayList<>();

    @Override
    public synchronized void append(Value value) {
        values.add(value);
    }

    @Override
    public synchronized String toLogString() {
        StringBuilder ret = new StringBuilder();
        for(Value v : values) {
            ret.append(v.toString()).append("\n");
        }
        return ret.toString();
    }

    @Override
    public synchronized ListOut deepCopy() {
        ListOut newOut = new ListOut();
        for(Value value : values) newOut.append(value.deepCopy());
        return newOut;
    }

    @Override
    public synchronized String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append('[');
        for(var val : values) {
            sb.append(val.toString().toCharArray());
            sb.append(',');
        }
        // sb.deleteCharAt(sb.length() - 1);

        sb.append(']');
        return sb.toString();
    }
}
