package com.tuneup;

import java.util.Scanner;

public class ComposeArea {
    private String composeID;
    private UserProfile userProfile;
    private Mode mode;
    private Scanner scanner;
    private InstrumentStrategy instrument;

    public ComposeArea(UserProfile userProfile) {
        this.userProfile = userProfile;
        scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        if (instrument == null) {
            selectInstrument();
        }

        boolean inComposeArea = true;
        while (inComposeArea) {
            System.out.println("\nCompose Area Menu");
            System.out.println("1. Create Mode");
            System.out.println("2. Play Mode");
            System.out.println("3. Change Instrument");
            System.out.println("4. Return to Main Menu");
            System.out.print("Please select a mode (1-4): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Entering Create Mode...");
                    mode = new CreateMode(userProfile, instrument);
                    mode.handle();
                    break;
                case 2:
                    System.out.println("Entering Play Mode...");
                    mode = new PlayMode(userProfile, instrument);
                    mode.handle();
                    break;
                case 3:
                    selectInstrument();
                    break;
                case 4:
                    inComposeArea = false;
                    break;
                default:
                    System.out.println("Invalid option selected.");
                    break;
            }
        }
    }

    private void selectInstrument() {
        System.out.println("\nSelect an Instrument:");
        System.out.println("1. Piano");
        System.out.println("2. Guitar");
        System.out.print("Choose your instrument (1-2): ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                instrument = InstrumentFactory.createInstrument(InstrumentType.PIANO);
                System.out.println("Piano selected");
                break;
            case 2:
                instrument = InstrumentFactory.createInstrument(InstrumentType.GUITAR);
                System.out.println("Guitar selected");
                break;
            default:
                System.out.println("Invalid selection. Defaulting to Piano.");
                instrument = InstrumentFactory.createInstrument(InstrumentType.PIANO);
        }
    }
}