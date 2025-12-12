package model.expression;

import model.Type;
import model.exceptions.InvalidArithmeticExpception;
import model.state.MapHeap;
import model.state.SymbolTable;
import model.value.IntValue;
import model.value.Value;

import java.util.Arrays;

public record ArithmeticExpression(Expression left, Expression right, char operator) implements Expression{
    @Override
    public Value evaluate(SymbolTable symbolTable, MapHeap<Integer, Value> heap) {
        Value leftValue = left.evaluate(symbolTable, heap);
        Value rightValue = right.evaluate(symbolTable, heap);

        if(!(leftValue instanceof IntValue(Integer leftInt)) || !(rightValue instanceof IntValue(Integer rightInt))) {
            throw new InvalidArithmeticExpception();
        }

        return switch(operator) {
            case '+' -> new IntValue(leftInt + rightInt);
            case '-' -> new IntValue(leftInt - rightInt);
            case '*' -> new IntValue(leftInt * rightInt);
            case '/' -> {
                if(rightInt == 0) {
                    throw new ArithmeticException("Divide by 0");
                }
                yield new IntValue(leftInt/rightInt);
            }
            default -> throw new InvalidArithmeticExpception();
        };
    }

    @Override
    public Expression deepCopy() {
        return new ArithmeticExpression(left.deepCopy(), right.deepCopy(), operator);
    }

    @Override
    public String toString() {
        return left.toString() + operator + right.toString();
    }
}
