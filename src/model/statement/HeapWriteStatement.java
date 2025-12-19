package model.statement;

import model.RefType;
import model.Type;
import model.exceptions.HeapException;
import model.exceptions.InvalidArithmeticExpception;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.structures.m_HashMap;
import model.value.RefValue;
import model.value.Value;

public record HeapWriteStatement(String varName, Expression expression) implements Statement{
    @Override
    public ProgramState execute(ProgramState state) {
        // Get the variable from the symbol table
        var symTable = state.getSymbolTable();

        Value resultValue = symTable.getValue(varName);
        if(!(resultValue instanceof RefValue)) {
            throw new HeapException("Value must be of type RefValue");
        }

        Type target = ((RefValue)resultValue).getLocationType();
        Integer address = ((RefValue)resultValue).getAddress();

        var expressionResult = expression.evaluate(symTable, state.getHeap());
        if(!target.equals(expressionResult.getType())) {
            throw new HeapException("Type mismatch");
        }

        state.getHeap().put(address, expressionResult);

        return null;
    }

    @Override
    public Statement deepCopy() {
        return new HeapWriteStatement(varName, expression.deepCopy());
    }

    @Override
    public m_HashMap<String, Type> typeCheck(m_HashMap<String, Type> typeEnv) {
        Type typeVar = typeEnv.getValue(varName);
        Type typeExpr = expression.typeCheck(typeEnv);
        if(typeVar.equals(new RefType(typeExpr))) {
            return typeEnv;
        }

        throw new InvalidArithmeticExpception("HEAP WRITE statement: THS and LHS have different types");
    }

    @Override
    public String toString() {
        return "wH( " + varName + ", " + expression.toString() + " )";
    }
}
