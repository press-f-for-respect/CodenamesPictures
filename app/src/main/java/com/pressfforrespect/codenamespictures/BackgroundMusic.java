package com.pressfforrespect.codenamespictures;

import android.content.Context;
import android.media.MediaPlayer;

public class BackgroundMusic {

    private static BackgroundMusic player;
    private MediaPlayer media;
    private static boolean doPlay = true;

    private BackgroundMusic(Context context, int music){
        media = MediaPlayer.create(context, music);
        media.setLooping(true); // Set looping
        media.setVolume(100,100);
    }

    public static BackgroundMusic getInstance(Context context, int music){
        if(player == null)
            player = new BackgroundMusic(context, music);
        return player;
    }

    public static void setDoPlay(boolean doPlay) {
        BackgroundMusic.doPlay = doPlay;
    }

    public static BackgroundMusic getInstance(){
        return player;
    }

    public void play(){
        if(doPlay)
            media.start();
    }

    public void stop(){
        media.stop();
        player = null;
    }

    public void pause(){
        media.pause();
    }

    public void toggleSound(){
        doPlay = !doPlay;
    }
}
