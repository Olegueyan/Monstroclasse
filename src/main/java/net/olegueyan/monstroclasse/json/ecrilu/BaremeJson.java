package net.olegueyan.monstroclasse.json.ecrilu;

import com.google.gson.annotations.SerializedName;

public class BaremeJson
{
    @SerializedName("check")
    public NoteJson check;

    @SerializedName("three_star")
    public NoteJson threeStar;

    @SerializedName("two_star")
    public NoteJson twoStar;

    @SerializedName("one_star")
    public NoteJson oneStar;
}