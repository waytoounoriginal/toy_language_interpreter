package model.state;

import model.exceptions.FileAlreadyOpenedException;
import model.exceptions.FileNotFoundToyError;
import model.state.structures.m_HashMap;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileTable implements Cloneable {
    final m_HashMap<String, BufferedReader> fileDescriptors = new MyHashMap<>();

    public synchronized void openFile(String filePath) {
        if(fileDescriptors.isDefined(filePath)) {
            throw new FileAlreadyOpenedException();
        }

        try {
            var fileDescriptor = new BufferedReader(new FileReader(filePath));
            fileDescriptors.put(filePath, fileDescriptor);
        } catch(FileNotFoundException e) {
            throw new FileNotFoundToyError();
        }
    }

    public synchronized BufferedReader getFile(String filePath) {
        if(!fileDescriptors.isDefined(filePath)) {
            throw new FileNotFoundToyError();
        }

        return fileDescriptors.getValue(filePath);
    }

    public synchronized void closeFile(String filePath) {
        if(!fileDescriptors.isDefined(filePath)) {
            throw new FileNotFoundToyError();
        }

        try {
            fileDescriptors.getValue(filePath).close();
            fileDescriptors.removeKey(filePath);
        } catch( Exception e) {
            throw new IOError(e);
        }
    }

    public synchronized String toLogString() {
        final String[] ret = {""};
        fileDescriptors.getContent().forEach((k, v) -> {
            ret[0] += k + "\n";
        });
        return ret[0];
    }

    public synchronized FileTable deepCopy() throws CloneNotSupportedException {
        return (FileTable)super.clone();
    }
}
