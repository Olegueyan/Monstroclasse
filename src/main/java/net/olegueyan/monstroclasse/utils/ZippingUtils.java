package net.olegueyan.monstroclasse.utils;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.olegueyan.monstroclasse.Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public final class ZippingUtils
{
    public static void compressAll(Path zipPath, ArrayList<File> toCompress)
    {
        try (ZipFile zipFile = new ZipFile(zipPath.toFile()))
        {
            ZipParameters parameters = new ZipParameters();
            parameters.setIncludeRootFolder(true);
            parameters.setOverrideExistingFilesInZip(true);
            parameters.setCompressionLevel(CompressionLevel.FASTEST);

            for (File file : toCompress)
            {
                if (file.isDirectory())
                {
                    zipFile.addFolder(file, parameters);
                }
                else
                {
                    zipFile.addFile(file, parameters);
                }
            }

            Main.logger.debug("File compressed successfully");
        }
        catch (IOException e)
        {
            Main.logger.error(e.getMessage());
        }
    }
}