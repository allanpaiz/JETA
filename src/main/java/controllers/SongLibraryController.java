package controllers;

import tuneup.TuneUp;

public class SongLibraryController {
    private TuneUp facade;
    
    public SongLibraryController(TuneUp facade) {
        this.facade = facade;
    }

    public TuneUp getFacade() {
        return facade;
    }
}
