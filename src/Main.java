import controller.Controller;
import model.state.*;
import model.statement.Statement;
import repository.MultithreadedRepository;
import repository.Repository;
import repository.SingleProgramRepository;
import view.ExitCommand;
import view.RunExample;
import view.TextMenu;

import java.util.List;

public class Main {

    private static Controller createController(String programName, Statement programRoot) {
        ListExecutionStack exeStack = new ListExecutionStack();
        MapSymbolTable symTable = new MapSymbolTable();
        ListOut out = new ListOut();
        FileTable fileTable = new FileTable();
        MapHeap heap = new MapHeap();

        exeStack.push(programRoot);

        ProgramState state = new ProgramState(exeStack, symTable, out, fileTable, heap);
        Repository repository = new MultithreadedRepository();
        repository.setMainProgramState(state);

        return new Controller(repository, programName);
    }

    public static void main(String[] args) {
        TextMenu menu = new TextMenu();

        menu.addCommand(new ExitCommand("0", "Exit"));

        List<ProgramManager.Example> examples = ProgramManager.getExamples();
        for (int i = 0; i < examples.size(); i++) {
            ProgramManager.Example example = examples.get(i);
            String key = String.valueOf(i + 1);

            Controller controller = createController(examples.get(i).getDescription() ,example.getRoot());
            menu.addCommand(new RunExample(key, example.getDescription(), controller));
        }

        menu.show();
    }
}