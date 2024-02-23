package net.olegueyan.monstroclasse.json.ecrilu;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;
import java.util.Objects;

/**
 * Represents a sequence of ecrilu as JSON
 * <br>
 * {
 *   "id": "Sequence ID",
 *   "title": "Sequence Title",
 *   "exercises": [
 *     "ExX",
 *     "ExY",
 *     "ExZ"
 *   ]
 * }
 */
public class EcriluSequenceJson
{
    @SerializedName("id")
    public String id;

    @SerializedName("title")
    public String title;

    @SerializedName("exercises")
    public LinkedList<String> exercises;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EcriluSequenceJson that = (EcriluSequenceJson) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(exercises, that.exercises);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, exercises);
    }
}