package controllers;

import java.util.List;

import tuneup.DataLoader;
import tuneup.PianoStrategy;
import tuneup.Song;
import tuneup.SongLibrary;
import tuneup.SongLibraryMode;
import tuneup.TuneUp;
import tuneup.User;

public class SongLibraryController {
    private TuneUp facade;
    
    public SongLibraryController(TuneUp facade) {
        this.facade = facade;
    }

    public TuneUp getFacade() {
        return facade;
    }

    public void playSong(Song song) {
        System.out.println("Playing song: " + song.getTitle());

        List<String> notes = song.getNotes();
        PianoStrategy piano = new PianoStrategy();
        
        try {
            for (String note : notes) {
                piano.playNote(note);
                sleepWrapper(200);
                piano.stop();
                sleepWrapper(200);
            }
        } finally {
            piano.close();
        }
        System.out.println();
    }

    public void printSongToFile(Song song) {
        try {
            List<String> notes = song.getNotes();
            String timeSignature = song.getTimeSignature();
            int beatsPerMeasure = Integer.parseInt(timeSignature.split("/")[0]);

            java.io.FileWriter fileWriter = new java.io.FileWriter("./songs/" + song.getTitle() + ".txt");
            java.io.PrintWriter printWriter = new java.io.PrintWriter(fileWriter);

            printWriter.println(song.getTitle());
            printWriter.println("by " + song.getArtistName());
            printWriter.println();
            printWriter.println("Tempo: " + song.getTempo());
            printWriter.println("Time Signature: " + timeSignature);
            printWriter.println();
            printWriter.println(song.tempoNotation());

            for (int i = 0; i < notes.size(); i += beatsPerMeasure) {
                printWriter.print("Measure " + (i / beatsPerMeasure + 1) + ": ");
                for (int j = 0; j < beatsPerMeasure; j++) {
                    if (i + j < notes.size()) {
                        printWriter.print(notes.get(i + j) + " ");
                    }
                }
                printWriter.println();
            }

            printWriter.println();
            String creatorUsername = getUsernameById(song.getCreatorId());
            printWriter.println("Uploaded by: " + creatorUsername);
            printWriter.close();
        } catch (java.io.IOException e) {
            System.out.println("Error writing to file");
        }    
    }
    
    public List<Song> getSongs(String searchString) {
        List<Song> songs = SongLibrary.getSongLibrary();
        
        if (searchString == "-ALL-") {
            return songs;
        } else if ( searchString == null || searchString.isEmpty() ) {
            return songs;
        }

        songs.removeIf(song -> 
            !song.getTitle().toLowerCase().contains(searchString.toLowerCase()) && 
            !song.getArtistName().toLowerCase().contains(searchString.toLowerCase())
        );

        return songs;
    }

    public String getUsernameById(String userId) {

        List<User> users = DataLoader.loadUsers();
        for (User user : users) {
            if (userId.equals(user.getId())) {
                return user.getUsername();
            }
        }
        
        return "Unknown";
    }


    private void sleepWrapper(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
