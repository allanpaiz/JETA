package controllers;

import java.util.logging.Logger;

import tuneup.TuneUp;

/**
 * Controller for the PracticeMode funcitonality
 */
public class PracticeModeController {
    private static final Logger logger = Logger.getLogger(PracticeModeController.class.getName());
    private TuneUp facade;
    
    /**
     * Constructor initializes with app facade
     */
    public PracticeModeController(TuneUp facade) {
        this.facade = facade;
        logger.info("PracticeModeController initialized");
    }
    
    /**
     * Gets the application facade
     */
    public TuneUp getFacade() {
        return facade;
    }
}
