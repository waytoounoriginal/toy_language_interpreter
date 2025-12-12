package model.statement;

import model.exceptions.InvalidValueException;
import model.expression.Expression;
import model.state.ProgramState;
import model.value.StringValue;

public record CloseRFileStatement(Expression exp) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        var val = exp.evaluate(state.getSymbolTable(), state.getHeap());

        if(!( val instanceof StringValue(String filePath))) {
            throw new InvalidValueException();
        }

        state.getFileTable().closeFile(filePath);
        return null;
    }

    @Override
    public String toString() {
        return "[ CLOSE FILE: " + exp.toString() + " ]";
    }

    @Override
    public Statement deepCopy() {
        return new CloseRFileStatement(exp.deepCopy());
    }
}
