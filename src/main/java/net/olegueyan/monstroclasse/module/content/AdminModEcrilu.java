package net.olegueyan.monstroclasse.module.content;

import javafx.scene.image.Image;
import net.olegueyan.monstroclasse.Main;
import net.olegueyan.monstroclasse.common.MonstroclasseMemory;
import net.olegueyan.monstroclasse.common.PersistentData;
import net.olegueyan.monstroclasse.entity.Pupil;
import net.olegueyan.monstroclasse.event.SqlEventListener;
import net.olegueyan.monstroclasse.event.SqlEventType;
import net.olegueyan.monstroclasse.event.ecrilu.EventListenerFocusedExercise;
import net.olegueyan.monstroclasse.event.ecrilu.EventListenerFocusedSequence;
import net.olegueyan.monstroclasse.event.ecrilu.EventListenerFocusedWord;
import net.olegueyan.monstroclasse.integration.SceneIntegration;
import net.olegueyan.monstroclasse.json.ecrilu.EcriluExerciseJson;
import net.olegueyan.monstroclasse.json.ecrilu.EcriluSequenceJson;
import net.olegueyan.monstroclasse.json.ecrilu.EcriluWordJson;
import net.olegueyan.monstroclasse.module.IsModule;
import net.olegueyan.monstroclasse.service.DataService;
import net.olegueyan.monstroclasse.service.EntireEcritoireService;
import net.olegueyan.monstroclasse.utils.CSVManipulator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.*;

