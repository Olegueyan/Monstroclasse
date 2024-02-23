package net.olegueyan.monstroclasse.json.ecritoire;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;

public class EcritoireColumn
{
    @SerializedName("specs")
    public EcritoireColumnSpecs specs;

    @SerializedName("top")
    public LinkedList<String> top;

    @SerializedName("bottom")
    public LinkedList<LinkedList<String>> bottom;
}