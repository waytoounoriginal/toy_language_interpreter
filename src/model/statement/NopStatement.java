package model.statement;

import model.Type;
import model.state.ProgramState;
import model.state.structures.m_HashMap;

public class NopStatement implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new NopStatement();
    }

    @Override
    public m_HashMap<String, Type> typeCheck(m_HashMap<String, Type> typeEnv) {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "[ NOP ]";
    }
}
