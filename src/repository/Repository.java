package repository;


import model.state.ProgramState;

import java.io.IOException;
import java.util.List;

public interface Repository {
    void setMainProgramState(ProgramState state);
    void logProgramState(String fileName, ProgramState state) throws IOException;

    List<ProgramState> getProgramStatesList();
    void setProgramStatesList(List<ProgramState> list);

    Repository deepCopy();
}
