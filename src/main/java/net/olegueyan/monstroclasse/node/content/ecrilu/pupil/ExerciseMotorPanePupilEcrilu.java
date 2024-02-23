package net.olegueyan.monstroclasse.node.content.ecrilu.pupil;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import net.olegueyan.monstroclasse.common.AppTheme;
import net.olegueyan.monstroclasse.common.Constants;
import net.olegueyan.monstroclasse.common.PersistentData;
import net.olegueyan.monstroclasse.function.DualCounter;
import net.olegueyan.monstroclasse.json.ecrilu.EcriluExerciseJson;
import net.olegueyan.monstroclasse.json.ecrilu.EcriluWordJson;
import net.olegueyan.monstroclasse.json.ecritoire.EcritoireTone;
import net.olegueyan.monstroclasse.screen.ScreenStructurationInfo;
import net.olegueyan.monstroclasse.service.DataService;
import net.olegueyan.monstroclasse.node.FixedNodeCreatorUtils;
import net.olegueyan.monstroclasse.utils.MonstroclasseUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.function.Consumer;

public class ExerciseMotorPanePupilEcrilu extends Pane
{
    // ***************************************************************
    // ExerciseMotorPanePupilEcrilu - ATTRIBUTES
    // ***************************************************************

    private final EcriluExerciseJson ecriluExerciseJson;

    private Pane paneExerciseLoader;

    private Pane paneWrittenWord;

    private int maxWords = 12;

    private Image imageNotDone = Constants.INTERROG_IMAGE;

    private Image imageInDoing = Constants.EDIT_IMAGE;

    private Image imageDone = Constants.CHECK_IMAGE;

    private final ObservableList<EcritoireTone> currentWrittenWord = FXCollections.observableList(new LinkedList<>());

    private boolean exerciseCompleted;

    private ArrayList<QuestionWordButtonPupilEcrilu> words;

    private QuestionWordButtonPupilEcrilu activeStudentWordButton = null;

    private ArrayList<QuestionWordButtonPupilEcrilu> notFinished = new ArrayList<>();
    private ArrayList<QuestionWordButtonPupilEcrilu> completed = new ArrayList<>();

    private boolean isWriting = false;

    private ColorationSyntaxMode syntaxMode = ColorationSyntaxMode.COLOR_ALTERNATE;

    // ------- Mistake counter ------- //
    private int mistakes = 0;
    // ------------------------------- //

    // ***************************************************************
    // END
    // ***************************************************************

