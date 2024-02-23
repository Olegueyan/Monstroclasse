package net.olegueyan.monstroclasse.json.ecrilu;

import com.google.gson.annotations.SerializedName;

public class NoteJson
{
    @SerializedName("note")
    public int note;

    @SerializedName("max_mistakes")
    public int maxMistakes;
}