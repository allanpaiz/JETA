package com.tuneup;
/*
 *  @author Terdooachu
 * 
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class LessonTest {

    private Lesson lesson;
    private Song song;
    private List<ExperienceLevel> levels;
    private List<User> users;

    @Before
    public void setUp() {
        song = new Song("song-id", "Test Song", "creator-id", new ArrayList<>(), 0, "genre");
        levels = Arrays.asList(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE);
        users = Arrays.asList(
            new User("id1", "alice", "hashedPass", "alice@example.com", UserType.STUDENT, ExperienceLevel.BEGINNER),
            new User("id2", "bob", "hashedPass", "bob@example.com", UserType.TEACHER, ExperienceLevel.ADVANCED)
        );
        lesson = new Lesson("lesson-1", "Intro to Piano", "Mr. Music", song, levels, users);
    }

    @Test
    public void testLessonId() {
        assertEquals("lesson-1", lesson.getId());
    }

    @Test
    public void testLessonTitle() {
        assertEquals("Intro to Piano", lesson.getTitle());
    }

    @Test
    public void testLessonInstructor() {
        assertEquals("Mr. Music", lesson.getInstructor());
    }

    @Test
    public void testLessonSong() {
        assertEquals(song, lesson.getSong());
    }

    @Test
    public void testLessonAssignedLevels() {
        assertEquals(levels, lesson.getAssignedLevels());
    }

    @Test
    public void testLessonAssignedUsers() {
        assertEquals(users, lesson.getAssignedUsers());
    }

    @Test
    public void testSetId() {
        lesson.setId("lesson-2");
        assertEquals("lesson-2", lesson.getId());
    }

    @Test
    public void testSetTitle() {
        lesson.setTitle("Advanced Piano");
        assertEquals("Advanced Piano", lesson.getTitle());
    }

    @Test
    public void testSetInstructor() {
        lesson.setInstructor("Dr. Tune");
        assertEquals("Dr. Tune", lesson.getInstructor());
    }

    @Test
    public void testSetSong() {
        Song newSong = new Song("id2", "Jazz Basics", "new-creator", new ArrayList<>(), 0, "genre");
        lesson.setSong(newSong);
        assertEquals(newSong, lesson.getSong());
    }

    @Test
    public void testSetAssignedLevels() {
        List<ExperienceLevel> newLevels = Collections.singletonList(ExperienceLevel.ADVANCED);
        lesson.setAssignedLevels(newLevels);
        assertEquals(newLevels, lesson.getAssignedLevels());
    }

    @Test
    public void testSetAssignedUsers() {
        List<User> newUsers = Collections.singletonList(
            new User("id3", "charlie", "pass", "charlie@example.com", UserType.STUDENT, ExperienceLevel.INTERMEDIATE)
        );
        lesson.setAssignedUsers(newUsers);
        assertEquals(newUsers, lesson.getAssignedUsers());
    }
}
