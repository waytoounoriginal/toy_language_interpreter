package model.statement;

import model.Type;
import model.exceptions.InvalidValueException;
import model.exceptions.ToyException;
import model.expression.Expression;
import model.state.ExecutionStack;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public record IfStatement(Expression conditional, Statement ifStmt, Statement elseStmt) implements Statement {


    @Override
    public ProgramState execute(ProgramState state) {
        var symbolTable = state.getSymbolTable();
        ExecutionStack executionStack = state.getExecutionStack();

        var evaluation = conditional.evaluate(symbolTable, state.getHeap());

        boolean cond;

        if (evaluation instanceof BoolValue b) cond = (Boolean)b.getValue();
        else if (evaluation instanceof IntValue i) cond = (Integer)i.getValue() != 0;
        else throw new InvalidValueException();

        if (cond) executionStack.push(ifStmt);
        else executionStack.push(elseStmt);

        return null;
    }

    @Override
    public String toString() {
        return "[ IF: %s THEN %s | ELSE: %s ]".formatted(conditional.toString(), ifStmt.toString(), elseStmt.toString());
    }

    @Override
    public Statement deepCopy() {
        return new IfStatement(conditional.deepCopy(), ifStmt.deepCopy(), elseStmt.deepCopy());
    }
}
