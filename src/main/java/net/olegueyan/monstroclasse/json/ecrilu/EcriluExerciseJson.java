package net.olegueyan.monstroclasse.json.ecrilu;

import com.google.gson.annotations.SerializedName;
import net.olegueyan.monstroclasse.json.ecritoire.Ecritoire;

import java.util.LinkedList;
import java.util.Objects;

/**
 * Represents an exercise of ecrilu as JSON
 * <br>
 * {
 *   "id": "Exercise ID",
 *   "title": "Exercise Title",
 *   "words": [
 *     "wordX",
 *     "wordY",
 *     "wordZ"
 *   ],
 *   "encoding": {
 *     "writingTable": [
 *       {
 *         "specs": {
 *           "scol": 1,
 *           "tone": 1
 *         },
 *         "top": [
 *           "w"
 *         ],
 *         "bottom": [
 *           [],
 *           []
 *         ]
 *       }
 *     ]
 *   }
 * }
 */
public class EcriluExerciseJson
{
    @SerializedName("id")
    public String id;

    @SerializedName("title")
    public String title;

    @SerializedName("words")
    public LinkedList<String> words;

    @SerializedName("encoding")
    public Ecritoire ecritoire;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EcriluExerciseJson that = (EcriluExerciseJson) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(words, that.words) &&
                Objects.equals(ecritoire, that.ecritoire);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, title, words, ecritoire);
    }
}