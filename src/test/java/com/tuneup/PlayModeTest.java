package com.tuneup;

/**
 *  @author Terdooachu
 * 
 */
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class PlayModeTest {

    private PlayMode playMode;
    private User mockUser;
    private MockInstrument instrument;
    private ByteArrayOutputStream outputStream;

    @Before
    public void setUp() {
        mockUser = new User("u1", "testUser", "pass", "test@example.com", UserType.STUDENT, ExperienceLevel.BEGINNER);
        playMode = new PlayMode(mockUser, null);
        instrument = new MockInstrument();

        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void testConstructorSetsUser() {
        assertEquals("testUser", mockUser.getUsername());
    }

    @Test
    public void testHandlePrintsCorrectMessage() {
        playMode.handle();
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Play mode activated for user: testUser"));
    }

    @Test
    public void testPlayNoteInvokesInstrumentStrategy() {
        playMode.playNote("C4", instrument);
        assertTrue(instrument.playNoteCalled);
        assertTrue(instrument.stopCalled);
    }

    // Mock implementation of InstrumentStrategy
    private static class MockInstrument implements InstrumentStrategy {
        boolean playNoteCalled = false;
        boolean stopCalled = false;

        @Override
        public void play() {}

        @Override
        public void playNote(String note) {
            playNoteCalled = true;
        }

        @Override
        public void stop() {
            stopCalled = true;
        }
    }
}
