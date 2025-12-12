package repository;

import jdk.jshell.spi.ExecutionControl;
import model.state.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class SingleProgramRepository implements Repository {
    private ProgramState programState;

    @Override
    public void setMainProgramState(ProgramState state) {
        programState = state;
    }

    @Override
    public void logProgramState(String fileName, ProgramState state) throws IOException {
        PrintWriter printWriter = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(fileName, true)));

        printWriter.print(state.toLogString());
        printWriter.close();
    }

    @Override
    public List<ProgramState> getProgramStatesList() {
        return new ArrayList<>();
    }

    @Override
    public void setProgramStatesList(List<ProgramState> list) {
        throw new RuntimeException("Method not implemented for single-threaded repository");
    }

    @Override
    public Repository deepCopy() {
        Repository copy = new SingleProgramRepository();
        return copy;
    }

    // For FileTable implementation:
    // Map<String, BufferedReader>
}
