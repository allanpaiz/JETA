package com.tuneup;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateMode implements Mode {
    private User userProfile;
    private InstrumentStrategy instrument;
    private Scanner scanner;
    private List<String> composition;

    public CreateMode(User userProfile, InstrumentStrategy instrument) {
        this.userProfile = userProfile;
        this.instrument = instrument;
        this.scanner = new Scanner(System.in);
        this.composition = new ArrayList<>();
    }

    @Override
    public void handle() {
        boolean inCreateMode = true;
        while (inCreateMode) {
            System.out.println("\nCreate Mode");
            System.out.println("1. Add Note to Composition");
            System.out.println("2. View Composition");
            System.out.println("3. Save Composition");
            System.out.println("4. Return to Main Menu");
            System.out.print("Please select an option (1-4): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addNoteToComposition();
                    break;
                case 2:
                    viewComposition();
                    break;
                case 3:
                    saveComposition();
                    break;
                case 4:
                    inCreateMode = false;
                    break;
                default:
                    System.out.println("Invalid option selected.");
                    break;
            }
        }
    }

    private void addNoteToComposition() {
        System.out.print("Enter note to add to composition (C, D, E, F, G, A, B, HIGH C): ");
        String note = scanner.nextLine().toUpperCase();
        composition.add(note);
        System.out.println("Note added to composition.");
    }

    private void viewComposition() {
        if (composition.isEmpty()) {
            System.out.println("The composition is empty.");
        } else {
            System.out.println("Current Composition:");
            for (int i = 0; i < composition.size(); i++) {
                System.out.println((i + 1) + ". " + composition.get(i));
            }
        }
    }

    private void saveComposition() {
        // Implement logic to save the composition
        System.out.println("Composition saved.");
    }
}