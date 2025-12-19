package model.statement;

import model.RefType;
import model.StringType;
import model.Type;
import model.exceptions.InvalidArithmeticExpception;
import model.exceptions.InvalidValueException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.structures.m_HashMap;
import model.value.StringValue;

public record OpenRFileStatement(Expression exp) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        var val = exp.evaluate(state.getSymbolTable(), state.getHeap());

        if(!( val instanceof StringValue(String filePath))) {
            throw new InvalidValueException();
        }

        state.getFileTable().openFile(filePath);
        return null;
    }

    @Override
    public String toString() {
        return "[ OPEN: %s ]".formatted(exp.toString());
    }

    @Override
    public Statement deepCopy() {
        return new OpenRFileStatement(exp.deepCopy());
    }

    @Override
    public m_HashMap<String, Type> typeCheck(m_HashMap<String, Type> typeEnv) {
        Type expType = exp.typeCheck(typeEnv);
        if(expType instanceof StringType) {
            return typeEnv;
        }

        throw new InvalidArithmeticExpception("FILE OPEN statement: RHS not a string");
    }
}
