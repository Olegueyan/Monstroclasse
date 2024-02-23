package net.olegueyan.monstroclasse.node.ecrilu.grid;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.FontWeight;
import net.olegueyan.monstroclasse.Main;
import net.olegueyan.monstroclasse.common.AppTheme;
import net.olegueyan.monstroclasse.common.Constants;
import net.olegueyan.monstroclasse.entity.Pupil;
import net.olegueyan.monstroclasse.function.Selector;
import net.olegueyan.monstroclasse.node.partial.VerticalScrollPane;
import net.olegueyan.monstroclasse.repository.PupilRepository;
import net.olegueyan.monstroclasse.screen.ScreenStructurationInfo;
import net.olegueyan.monstroclasse.node.FixedNodeCreatorUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.util.*;

public class SequencePupilFollowGridPane
{
    private static final double WIDTH_PERCENTAGE_PUPIL_NODE = 0.16;
    private static final double HEIGHT_PERCENTAGE_PUPIL_NODE = 0.15;

    private static final int EXERCISE_PER_BAR = 6; // Will indicate the number of pages that is required

    private final Map<Integer, GridPane> exercisesBarGridPaneMap = new HashMap<>();
    private final Map<Integer, VerticalScrollPane> contentGridPaneMap = new HashMap<>();

    private int currentPage;

    private Selector<GridPane> gridPaneSelector;
    private Selector<VerticalScrollPane> verticalScrollPaneSelector;

    /**
     * A valid csv type file required

     Pupil, Ex1, Ex2, Ex3
     1, 2, -1, 3
     2, -1, 4, -1
     3, 2, 0, 0

     * @param csvPath
     */
    public SequencePupilFollowGridPane(Pane exercisesBar, Pane contentPane, Path csvPath)
    {
        this.currentPage = 1;

        final double widthExercisesBar = exercisesBar.getPrefWidth();
        final double heightExercisesBar = exercisesBar.getPrefHeight();

        final double widthContentPane = contentPane.getPrefWidth();
        final double heightContentPane = contentPane.getPrefHeight();

        File csvFile = new File(csvPath.toUri());

        if (!csvFile.exists())
        {
            Main.logger.error("[" + csvFile.getName() + "] doesn't exist !");
            return;
        }

        try (Reader reader = new FileReader(csvFile);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT))
        {
            List<CSVRecord> csvRecords = csvParser.getRecords();
            
            CSVRecord header = csvRecords.removeFirst();

            int requiredPage = Math.max(1, (int) Math.ceil((double) (header.size() - 1) / EXERCISE_PER_BAR));

            final double exercisesBarGridWith = widthExercisesBar * (1 - WIDTH_PERCENTAGE_PUPIL_NODE);
            final double widthExercisesBarNode = exercisesBarGridWith / EXERCISE_PER_BAR;

            int indexExercises = 0;

            for (var i = 0; i < requiredPage; i++)
            {
                GridPane exercisesBarGrid = new GridPane();

                int indexExercisesPane = 0;

                ArrayList<Integer> recordHeaderIds = new ArrayList<>();

                while (indexExercisesPane < EXERCISE_PER_BAR)
                {
                    if (indexExercises == (header.size() - 1))
                    {
                        break;
                    }

                    Pane exercisePane = new Pane();

                    FixedNodeCreatorUtils.fixeRegionNode(exercisePane, widthExercisesBarNode, heightExercisesBar);

                    Label label = FixedNodeCreatorUtils.prepareLabel(widthExercisesBarNode, heightExercisesBar, 10, FontWeight.BOLD);
                    label.setAlignment(Pos.CENTER);
                    label.setText(header.get(indexExercises + 1));

                    exercisePane.getChildren().add(label);

                    exercisesBarGrid.add(exercisePane, indexExercisesPane, 0);

                    recordHeaderIds.add(indexExercises + 1);

                    indexExercisesPane++;
                    indexExercises++;
                }

                // VerticalScrollPane

                VerticalScrollPane verticalScrollPane = new VerticalScrollPane(widthContentPane, heightContentPane, 0);

                for (CSVRecord csvRecord : csvRecords)
                {
                    HBox bigPane = new HBox();

                    FixedNodeCreatorUtils.fixeRegionNode(bigPane, widthContentPane, heightContentPane * HEIGHT_PERCENTAGE_PUPIL_NODE);

                    Pupil pupil = Objects.requireNonNull(PupilRepository.read(Integer.parseInt(csvRecord.get(0))));

                    Pane pupilPane = new Pane();

                    final double pupilPaneWidth = widthExercisesBar * WIDTH_PERCENTAGE_PUPIL_NODE;

                    FixedNodeCreatorUtils.fixeRegionNode(pupilPane, pupilPaneWidth, heightContentPane * HEIGHT_PERCENTAGE_PUPIL_NODE);

                    Pane internalPupilPane = new Pane();

                    FixedNodeCreatorUtils.fixeRegionNode(internalPupilPane,pupilPaneWidth - 4, heightContentPane * HEIGHT_PERCENTAGE_PUPIL_NODE - 4);

                    internalPupilPane.setLayoutX(2);
                    internalPupilPane.setLayoutY(2);

                    ScreenStructurationInfo.processCssRounded(internalPupilPane, AppTheme.ECRILU_STUDENT_SUB_NODE_COLOR_HEX, 5);

                    Label label = FixedNodeCreatorUtils.prepareLabel(
                            internalPupilPane.getPrefWidth(),
                            internalPupilPane.getPrefHeight(),
                            Constants.STUDENT_MAX_LENGTH_NAME,
                            FontWeight.NORMAL
                    );

                    label.setAlignment(Pos.CENTER);
                    label.setText(pupil.getName());

                    GridPane gridPanePupilNode = new GridPane();

                    FixedNodeCreatorUtils.fixeRegionNode(gridPanePupilNode, widthContentPane - pupilPaneWidth, heightContentPane * HEIGHT_PERCENTAGE_PUPIL_NODE);

                    final double paddingImageNote = 10;

                    final double widthNoteNode = (widthContentPane - pupilPaneWidth) / EXERCISE_PER_BAR;

                    int counter = 0;

                    for (Integer integer : recordHeaderIds)
                    {
                        Image score = getImageForScore(Integer.parseInt(csvRecord.get(integer)));

                        HBox paneImageNote = new HBox();

                        paneImageNote.setAlignment(Pos.CENTER);

                        paneImageNote.setPrefWidth(widthNoteNode);
                        paneImageNote.setPrefHeight(heightContentPane * HEIGHT_PERCENTAGE_PUPIL_NODE);

                        ImageView view = new ImageView(score);

                        view.setSmooth(false);
                        view.setPreserveRatio(true);

                        view.setFitWidth(widthNoteNode - paddingImageNote * 2);
                        view.setFitHeight(heightContentPane * HEIGHT_PERCENTAGE_PUPIL_NODE - paddingImageNote);

                        paneImageNote.getChildren().add(view);

                        gridPanePupilNode.add(paneImageNote, counter, 0);

                        counter++;
                    }

                    bigPane.getChildren().add(pupilPane);
                    pupilPane.getChildren().add(internalPupilPane);
                    internalPupilPane.getChildren().add(label);

                    bigPane.getChildren().add(gridPanePupilNode);

                    verticalScrollPane.addNode(bigPane);
                }

                FixedNodeCreatorUtils.fixeRegionNode(exercisesBarGrid, exercisesBarGridWith, heightExercisesBar);

                exercisesBarGrid.setLayoutX(widthExercisesBar - exercisesBarGridWith);

                exercisesBar.getChildren().add(exercisesBarGrid);
                this.exercisesBarGridPaneMap.put(i + 1, exercisesBarGrid);

                contentPane.getChildren().add(verticalScrollPane);
                this.contentGridPaneMap.put(i + 1, verticalScrollPane);
            }
        }
        catch (Exception e)
        {
            System.err.println("Erreur lors de la lecture du fichier CSV : " + e.getMessage());
        }

