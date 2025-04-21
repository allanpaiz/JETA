package controllers;

import java.util.logging.Logger;

import tuneup.TuneUp;

/**
 * Controller for the Piano funcitonality
 */
public class PianoController {
    private static final Logger logger = Logger.getLogger(PianoController.class.getName());
    private TuneUp facade;
    
    /**
     * Constructor initializes with app facade
     */
    public PianoController(TuneUp facade) {
        this.facade = facade;
        logger.info("Piano initialized");
    }
    
    /**
     * Gets the application facade
     */
    public TuneUp getFacade() {
        return facade;
    }
}