public class AdminModEcrilu extends SceneIntegration<AdminModEcriluController> implements IsModule, SqlEventListener, EventListenerFocusedExercise, EventListenerFocusedSequence, EventListenerFocusedWord
{
    // ------- Logger of AdminModEcrilu ------- //
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminModEcrilu.class);
    // ---------------------------------------- //

    // ------- make AdminModEcrilu a Singleton ------- //
    private static AdminModEcrilu INSTANCE;
    // ----------------------------------------------- //

    @Override
    public void onActive()
    {
        // ------- Load the service of "ecritoire" ------- //
        EntireEcritoireService.load();
        // ----------------------------------------------- //

        // LOG //
        LOGGER.info("[" + "Ecritoire" + "]" + " loaded !");

        // ------- Display avatar of current connected account ------- //
        this.controller.currentAccountImage.setImage(PersistentData.currentConnectedAccount.image());
        // ----------------------------------------------------------- //

        // ------- Initialisation of PaneHandler component ------- //
        this.paneHandler.addPane("home", this.controller.paneHome);
        this.paneHandler.addPane("wordsManager", this.controller.paneWordsManager);
        this.paneHandler.addPane("wordFeature", this.controller.paneWordFeature);
        this.paneHandler.addPane("wordDeleteConfirm", this.controller.paneWordDeleteConfirm);
        this.paneHandler.addPane("exercisesManager", this.controller.paneExercisesManager);
        this.paneHandler.addPane("exerciseFeature", this.controller.paneExerciseFeature);
        this.paneHandler.addPane("exerciseFeatureEncoding", this.controller.paneExerciseFeatureEncoding);
        this.paneHandler.addPane("exerciseDeleteConfirm", this.controller.paneExerciseDeleteConfirm);
        this.paneHandler.addPane("sequencesManager", this.controller.paneSequencesManager);
        this.paneHandler.addPane("sequenceFeature", this.controller.paneSequenceFeature);
        this.paneHandler.addPane("sequenceDeleteConfirm", this.controller.paneSequenceDeleteConfirm);
        this.paneHandler.addPane("sequenceAttributionManager", this.controller.paneSequenceAttributionManager);
        this.paneHandler.addPane("sequenceAttributionEdit", this.controller.paneSequenceAttributionEdit);
        this.paneHandler.addPane("pupilFollowSequenceSelection", this.controller.panePupilFollowSequenceSelection);
        this.paneHandler.addPane("pupilFollowSequence", this.controller.panePupilFollowSequence);
        // ------------------------------------------------------- //

        // ------- Show home pane (entry pane) ------- //
        this.paneHandler.hideAll();
        this.paneHandler.show("home");
        // ------------------------------------------- //
    }

    @Override
    public void onDisable()
    {
        // ------- Unload the service of "ecritoire" ------- //
        EntireEcritoireService.unload();
        // ------------------------------------------------- //

        // LOG //
        LOGGER.info("[" + "Ecritoire" + "]" + " loaded !");
    }

    /**
     * Constructor of AdminModEcrilu
     * <br>
     * It creates the scene of Ecrilu Admin Module
     */
    public AdminModEcrilu()
    {
        // ------- Load the scene contained in "amodecrilu" fxml file ------- //
        super(Objects.requireNonNull(AdminModStudentManager.class.getResource("amodecrilu.fxml")));
        // ------------------------------------------------------------------ //
    }

    /**
     * Singleton
     * @return AdminModEcrilu
     */
    public static AdminModEcrilu getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new AdminModEcrilu();
        }
        return INSTANCE;
    }

    // ***************************************************************
    // AdminModEcrilu - IsModule
    // ***************************************************************

    @Override
    public String name()
    {
        return "Ecrilu";
    }

    @Override
    public String reference()
    {
        return "EA1M";
    }

    @Override
    public SceneIntegration<?> scene()
    {
        return this;
    }

    @Override
    public Image avatar()
    {
        InputStream is = Objects.requireNonNull(Main.class.getResourceAsStream("module/ecrilu.png"));
        return new Image(is);
    }

    @Override
    public boolean admin()
    {
        return true;
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // AdminModEcrilu - SqlEventListener
    // ***************************************************************

    @Override
    public void onSqlPerform(Object[] entities, SqlEventType sqlEventType)
    {
        Path pathOfSequenceAttributionCsv = Path.of(DataService.pathOf("attr_seq.csv", "ecrilu"));
        File sequenceAttributionCsvFile = new File(pathOfSequenceAttributionCsv.toUri());

        if (!sequenceAttributionCsvFile.exists())
        {
            Main.logger.error("[attr_seq.csv] doesn't exist !");
        }

        if (entities[0] instanceof Pupil pupil)
        {
            switch (sqlEventType)
            {
                case INSERT ->
                {
                    ArrayList<List<String>> newRecords = new ArrayList<>();

                    try (Reader reader = new FileReader(sequenceAttributionCsvFile);
                         CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT))
                    {
                        for (CSVRecord csvRecord : csvParser.getRecords())
                        {
                            newRecords.add(List.of(csvRecord.values()));
                        }
                    }
                    catch (Exception e)
                    {
                        System.err.println("Erreur lors de la lecture du fichier CSV : " + e.getMessage());
                    }

                    ArrayList<String> strings = new ArrayList<>();

                    strings.add(String.valueOf(pupil.getIdPupil()));

                    for (var i = 0; i < newRecords.getFirst().size() - 1; i++)
                    {
                        strings.add("0");
                    }

                    newRecords.add(strings);

                    CSVManipulator.writeCSVContent(newRecords, pathOfSequenceAttributionCsv);

                    processEcriluAttrPupilCreated(pupil);
                }
                case DELETE ->
                {
                    ArrayList<List<String>> newRecords = new ArrayList<>();

                    try (Reader reader = new FileReader(sequenceAttributionCsvFile);
                         CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT))
                    {
                        for (CSVRecord csvRecord : csvParser.getRecords())
                        {
                            try
                            {
                                if (Integer.parseInt(csvRecord.values()[0]) != pupil.getIdPupil())
                                {
                                    newRecords.add(List.of(csvRecord.values()));
                                }
                            }
                            catch (NumberFormatException ignored)
                            {
                                newRecords.add(List.of(csvRecord.values()));
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        System.err.println("Erreur lors de la lecture du fichier CSV : " + e.getMessage());
                    }

                    CSVManipulator.writeCSVContent(newRecords, pathOfSequenceAttributionCsv);

                    processEcriluAttrPupilDeleted(pupil);
                }
                case null, default -> {}
            }
        }
    }

    private static void processEcriluAttrPupilCreated(Pupil pupil)
    {
        ArrayList<EcriluSequenceJson> ecriluSequenceJsons = DataService.readAllJsonOf(EcriluSequenceJson.class, "ecrilu", "sequences");

        for (EcriluSequenceJson ecriluSequenceJson : ecriluSequenceJsons)
        {
            Path path = Path.of(
                    DataService.pathOf(ecriluSequenceJson.id + ".csv", "ecrilu", "attr")
            );

            CSVManipulator.asReadMode(path, csvParser ->
            {
                ArrayList<List<String>> newRecords = new ArrayList<>();

                for (CSVRecord csvRecord : csvParser.getRecords())
                {
                    newRecords.add(List.of(csvRecord.values()));
                }

                ArrayList<String> strings = new ArrayList<>();

                strings.add(String.valueOf(pupil.getIdPupil()));

                for (var i = 0; i < newRecords.getFirst().size() - 1; i++)
                {
                    strings.add("0");
                }

                newRecords.add(strings);

                CSVManipulator.writeCSVContent(newRecords, path);
            });
        }
    }

    private static void processEcriluAttrPupilDeleted(Pupil pupil)
    {
        ArrayList<EcriluSequenceJson> ecriluSequenceJsons = DataService.readAllJsonOf(EcriluSequenceJson.class, "ecrilu", "sequences");

        for (EcriluSequenceJson ecriluSequenceJson : ecriluSequenceJsons)
        {
            Path path = Path.of(
                    DataService.pathOf(ecriluSequenceJson.id + ".csv", "ecrilu", "attr")
            );

            CSVManipulator.asReadMode(path, csvParser ->
            {
                ArrayList<List<String>> newRecords = new ArrayList<>();

                for (CSVRecord csvRecord : csvParser.getRecords())
                {
                    try
                    {
                        if (Integer.parseInt(csvRecord.values()[0]) != pupil.getIdPupil())
                        {
                            newRecords.add(List.of(csvRecord.values()));
                        }
                    }
                    catch (NumberFormatException ignored)
                    {
                        newRecords.add(List.of(csvRecord.values()));
                    }
                }

                CSVManipulator.writeCSVContent(newRecords, path);
            });
        }
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // AdminModEcrilu - EventListenerFocusedWord
    // ***************************************************************

    @Override
    public void onWordCreated(EcriluWordJson word)
    {
        LOGGER.info("[" + word.id + "]" + " has been created !");
    }

    @Override
    public void onWordUpdated(EcriluWordJson oldWord, EcriluWordJson newWord)
    {
        LOGGER.info("[" + oldWord.id + "]" + " has been updated ! (" + newWord.id + ")");

        // ------- Modify the file in recreated it ------- //
        ArrayList<EcriluExerciseJson> ecriluExerciseJsons = DataService.readAllJsonOf(EcriluExerciseJson.class, "ecrilu", "exercises");
        // ----------------------------------------------- //

        for (EcriluExerciseJson ecriluExerciseJson : ecriluExerciseJsons)
        {
            for (var i = 0; i < ecriluExerciseJson.words.size(); i++)
            {
                String ecriluWordJson = ecriluExerciseJson.words.get(i);

                if (Objects.equals(ecriluWordJson, oldWord.id))
                {
                    ecriluExerciseJson.words.set(i, newWord.id);
                }
            }

            // ------- Modify the file in recreated it ------- //
            DataService.delete(ecriluExerciseJson.id + ".json", "ecrilu", "exercises");
            DataService.saveJson(ecriluExerciseJson, ecriluExerciseJson.id, "ecrilu", "exercises");
            // ----------------------------------------------- //

            LOGGER.info("[" + ecriluExerciseJson.id + "]" + " has been modified !");
        }
    }

    @Override
    public void onWordDeleted(EcriluWordJson word)
    {
        LOGGER.info("[" + word.id + "]" + " has been deleted !");

        ArrayList<EcriluExerciseJson> ecriluExerciseJsons = DataService.readAllJsonOf(EcriluExerciseJson.class, "ecrilu", "exercises");

        for (EcriluExerciseJson ecriluExerciseJson : ecriluExerciseJsons)
        {
            ecriluExerciseJson.words.removeIf(s -> Objects.equals(s, word.id));
            DataService.delete(ecriluExerciseJson.id + ".json", "ecrilu", "exercises");
            DataService.saveJson(ecriluExerciseJson, ecriluExerciseJson.id, "ecrilu", "exercises");

            LOGGER.info("[" + ecriluExerciseJson.id + "]" + " has been modified !");
        }
    }

    // ***************************************************************
    // END
    // ***************************************************************

    @Override
    public void onExerciseCreated(EcriluExerciseJson exercise)
    {
        LOGGER.info("[" + exercise.id + "]" + " has been created !");
    }

    @Override
    public void onExerciseUpdated(EcriluExerciseJson oldExercise, EcriluExerciseJson newExercise)
    {
        LOGGER.info("[" + oldExercise.id + "]" + " has been updated ! (" + newExercise.id + ")");

        ArrayList<EcriluSequenceJson> ecriluSequenceJsons = DataService.readAllJsonOf(EcriluSequenceJson.class, "ecrilu", "sequences");

        for (EcriluSequenceJson ecriluSequenceJson : ecriluSequenceJsons)
        {
            for (var i = 0; i < ecriluSequenceJson.exercises.size(); i++)
            {
                String ecriluExerciseJson = ecriluSequenceJson.exercises.get(i);

                if (Objects.equals(ecriluExerciseJson, oldExercise.id))
                {
                    ecriluSequenceJson.exercises.set(i, newExercise.id);
                }
            }

            DataService.delete(ecriluSequenceJson.id + ".json", "ecrilu", "sequences");
            DataService.saveJson(ecriluSequenceJson, ecriluSequenceJson.id, "ecrilu", "sequences");

            // attr

            Path sequenceAttr = Path.of(DataService.pathOf(ecriluSequenceJson.id + ".csv", "ecrilu", "attr"));

            CSVManipulator.asReadMode(sequenceAttr, csvParser ->
            {
                CSVRecord header = null;

                ArrayList<List<String>> newRecords = new ArrayList<>();

                for (CSVRecord csvRecord : csvParser.getRecords())
                {
                    if (header == null)
                    {
                        header = csvRecord;
                        int index = -1;

                        for (int i = 0; i < header.values().length; i++)
                        {
                            if (header.values()[i].equals(oldExercise.id))
                            {
                                index = i;
                                break;
                            }
                        }

                        if (index != -1)
                        {
                            List<String> strings = new ArrayList<>(List.of(csvRecord.values()));
                            strings.remove(index);
                            strings.add(index, newExercise.id);
                            newRecords.add(strings);
                        }
                    }
                    else
                    {
                        newRecords.add(List.of(csvRecord.values()));
                    }
                }

                CSVManipulator.writeCSVContent(newRecords, sequenceAttr);
            });

            LOGGER.info("[" + ecriluSequenceJson.id + "]" + " has been updated !");
        }
    }

    @Override
    public void onExerciseDeleted(EcriluExerciseJson exercise)
    {
        LOGGER.info("[" + exercise.id + "]" + " has been deleted !");
    }

    // ***************************************************************
    // AdminModEcrilu - EventListenerFocusedSequence
    // ***************************************************************

    @Override
    public void onSequenceCreated(EcriluSequenceJson sequence)
    {
        LOGGER.info("[" + sequence.id + "]" + " has been created !");

        Path path = Path.of(DataService.pathOf("attr_seq.csv", "ecrilu"));

        CSVManipulator.asReadMode(path, csvParser ->
        {
            ArrayList<List<String>> newRecords = new ArrayList<>();

            boolean firstRecord = true;

            for (CSVRecord csvRecord : csvParser.getRecords())
            {
                ArrayList<String> strings = new ArrayList<>(List.of(csvRecord.values()));
                if (!firstRecord)
                {
                    strings.add("0");
                }
                else
                {
                    strings.add(sequence.id);
                    firstRecord = false;
                }
                newRecords.add(strings);
            }

            CSVManipulator.writeCSVContent(newRecords, path);
        });

        LOGGER.info("[" + path.toFile().getName() + "] created !");

        Path sequenceAttrPath = Path.of(
                DataService.pathOf(sequence.id + ".csv", "ecrilu", "attr")
        );

        CSVManipulator.asWriteMode(sequenceAttrPath, csvPrinter ->
        {
            List<String> header = new ArrayList<>();
            header.add("Pupil");
            header.addAll(sequence.exercises);
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

            LOGGER.info("[" + sequenceAttrPath.toFile().getName() + "] created !");
        });
    }

    @Override
    public void onSequenceUpdated(EcriluSequenceJson oldSequence, EcriluSequenceJson newSequence)
    {
        LOGGER.info("[" + oldSequence.id + "]" + " has been updated ! (" + newSequence.id + ")");

        Path path = Path.of(DataService.pathOf("attr_seq.csv", "ecrilu"));

        CSVManipulator.asReadMode(path, csvParser ->
        {
            CSVRecord header = null;

            ArrayList<List<String>> newRecords = new ArrayList<>();

            for (CSVRecord csvRecord : csvParser.getRecords())
            {
                if (header == null)
                {
                    header = csvRecord;
                    int index = -1;

                    for (int i = 0; i < header.values().length; i++)
                    {
                        if (header.values()[i].equals(oldSequence.id))
                        {
                            index = i;
                            break;
                        }
                    }

                    if (index != -1)
                    {
                        List<String> strings = new ArrayList<>(List.of(csvRecord.values()));
                        strings.remove(index);
                        strings.add(index, newSequence.id);
                        newRecords.add(strings);
                    }
                    else
                    {
                        throw new RuntimeException("Unexpected error !");
                    }
                }
                else
                {
                    newRecords.add(List.of(csvRecord.values()));
                }
            }

            CSVManipulator.writeCSVContent(newRecords, path);
        });

        Path oldSeqAttr = Path.of(DataService.pathOf(oldSequence.id + ".csv", "ecrilu", "attr"));
        Path newSeqAttr = Path.of(DataService.pathOf(newSequence.id + ".csv", "ecrilu", "attr"));

        if (oldSeqAttr.toFile().renameTo(newSeqAttr.toFile()))
        {
            LOGGER.info("[" + oldSequence.id + ".csv" + "] renamed into [" + newSequence.id + ".csv" + "] !");
        }

        LinkedList<String> oldExercise = oldSequence.exercises;
        LinkedList<String> newExercise = newSequence.exercises;

        Set<String> oldSet = new HashSet<>(oldExercise);
        Set<String> newSet = new HashSet<>(newExercise);

        // Deleted Exercises
        Set<String> deletedExercises = new HashSet<>(oldSet);
        deletedExercises.removeAll(newSet);

        // New Exercises
        Set<String> newExercises = new HashSet<>(newSet);
        newExercises.removeAll(oldSet);

        ArrayList<List<String>> newRecords = new ArrayList<>();

        ArrayList<Integer> deletedExercisesId = new ArrayList<>();

        CSVManipulator.asReadMode(newSeqAttr, csvParser ->
        {
            CSVRecord header = null;

            for (CSVRecord csvRecord : csvParser.getRecords())
            {
                if (header == null)
                {
                    header = csvRecord;

                    for (var i = 0; i < header.values().length; i++)
                    {
                        if (deletedExercises.contains(header.values()[i]))
                        {
                            deletedExercisesId.add(i);
                        }
                    }

                    ArrayList<String> strings = new ArrayList<>(List.of(header.values()));
                    for (int i : deletedExercisesId) strings.remove(i);
                    strings.addAll(newExercises);
                    newRecords.add(strings);
                }
                else
                {
                    ArrayList<String> values = new ArrayList<>(List.of(csvRecord.values()));
                    for (int i : deletedExercisesId) values.remove(i);
                    for (String ignored : newExercises) values.add("0");
                    newRecords.add(values);
                }
            }

            CSVManipulator.writeCSVContent(newRecords, newSeqAttr);
        });
    }

    @Override
    public void onSequenceDeleted(EcriluSequenceJson sequence)
    {
        LOGGER.info("[" + sequence.id + "]" + " has been deleted !");

        Path path = Path.of(DataService.pathOf("attr_seq.csv", "ecrilu"));

        CSVManipulator.asReadMode(path, csvParser ->
        {
            CSVRecord header = null;

            ArrayList<List<String>> newRecords = new ArrayList<>();

            for (CSVRecord csvRecord : csvParser.getRecords())
            {
                if (header == null)
                {
                    header = csvRecord;
                }
                int index = -1;

                for (int i = 0; i < header.values().length; i++)
                {
                    if (header.values()[i].equals(sequence.id))
                    {
                        index = i;
                        break;
                    }
                }

                if (index != -1)
                {
                    List<String> strings = new ArrayList<>(List.of(csvRecord.values()));
                    strings.remove(index);
                    newRecords.add(strings);
                }
                else
                {
                    throw new RuntimeException("Unexpected error !");
                }

                LOGGER.info("[" + sequence.id + "] was removed from [attr_seq.csv] !");
            }

            CSVManipulator.writeCSVContent(newRecords, path);
        });

        DataService.delete(sequence.id + ".csv", "ecrilu", "attr");

        LOGGER.info("[" + sequence.id + ".csv" + "] deleted !");
    }

    // ***************************************************************
    // END
    // ***************************************************************
}