package model.statement;

import model.state.ProgramState;

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
    public String toString() {
        return "[ NOP ]";
    }
}
