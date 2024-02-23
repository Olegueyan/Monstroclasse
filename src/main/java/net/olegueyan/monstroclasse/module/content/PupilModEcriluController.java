package net.olegueyan.monstroclasse.module.content;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import net.olegueyan.monstroclasse.Main;
import net.olegueyan.monstroclasse.common.AppTheme;
import net.olegueyan.monstroclasse.common.Constants;
import net.olegueyan.monstroclasse.common.PersistentData;
import net.olegueyan.monstroclasse.entity.Pupil;
import net.olegueyan.monstroclasse.integration.ControllerIntegration;
import net.olegueyan.monstroclasse.json.ecrilu.BaremeJson;
import net.olegueyan.monstroclasse.json.ecrilu.EcriluExerciseJson;
import net.olegueyan.monstroclasse.json.ecrilu.EcriluSequenceJson;
import net.olegueyan.monstroclasse.json.ecrilu.EcriluWordJson;
import net.olegueyan.monstroclasse.json.ecritoire.Ecritoire;
import net.olegueyan.monstroclasse.motor.core.*;
import net.olegueyan.monstroclasse.node.FixedNodeCreatorUtils;
import net.olegueyan.monstroclasse.node.content.ecrilu.pupil.ExerciseMotorPanePupilEcrilu;
import net.olegueyan.monstroclasse.node.content.ecrilu.pupil.QuestionWordButtonPupilEcrilu;
import net.olegueyan.monstroclasse.node.content.ecrilu.ecritoire.EcritoireNode;
import net.olegueyan.monstroclasse.node.partial.FlatLabeledCard;
import net.olegueyan.monstroclasse.node.partial.PaginatorNode;
import net.olegueyan.monstroclasse.portal.Monstroclasse;
import net.olegueyan.monstroclasse.screen.ScreenStructurationInfo;
import net.olegueyan.monstroclasse.service.DataService;
import net.olegueyan.monstroclasse.utils.CSVManipulator;
import net.olegueyan.monstroclasse.utils.MonstroclasseUtils;

import java.nio.file.Path;
import java.util.*;

public class PupilModEcriluController extends ControllerIntegration
{
    /*

        // Core App

    */

    @FXML
    @AnchoredPaneStructuration
    public AnchorPane paneContainer;

    @FXML
    @BannerStructuration
    public ImageView appBanner;

    @FXML
    @DimensionAdjustment(wRef = 92, hRef = 92)
    @OffsetAdjustment(offsetX = 5, offsetY = 5)
    public ImageView currentAccountImage;

