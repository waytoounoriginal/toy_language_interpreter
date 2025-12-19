package model.statement;

import model.Type;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.state.structures.m_HashMap;

public record VariableDeclarationStatement(Type type, String varName) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        SymbolTable symbolTable = state.getSymbolTable();
        symbolTable.declareVariable(type, varName);

        return null;
    }

    @Override
    public Statement deepCopy() {
        return new VariableDeclarationStatement(type, varName);
    }

    @Override
    public m_HashMap<String, Type> typeCheck(m_HashMap<String, Type> typeEnv) {
        typeEnv.put(varName, type);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "[ VAR DECLARATION: " + type.toString() + " | " + varName + " ]";
    }
}
