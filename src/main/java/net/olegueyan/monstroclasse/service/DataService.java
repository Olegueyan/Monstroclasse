package net.olegueyan.monstroclasse.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Service allowing access to files in the data folder
 */
public final class DataService
{
    // ***************************************************************
    // DataService - ATTRIBUTES # STATIC
    // ***************************************************************

    public static final String DATA_FOLDER = "data";

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // DataService - METHODS # STATIC
    // ***************************************************************

    /**
     * Get the relative path for a resource inside the data folder
     * <br>
     * "root"/data/"filename"
     * @param filename name of the file
     * @return String
     */
    public static String pathOf(String filename)
    {
        return String.join("/", DATA_FOLDER, filename);
    }

    public static String pathOf(String filename, String... folders)
    {
        return DATA_FOLDER + "/" + String.join("/", folders) + "/" + filename;
    }

    public static void delete(String filename, String... folders)
    {
        try
        {
            Files.delete(Path.of(DATA_FOLDER + "/" + String.join("/", folders) + "/" + filename));
        }
        catch (IOException e)
        {
            e.fillInStackTrace();
        }
    }

    public static ArrayList<String> readEntireFolder(String... folders)
    {
        ArrayList<String> fileNames = new ArrayList<>();

        String folderPath = DATA_FOLDER + "/" + String.join("/", folders);

        Path folder = Paths.get(folderPath);

        try(Stream<Path> pathStream = Files.walk(folder, FileVisitOption.FOLLOW_LINKS))
        {
            pathStream.filter(Files::isRegularFile)
                    .forEach(path -> fileNames.add(path.getFileName().toString()));
        }
        catch (IOException e)
        {
            e.fillInStackTrace();
        }

        return fileNames;
    }

    // ###############################################################
    // Json Data Service
    // ###############################################################

    public static <T> T readJson(Class<T> tClass, String name, String... folders)
    {
        String path = DATA_FOLDER + "/" + String.join("/", folders) + "/" + name + ".json";

        try
        {
            String json = Files.readString(Path.of(path));

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            return gson.fromJson(json, tClass);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static <T> void saveJson(T toSave, String name, String... folders)
    {
        String path = String.join("/", folders) + "/" + name + ".json";

        try (FileWriter writer = new FileWriter(String.join("/", DATA_FOLDER, path)))
        {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            gson.toJson(toSave, writer);
        }
        catch (IOException e)
        {
            e.fillInStackTrace();
        }
    }

    public static <T> ArrayList<T> readAllJsonOf(Class<T> tClass, String... folders)
    {
        ArrayList<T> files = new ArrayList<>();

        String folderPath = DATA_FOLDER + "/" + String.join("/", folders);

        Path folder = Paths.get(folderPath);

        Gson gson = new Gson();

        try(Stream<Path> pathStream = Files.walk(folder, FileVisitOption.FOLLOW_LINKS))
        {
            pathStream.filter(Files::isRegularFile)
                    .forEach(path ->
                    {
                        try
                        {
                            files.add(gson.fromJson(Files.readString(path), tClass));
                        }
                        catch (IOException e)
                        {
                            throw new RuntimeException(e);
                        }
                    });
        }
        catch (IOException e)
        {
            e.fillInStackTrace();
        }

        return files;
    }

    // ###############################################################
    // Json Data Service
    // ###############################################################

    // ***************************************************************
    // DataService - Methods # STATIC
    // ****************************************************************

    // ***************************************************************
    // DataService - Exportation
    // ***************************************************************



    // ***************************************************************
    // DataService - Exportation
    // ***************************************************************
}