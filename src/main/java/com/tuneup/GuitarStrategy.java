package com.tuneup;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class GuitarStrategy implements InstrumentStrategy {
    private static final int GUITAR_INSTRUMENT = 24; // Nylon String Guitar MIDI instrument
    private Synthesizer synth;
    private MidiChannel[] channels;

    public GuitarStrategy() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();
            // Set guitar instrument
            channels[0].programChange(GUITAR_INSTRUMENT);
        } catch (MidiUnavailableException e) {
            System.out.println("Error initializing MIDI: " + e.getMessage());
        }
    }

    @Override
    public void play() {
        System.out.println("Guitar ready to play...");
    }

    @Override
    public void playNote(String note) {
        int midiNote = getMidiNote(note);
        if (midiNote != -1) {
            try {
                // Play note with medium velocity (64)
                channels[0].noteOn(midiNote, 64);
                Thread.sleep(300); // Hold note for 300ms
                channels[0].noteOff(midiNote);
            } catch (InterruptedException e) {
                System.out.println("Error playing note: " + e.getMessage());
            }
        }
    }

    private int getMidiNote(String note) {
        // Guitar strings are typically tuned to E A D G B E
        switch (note.toUpperCase()) {
            case "C": return 60;  // Middle C
            case "D": return 62;
            case "E": return 64;
            case "F": return 65;
            case "G": return 67;
            case "A": return 69;
            case "B": return 71;
            case "HIGH C": return 72; // One octave up
            default: return -1;
        }
    }

    public void close() {
        if (synth != null) {
            synth.close();
        }
    }
}