    public ExerciseMotorPanePupilEcrilu(EcriluExerciseJson ecriluExerciseJson, Pane paneExerciseLoader, Pane paneWrittenWord, Pane paneWordsOfExercise, int maxWords, Image imageNotDone, Image imageInDoing, Image imageDone, Consumer<EcriluWordJson> onWordClicked)
    {
        final double spacing = 2.5;

        this.ecriluExerciseJson = ecriluExerciseJson;
        this.paneExerciseLoader = paneExerciseLoader;
        this.paneWrittenWord = paneWrittenWord;
        this.maxWords = maxWords;
        this.imageNotDone = imageNotDone;
        this.imageInDoing = imageInDoing;
        this.imageDone = imageDone;

        double fontSize = ScreenStructurationInfo.getAdjustedFontSize(
                32,
                this.paneWrittenWord.getPrefWidth(),
                this.paneWrittenWord.getPrefHeight()
        );

        Font font = Font.font("Verdana", FontWeight.BOLD, fontSize);

        final double squareWordButton = this.paneExerciseLoader.getPrefHeight();

        // 4x3

        GridPane gridPane = new GridPane();

        gridPane.setPadding(new Insets(0));
        gridPane.setHgap(0.05);
        gridPane.setVgap(0);

        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();

        for (var x = 0; x < 3; x++)
        {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / 3.0);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        for (var y = 0; y < 4; y++)
        {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0 / 4.0);
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        FixedNodeCreatorUtils.fixeRegionNode(gridPane, paneWordsOfExercise.getPrefWidth(), paneWordsOfExercise.getPrefHeight());

        paneWordsOfExercise.getChildren().add(gridPane);

        DualCounter dualCounter = new DualCounter(3, 4);

        final double wordLabelWidth = paneWordsOfExercise.getPrefWidth() / 4;
        final double wordLabelHeight = paneWordsOfExercise.getPrefHeight() / 3;

        ArrayList<String> unshuffled = new ArrayList<>(this.ecriluExerciseJson.words);

        // Shuffle words
        Collections.shuffle(this.ecriluExerciseJson.words);

        for (var i = 0; i < this.ecriluExerciseJson.words.size(); i++)
        {
            if (i < this.maxWords)
            {
                String wordShuffled = this.ecriluExerciseJson.words.get(i);
                String wordUnshuffled = unshuffled.get(i);

                EcriluWordJson ecriluWordJsonShuffled = DataService.readJson(EcriluWordJson.class, wordShuffled, "ecrilu", "words");
                EcriluWordJson ecriluWordJsonUnshuffled = DataService.readJson(EcriluWordJson.class, wordUnshuffled, "ecrilu", "words");
                
                // Add word to word pane

                Label label = FixedNodeCreatorUtils.prepareLabel(wordLabelWidth, wordLabelHeight, 32, FontWeight.BOLD);
                label.setText(ecriluWordJsonShuffled.word);
                label.setAlignment(Pos.CENTER);

                label.setOnMouseEntered(mouseEvent ->
                {
                    label.setCursor(Cursor.HAND);
                    label.setBackground(Background.fill(Color.web(AppTheme.ECRILU_PAGINATION_COLOR_BUTTON)));
                });

                label.setOnMouseExited(mouseEvent ->
                {
                    label.setCursor(Cursor.DEFAULT);
                    label.setBackground(Background.fill(Color.web(AppTheme.SUB_PANE_COLOR_HEX)));
                });

                label.setOnMouseClicked(mouseEvent -> onWordClicked.accept(ecriluWordJsonShuffled));

                gridPane.add(label, dualCounter.current().get1(), dualCounter.current().get0());

                dualCounter.increment();

                QuestionWordButtonPupilEcrilu questionWordButtonPupilEcrilu = new QuestionWordButtonPupilEcrilu(
                        this.imageNotDone,
                        this.imageInDoing,
                        this.imageDone,
                        ecriluWordJsonUnshuffled,
                        (eswb) -> {

                            if (this.completed.contains(eswb)) return;

                            if (this.activeStudentWordButton == null)
                            {
                                this.activeStudentWordButton = eswb;

                                eswb.setInDoing();

                                String audioPath = DataService.pathOf(eswb.getEcriluWordJson().audioPath, "ecrilu", "audio");
                                MonstroclasseUtils.playAudio(audioPath);

                                currentWrittenWord.clear();
                                this.isWriting = false;
                                currentWrittenWord.addAll(eswb.getEcriluWordJson().composition);
                            }
                            else if (this.activeStudentWordButton == eswb)
                            {
                                if (!PersistentData.anAudioIsPlaying)
                                {
                                    String audioPath = DataService.pathOf(eswb.getEcriluWordJson().audioPath, "ecrilu", "audio");
                                    MonstroclasseUtils.playAudio(audioPath);
                                    currentWrittenWord.clear();
                                    this.isWriting = false;
                                    currentWrittenWord.addAll(eswb.getEcriluWordJson().composition);
                                }
                            }
                            else
                            {
                                this.activeStudentWordButton.setNotDone();

                                if (PersistentData.anAudioIsPlaying)
                                {
                                    PersistentData.currentPlayedAudio.stop();
                                }

                                this.activeStudentWordButton = eswb;

                                String audioPath = DataService.pathOf(eswb.getEcriluWordJson().audioPath, "ecrilu", "audio");

                                MonstroclasseUtils.playAudio(audioPath);

                                eswb.setInDoing();

                                currentWrittenWord.clear();
                                this.isWriting = false;
                                currentWrittenWord.addAll(eswb.getEcriluWordJson().composition);
                            }
                        });

                FixedNodeCreatorUtils.fixeRegionNode(questionWordButtonPupilEcrilu, squareWordButton, squareWordButton);

                questionWordButtonPupilEcrilu.setLayoutX((i * spacing) + (i * squareWordButton));
                questionWordButtonPupilEcrilu.setLayoutY((this.paneExerciseLoader.getPrefHeight() - squareWordButton) / 2);

                this.paneExerciseLoader.getChildren().add(questionWordButtonPupilEcrilu);

                this.notFinished.add(questionWordButtonPupilEcrilu);
            }
        }

        this.currentWrittenWord.addListener((ListChangeListener<? super EcritoireTone>) change ->
        {
            boolean alternateColor = false;

            this.paneWrittenWord.getChildren().clear();

            HBox hBox = new HBox();
            hBox.setPadding(new Insets(5));
            hBox.setSpacing(0);
            hBox.setAlignment(Pos.CENTER_LEFT);

           FixedNodeCreatorUtils.forcePrefDimension(hBox);

            hBox.setPrefWidth(this.paneWrittenWord.getPrefWidth());
            hBox.setPrefHeight(this.paneWrittenWord.getPrefHeight());

            this.paneWrittenWord.getChildren().add(hBox);

            for (EcritoireTone composite : this.currentWrittenWord)
            {
                Text text = new Text(composite.tone);
                text.setFont(font);

                switch (this.syntaxMode)
                {
                    case BLACK -> text.setFill(Color.BLACK);
                    case COLOR_ALTERNATE ->
                    {
                        if (alternateColor)
                        {
                            text.setFill(Color.web(AppTheme.PEER_TONE_COLOR_HEX)); // Couleur alternée
                        }
                        else
                        {
                            text.setFill(Color.web(AppTheme.ODD_TONE_COLOR_HEX));  // Couleur alternée
                        }
                        alternateColor = !alternateColor;
                    }
                    case null, default -> throw new RuntimeException("Unexpected error !");
                }

                hBox.getChildren().add(text);
            }
        });
    }

