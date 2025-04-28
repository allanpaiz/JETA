package controllers;

import java.util.List;

import tuneup.DataLoader;
import tuneup.PianoStrategy;
import tuneup.Song;
import tuneup.SongLibrary;
import tuneup.SongLibraryMode;
import tuneup.TuneUp;
import tuneup.User;

/**
 * Controller class for Song Library
 *
 * @author allanpaiz
 */
public class SongLibraryController {
    private TuneUp facade;

    /**
     * Constructor
     *
     * @param facade TuneUp
     */
    public SongLibraryController(TuneUp facade) {
        this.facade = facade;
    }

    /**
     * Facade gettor 
     *
     * @return TuneUp
     */
    public TuneUp getFacade() {
        return facade;
    }

    /**
     * Method that plays selected song.
     *
     * @param song Song
     */
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

    /**
     * Method that prints selected song to a .txt file.
     *
     * @param song Song
     */
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

    /**
     * Method utilized to build the song library list.
     *
     * @param searchString String
     *
     * @return List<Song>
     */
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

    /**
     * Method to return username by userid.
     *
     * @param userId String
     *
     * @return String
     */
    public String getUsernameById(String userId) {

        List<User> users = DataLoader.loadUsers();
        for (User user : users) {
            if (userId.equals(user.getId())) {
                return user.getUsername();
            }
        }

        return "Unknown";
    }

    /**
     * Method to clean up the code, used to force pauses between note playing.
     *
     * @param miliseconds Int
     */
    private void sleepWrapper(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
