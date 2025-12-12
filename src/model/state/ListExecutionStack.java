package model.state;

import model.exceptions.EmptyExecutionStackException;
import model.statement.Statement;

import java.util.LinkedList;
import java.util.List;

public class ListExecutionStack implements ExecutionStack {
    private final List<Statement> statements = new LinkedList<>();

    @Override
    public Statement top() {
        if(statements.isEmpty()) return null;
        return statements.getFirst();
    }

    @Override
    public Statement pop() {
        if(statements.isEmpty()) {
            throw new EmptyExecutionStackException();
        }
        return statements.removeFirst();
    }

    @Override
    public void push(Statement statement) {
        statements.addFirst(statement);
    }

    @Override
    public boolean isEmpty() {
        return statements.isEmpty();
    }

    @Override
    public String toLogString() {
        StringBuilder res = new StringBuilder();
        for (Statement s : statements) {
            res.append(s.toString());
            res.append("\n");
        }
        return res.toString();
    }

    @Override
    public ListExecutionStack deepCopy() {
        ListExecutionStack stack = new ListExecutionStack();
        for(Statement stmt : statements) {
            stack.push(stmt.deepCopy());
        }
        return stack;
    }
}
