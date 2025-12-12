package model.statement;

import model.exceptions.InvalidValueException;
import model.expression.Expression;
import model.state.ProgramState;
import model.value.BoolValue;
import model.value.Value;

public record WhileStatement(Expression expr, Statement statement) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        // Evaluate the statement
        Value result = expr.evaluate(state.getSymbolTable(), state.getHeap());

        // Check if it is a boolean statement
        if(!(result instanceof BoolValue(Boolean boolResult))) {
            throw new InvalidValueException("Expected a boolean value");
        }

        if(boolResult) {
            state.getExecutionStack().push(deepCopy());
            state.getExecutionStack().push(statement);
        }

        return null;
    }

    @Override
    public Statement deepCopy() {
        return new WhileStatement(expr.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return "while( " + expr.toString() + ") { " + statement.toString() + " }";
    }
}
