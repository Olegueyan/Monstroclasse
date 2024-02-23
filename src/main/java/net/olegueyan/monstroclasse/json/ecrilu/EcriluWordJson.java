package net.olegueyan.monstroclasse.json.ecrilu;

import com.google.gson.annotations.SerializedName;
import net.olegueyan.monstroclasse.json.ecritoire.EcritoireTone;

import java.util.LinkedList;
import java.util.Objects;

/**
 * Represents an exercise of ecrilu as JSON
 * <br>
 * {
 *   "id": "Word ID",
 *   "word": "cendres",
 *   "audio": "CENDRES.WAV",
 *   "composition": [
 *     {
 *       "col": "s",
 *       "tone": "c"
 *     },
 *     {
 *       "col": "en/an",
 *       "tone": "en"
 *     },
 *     {
 *       "col": "d",
 *       "tone": "d"
 *     },
 *     {
 *       "col": "r",
 *       "tone": "res"
 *     }
 *   ]
 * }
 */
public class EcriluWordJson
{
    @SerializedName("id")
    public String id;

    @SerializedName("word")
    public String word;

    @SerializedName("audio")
    public String audioPath;

    @SerializedName("composition")
    public LinkedList<EcritoireTone> composition;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EcriluWordJson that = (EcriluWordJson) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(word, that.word) &&
                Objects.equals(audioPath, that.audioPath) &&
                Objects.equals(composition, that.composition);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, word, audioPath, composition);
    }
}