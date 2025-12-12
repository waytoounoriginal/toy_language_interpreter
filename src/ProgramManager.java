import model.BoolType;
import model.IntType;
import model.RefType;
import model.Type;
import model.expression.*;
import model.statement.*;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;

import java.util.ArrayList;
import java.util.List;

public class ProgramManager {

    public static class Example {
        private final String description;
        private final Statement root;

        public Example(String description, Statement root) {
            this.description = description;
            this.root = root;
        }

        public String getDescription() { return description; }
        public Statement getRoot() { return root; }
    }

    // Helper to build compound statements easily
    private static Statement makeCompound(Statement... statements) {
        if (statements.length == 0) return new NopStatement();
        if (statements.length == 1) return statements[0];

        Statement result = statements[statements.length - 1];
        for (int i = statements.length - 2; i >= 0; i--) {
            result = new CompoundStatement(statements[i], result);
        }
        return result;
    }

    public static List<Example> getExamples() {
        List<Example> examples = new ArrayList<>();

        // --- Program 1 ---
        examples.add(new Example(
                "Program 1",
                makeCompound(
                        new VariableDeclarationStatement(new IntType(), "v"),
                        new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))
                )
        ));

        // --- Program 2 ---
        examples.add(new Example(
                "Program 2",
                makeCompound(
                        new VariableDeclarationStatement(new IntType(), "a"),
                        new AssignmentStatement("a", new ArithmeticExpression(
                                new ValueExpression(new IntValue(2)),
                                new ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), '*'),
                                '+'
                        )),
                        new VariableDeclarationStatement(new IntType(), "b"),
                        new AssignmentStatement("b", new ArithmeticExpression(
                                new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(7)), '+'),
                                new ArithmeticExpression(new ValueExpression(new IntValue(4)), new ValueExpression(new IntValue(2)), '/'),
                                '-'
                        )),
                        new PrintStatement(new VariableExpression("b"))
                )
        ));

        // --- Program 3 ---
        examples.add(new Example(
                "Program 3",
                makeCompound(
                        new VariableDeclarationStatement(new BoolType(), "a"),
                        new AssignmentStatement("a", new ValueExpression(new BoolValue(false))),
                        new VariableDeclarationStatement(new IntType(), "v"),
                        new IfStatement(
                                new VariableExpression("a"),
                                new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                new AssignmentStatement("v", new ValueExpression(new IntValue(3)))
                        ),
                        new PrintStatement(new VariableExpression("v"))
                )
        ));

        // --- Program 4 ---
        examples.add(new Example(
                "Program 4",
                makeCompound(
                        new VariableDeclarationStatement(new IntType(), "v"),
                        new OpenRFileStatement(new ValueExpression(new StringValue("test_file.txt"))),
                        new ReadFileStatement(new ValueExpression(new StringValue("test_file.txt")), "v"),
                        new VariableDeclarationStatement(new IntType(), "a"),
                        new ReadFileStatement(new ValueExpression(new StringValue("test_file.txt")), "a"),
                        new PrintStatement(new ArithmeticExpression(new VariableExpression("v"), new VariableExpression("a"), '+')),
                        new CloseRFileStatement(new ValueExpression(new StringValue("test_file.txt")))
                )
        ));

        // ---- Test Heap allocation
        examples.add(new Example(
                "Allocation expression test",
                makeCompound(
                        new VariableDeclarationStatement(new RefType(new IntType()), "v"),
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new VariableDeclarationStatement(
                                new RefType(new RefType(new IntType())), "w"
                        ),
                        new HeapAllocationStatement(
                                "w", new VariableExpression("v")
                        ),
                        new PrintStatement(new VariableExpression("v")),
                        new PrintStatement(new VariableExpression("w"))
                )
        ));

        // --- Test Heap reading
        examples.add(new Example(
                "Heap reading test",
                makeCompound(
                        new VariableDeclarationStatement(new RefType(new IntType()), "v"),
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new VariableDeclarationStatement(
                                new RefType(new RefType(new IntType())), "w"
                        ),
                        new HeapAllocationStatement(
                                "w", new VariableExpression("v")
                        ),
                        new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                        new PrintStatement(
                                new HeapReadingExpression(
                                        new HeapReadingExpression(new VariableExpression("w"))
                                )
                        )
                )
        ));

        // --- Test heap writing
        examples.add(new Example(
                "Heap writing test",
                makeCompound(
                        new VariableDeclarationStatement(new RefType(new IntType()), "v"),
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                        new HeapWriteStatement("v", new ValueExpression(new IntValue(30))),
                        new PrintStatement(new HeapReadingExpression(new VariableExpression("v")))
                )
        ));

        // --- Test the garbage collector
        examples.add(new Example(
                "Garbage collector test",
                makeCompound(
                        new VariableDeclarationStatement(new RefType(new IntType()), "v"),
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new VariableDeclarationStatement(
                                new RefType(new RefType(new IntType())), "w"
                        ),
                        new HeapAllocationStatement("w", new VariableExpression("v")),
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                        new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("w"))))
                )
        ));

        // --- More garbage collector tests
        examples.add(new Example(
                "Garbage collector test v2",
                makeCompound(
                        new VariableDeclarationStatement(new RefType(new IntType()), "v"),
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(30)))
                )
        ));

        // --- More garbage collector tests
        examples.add(new Example(
                "Garbage collector test v3",
                makeCompound(
                        new VariableDeclarationStatement(new RefType(new IntType()), "v"),
                        new VariableDeclarationStatement(new RefType(new RefType(new IntType())), "double_ref"),
                        new VariableDeclarationStatement(new RefType(new RefType(new RefType(new IntType()))), "triple_ref"),
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new HeapAllocationStatement("double_ref", new VariableExpression("v")),
                        new HeapAllocationStatement("triple_ref", new VariableExpression("double_ref")),
                        new PrintStatement(
                                new HeapReadingExpression(new VariableExpression("triple_ref"))
                        ),
                        new PrintStatement(
                                new HeapReadingExpression(
                                        new HeapReadingExpression(
                                                new HeapReadingExpression(
                                                        new VariableExpression("triple_ref")
                                                )
                                        )
                                )
                        )
                )
        ));

        // --- While test
        examples.add(new Example(
                "While test",
                makeCompound(
                        new VariableDeclarationStatement(new IntType(), "v"),
                        new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                        new WhileStatement(
                                new RelationalExpression(
                                        new VariableExpression("v"),
                                        new ValueExpression(new IntValue(0)),
                                        ">"
                                ),
                                makeCompound(
                                        new PrintStatement(new VariableExpression("v")),
                                        new AssignmentStatement(
                                                "v",
                                                new ArithmeticExpression(
                                                        new VariableExpression("v" ),
                                                        new ValueExpression(new IntValue(1)),
                                                        '-'
                                                )
                                        )
                                )
                        )
                )
        ));

        // --- Fork test
        examples.add(new Example(
                "Fork test",
                makeCompound(
                        new VariableDeclarationStatement(new IntType(), "v"),
                        new VariableDeclarationStatement(new RefType(new IntType()), "a"),
                        new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                        new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))),
                        new ForkStatement(
                                makeCompound(
                                        new HeapWriteStatement("a", new ValueExpression(new IntValue(30))),
                                        new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                        new PrintStatement(new VariableExpression("v")),
                                        new PrintStatement(
                                                new HeapReadingExpression(
                                                        new VariableExpression("a")
                                                )
                                        )
                                )
                        ),
                        new PrintStatement(new VariableExpression("v")),
                        new PrintStatement(new HeapReadingExpression(new VariableExpression("a")))
                )
        ));

        // ---- Multithreaded garbage collector test
        examples.add(new Example(
                "Multithreaded garbage collector test",
                makeCompound(
                        new VariableDeclarationStatement(new RefType(new IntType()), "a"),
                        new HeapAllocationStatement(
                                "a", new ValueExpression(new IntValue(10))
                        ),
                        new ForkStatement(
                                makeCompound(
                                        new VariableDeclarationStatement(new RefType(new IntType()), "a2"),
                                        new HeapAllocationStatement(
                                                "a", new ValueExpression(new IntValue(20))
                                        ),
                                        new HeapAllocationStatement(
                                                "a2", new ValueExpression(new IntValue(30))
                                        ),
                                        new ForkStatement(
                                                makeCompound(
                                                        new VariableDeclarationStatement(new RefType(new IntType()), "a3"),
                                                        new HeapAllocationStatement(
                                                                "a", new ValueExpression(new IntValue(40))
                                                        ),
                                                        new HeapAllocationStatement(
                                                                "a2", new ValueExpression(new IntValue(50))
                                                        ),
                                                        new HeapAllocationStatement(
                                                                "a3", new ValueExpression(new IntValue(60))
                                                        )
                                                )
                                        )
                                )
                        ),
                        new HeapAllocationStatement("a", new ValueExpression(new IntValue(-10)))
                )
        ));

        return examples;
    }
}