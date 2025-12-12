package model.state;

import model.state.structures.m_Stack;
import model.statement.Statement;

import java.util.Stack;

public interface ExecutionStack extends m_Stack<Statement> {
    String toLogString();

    ExecutionStack deepCopy();
}