    @FXML
    @DimensionAdjustment(wRef = 240, hRef = 28)
    @CoordinatesAdjustment(xRef = 102, yRef = 70)
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "bold", fontRef = 21)
    public Label currentAccountName;

    /*

        // Home Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.ECRILU_COLOR_HEX)
    public Pane paneHome;

    @FXML
    @CorePaneStructuration
    public Pane corePaneHome;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 15, yRef = 531)
    public Button closeModuleBtnHome;

    @FXML
    @ImageViewBinder(fieldName = "closeModuleBtnHome")
    public ImageView closeModuleBtnHomeImage;

    @FXML
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    @DimensionAdjustment(wRef = 940, hRef = 586)
    @CoordinatesAdjustment(xRef = 170, yRef = 15)
    public Pane subPaneHome;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 78)
    @CoordinatesAdjustment(xRef = 0, yRef = 0)
    public Pane internalPaneOneOfHome;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Bold", fontRef = 30)
    @DimensionAdjustment(wRef = 450, hRef = 40)
    @CoordinatesAdjustment(xRef = 245, yRef = 17.5)
    public Label titleSubPaneHome;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 458)
    @CoordinatesAdjustment(xRef = 0, yRef = 78)
    public Pane internalPaneTwoOfHome;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 50)
    @CoordinatesAdjustment(xRef = 0, yRef = 536)
    public Pane internalPaneThreeOfHome;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @RoundedStyle(color = AppTheme.ECRILU_PAGINATION_COLOR_BUTTON, cornerRadius = 8)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    @CoordinatesAdjustment(xRef = 250, yRef = 5)
    public Pane firstPaginationPaneHome;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    public Label firstPaginationLabelHome;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 370, yRef = 5)
    public Button leftPaginationBtnHome;

    @FXML
    @ImageViewBinder(fieldName = "leftPaginationBtnHome")
    public ImageView leftPaginationBtnHomeImage;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Bold", fontRef = 20)
    @DimensionAdjustment(wRef = 75, hRef = 38)
    @CoordinatesAdjustment(xRef = 432.5, yRef = 6)
    public TextField fieldPaginationHome;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 530, yRef = 5)
    public Button rightPaginationBtnHome;

    @FXML
    @ImageViewBinder(fieldName = "rightPaginationBtnHome")
    public ImageView rightPaginationBtnHomeImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @RoundedStyle(color = AppTheme.ECRILU_PAGINATION_COLOR_BUTTON, cornerRadius = 8)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    @CoordinatesAdjustment(xRef = 590, yRef = 5)
    public Pane lastPaginationPaneHome;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    public Label lastPaginationLabelHome;

    /*

        // Exercise Selection Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.ECRILU_COLOR_HEX)
    public Pane paneExerciseSelection;

    @FXML
    @CorePaneStructuration
    public Pane corePaneExerciseSelection;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 15, yRef = 531)
    public Button closeModuleBtnExerciseSelection;

    @FXML
    @ImageViewBinder(fieldName = "closeModuleBtnExerciseSelection")
    public ImageView closeModuleBtnExerciseSelectionImage;

    @FXML
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    @DimensionAdjustment(wRef = 940, hRef = 586)
    @CoordinatesAdjustment(xRef = 170, yRef = 15)
    public Pane subPaneExerciseSelection;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 78)
    @CoordinatesAdjustment(xRef = 0, yRef = 0)
    public Pane internalPaneOneOfExerciseSelection;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Bold", fontRef = 30)
    @DimensionAdjustment(wRef = 450, hRef = 40)
    @CoordinatesAdjustment(xRef = 245, yRef = 17.5)
    public Label titleSubPaneExerciseSelection;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 458)
    @CoordinatesAdjustment(xRef = 0, yRef = 78)
    public Pane internalPaneTwoOfExerciseSelection;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 50)
    @CoordinatesAdjustment(xRef = 0, yRef = 536)
    public Pane internalPaneThreeOfExerciseSelection;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @RoundedStyle(color = AppTheme.ECRILU_PAGINATION_COLOR_BUTTON, cornerRadius = 8)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    @CoordinatesAdjustment(xRef = 250, yRef = 5)
    public Pane firstPaginationPaneExerciseSelection;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    public Label firstPaginationLabelExerciseSelection;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 370, yRef = 5)
    public Button leftPaginationBtnExerciseSelection;

    @FXML
    @ImageViewBinder(fieldName = "leftPaginationBtnExerciseSelection")
    public ImageView leftPaginationBtnExerciseSelectionImage;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Bold", fontRef = 20)
    @DimensionAdjustment(wRef = 75, hRef = 38)
    @CoordinatesAdjustment(xRef = 432.5, yRef = 6)
    public TextField fieldPaginationExerciseSelection;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 530, yRef = 5)
    public Button rightPaginationBtnExerciseSelection;

    @FXML
    @ImageViewBinder(fieldName = "rightPaginationBtnExerciseSelection")
    public ImageView rightPaginationBtnExerciseSelectionImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @RoundedStyle(color = AppTheme.ECRILU_PAGINATION_COLOR_BUTTON, cornerRadius = 8)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    @CoordinatesAdjustment(xRef = 590, yRef = 5)
    public Pane lastPaginationPaneExerciseSelection;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    public Label lastPaginationLabelExerciseSelection;

    /*

        // Exercise Execution Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.ECRILU_COLOR_HEX)
    public Pane paneExerciseExecution;

    @FXML
    @CorePaneStructuration
    public Pane corePaneExerciseExecution;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 15, yRef = 531)
    public Button closeModuleBtnExerciseExecution;

    @FXML
    @ImageViewBinder(fieldName = "closeModuleBtnExerciseExecution")
    public ImageView closeModuleBtnExerciseExecutionImage;

    @FXML
    @DimensionAdjustment(wRef = 1150, hRef = 460)
    @CoordinatesAdjustment(xRef = 100, yRef = 145)
    public Pane ecritoirePaneExerciseExecution;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 55, hRef = 55)
    @CoordinatesAdjustment(xRef = 22.5, yRef = 340)
    public Button leftArrowEcritoireBtnExerciseExecution;

    @FXML
    @ImageViewBinder(fieldName = "leftArrowEcritoireBtnExerciseExecution")
    public ImageView leftArrowEcritoireBtnExerciseExecutionImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 55, hRef = 55)
    @CoordinatesAdjustment(xRef = 22.5, yRef = 340)
    public Button rightArrowEcritoireBtnExerciseExecution;

    @FXML
    @ImageViewBinder(fieldName = "rightArrowEcritoireBtnExerciseExecution")
    public ImageView rightArrowEcritoireBtnExerciseExecutionImage;

    @FXML
    @DimensionAdjustment(wRef = 720, hRef = 135)
    @CoordinatesAdjustment(xRef = 100, yRef = 5)
    public Pane paneExerciseBanner;

    @FXML
    @DimensionAdjustment(wRef = 1150, hRef = 80)
    @CoordinatesAdjustment(xRef = 0, yRef = 0)
    public Pane paneExerciseLoader;

    @FXML
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 5)
    @DimensionAdjustment(wRef = 970, hRef = 50)
    @CoordinatesAdjustment(xRef = 0, yRef = 85)
    public Pane paneWordWrittenExerciseExecution;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 50, hRef = 50)
    @CoordinatesAdjustment(xRef = 1100, yRef = 85)
    public Button validateBtnExerciseExecution;

    @FXML
    @ImageViewBinder(fieldName = "validateBtnExerciseExecution")
    public ImageView validateBtnExerciseExecutionImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 50, hRef = 50)
    @CoordinatesAdjustment(xRef = 1040, yRef = 85)
    public Button restartBtnExerciseExecution;

    @FXML
    @ImageViewBinder(fieldName = "restartBtnExerciseExecution")
    public ImageView restartBtnExerciseExecutionImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 50, hRef = 50)
    @CoordinatesAdjustment(xRef = 980, yRef = 85)
    public Button carriageReturnBtnExerciseExecution;

    @FXML
    @ImageViewBinder(fieldName = "carriageReturnBtnExerciseExecution")
    public ImageView carriageReturnBtnExerciseExecutionImage;

    @FXML
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 3)
    @DimensionAdjustment(wRef = 1150, hRef = 80)
    @CoordinatesAdjustment(xRef = 0, yRef = 0)
    public Pane paneWordsOfExercise;

    /*

        // Close Exercise Execution Confirm Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.ECRILU_COLOR_HEX)
    public Pane paneCloseExerciseExecutionConfirm;

    @FXML
    @CorePaneStructuration
    public Pane corePaneCloseExerciseExecutionConfirm;

    @FXML
    @DimensionAdjustment(wRef = 538, hRef = 302.4)
    @CoordinatesAdjustment(xRef = 371, yRef = 156.8)
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    public Pane subPaneCloseExerciseExecutionConfirm;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 10, yRef = 232.4)
    public Button undoBtnCloseExerciseExecutionConfirm;

    @FXML
    @ImageViewBinder(fieldName = "undoBtnCloseExerciseExecutionConfirm")
    public ImageView undoBtnCloseExerciseExecutionConfirmImage;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 468, yRef = 232.4)
    public Button checkBtnCloseExerciseExecutionConfirm;

    @FXML
    @ImageViewBinder(fieldName = "checkBtnCloseExerciseExecutionConfirm")
    public ImageView checkBtnCloseExerciseExecutionConfirmImage;

    @FXML
    @DimensionAdjustment(wRef = 450, hRef = 200)
    @CoordinatesAdjustment(xRef = 44, yRef = 10)
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "bold", fontRef = 30)
    public Label labelCloseExerciseExecutionConfirm;

    private boolean tryExitExerciseExecution;

    private boolean lockEcritoire = false;

    // ***************************************************************
    // PupilModEcriluController - PROPERTIES
    // ***************************************************************

    // ------- Paginator of Home Pane ------- //
    private PaginatorNode<EcriluSequenceJson> paginatorSequenceNode;
    // -------------------------------------- //

    // ------- Paginator of Exercise Pane ------- //
    private PaginatorNode<EcriluExerciseJson> paginatorExerciseNode;
    // -------------------------------------- //

    // ------- Exercise handler ------- //
    private ExerciseMotorPanePupilEcrilu exerciseMotorPanePupilEcrilu;
    // -------------------------------- //

    // ------- Current sequence ------- //
    private EcriluSequenceJson ecriluSequenceJson = null;
    // -------------------------------- //

    // ------- Current exercise ------- //
    private EcriluExerciseJson ecriluExerciseJson = null;
    // -------------------------------- //

    // ***************************************************************
    // END
    // ***************************************************************

    @Override
    public void loadController()
    {
        this.paneHome.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (observableValue.getValue())
            {
                this.ecriluSequenceJson = null;
                this.ecriluExerciseJson = null;

                Path seqAttrPath = Path.of(DataService.pathOf("attr_seq.csv", "ecrilu"));

                Pupil pupil = PersistentData.currentConnectedAccount.getAccountData(Pupil.class);

                List<String> csvRow = CSVManipulator.getCSVRow(seqAttrPath, String.valueOf(pupil.getIdPupil()));
                List<String> csvHeader = CSVManipulator.getCSVHeader(seqAttrPath);

                ArrayList<EcriluSequenceJson> sequenceJsons = new ArrayList<>();

                for (var i = 1; i < csvHeader.size(); i++)
                {
                    if (Integer.parseInt(csvRow.get(i)) == 1)
                    {
                        sequenceJsons.add(DataService.readJson(
                                EcriluSequenceJson.class,
                                csvHeader.get(i),
                                "ecrilu", "sequences"
                        ));
                    }
                }

                sequenceJsons.sort(Comparator.comparing(ecriluSequenceJson -> ecriluSequenceJson.title));

                this.paginatorSequenceNode = new PaginatorNode.Builder<EcriluSequenceJson>()
                        .withDimension(
                                this.internalPaneTwoOfHome.getPrefWidth() - 20,
                                this.internalPaneTwoOfHome.getPrefHeight() - 20
                        )
                        .withGridDisposition(6, 3)
                        .withToPaginate(sequenceJsons)
                        .withNodeCreator((obj, width, height) ->
                        {
                            Pane pane = new Pane();

                            FixedNodeCreatorUtils.fixeRegionNode(pane, width, height);

                            FlatLabeledCard flatLabeledCard = new FlatLabeledCard(width - 10, height - 10, obj.title);

                            flatLabeledCard.getStyleClass().add("node-flat");
                            flatLabeledCard.getStyleClass().add("-handed");
                            flatLabeledCard.applyCss();

                            flatLabeledCard.setOnMouseClicked(mouseEvent ->
                            {
                                this.ecriluSequenceJson = obj;

                                /* ------- SWITCH TO EXERCISE SELECTION PANE ------- */
                                PupilModEcrilu.getInstance().paneHandler.show("exerciseSelection");
                                /* ------------------------------------------------- */
                            });

                            ScreenStructurationInfo.processCssRounded(
                                    flatLabeledCard,
                                    AppTheme.ECRILU_STUDENT_SUB_NODE_COLOR_HEX,
                                    10
                            );

                            flatLabeledCard.setLayoutX(5);
                            flatLabeledCard.setLayoutY(5);

                            pane.getChildren().add(flatLabeledCard);

                            return pane;
                        })
                        .onPageChange((page, hasPrevious, hasNext) ->
                        {
                            this.fieldPaginationHome.setText(String.valueOf(page));
                            this.leftPaginationBtnHome.setVisible(hasPrevious);
                            this.rightPaginationBtnHome.setVisible(hasNext);
                        })
                        .build();

                this.paginatorSequenceNode.setLayoutX(10);
                this.paginatorSequenceNode.setLayoutY(10);

                this.leftPaginationBtnHome.setVisible(this.paginatorSequenceNode.getPaginator().hasPrevious());
                this.rightPaginationBtnHome.setVisible(this.paginatorSequenceNode.getPaginator().hasNext());

                this.fieldPaginationHome.setText(String.valueOf(this.paginatorSequenceNode.getPaginator().currentPage()));
                this.fieldPaginationHome.setFocusTraversable(false);

                this.internalPaneTwoOfHome.getChildren().add(this.paginatorSequenceNode);
            }
            else
            {
                this.fieldPaginationHome.setText("");
                this.internalPaneTwoOfHome.getChildren().clear();
            }
        });

        this.paneExerciseSelection.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (observableValue.getValue())
            {
                this.titleSubPaneExerciseSelection.setText(this.ecriluSequenceJson.title);

                ArrayList<EcriluExerciseJson> ecriluExerciseJsons = new ArrayList<>();

                for (String string : this.ecriluSequenceJson.exercises)
                {
                    ecriluExerciseJsons.add(DataService.readJson(
                            EcriluExerciseJson.class, string, "ecrilu", "exercises"
                    ));
                }

                this.paginatorExerciseNode = new PaginatorNode.Builder<EcriluExerciseJson>()
                        .withDimension(
                                this.internalPaneTwoOfExerciseSelection.getPrefWidth() - 20,
                                this.internalPaneTwoOfExerciseSelection.getPrefHeight() - 20
                        )
                        .withGridDisposition(6, 2)
                        .withToPaginate(ecriluExerciseJsons)
                        .withNodeCreator((obj, width, height) ->
                        {
                            Pane parentPane = new Pane();

                            FixedNodeCreatorUtils.fixeRegionNode(parentPane, width, height);

                            HBox pane = new HBox();
                            pane.setAlignment(Pos.CENTER);
                            pane.setPadding(new Insets(0));
                            pane.setSpacing(0);

                            FixedNodeCreatorUtils.fixeRegionNode(pane, width - 10, height - 10);

                            pane.setLayoutX(5);
                            pane.setLayoutY(5);

                            final double widthExoPane = width * 0.65;
                            final double widthStarPane = width * 0.35;

                            Pane exoPane = new Pane();

                            FixedNodeCreatorUtils.fixeRegionNode(exoPane, widthExoPane, height);

                            exoPane.setLayoutY(5);

                            FlatLabeledCard flatLabeledCard = new FlatLabeledCard(widthExoPane, height, obj.title);

                            exoPane.getChildren().add(flatLabeledCard);

                            Pane starPane = new Pane();

                            starPane.setLayoutY(5);

                            FixedNodeCreatorUtils.fixeRegionNode(starPane, widthStarPane, height - 10);

                            pane.getChildren().add(exoPane);
                            pane.getChildren().add(starPane);

                            ScreenStructurationInfo.processCssRounded(
                                    pane,
                                    AppTheme.ECRILU_STUDENT_SUB_NODE_COLOR_HEX,
                                    10
                            );

                            pane.getStyleClass().add("node-flat");
                            pane.getStyleClass().add("-handed");
                            pane.applyCss();

                            pane.setOnMouseClicked(mouseEvent ->
                            {
                                this.ecriluExerciseJson = obj;

                                /* ------- SWITCH TO EXERCISE EXECUTION PANE ------- */
                                PupilModEcrilu.getInstance().paneHandler.show("exerciseExecution");
                                /* ------------------------------------------------- */
                            });

                            parentPane.getChildren().add(pane);

                            // Add note

                            Path path = Path.of(DataService.pathOf(
                                    this.ecriluSequenceJson.id + ".csv",
                                    "ecrilu", "attr"
                            ));

                            Pupil pupil = PersistentData.currentConnectedAccount.getAccountData(Pupil.class);

                            Optional<String> actualNote = CSVManipulator.getValue(path,
                                    obj.id, String.valueOf(pupil.getIdPupil()));

                            actualNote.ifPresent(s ->
                            {
                                int actualNoteInteger = Integer.parseInt(s);

                                Image image = getImageFromNote(actualNoteInteger);

                                ImageView imageView = new ImageView(image);

                                imageView.setFitWidth(starPane.getPrefWidth() - 14);
                                imageView.setFitHeight(starPane.getPrefHeight() - 14);

                                imageView.setLayoutX(7);
                                imageView.setLayoutY(7);

                                starPane.getChildren().add(imageView);
                            });

                            return parentPane;
                        })
                        .onPageChange((page, hasPrevious, hasNext) ->
                        {
                            this.fieldPaginationExerciseSelection.setText(String.valueOf(page));
                            this.leftPaginationBtnExerciseSelection.setVisible(hasPrevious);
                            this.rightPaginationBtnExerciseSelection.setVisible(hasNext);
                        })
                        .build();

                this.paginatorExerciseNode.setLayoutX(10);
                this.paginatorExerciseNode.setLayoutY(10);

                this.leftPaginationBtnExerciseSelection.setVisible(this.paginatorExerciseNode.getPaginator().hasPrevious());
                this.rightPaginationBtnExerciseSelection.setVisible(this.paginatorExerciseNode.getPaginator().hasNext());

                this.fieldPaginationExerciseSelection.setText(String.valueOf(this.paginatorExerciseNode.getPaginator().currentPage()));
                this.fieldPaginationExerciseSelection.setFocusTraversable(false);

                this.internalPaneTwoOfExerciseSelection.getChildren().add(this.paginatorExerciseNode);
            }
            else
            {
                this.fieldPaginationExerciseSelection.setText("");
                this.titleSubPaneExerciseSelection.setText("");
                this.internalPaneTwoOfExerciseSelection.getChildren().clear();
            }
        });

        this.paneExerciseExecution.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (this.tryExitExerciseExecution) return;
            if (observableValue.getValue())
            {
                this.paneExerciseBanner.setVisible(true);
                this.paneWordsOfExercise.setVisible(false);

                this.exerciseMotorPanePupilEcrilu = new ExerciseMotorPanePupilEcrilu.Builder()
                        .withExerciseJson(this.ecriluExerciseJson)
                        .withPaneExerciseLoader(this.paneExerciseLoader)
                        .withPaneWrittenWord(this.paneWordWrittenExerciseExecution)
                        .withPaneWordsOfExercise(this.paneWordsOfExercise)
                        .withMaxWords(12)
                        .withImageNotDone(Constants.INTERROG_IMAGE)
                        .withImageInDoing(Constants.IN_DOING_IMAGE)
                        .withImageDone(Constants.CHECK_IMAGE)
                        .withOnWordClicked(ecriluWordJson ->
                        {
                            QuestionWordButtonPupilEcrilu button = this.exerciseMotorPanePupilEcrilu.getActiveStudentWordButton();
                            if (button != null)
                            {
                                if (Objects.equals(ecriluWordJson, button.getEcriluWordJson()))
                                {
                                    this.exerciseMotorPanePupilEcrilu.success();

                                    this.paneExerciseLoader.setVisible(true);
                                    this.carriageReturnBtnExerciseExecution.setVisible(true);
                                    this.restartBtnExerciseExecution.setVisible(true);
                                    this.validateBtnExerciseExecution.setVisible(true);
                                    this.paneWordsOfExercise.setVisible(false);
                                    this.exerciseMotorPanePupilEcrilu.setColorationSyntaxMode(
                                            ExerciseMotorPanePupilEcrilu.ColorationSyntaxMode.COLOR_ALTERNATE
                                    );
                                    this.lockEcritoire = false;

                                    // Check end exercise

                                    if (this.exerciseMotorPanePupilEcrilu.isExerciseCompleted())
                                    {
                                        int mistakes = this.exerciseMotorPanePupilEcrilu.getMistakes();

                                        Path path = Path.of(DataService.pathOf(
                                                this.ecriluSequenceJson.id + ".csv",
                                                "ecrilu", "attr"
                                        ));

                                        String note = String.valueOf(
                                                getNoteFromMistakes(mistakes, PersistentData.baremeJson)
                                        );

                                        Pupil pupil = PersistentData.currentConnectedAccount.getAccountData(Pupil.class);

                                        Optional<String> actualNote = CSVManipulator.getValue(path,
                                                this.ecriluExerciseJson.id, String.valueOf(pupil.getIdPupil()));

                                        actualNote.ifPresentOrElse(s ->
                                        {
                                            try
                                            {
                                                int oldNote = Integer.parseInt(s);
                                                int newNote = Integer.parseInt(note);

                                                if (newNote > oldNote)
                                                {
                                                    CSVManipulator.replaceValue(
                                                            path, note,
                                                            this.ecriluExerciseJson.id, String.valueOf(pupil.getIdPupil())
                                                    );
                                                }
                                            }
                                            catch (NumberFormatException e)
                                            {
                                                CSVManipulator.replaceValue(
                                                        path, note,
                                                        this.ecriluExerciseJson.id, String.valueOf(pupil.getIdPupil())
                                                );
                                            }
                                        }, () -> CSVManipulator.replaceValue(
                                                        path, note,
                                                        this.ecriluExerciseJson.id, String.valueOf(pupil.getIdPupil())
                                                ));

                                        MonstroclasseUtils.playAudio(Constants.getCorrectSoundInputStream());

                                        /* ------- SWITCH TO EXERCISE SELECTION PANE ------- */
                                        PupilModEcrilu.getInstance().paneHandler.show("exerciseSelection");
                                        /* ------------------------------------------------- */
                                    }
                                }
                                else
                                {
                                    MonstroclasseUtils.playAudio(Constants.getErrorSoundInputStream());
                                    this.exerciseMotorPanePupilEcrilu.mistake();
                                }
                            }
                        })
                        .build();

                Ecritoire ecritoire = this.ecriluExerciseJson.ecritoire;

                EcritoireNode ecritoireNode = new EcritoireNode.Builder()
                        .withDimension(this.ecritoirePaneExerciseExecution.getPrefWidth(), this.ecritoirePaneExerciseExecution.getPrefHeight())
                        .withEcritoire(ecritoire)
                        .withPaneProcess(pane -> ScreenStructurationInfo.processCssRounded(pane, AppTheme.SUB_PANE_COLOR_HEX, 10))
                        .withPanePerPage(2)
                        .withSpacing(5)
                        .withToneOnColumn(12)
                        .withEcritoireListener((tone, locked) ->
                        {
                            if (this.lockEcritoire) return;
                            if (this.exerciseMotorPanePupilEcrilu.getActiveStudentWordButton() == null) return;
                            if (!this.exerciseMotorPanePupilEcrilu.getIsWriting())
                            {
                                this.exerciseMotorPanePupilEcrilu.getCurrentWrittenWord().clear();
                                this.exerciseMotorPanePupilEcrilu.setIsWriting(true);
                            }
                            this.exerciseMotorPanePupilEcrilu.getCurrentWrittenWord().add(tone);
                        })
                        .withFilterEcritoireColumnDisposition(new HashMap<>()
                        {{
                            put(0, new String[]{"i", "u", "o", "a", "e", "é", "enan", "è", "on", "au", "ou", "in", "un", "eu", "oi", "oin", "eu"});
                            put(1, new String[]{"m", "r", "n", "l", "p", "t", "s", "f", "d", "j", "g", "z", "c", "b", "x", "ch", "v", "x", "ill", "gn"});
                        }})
                        .build();

                ecritoireNode.bindSelector(this.leftArrowEcritoireBtnExerciseExecution, this.rightArrowEcritoireBtnExerciseExecution);

                this.ecritoirePaneExerciseExecution.getChildren().add(ecritoireNode);
            }
            else
            {
                this.paneExerciseLoader.getChildren().clear();
                this.paneWordWrittenExerciseExecution.getChildren().clear();
                this.paneWordsOfExercise.getChildren().clear();
                this.exerciseMotorPanePupilEcrilu = null;
                this.ecritoirePaneExerciseExecution.getChildren().clear();
                if (PersistentData.anAudioIsPlaying)
                {
                    PersistentData.currentPlayedAudio.stop();
                }
            }
        });

        appBanner.toFront(); // Force to be in front of all objects
        currentAccountImage.toFront(); // Force to be in front of all objects
        currentAccountName.toFront(); // Force to be in front of all objects

        Main.logger.info(this.getClass().getName() + " loaded !");
    }

    /*

        // Home External Events

    */

    @FXML
    public void closeModuleBtnHomeClick(ActionEvent actionEvent)
    {
        /* #_____# ACCESS TO Monstroclasse MODULE #_____# */
        PersistentData.stageHandler.load(Monstroclasse.getInstance());
        /* #_____# ACCESS TO Monstroclasse MODULE #_____# */
    }

    @FXML
    public void firstPaginationPaneHomeClick(MouseEvent mouseEvent)
    {
        this.paginatorSequenceNode.getPaginator().firstPage();
    }

    @FXML
    public void leftPaginationBtnHomeClick(ActionEvent actionEvent)
    {
        this.paginatorSequenceNode.getPaginator().previousPage();
    }


    @FXML
    public void fieldPaginationHomeKeyPressed(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
            try
            {
                int entry = Integer.parseInt(this.fieldPaginationHome.getText());
                this.paginatorSequenceNode.getPaginator().setPage(entry);
            }
            catch (NumberFormatException ignored) {}
            finally
            {
                this.fieldPaginationHome.setText(String.valueOf(this.paginatorSequenceNode.getPaginator().currentPage()));
                this.fieldPaginationHome.positionCaret(this.fieldPaginationHome.getText().length());
            }
        }
    }

    @FXML
    public void rightPaginationBtnHomeClick(ActionEvent actionEvent)
    {
        this.paginatorSequenceNode.getPaginator().nextPage();
    }

    @FXML
    public void lastPaginationPaneHomeClick(MouseEvent mouseEvent)
    {
        this.paginatorSequenceNode.getPaginator().lastPage();
    }

    /*

        // Exercise Selection External Events

    */

    @FXML
    public void closeModuleBtnExerciseSelectionClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO HOME PANE ------- */
        PupilModEcrilu.getInstance().paneHandler.show("home");
        /* ----------------------------------- */
    }

    @FXML
    public void firstPaginationPaneExerciseSelectionClick(MouseEvent mouseEvent)
    {
        this.paginatorExerciseNode.getPaginator().firstPage();
    }


    @FXML
    public void leftPaginationBtnExerciseSelectionClick(ActionEvent actionEvent)
    {
        this.paginatorExerciseNode.getPaginator().previousPage();
    }

    @FXML
    public void fieldPaginationExerciseSelectionKeyPressed(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
            try
            {
                int entry = Integer.parseInt(this.fieldPaginationExerciseSelection.getText());
                this.paginatorExerciseNode.getPaginator().setPage(entry);
            }
            catch (NumberFormatException ignored) {}
            finally
            {
                this.fieldPaginationExerciseSelection.setText(String.valueOf(this.paginatorExerciseNode.getPaginator().currentPage()));
                this.fieldPaginationExerciseSelection.positionCaret(this.fieldPaginationExerciseSelection.getText().length());
            }
        }
    }

    @FXML
    public void rightPaginationBtnExerciseSelectionClick(ActionEvent actionEvent)
    {
        this.paginatorExerciseNode.getPaginator().nextPage();
    }

    @FXML
    public void lastPaginationPaneExerciseSelectionClick(MouseEvent mouseEvent)
    {
        this.paginatorExerciseNode.getPaginator().lastPage();
    }

    /*

        // Exercise Execution External Events

    */

    @FXML
    public void closeModuleBtnExerciseExecutionClick(ActionEvent actionEvent)
    {
        // ------- Allow Exercise Execution to not dump his field ------- //
        this.tryExitExerciseExecution = true;
        // -------------------------------------------------------------- //

        /* ------- SWITCH TO CLOSE EXERCISE EXECUTION CONFIRM PANE ------- */
        PupilModEcrilu.getInstance().paneHandler.show("closeExerciseExecutionConfirm");
        /* --------------------------------------------------------------- */
    }

    @FXML
    public void validateBtnExerciseExecutionClick(ActionEvent actionEvent)
    {
        if (this.exerciseMotorPanePupilEcrilu.getActiveStudentWordButton() == null) return;
        if (!this.exerciseMotorPanePupilEcrilu.getIsWriting())
        {
            this.exerciseMotorPanePupilEcrilu.getCurrentWrittenWord().clear();
            this.exerciseMotorPanePupilEcrilu.setIsWriting(true);
        }
        else
        {
            if (PersistentData.anAudioIsPlaying) PersistentData.currentPlayedAudio.stop();

            EcriluWordJson ecriluWordJson = this.exerciseMotorPanePupilEcrilu
                    .getActiveStudentWordButton()
                    .getEcriluWordJson();

            if (ecriluWordJson.composition.equals(this.exerciseMotorPanePupilEcrilu.getCurrentWrittenWord()))
            {
                this.paneExerciseLoader.setVisible(false);
                this.carriageReturnBtnExerciseExecution.setVisible(false);
                this.restartBtnExerciseExecution.setVisible(false);
                this.validateBtnExerciseExecution.setVisible(false);
                this.paneWordsOfExercise.setVisible(true);
                this.exerciseMotorPanePupilEcrilu.setColorationSyntaxMode(
                        ExerciseMotorPanePupilEcrilu.ColorationSyntaxMode.BLACK
                );
                this.lockEcritoire = true;
            }
            else
            {
                this.exerciseMotorPanePupilEcrilu.getCurrentWrittenWord().clear();
                MonstroclasseUtils.playAudio(Constants.getErrorSoundInputStream());
                this.exerciseMotorPanePupilEcrilu.mistake();

                // Reset the word
                this.exerciseMotorPanePupilEcrilu.reshowWord();
            }
        }
    }

    @FXML
    public void restartBtnExerciseExecutionClick(ActionEvent actionEvent)
    {
        if (this.exerciseMotorPanePupilEcrilu.getActiveStudentWordButton() == null) return;
        if (!this.exerciseMotorPanePupilEcrilu.getIsWriting())
        {
            this.exerciseMotorPanePupilEcrilu.setIsWriting(true);
        }
        this.exerciseMotorPanePupilEcrilu.getCurrentWrittenWord().clear();
    }

    @FXML
    public void carriageReturnBtnExerciseExecutionClick(ActionEvent actionEvent)
    {
        if (this.exerciseMotorPanePupilEcrilu.getActiveStudentWordButton() == null) return;
        if (!this.exerciseMotorPanePupilEcrilu.getIsWriting())
        {
            this.exerciseMotorPanePupilEcrilu.getCurrentWrittenWord().clear();
            this.exerciseMotorPanePupilEcrilu.setIsWriting(true);
        }
        else
        {
            if (!this.exerciseMotorPanePupilEcrilu.getCurrentWrittenWord().isEmpty())
            {
                this.exerciseMotorPanePupilEcrilu.getCurrentWrittenWord().removeLast();
            }
        }
    }

    /*

        // Close Exercise Execution Confirm External Events

    */

    @FXML
    public void checkBtnCloseExerciseExecutionConfirmClick(ActionEvent actionEvent)
    {
        this.paneExerciseLoader.getChildren().clear();
        this.paneWordWrittenExerciseExecution.getChildren().clear();
        this.paneWordsOfExercise.getChildren().clear();
        this.exerciseMotorPanePupilEcrilu = null;
        this.ecritoirePaneExerciseExecution.getChildren().clear();
        if (PersistentData.anAudioIsPlaying)
        {
            PersistentData.currentPlayedAudio.stop();
        }

        this.paneExerciseLoader.setVisible(true);
        this.carriageReturnBtnExerciseExecution.setVisible(true);
        this.restartBtnExerciseExecution.setVisible(true);
        this.validateBtnExerciseExecution.setVisible(true);
        this.paneWordsOfExercise.setVisible(false);

        /* ------- SWITCH TO EXERCISE SELECTION PANE ------- */
        PupilModEcrilu.getInstance().paneHandler.show("exerciseSelection");
        /* ------------------------------------------------- */

        this.tryExitExerciseExecution = false;
    }

    @FXML
    public void undoBtnCloseExerciseExecutionConfirmClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO EXERCISE EXECUTION PANE ------- */
        PupilModEcrilu.getInstance().paneHandler.show("exerciseExecution");
        /* ------------------------------------------------- */

        // ------- Allow Exercise Execution to not dump his field ------- //
        this.tryExitExerciseExecution = false;
        // -------------------------------------------------------------- //
    }

    public static int getNoteFromMistakes(int mistakes, BaremeJson baremeJson)
    {
        if (mistakes <= baremeJson.check.maxMistakes)
        {
            return baremeJson.check.note;
        }
        else if (mistakes <= baremeJson.threeStar.maxMistakes)
        {
            return baremeJson.threeStar.note;
        }
        else if (mistakes <= baremeJson.twoStar.maxMistakes)
        {
            return baremeJson.twoStar.note;
        }
        else if (mistakes <= baremeJson.oneStar.maxMistakes)
        {
            return baremeJson.oneStar.note;
        }
        else
        {
            return 0;
        }
    }

    public static Image getImageFromNote(int note)
    {
        if (note == 4)
        {
            return Constants.PERFECT_NOTE;
        }
        else if (note == 3)
        {
            return Constants.THREE_STAR;
        }
        else if (note == 2)
        {
            return Constants.TWO_STAR;
        }
        else if (note == 1)
        {
            return Constants.ONE_STAR;
        }
        else
        {
            return null;
        }
    }
}