package model.statement;

import model.RefType;
import model.Type;
import model.exceptions.HeapException;
import model.exceptions.InvalidArithmeticExpception;
import model.exceptions.VariableNotDeclaredException;
import model.expression.Expression;
import model.state.MapHeap;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.state.structures.m_HashMap;
import model.value.RefValue;
import model.value.Value;

public record HeapAllocationStatement(String variableName, Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        SymbolTable symbolTable = state.getSymbolTable();
        MapHeap<Integer, Value> heap = state.getHeap();

        if(!symbolTable.isDefined(variableName) && !(symbolTable.getVariableType(variableName) instanceof RefType)) {
            throw new HeapException("Variable " + variableName + " not a declared RefType");
        }

        Value result = expression.evaluate(symbolTable, heap);
        RefValue originalValue = (RefValue)symbolTable.getValue(variableName); // this should work by default

        if(!result.getType().equals(originalValue.getLocationType())) {
            throw new HeapException("Incompatible types given");
        }

        Integer newAddress = heap.allocate();

        // Allocate in the heap
        heap.put(newAddress, result);

        // Update the refValue
        RefValue newRef = new RefValue(newAddress, originalValue.getLocationType());
        symbolTable.put(variableName, newRef);


        return null;
    }

    @Override
    public Statement deepCopy() {
        return new HeapAllocationStatement(
                variableName, expression.deepCopy()
        );
    }

    @Override
    public m_HashMap<String, Type> typeCheck(m_HashMap<String, Type> typeEnv) {
        Type typeVar = typeEnv.getValue(variableName);
        Type typeExpr = expression.typeCheck(typeEnv);
        if(typeVar.equals(new RefType(typeExpr))) {
            return typeEnv;
        }

        throw new InvalidArithmeticExpception("NEW statement: THS and LHS have different types");
    }

    @Override
    public String toString() {
        return "NEW ( " + variableName + ", " + expression.toString() + " )";
    }
}