    public ObservableList<EcritoireTone> getCurrentWrittenWord()
    {
        return this.currentWrittenWord;
    }

    public void success()
    {
        this.activeStudentWordButton.setDone();
        this.notFinished.remove(this.activeStudentWordButton);
        this.completed.add(this.activeStudentWordButton);
        this.activeStudentWordButton = null;
        this.setIsWriting(false);
        this.currentWrittenWord.clear();

        if (this.notFinished.isEmpty())
        {
            this.exerciseCompleted = true;
        }
    }

    public void setColorationSyntaxMode(ColorationSyntaxMode syntaxMode)
    {
        EcritoireTone[] ecritoireTones = this.currentWrittenWord.toArray(new EcritoireTone[0]);
        this.currentWrittenWord.clear();
        this.syntaxMode = syntaxMode;
        this.currentWrittenWord.addAll(ecritoireTones);
    }

    public boolean isExerciseCompleted()
    {
        return this.exerciseCompleted;
    }

    public static class Builder
    {
        private EcriluExerciseJson ecriluExerciseJson;

        private Pane paneExerciseLoader;

        private Pane paneWrittenWord;

        private Pane paneWordsOfExercise;

        private int maxWords = 12;

        private Image imageNotDone = Constants.INTERROG_IMAGE;

        private Image imageInDoing = Constants.EDIT_IMAGE;

        private Image imageDone = Constants.CHECK_IMAGE;

        private Consumer<EcriluWordJson> onWordClicked = null;

        public Builder withExerciseJson(EcriluExerciseJson ecriluExerciseJson)
        {
            this.ecriluExerciseJson = ecriluExerciseJson;

            return this;
        }

        public Builder withPaneExerciseLoader(Pane paneExerciseLoader)
        {
            this.paneExerciseLoader = paneExerciseLoader;

            return this;
        }

        public Builder withPaneWrittenWord(Pane paneWrittenWord)
        {
            this.paneWrittenWord = paneWrittenWord;

            return this;
        }

        public Builder withPaneWordsOfExercise(Pane paneWordsOfExercise)
        {
            this.paneWordsOfExercise = paneWordsOfExercise;

            return this;
        }

        public Builder withMaxWords(int maxWords)
        {
            this.maxWords = maxWords;

            return this;
        }

        public Builder withImageNotDone(Image imageNotDone)
        {
            this.imageNotDone = imageNotDone;

            return this;
        }

        public Builder withImageInDoing(Image imageInDoing)
        {
            this.imageInDoing = imageInDoing;

            return this;
        }

        public Builder withImageDone(Image imageDone)
        {
            this.imageDone = imageDone;

            return this;
        }

        public Builder withOnWordClicked(Consumer<EcriluWordJson> onWordClicked)
        {
            this.onWordClicked = onWordClicked;

            return this;
        }

        public ExerciseMotorPanePupilEcrilu build()
        {
            return new ExerciseMotorPanePupilEcrilu(ecriluExerciseJson, paneExerciseLoader, paneWrittenWord, paneWordsOfExercise, maxWords, imageNotDone, imageInDoing, imageDone, onWordClicked);
        }
    }

    public EcriluExerciseJson getEcriluExerciseJson()
    {
        return this.ecriluExerciseJson;
    }

    public boolean getIsWriting()
    {
        return this.isWriting;
    }

    public void setIsWriting(boolean isWriting)
    {
        this.isWriting = isWriting;
    }

    public @Nullable QuestionWordButtonPupilEcrilu getActiveStudentWordButton()
    {
        return this.activeStudentWordButton;
    }

    public int getMistakes()
    {
        return this.mistakes;
    }

    public void mistake()
    {
        this.mistakes++;
    }

    public void reshowWord()
    {
        this.isWriting = false;
        currentWrittenWord.addAll(this.activeStudentWordButton.getEcriluWordJson().composition);
    }

    public enum ColorationSyntaxMode
    {
        COLOR_ALTERNATE,
        BLACK
    }
}
