package com.tuneup;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Assert;
import com.tuneup.*;

import java.util.Scanner;

/**
 * Tested by JChubb
 */
public class CreateModeTest {
    public CreateMode testCreateMode = new CreateMode(null, null);

    @Test
    public void testTesting() {
        assertTrue(true);
    }

    @Test
    public void testBeatsPerMeasureTrue() {
        int beats = testCreateMode.getBeatsPerMeasure("3/4");
        assertEquals(beats, 3);
    }
    // if given an invalid time signature, defaults to four beats per measure
    @Test
    public void testBeatsPerMeasureInvalid() {
        int beats = testCreateMode.getBeatsPerMeasure("no");
        assertEquals(beats, 4);
    }
    @Test
    public void testBeatsPerMeasureEmpty() {
        int beats = testCreateMode.getBeatsPerMeasure("");
        assertEquals(beats, 4);
    }
    @Test
    public void testBeatsPerMeasureNull() {
        int beats = testCreateMode.getBeatsPerMeasure(null);
        assertEquals(beats, 4);
    }

    @Test
    public void testIsValidNoteTrue() {
        assertTrue(testCreateMode.isValidNote("C4"));
    }
    @Test
    public void testIsValidNoteFalse() {
        assertFalse(testCreateMode.isValidNote("not a note"));
    }
    @Test
    public void testIsValidNoteNull() {
        assertFalse(testCreateMode.isValidNote(null));
    }
    @Test
    public void testIsValidNoteUnlistedNote() {
        assertFalse(testCreateMode.isValidNote("C3"));
    }
    @Test
    public void testIsValidNoteIncompleteNote() {
        assertFalse(testCreateMode.isValidNote("C"));
    }
}
