package com.tuneup;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class CreateMode implements Mode {
    private UserProfile userProfile;
    private InstrumentStrategy instrument;
    private Scanner scanner;
    private List<List<String>> measures;
    private static final int NOTES_PER_MEASURE = 4;
    private int tempo; // Beats per minute
    private static final String SHEET_MUSIC_PATH = "sheetmusic/";

    public CreateMode(UserProfile userProfile, InstrumentStrategy instrument) {
        this.userProfile = userProfile;
        this.instrument = instrument;
        scanner = new Scanner(System.in);
        measures = new ArrayList<>();
        tempo = 120; // Default tempo: 120 BPM
    }

    @Override
    public void handle() {
        boolean composing = true;
        while (composing) {
            System.out.println("\nCreate Mode - Compose your music");
            System.out.println("Current tempo: " + tempo + " BPM");
            System.out.println("1. Add Measure");
            System.out.println("2. View Composition");
            System.out.println("3. Play Composition");
            System.out.println("4. Change Tempo");
            System.out.println("5. Save Composition");
            System.out.println("6. Return to Compose Area");
            System.out.print("Select an option (1-6): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addMeasure();
                    break;
                case 2:
                    viewComposition();
                    break;
                case 3:
                    playComposition();
                    break;
                case 4:
                    changeTempo();
                    break;
                case 5:
                    saveComposition();
                    break;
                case 6:
                    composing = false;
                    break;
                default:
                    System.out.println("Invalid option selected.");
            }
        }
    }

    private void addMeasure() {
        List<String> measure = new ArrayList<>();
        System.out.println("\nAdding new measure (4 notes)");
        System.out.println("Available notes: C, D, E, F, G, A, B, HIGH C");
        
        for (int i = 0; i < NOTES_PER_MEASURE; i++) {
            System.out.print("Enter note " + (i + 1) + ": ");
            String note = scanner.nextLine().toUpperCase();
            measure.add(note);
        }
        
        measures.add(measure);
        System.out.println("Measure added successfully!");
    }

    private void viewComposition() {
        if (measures.isEmpty()) {
            System.out.println("\nNo measures in composition yet.");
            return;
        }

        System.out.println("\nCurrent Composition:");
        System.out.println("Tempo: " + tempo + " BPM");
        for (int i = 0; i < measures.size(); i++) {
            System.out.print("Measure " + (i + 1) + ": ");
            System.out.println(String.join(" ", measures.get(i)));
        }
    }

    private void changeTempo() {
        System.out.println("\nCurrent tempo: " + tempo + " BPM");
        System.out.print("Enter new tempo (40-208 BPM): ");
        int newTempo = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (newTempo >= 40 && newTempo <= 208) {
            tempo = newTempo;
            System.out.println("Tempo updated to " + tempo + " BPM");
        } else {
            System.out.println("Invalid tempo. Please choose between 40 and 208 BPM.");
        }
    }

    private void playComposition() {
        if (measures.isEmpty()) {
            System.out.println("\nNo measures to play.");
            return;
        }

        System.out.println("\nPlaying composition at " + tempo + " BPM...");
        // Calculate milliseconds per beat
        int msPerBeat = 60000 / tempo;

        for (List<String> measure : measures) {
            for (String note : measure) {
                instrument.playNote(note);
                try {
                    Thread.sleep(msPerBeat); // Pause between notes based on tempo
                } catch (InterruptedException e) {
                    System.out.println("Playback interrupted.");
                }
            }
        }
    }

    private void saveComposition() {
        if (measures.isEmpty()) {
            System.out.println("\nNo measures to save.");
            return;
        }

        System.out.print("Enter composition name (without .pdf): ");
        String fileName = scanner.nextLine();
        fileName = fileName.replaceAll("[^a-zA-Z0-9]", "_") + ".pdf";

        File directory = new File(SHEET_MUSIC_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(SHEET_MUSIC_PATH + fileName));
            document.open();

            // Add title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph title = new Paragraph(fileName.replace(".pdf", ""), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            // Add tempo and composer
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);
            document.add(new Paragraph("Tempo: " + tempo + " BPM", normalFont));
            document.add(new Paragraph("Composer: " + userProfile.getUsername(), normalFont));
            document.add(new Paragraph("\n"));

            // Add measures
            for (int i = 0; i < measures.size(); i++) {
                String measureText = "Measure " + (i + 1) + ": " + String.join(" ", measures.get(i));
                document.add(new Paragraph(measureText, normalFont));
            }

            document.close();
            System.out.println("Composition saved as: " + fileName);

        } catch (Exception e) {
            System.out.println("Error saving composition: " + e.getMessage());
        }
    }
}