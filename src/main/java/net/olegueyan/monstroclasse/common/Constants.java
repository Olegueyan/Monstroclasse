package net.olegueyan.monstroclasse.common;

import javafx.scene.image.Image;
import net.olegueyan.monstroclasse.Main;
import net.olegueyan.monstroclasse.entity.Module;
import net.olegueyan.monstroclasse.repository.ModuleRepository;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Declaration of all constants
 */
public final class Constants
{
    // ***************************************************************
    // ADMIN - Constants
    // ***************************************************************

    // ------- Avatar of Admin account ------- //
    public static final Image ADMIN_IMAGE = new Image(Main.getResource("assets", "admin.png"));
    // --------------------------------------- //

    // ------- Password of Admin account ------- //
    public static final String ADMIN_PASSWORD = "24680";
    // ----------------------------------------- //

    // ------- All the modules that the admin has ------- //
    public static final ArrayList<Module> ADMIN_MODULES = new ArrayList<>()
    {
        {
            add(ModuleRepository.read("A1GS"));
            add(ModuleRepository.read("EA1M"));
        }
    };
    // -------------------------------------------------- //

    // ------- Font family of the whole application ------- //
    public static final String FONT_FAMILY = "Verdana";
    // ---------------------------------------------------- //

    // ***************************************************************
    // ADMIN - Constants
    // ***************************************************************

    // ***************************************************************
    // Other - Constants
    // ***************************************************************

    // ------- Define how many characters will be printed into the label of student name ------- //
    public static final int STUDENT_MAX_LENGTH_NAME = 20;
    // ----------------------------------------------------------------------------------------- //

    public static final int MAX_WORD_SIZE = 28;

    // ------- Ecrilu word audio extension supported ------- //
    public static final String[] AUDIO_EXTENSION_SUPPORTED = new String[]{
            "*.mp3",
            "*.wav",
            "*.flac"
    };
    // ----------------------------------------------------- //

    // ------- Size of the identifier of an exercise ------- //
    public static final int LIMIT_CHARACTERS_EXERCISE_ID = 8;
    // ----------------------------------------------------- //

    // ------- Size of the identifier of a sequence ------- //
    public static final int LIMIT_CHARACTERS_SEQUENCE_ID = 12;
    // ---------------------------------------------------- //

    // ------- The number of words that we can put inside an exercise ------- //
    public static final int LIMIT_WORD_PER_EXERCISE = 12;
    // ---------------------------------------------------------------------- //

    // ------- Limit of size of an audio ------- //
    public static final double LIMIT_SIZE_AUDIO_IN_MO = 6;
    // ----------------------------------------- //

    // ***************************************************************
    // Other - Constants
    // ***************************************************************

    // ***************************************************************
    // IMAGES - Fixed assets
    // ***************************************************************

    public static Image CHECK_IMAGE;
    public static Image CLOSE_IMAGE;
    public static Image EDIT_IMAGE;
    public static Image DELETE_IMAGE;
    public static Image LEFT_ARROW;
    public static Image RIGHT_ARROW;
    public static Image INTERROG_IMAGE;
    public static Image IN_DOING_IMAGE;
    public static Image UP_ARROW_IMAGE;
    public static Image DOWN_ARROW_IMAGE;

    public static Image ONE_STAR;
    public static Image TWO_STAR;
    public static Image THREE_STAR;
    public static Image PERFECT_NOTE;

    static
    {
        try
        {
            CHECK_IMAGE = new Image(Objects.requireNonNull(Main.class.getResource("assets/check.png")).openStream());
            CLOSE_IMAGE = new Image(Objects.requireNonNull(Main.class.getResource("assets/close.png")).openStream());
            EDIT_IMAGE = new Image(Objects.requireNonNull(Main.class.getResource("assets/edit.png")).openStream());
            DELETE_IMAGE = new Image(Objects.requireNonNull(Main.class.getResource("assets/delete.png")).openStream());
            LEFT_ARROW = new Image(Objects.requireNonNull(Main.class.getResource("assets/leftArrow.png")).openStream());
            RIGHT_ARROW = new Image(Objects.requireNonNull(Main.class.getResource("assets/rightArrow.png")).openStream());
            INTERROG_IMAGE = new Image(Objects.requireNonNull(Main.class.getResource("assets/questionMark.png")).openStream());
            IN_DOING_IMAGE = new Image(Objects.requireNonNull(Main.class.getResource("assets/inDoing.png")).openStream());
            UP_ARROW_IMAGE = new Image(Objects.requireNonNull(Main.class.getResource("assets/upArrow.png")).openStream());
            DOWN_ARROW_IMAGE = new Image(Objects.requireNonNull(Main.class.getResource("assets/downArrow.png")).openStream());
            ONE_STAR = new Image(Objects.requireNonNull(Main.class.getResource("assets/oneStar.png")).openStream());
            TWO_STAR = new Image(Objects.requireNonNull(Main.class.getResource("assets/twoStar.png")).openStream());
            THREE_STAR = new Image(Objects.requireNonNull(Main.class.getResource("assets/threeStar.png")).openStream());
            PERFECT_NOTE = new Image(Objects.requireNonNull(Main.class.getResource("assets/perfectNote.png")).openStream());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    // ***************************************************************
    // IMAGES - Fixed assets
    // ***************************************************************

    // ***************************************************************
    // SOUNDS - Fixed assets
    // ***************************************************************

    /**
     * Get a fresh audio input stream for error sound
     * @return AudioInputStream
     */
    public static AudioInputStream getErrorSoundInputStream()
    {
        try
        {
            byte[] errorSoundBytes = Main.getResource("assets/errorEcriluStudentSound.wav").readAllBytes();
            return AudioSystem.getAudioInputStream(new ByteArrayInputStream(errorSoundBytes));
        }
        catch (IOException | UnsupportedAudioFileException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get a fresh audio input stream for correct sound
     * @return AudioInputStream
     */
    public static AudioInputStream getCorrectSoundInputStream()
    {
        try
        {
            byte[] correctSoundBytes = Main.getResource("assets/correctEcriluStudentSound.wav").readAllBytes();
            return AudioSystem.getAudioInputStream(new ByteArrayInputStream(correctSoundBytes));
        }
        catch (IOException | UnsupportedAudioFileException e)
        {
            throw new RuntimeException(e);
        }
    }

    // ***************************************************************
    // SOUNDS - Fixed assets
    // ***************************************************************
}