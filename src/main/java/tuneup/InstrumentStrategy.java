package tuneup;

/**
 * Strategy interface for instruments
 * 
 * @author edwinjwood
 */
public interface InstrumentStrategy {
    void play();
    void playNote(String note);
    void stop();
}