package net.olegueyan.monstroclasse.common;

import net.olegueyan.monstroclasse.component.StageHandlerComponent;
import net.olegueyan.monstroclasse.entity.Account;
import net.olegueyan.monstroclasse.json.ecrilu.BaremeJson;

import javax.sound.sampled.Clip;

/**
 * Data that can access through any module
 */
public final class PersistentData
{
    /* ------- Current connected account Pupil/Admin ------- */
    public static Account currentConnectedAccount;
    /* ----------------------------------------------------- */

    /* ------- Stage Handler Component to manipulate multiple windows ------- */
    public static StageHandlerComponent stageHandler = null;
    /* ---------------------------------------------------------------------- */

    /* ------- Prevent audio spamming ------- */
    public static boolean anAudioIsPlaying = false;
    /* -------------------------------------- */

    /* ------- Current audio ---------------- */
    public static Clip currentPlayedAudio = null;
    /* -------------------------------------- */

    /* ------- Bareme Ecrilu ------- */
    public static BaremeJson baremeJson = null;
    /* ----------------------------- */
}