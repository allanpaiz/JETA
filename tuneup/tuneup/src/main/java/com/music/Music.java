package com.music;

import org.jfugue.player.Player;

public class Music {
    public static void playNote(String note){
        Player player = new Player();
        player.play(note);
    }
}