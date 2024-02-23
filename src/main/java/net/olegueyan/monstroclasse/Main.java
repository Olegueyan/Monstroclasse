package net.olegueyan.monstroclasse;

import net.olegueyan.monstroclasse.common.PersistentData;
import net.olegueyan.monstroclasse.common.MonstroclasseMemory;
import net.olegueyan.monstroclasse.entity.Pupil;
import net.olegueyan.monstroclasse.entry.MonstroclasseAppLoader;
import net.olegueyan.monstroclasse.json.ecrilu.BaremeJson;
import net.olegueyan.monstroclasse.json.ecrilu.EcriluSequenceJson;
import net.olegueyan.monstroclasse.service.DataService;
import net.olegueyan.monstroclasse.service.DatabaseService;
import net.olegueyan.monstroclasse.utils.FoldersCreator;
import net.olegueyan.monstroclasse.utils.CSVManipulator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main
{
    // ------- Logger of the the application | Interact with console ------ //
    public static Logger logger = LoggerFactory.getLogger(Main.class.getName());
    // -------------------------------------------------------------------- //

    // ***************************************************************
    // Main - entry point of the application
    // ***************************************************************

    public static void main(String[] args) throws IOException, URISyntaxException
    {
        /*
            Before Launching Anything
         */

        // ##### Data folder creation ##### //

        FoldersCreator foldersCreatorData = new FoldersCreator();
        foldersCreatorData.addFile(new File("data"));
        foldersCreatorData.processCreationOfFolders();

        // ##### Data folder creation ##### //

        // ##### Ecrilu structure creation ##### //

        FoldersCreator foldersCreatorEcrilu = new FoldersCreator();
        foldersCreatorEcrilu.addFile(new File("data/ecrilu"));
        foldersCreatorEcrilu.addFile(new File("data/ecrilu"));
        foldersCreatorEcrilu.addFile(new File("data/ecrilu/attr"));
        foldersCreatorEcrilu.addFile(new File("data/ecrilu/audio"));
        foldersCreatorEcrilu.addFile(new File("data/ecrilu/exercises"));
        foldersCreatorEcrilu.addFile(new File("data/ecrilu/sequences"));
        foldersCreatorEcrilu.addFile(new File("data/ecrilu/words"));
        foldersCreatorEcrilu.processCreationOfFolders();

        // ##### Ecrilu structure creation ##### //

        File writingTable = new File(DataService.pathOf("writingTable.json"));

        if (!writingTable.exists())
        {
            try
            {
                Files.copy(
                        Main.getResource("writingTable.json"),
                        writingTable.toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                );
            }
            catch (IOException e)
            {
                Main.logger.error(e.getMessage());
            }
        }

        File bareme = new File(DataService.pathOf("bareme.json"));

        if (!bareme.exists())
        {
            try
            {
                Files.copy(
                        Main.getResource("bareme.json"),
                        bareme.toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                );
            }
            catch (IOException e)
            {
                Main.logger.error(e.getMessage());
            }
        }

        PersistentData.baremeJson = DataService.readJson(BaremeJson.class, "bareme");

        File database = new File(DataService.pathOf("monstroclasse.db"));

        Main.logger.debug(database.getAbsolutePath());

        if (!database.exists())
        {
            Main.logger.info("File creating...");

            try
            {
                Files.createFile(database.toPath());
            }
            catch (IOException e)
            {
                Main.logger.error(e.getMessage());
            }

            Main.logger.info("File created !");

            InputStream inputStream = Main.class.getResourceAsStream("monstroclasse.db.sql");

            if (inputStream != null)
            {
                try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8))
                {
                    // Utilisez un Scanner pour lire le contenu du fichier
                    StringBuilder stringBuilder = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        stringBuilder.append(scanner.nextLine()).append("\n");
                    }

                    try
                    {
                        // Convertissez le contenu lu en tant que chaîne de caractères
                        String fileContent = stringBuilder.toString();

                        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + database.getPath());
                        try (Statement statement = connection.createStatement())
                        {
                            statement.executeUpdate(fileContent);
                            Main.logger.info("Database created !");
                        }
                        connection.close();
                    }
                    catch (SQLException e)
                    {
                        Main.logger.error(e.getMessage());
                    }
                }
                finally
                {
                    try
                    {
                        // Fermez le flux d'entrée une fois que vous avez fini de l'utiliser
                        inputStream.close();
                    }
                    catch (IOException e)
                    {
                        e.fillInStackTrace();
                    }
                }
            }
            else
            {
                Main.logger.error("The file was not found !");
            }
        }
        else
        {
            // TODO Database verification
        }

        /*
            Initialization of the database service
         */

        DatabaseService.initialization();

        // ##### Ecrilu attr_seq creation ##### //

        Path pathOfSequenceAttributionCsv = Path.of(DataService.pathOf("attr_seq.csv", "ecrilu"));
        File sequenceAttributionCsvFile = new File(pathOfSequenceAttributionCsv.toUri());

        if (!sequenceAttributionCsvFile.exists())
        {
            try (Writer writer = new FileWriter(sequenceAttributionCsvFile);
                 CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT))
            {

                ArrayList<EcriluSequenceJson> sequenceJsons = DataService.readAllJsonOf(EcriluSequenceJson.class, "ecrilu", "sequences");

                ArrayList<String> sequenceIds = new ArrayList<>();

                for (EcriluSequenceJson ecriluSequenceJson : sequenceJsons) sequenceIds.add(ecriluSequenceJson.id);

                ArrayList<String> integers = new ArrayList<>(sequenceIds.size());
                for (int i = 0; i < sequenceIds.size(); i++) integers.add("0");

                List<String> header = new ArrayList<>();
                header.add("Pupil");
                header.addAll(sequenceIds);
                csvPrinter.printRecord(header);

                for (Pupil pupil : MonstroclasseMemory.getInstance().getPupils())
                {
                    List<String> content = new ArrayList<>();
                    content.add(pupil.getIdPupil().toString());
                    content.addAll(integers);
                    csvPrinter.printRecord(content);
                }

                Main.logger.info("[attr_seq.csv] created !");
            }
            catch (IOException e)
            {
                Main.logger.error("Error in creation of [attr_seq.csv] !");
            }
        }

        // ##### Ecrilu attr_seq creation ##### //

        // ##### Ecrilu seq_follow creation ##### //

        ArrayList<EcriluSequenceJson> ecriluSequenceJsons = DataService.readAllJsonOf(EcriluSequenceJson.class, "ecrilu", "sequences");

        for (EcriluSequenceJson ecriluSequenceJson : ecriluSequenceJsons)
        {
            Path path = Path.of(
                    DataService.pathOf(ecriluSequenceJson.id + ".csv", "ecrilu", "attr")
            );

            if (!path.toFile().exists())
            {
                CSVManipulator.asWriteMode(path, csvPrinter -> {
                    List<String> header = new ArrayList<>();
                    header.add("Pupil");
                    header.addAll(ecriluSequenceJson.exercises);
                    csvPrinter.printRecord(header);

                    ArrayList<String> integers = new ArrayList<>();

                    for (var i = 0; i < header.size() - 1; i++)
                    {
                        integers.add("0");
                    }

                    for (Pupil pupil : MonstroclasseMemory.getInstance().getPupils())
                    {
                        List<String> content = new ArrayList<>();
                        content.add(pupil.getIdPupil().toString());
                        content.addAll(integers);
                        csvPrinter.printRecord(content);
                    }

                    Main.logger.info("[" + path.toFile().getName() + "] created !");
                });
            }
        }

        // ##### Ecrilu seq_follow creation ##### //

        /*
            Launch Monstroclasse UI
         */
        MonstroclasseAppLoader.main(args);
    }

    // ***************************************************************
    // Main - entry point of the application
    // ***************************************************************

    // ***************************************************************
    // Main - Methods
    // ***************************************************************

    /**
     * Get a resource from asset folder as an InputStream
     * @param path path of the resource from asset folder
     * @return InputStream
     */
    public static InputStream getResource(String... path)
    {
        return Main.class.getResourceAsStream(String.join("/", path));
    }

    // ***************************************************************
    // Main - Methods
    // ***************************************************************
}