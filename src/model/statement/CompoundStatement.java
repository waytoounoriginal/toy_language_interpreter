package model.statement;

import model.Type;
import model.state.ExecutionStack;
import model.state.ProgramState;
import model.state.structures.m_HashMap;

public class CompoundStatement implements Statement {
    private final Statement stmt1;
    private final Statement stmt2;

    public CompoundStatement(Statement stmt1, Statement stmt2) {
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
    }

    @Override
    public String toString() {
        return "[ %s ; %s ]".formatted(stmt1.toString(), stmt2.toString());
    }

    @Override
    public ProgramState execute(ProgramState state) {
        ExecutionStack exeStack = state.getExecutionStack();

        // Push the statements
        exeStack.push(stmt2);
        exeStack.push(stmt1);

        return null;
    }

    @Override
    public Statement deepCopy() {
        return new CompoundStatement(stmt1.deepCopy(), stmt2.deepCopy());
    }

    @Override
    public m_HashMap<String, Type> typeCheck(m_HashMap<String, Type> typeEnv) {
        m_HashMap<String, Type> typeEnv1 = stmt1.typeCheck(typeEnv);
        m_HashMap<String, Type> typeEnv2 = stmt2.typeCheck(typeEnv1);
        return typeEnv2;
    }
}
