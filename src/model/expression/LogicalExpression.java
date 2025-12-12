package model.expression;

import model.exceptions.InvalidArithmeticExpception;
import model.state.MapHeap;
import model.state.SymbolTable;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public record LogicalExpression(Expression left, Expression right, String operator) implements Expression {

    @Override
    public Value evaluate(SymbolTable symbolTable, MapHeap<Integer, Value> heap) {
        Value leftValue = left.evaluate(symbolTable, heap);
        Value rightValue = right.evaluate(symbolTable, heap);

        if(!(leftValue instanceof BoolValue(Boolean leftBool)) || !(rightValue instanceof BoolValue(Boolean rightBool))) {
            throw new InvalidArithmeticExpception();
        }

        return switch(operator) {
            case "&&" -> new BoolValue(leftBool && rightBool);
            case "||" -> new BoolValue(leftBool || rightBool);
            default -> throw new InvalidArithmeticExpception();
        };
    }

    @Override
    public Expression deepCopy() {
        return new LogicalExpression(left.deepCopy(), right.deepCopy(), operator);
    }

    @Override
    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }
}
