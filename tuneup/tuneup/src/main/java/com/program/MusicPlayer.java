package com.program;

import com.music.*;
import java.lang.Thread;

public class MusicPlayer {

    public void playSong() {
        try {
            playLine1();
            Thread.sleep(300);
            playLine2();
            Thread.sleep(300);
            playLine3();
            Thread.sleep(300);
            playLine4();
        } 
        catch(Exception e){
            System.out.println(e);
        }
    }

    private void playLine1() {
        Music.playNote("C");
        Music.playNote("C");
        Music.playNote("D");
        Music.playNote("C");
        Music.playNote("F");
        Music.playNote("E");
    }

    private void playLine2() {
        Music.playNote("C");
        Music.playNote("C");
        Music.playNote("D");
        Music.playNote("C");
        Music.playNote("G");
        Music.playNote("F");
    }

    private void playLine3(){
        Music.playNote("C");
        Music.playNote("C");
        Music.playNote("C");
        Music.playNote("A");
        Music.playNote("F");
        Music.playNote("E");
        Music.playNote("D");
    }

    private void playLine4(){
        Music.playNote("A#");
        Music.playNote("A#");
        Music.playNote("A");
        Music.playNote("F");
        Music.playNote("G");
        Music.playNote("F");
    }

    public static void main(String[] args){
        MusicPlayer player = new MusicPlayer();
        player.playSong();       
    }
}
