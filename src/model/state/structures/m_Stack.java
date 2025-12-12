package model.state.structures;

public interface m_Stack<T> {
    T top();
    T pop();
    void push(T statement);
    boolean isEmpty();
}
