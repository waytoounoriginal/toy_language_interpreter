package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class TextMenu {
    private final Map<String, Command> commands;

    public TextMenu() {
        this.commands = new HashMap<>();
    }

    public void addCommand(Command c) {
        commands.put(c.getKey(), c);
    }

    private void printMenu() {
        System.out.println("\n--- TEXT MENU ---");
        // Printing in order of keys if possible, or just iterating values
        commands.values()
                .forEach(com -> {
                    String line = String.format("%4s : %s", com.getKey(), com.getDescription());
                    System.out.println(line);
                });
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Input the option: ");
            String key = scanner.nextLine().trim();
            Command com = commands.get(key);
            if (com == null) {
                System.out.println("Invalid Option");
                continue;
            }
            com.execute();
        }
    }
}