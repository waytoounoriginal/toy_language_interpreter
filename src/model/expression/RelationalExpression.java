package model.expression;

import model.IntType;
import model.exceptions.InvalidArithmeticExpception;
import model.state.MapHeap;
import model.state.SymbolTable;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public class RelationalExpression implements Expression {
    private final Expression exp1;
    private final Expression exp2;
    private final String operator;

    public RelationalExpression(Expression exp1, Expression exp2, String operator) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operator = operator;
    }

    @Override
    public Value evaluate(SymbolTable symbolTable, MapHeap<Integer, Value> heap) {
        Value v1 = exp1.evaluate(symbolTable, heap);
        if (v1.getType() instanceof IntType) {
            Value v2 = exp2.evaluate(symbolTable, heap);
            if (v2.getType() instanceof IntType) {
                IntValue val1 = (IntValue) v1;
                IntValue val2 = (IntValue) v2;

                int n1 = (int)val1.getValue();
                int n2 = (int)val2.getValue();

                switch (operator) {
                    case "<=":
                        return new BoolValue(n1 <= n2);
                    case ">=":
                        return new BoolValue(n1 >= n2);
                    case "==":
                        return new BoolValue(n1 == n2);
                    case "!=":
                        return new BoolValue(n1 != n2);
                    case "<":
                        return new BoolValue(n1 < n2);
                    case ">":
                        return new BoolValue(n1 > n2);
                    default:
                        throw new InvalidArithmeticExpception();
                }
            }
        }
        throw new InvalidArithmeticExpception("Operands are not Integers");
    }

    @Override
    public Expression deepCopy() {
        return new RelationalExpression(exp1.deepCopy(), exp2.deepCopy(), operator);
    }

    @Override
    public String toString() {
        return exp1.toString() + " " + operator + " " + exp2.toString();
    }
}