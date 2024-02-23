package net.olegueyan.monstroclasse.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class FoldersCreator
{
    private final ArrayList<File> files;

    public FoldersCreator()
    {
        this.files = new ArrayList<>();
    }

    public void addFile(File file)
    {
        this.files.add(file);
    }

    public void removeFile(File file)
    {
        this.files.remove(file);
    }

    public ArrayList<File> getFiles()
    {
        return this.files;
    }

    public void processCreationOfFolders()
    {
        while (!this.files.isEmpty())
        {
            File file = this.files.removeFirst();

            if (!file.exists())
            {
                try
                {
                    Files.createDirectory(file.toPath());
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}