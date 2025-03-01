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
        channels[0].noteOn(midiNote, 600);
    }

    @Override
    public void stop() {
        channels[0].allNotesOff();
    }

    private int getMidiNote(String note) {
        switch (note) {
            case "C":
                return 60;
            case "D":
                return 62;
            case "E":
                return 64;
            case "F":
                return 65;
            case "G":
                return 67;
            case "A":
                return 69;
            case "B":
                return 71;
            case "HIGH C":
                return 72;
            default:
                return 60;
        }
    }

    public void close() {
        synth.close();
    }
}