package view;

import controller.Controller;
import model.exceptions.EmptyExecutionStackException;

import java.util.Scanner;

public class RunExample extends Command {
    private final Controller controller;

    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {

        try {
            System.out.println("Running " + getDescription() + "...");
            controller.allSteps();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nExecution stopped: " + e.getMessage());
        }

        controller.reset();
    }

}