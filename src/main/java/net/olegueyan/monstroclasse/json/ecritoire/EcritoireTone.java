package net.olegueyan.monstroclasse.json.ecritoire;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Objects;

public class EcritoireTone
{
    @SerializedName("col")
    public String col;

    @SerializedName("tone")
    public String tone;

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) { return true; }
        if (obj == null || getClass() != obj.getClass()) return false;
        EcritoireTone other = (EcritoireTone) obj;
        return Objects.equals(col, other.col) && Objects.equals(tone, other.tone);
    }

    @Override
    public String toString() {
        return Arrays.toString(new String[]{col, tone});
    }
}