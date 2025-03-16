package com.tuneup;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class PianoStrategy implements InstrumentStrategy {
    private static final int PIANO_INSTRUMENT = 0;
    private Synthesizer synth;
    private MidiChannel[] channels;
    private int currentNote = -1; // Track the currently playing note

    public PianoStrategy() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();
            Instrument[] instruments = synth.getDefaultSoundbank().getInstruments();
            synth.loadInstrument(instruments[PIANO_INSTRUMENT]);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void play() {
        // Implementation for playing the instrument
    }

    @Override
    public void playNote(String note) {
        int midiNote = getMidiNote(note);
        currentNote = midiNote; // Store the current note
        System.out.println("Playing MIDI note: " + midiNote);
        channels[0].noteOn(midiNote, 100);
    }

    @Override
    public void stop() {
        if (currentNote != -1) {
            channels[0].noteOff(currentNote);
            currentNote = -1;
        }
    }

    /**
     * Converts a note name (e.g., "C4", "D4") to a MIDI note number
     * @param note The note name
     * @return The MIDI note number
     */
    private int getMidiNote(String note) {
        // Basic structure of a MIDI octave: C, C#, D, D#, E, F, F#, G, G#, A, A#, B
        // Middle C (C4) = 60, C0 = 12
        
        // Extract the note letter and octave
        if (note == null || note.length() < 2) {
            return 60; // Default to middle C if invalid
        }
        
        char noteLetter = note.charAt(0);
        int octave = 4; // Default to middle octave
        
        // Get the octave if specified
        if (note.length() > 1 && Character.isDigit(note.charAt(note.length()-1))) {
            octave = Character.getNumericValue(note.charAt(note.length()-1));
        }
        
        // Check if it's a sharp note
        boolean isSharp = note.contains("#");
        
        // Calculate the base note value within an octave
        int noteValue;
        switch (noteLetter) {
            case 'C': noteValue = 0; break;
            case 'D': noteValue = 2; break;
            case 'E': noteValue = 4; break;
            case 'F': noteValue = 5; break;
            case 'G': noteValue = 7; break;
            case 'A': noteValue = 9; break;
            case 'B': noteValue = 11; break;
            default: return 60; // Default to middle C if invalid note letter
        }
        
        // Adjust for sharp
        if (isSharp) {
            noteValue += 1;
        }
        
        // Calculate the final MIDI note number
        // Formula: (octave + 1) * 12 + noteValue
        return (octave + 1) * 12 + noteValue;
    }

    /**
     * Closes the synthesizer to free resources
     */
    public void close() {
        if (synth != null && synth.isOpen()) {
            synth.close();
        }
    }
}