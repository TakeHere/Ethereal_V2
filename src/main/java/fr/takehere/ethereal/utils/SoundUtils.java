package fr.takehere.ethereal.utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundUtils {

    public static void playSound(String name, float volume, boolean loop){
        playSound(RessourcesManager.getSound(name), volume, loop);
    }

    public static void playSound(Media media, float volume, boolean loop){
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(volume);
        mediaPlayer.play();

        if (loop){
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.seek(Duration.ZERO);
                    mediaPlayer.play();
                }
            });
        }

        return;
    }
}
