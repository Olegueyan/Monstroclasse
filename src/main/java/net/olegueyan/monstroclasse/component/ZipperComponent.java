package net.olegueyan.monstroclasse.component;

import com.speedment.common.tuple.Tuple2;
import com.speedment.common.tuple.Tuples;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.olegueyan.monstroclasse.Main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Only for decompression, need to be moved into ZipperUtils (doesn't use zip lib)
 */
@Deprecated
public class ZipperComponent
{
    private final File pointer;

    public ZipperComponent(File pointer)
    {
        if (!pointer.exists())
        {
            throw new RuntimeException("The pointer must exists !");
        }

        if (!pointer.isDirectory())
        {
            throw new RuntimeException("The pointer must be a folder !");
        }

        this.pointer = pointer;
    }

    public ArrayList<Tuple2<Path, Path>> decompressFolder(Path zipFolder, String[] toNotReplace)
    {
        ArrayList<Tuple2<Path, Path>> noReplacedFiles = new ArrayList<>();
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFolder.toFile())))
        {
            ZipEntry entry = zis.getNextEntry();
            while (entry != null)
            {
                Path outputEntryPath = Path.of(this.pointer.getAbsolutePath() + "/" + entry.getName());
                if (entry.isDirectory() && !Files.exists(outputEntryPath))
                {
                    Files.createDirectory(outputEntryPath);
                }
                else if (!entry.isDirectory())
                {
                    File outputFile = outputEntryPath.toFile();
                    if (outputFile.exists())
                    {
                        if (Arrays.asList(toNotReplace).contains(outputFile.getName()))
                        {
                            try (FileOutputStream fos = new FileOutputStream(outputEntryPath.toFile() + ".ignored"))
                            {
                                copy(zis, fos);
                                noReplacedFiles.add(Tuples.of(outputEntryPath, Path.of(outputEntryPath.toFile() + ".ignored")));
                            }
                        }
                        else
                        {
                            try (FileOutputStream fos = new FileOutputStream(outputEntryPath.toFile()))
                            {
                                copy(zis, fos);
                            }
                        }
                    }
                    else
                    {
                        try (FileOutputStream fos = new FileOutputStream(outputEntryPath.toFile()))
                        {
                            copy(zis, fos);
                        }
                    }
                }
                entry = zis.getNextEntry();
            }
            zis.closeEntry();
        }
        catch (IOException e)
        {
            e.fillInStackTrace();
        }
        return noReplacedFiles;
    }

    private static void copy(InputStream inputStream, OutputStream outputStream) throws IOException
    {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) >= 0)
        {
            outputStream.write(buffer, 0, length);
        }
    }
}