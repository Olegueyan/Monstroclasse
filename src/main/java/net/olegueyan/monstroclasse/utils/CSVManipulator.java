package net.olegueyan.monstroclasse.utils;

import net.olegueyan.monstroclasse.function.ConsumerNoError;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Class that allows to manipulate CSV File
 * <br>
 * Implementation of methods that don't have org.apache.commons.csv
 * <br>
 * Factorised method for reading and writing CSV (takes up less space in terms of code)
 */
public final class CSVManipulator
{
    // ------- Logger of CSVManipulator ------- //
    private static final Logger LOGGER = LoggerFactory.getLogger(CSVManipulator.class);
    // ---------------------------------------- //

    /**
     * Read a csv file and do something on CSVParser (Representation of CSV File)
     * @param path path of csv file
     * @param consumer action to operate on CSVParser
     */
    public static void asReadMode(Path path, ConsumerNoError<CSVParser> consumer)
    {
        File file = path.toFile();

        if (file.exists())
        {
            try (Reader reader = new FileReader(file);
                 CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT))
            {
                consumer.accept(csvParser);
            }
            catch (Exception e)
            {
                LOGGER.error("Error in reading [" + file.getName() +  "] : " + e.getMessage());
            }
        }
        else
        {
            LOGGER.error("[" + file.getName() + "] doesn't exist !");
        }
    }

    /**
     * Write a csv file by using a CSVPrinter
     * <br>
     * Replace the file if it exists
     * @param path path of csv file
     * @param consumer action to operate on CSVPrinter
     */
    public static void asWriteMode(Path path, ConsumerNoError<CSVPrinter> consumer)
    {
        File file = path.toFile();

        try (Writer writer = new FileWriter(file);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT))
        {
            consumer.accept(csvPrinter);
        }
        catch (Exception e)
        {
            LOGGER.error("Error in writing [" + file.getName() +  "] : " + e.getMessage());
        }
    }

    /**
     *
     * @param csvPath
     * @param column
     * @param row
     * @return
     */
    public static Optional<String> getValue(Path csvPath, String column, String row)
    {
        AtomicReference<Optional<String>> optionalS = new AtomicReference<>(Optional.empty());

        CSVManipulator.asReadMode(csvPath, csvParser ->
        {
            CSVRecord header = null;
            int rowIndex = -1;

            for (CSVRecord csvRecord : csvParser.getRecords())
            {
                if (header == null)
                {
                    header = csvRecord;
                    rowIndex = ArraysManipulator.getIndexOf(header.values(), column);
                }

                if (Objects.equals(csvRecord.get(0), row))
                {
                    optionalS.set(Optional.of(csvRecord.get(rowIndex)));
                }
            }
        });

        return optionalS.get();
    }

    /**
     *
     * @param csvPath
     * @param value
     * @param column
     * @param row
     */
    public static void replaceValue(Path csvPath, String value, String column, String row)
    {
        ArrayList<List<String>> newRecords = new ArrayList<>();

        CSVManipulator.asReadMode(csvPath, csvParser ->
        {
            CSVRecord header = null;
            int rowIndex = -1;

            for (CSVRecord csvRecord : csvParser.getRecords())
            {
                if (header == null)
                {
                    header = csvRecord;
                    rowIndex = ArraysManipulator.getIndexOf(header.values(), column);
                }

                if (Objects.equals(csvRecord.get(0), row))
                {
                    ArrayList<String> strings = new ArrayList<>(List.of(csvRecord.values()));
                    strings.remove(rowIndex);
                    strings.add(rowIndex, value);
                    newRecords.add(strings);
                }
                else
                {
                    newRecords.add(List.of(csvRecord.values()));
                }
            }
        });

        CSVManipulator.asWriteMode(csvPath, csvPrinter ->
        {
            for (List<String> newRecord : newRecords)
            {
                csvPrinter.printRecord(newRecord);
            }

            LOGGER.info("[" + csvPath.getFileName() + "] created !");
        });
    }

    /**
     *
     * @param records
     * @param path
     */
    public static void writeCSVContent(ArrayList<List<String>> records, Path path)
    {
        CSVManipulator.asWriteMode(path, csvPrinter ->
        {
            for (List<String> newRecord : records) csvPrinter.printRecord(newRecord);
        });
    }

    public static List<String> getCSVRow(Path path, String row)
    {
        List<String> list = new ArrayList<>();

        CSVManipulator.asReadMode(path, csvParser ->
        {
            List<CSVRecord> csvRecords = csvParser.getRecords();

            csvRecords.stream()
                    .filter(csvRecord -> Objects.equals(csvRecord.get(0), row))
                    .forEach(csvRecord -> list.addAll(List.of(csvRecord.values())));
        });

        return list;
    }

    public static List<String> getCSVHeader(Path path)
    {
        List<String> list = new ArrayList<>();
        CSVManipulator.asReadMode(path, csvParser -> list.addAll(List.of(csvParser.getRecords().getFirst().values())));
        return list;
    }
}