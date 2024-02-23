package net.olegueyan.monstroclasse.utils;

import net.olegueyan.monstroclasse.Main;
import net.olegueyan.monstroclasse.common.PersistentData;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.io.File;
import java.text.Normalizer;

public final class MonstroclasseUtils
{
    public static void playAudio(String path)
    {
        File audioFile = new File(path);

        if (audioFile.exists())
        {
            try
            {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                MonstroclasseUtils.playAudio(audioStream);
            }
            catch (Exception e)
            {
                Main.logger.error(e.getMessage());
            }
        }
        else
        {
            Main.logger.error("The audio file doesn't exist !");
        }
    }

    public static void playAudio(AudioInputStream audioInputStream)
    {
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            clip.addLineListener(event ->
            {
                if (event.getType() == LineEvent.Type.START)
                {
                    PersistentData.anAudioIsPlaying = true;
                    PersistentData.currentPlayedAudio = clip;
                }
                if (event.getType() == LineEvent.Type.STOP)
                {
                    PersistentData.anAudioIsPlaying = false;
                    PersistentData.currentPlayedAudio = null;
                }
            });

            if (!PersistentData.anAudioIsPlaying)
            {
                clip.start();
            }
        }
        catch (Exception e)
        {
            Main.logger.error(e.getMessage());
        }
    }

    public static String accentRemover(String input)
    {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static boolean containsValue(String[] array, String value) {
        for (String element : array) {
            if (element.equals(value)) {
                return true;
            }
        }
        return false;
    }
}