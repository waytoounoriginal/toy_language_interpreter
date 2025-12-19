package model.expression;

import model.BoolType;
import model.Type;
import model.exceptions.InvalidArithmeticExpception;
import model.state.MapHeap;
import model.state.SymbolTable;
import model.state.structures.m_HashMap;
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
    public Type typeCheck(m_HashMap<String, Type> typeEnv) {
        Type type1, type2;

        type1 = left.typeCheck(typeEnv);
        type2 = right.typeCheck(typeEnv);

        if(type1 instanceof BoolType) {
            if(type2 instanceof BoolType) {
                return new BoolType();
            }
            throw new InvalidArithmeticExpception("Second operand is not a bool value");
        }
        throw new InvalidArithmeticExpception("First operand is not a bool value");
    }

    @Override
    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }
}
