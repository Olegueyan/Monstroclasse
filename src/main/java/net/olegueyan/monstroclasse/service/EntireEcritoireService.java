package net.olegueyan.monstroclasse.service;

import com.google.gson.Gson;
import net.olegueyan.monstroclasse.json.ecritoire.Ecritoire;
import net.olegueyan.monstroclasse.json.ecritoire.EcritoireColumn;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;

public final class EntireEcritoireService
{
    // ***************************************************************
    // EntireEcritoireService - ATTRIBUTES # STATIC
    // ***************************************************************

    // ------- Path of the Writing Table (Ecritoire) ------- //
    private static final String writingTablePath = DataService.pathOf("writingTable.json");
    // ----------------------------------------------------- //

    // ------- Stocked Ecritoire | Nullable ------- //
    private static Ecritoire ecritoire;
    // -------------------------------------------- //

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // EntireEcritoireService - METHODS # STATIC
    // ***************************************************************

    /**
     * Load the service
     */
    public static void load()
    {
        try
        {
            String content = Files.readString(Path.of(writingTablePath));
            ecritoire = new Gson().fromJson(content, Ecritoire.class);
            for (EcritoireColumn c : ecritoire.writingTable)
            {
                if (c.specs.tone != c.top.size())
                {
                    throw new RuntimeException("For " + Arrays.toString(c.top.toArray()) + " TONE is " + c.specs.tone + " and TOP length is " + c.top.size() + " !");
                }
                if (c.specs.scol != c.bottom.size())
                {
                    throw new RuntimeException("For " + Arrays.toString(c.top.toArray()) + " SCOL is " + c.specs.scol + " and BOTTOM length is " + c.bottom.size() + " !");
                }
                for (LinkedList<String> strs : c.bottom)
                {
                    if (strs.size() > 15)
                    {
                        throw new RuntimeException("For " + Arrays.toString(c.top.toArray()) + " the SUB COLUMN " + Arrays.toString(strs.toArray()) + " must be smaller than 15 !");
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.fillInStackTrace();
        }
    }

    /**
     * Unload the service
     */
    public static void unload()
    {
        ecritoire = null;
    }

    /**
     * Get entire "Ecritoire"
     * @return Ecritoire
     */
    public static Ecritoire getEcritoire()
    {
        if (ecritoire == null)
        {
            throw new RuntimeException("You must load 'Ecritoire' before to call it'");
        }
        return ecritoire;
    }

    // ***************************************************************
    // END
    // ***************************************************************
}