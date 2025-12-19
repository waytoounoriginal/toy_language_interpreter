package model.statement;

import model.Type;
import model.state.ProgramState;
import model.state.structures.m_HashMap;

public interface Statement {
    ProgramState execute(ProgramState state);
    Statement deepCopy();
    m_HashMap<String, Type> typeCheck(m_HashMap<String, Type> typeEnv);
}
