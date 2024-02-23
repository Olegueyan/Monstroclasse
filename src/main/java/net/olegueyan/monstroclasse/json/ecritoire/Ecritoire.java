package net.olegueyan.monstroclasse.json.ecritoire;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.LinkedList;

public class Ecritoire
{
    @SerializedName("writingTable")
    public LinkedList<EcritoireColumn> writingTable;

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (EcritoireColumn column : writingTable)
        {
            sb.append("----------------------------------").append("\n");
            sb.append(column.specs.scol).append(" - ").append(column.specs.tone).append("\n");
            sb.append(Arrays.toString(column.top.toArray())).append("\n");
            for (LinkedList<String> strings : column.bottom)
            {
                sb.append(Arrays.toString(strings.toArray())).append("\n");
            }
            sb.append("----------------------------------").append("\n");
        }
        return sb.substring(0, sb.length() - 1);
    }
}