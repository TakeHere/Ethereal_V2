package fr.takehere.ethereal.utils;

import fr.takehere.ethereal.GameWindow;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.net.URISyntaxException;
import java.util.HashMap;

public class RessourcesManager {
    private static HashMap<String, Image> images = new HashMap<>();
    private static HashMap<String, Media> sounds = new HashMap<>();

    public static Image addImage(String name, String path){
        Image image = new Image(path);
        images.put(name, image);

        return image;
    }

    public static Media addSound(String name, String path, Class resourceClass){
        try {
            Media sound = new Media(resourceClass.getResource(path).toURI().toString());
            //Media sound = new Media(GameWindow.class.getResource(path).toURI().toString());
            sounds.put(name, sound);

            return sound;
        }catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Image getImage(String name){
        Image image = images.get(name);
        if (image == null) throw new NullPointerException("Image " + name + " was not found");
        return images.get(name);
    }

    public static Media getSound(String name){
        Media sound = sounds.get(name);
        if (sound == null) throw new NullPointerException("Sound " + name + " was not found");
        return sound;
    }
}