        this.exercisesBarGridPaneMap.forEach((integer, gridPane) -> gridPane.setVisible(false));

        this.exercisesBarGridPaneMap
                .entrySet()
                .stream()
                .filter(integerGridPaneEntry -> integerGridPaneEntry.getKey() == this.currentPage)
                .forEach(integerGridPaneEntry -> integerGridPaneEntry.getValue().setVisible(true));

        this.contentGridPaneMap.forEach((integer, verticalScrollPane) -> verticalScrollPane.setVisible(false));

        this.contentGridPaneMap
                .entrySet()
                .stream()
                .filter(integerVerticalScrollPaneEntry -> integerVerticalScrollPaneEntry.getKey() == this.currentPage)
                .forEach(integerVerticalScrollPaneEntry -> integerVerticalScrollPaneEntry.getValue().setVisible(true));

        this.gridPaneSelector = new Selector<>(new ArrayList<>(this.exercisesBarGridPaneMap.values()));
        this.verticalScrollPaneSelector = new Selector<>(new ArrayList<>(this.contentGridPaneMap.values()));

        this.gridPaneSelector.setOnChange(gridPaneSelector1 ->
        {
            this.exercisesBarGridPaneMap.forEach((integer, gridPane) -> gridPane.setVisible(false));

            this.exercisesBarGridPaneMap
                    .entrySet()
                    .stream()
                    .filter(integerGridPaneEntry -> integerGridPaneEntry.getKey() == this.currentPage)
                    .forEach(integerGridPaneEntry -> integerGridPaneEntry.getValue().setVisible(true));
        });

        this.verticalScrollPaneSelector.setOnChange(verticalScrollPaneSelector1 ->
        {
            this.contentGridPaneMap.forEach((integer, verticalScrollPane) -> verticalScrollPane.setVisible(false));

            this.contentGridPaneMap
                    .entrySet()
                    .stream()
                    .filter(integerVerticalScrollPaneEntry -> integerVerticalScrollPaneEntry.getKey() == this.currentPage)
                    .forEach(integerVerticalScrollPaneEntry -> integerVerticalScrollPaneEntry.getValue().setVisible(true));
        });
    }

    public void bindSelector(Node left, Node right)
    {
        left.setVisible(this.gridPaneSelector.hasPrevious());
        right.setVisible(this.gridPaneSelector.hasNext());
        left.setOnMouseClicked(mouseEvent ->
        {
            this.currentPage--;
            this.gridPaneSelector.previous();
            this.verticalScrollPaneSelector.previous();
            left.setVisible(this.gridPaneSelector.hasPrevious());
            right.setVisible(this.gridPaneSelector.hasNext());
        });
        right.setOnMouseClicked(mouseEvent ->
        {
            this.currentPage++;
            this.gridPaneSelector.next();
            this.verticalScrollPaneSelector.next();
            left.setVisible(this.gridPaneSelector.hasPrevious());
            right.setVisible(this.gridPaneSelector.hasNext());
        });
    }

    public static Image getImageForScore(int score)
    {
        switch (score)
        {
            case 1 -> {
                return Constants.ONE_STAR;
            }
            case 2 -> {
                return Constants.TWO_STAR;
            }
            case 3 -> {
                return Constants.THREE_STAR;
            }
            case 4 -> {
                return Constants.PERFECT_NOTE;
            }
            default -> {
                return null;
            }
        }
    }
}