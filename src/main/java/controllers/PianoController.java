package controllers;

import java.util.logging.Logger;

import tuneup.PianoStrategy;
import tuneup.PlayMode;
import tuneup.TuneUp;

/**
 * Controller for the Piano funcitonality
 */
public class PianoController {
    private static final Logger logger = Logger.getLogger(PianoController.class.getName());
    private TuneUp facade;
    private PlayMode playModeFacade;
    
    /**
     * Constructor initializes with app facade
     */
    public PianoController(TuneUp facade) {
        this.facade = facade;
        this.playModeFacade = new PlayMode(null, facade);
        logger.info("Piano initialized");
    }
    
    /**
     * Gets the application facade
     */
    public TuneUp getFacade() {
        return facade;
    }

    public void playNote(String note) {
        playModeFacade.playNote(note, new PianoStrategy());
    }
}