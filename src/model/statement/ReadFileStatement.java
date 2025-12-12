package model.statement;

import model.exceptions.InvalidValueException;
import model.exceptions.VariableNotDeclaredException;
import model.expression.Expression;
import model.state.ProgramState;
import model.value.IntValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public record ReadFileStatement(Expression exp, String varName) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        var val = exp.evaluate(state.getSymbolTable(), state.getHeap());
        var symbolTable = state.getSymbolTable();

        if(!( val instanceof StringValue(String filePath))) {
            throw new InvalidValueException();
        }

        if(!(symbolTable.isDefined(varName))) {
            throw new VariableNotDeclaredException();
        }

        try {
            BufferedReader reader = state.getFileTable().getFile(filePath);
            var line = reader.readLine();
            int readValue = line == null ? 0 : Integer.parseInt(line);
            IntValue intValue = new IntValue(readValue);
            state.getSymbolTable().put(varName, intValue);
        } catch(IOException e) {
            throw new InvalidValueException();
        }

        return null;
    }

    @Override
    public String toString() {
        return "[ READ LINE (%s) INTO %s ]".formatted(exp.toString(), varName);
    }

    @Override
    public Statement deepCopy() {
        return null;
    }
}
