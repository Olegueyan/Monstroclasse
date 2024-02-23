package net.olegueyan.monstroclasse.module.content;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import net.olegueyan.monstroclasse.Main;
import net.olegueyan.monstroclasse.common.AppTheme;
import net.olegueyan.monstroclasse.common.Constants;
import net.olegueyan.monstroclasse.common.PersistentData;
import net.olegueyan.monstroclasse.entity.Pupil;
import net.olegueyan.monstroclasse.event.ecrilu.EventListenerFocusedExercise;
import net.olegueyan.monstroclasse.event.ecrilu.EventListenerFocusedSequence;
import net.olegueyan.monstroclasse.event.ecrilu.EventListenerFocusedWord;
import net.olegueyan.monstroclasse.integration.ControllerIntegration;
import net.olegueyan.monstroclasse.json.ecrilu.EcriluExerciseJson;
import net.olegueyan.monstroclasse.json.ecrilu.EcriluSequenceJson;
import net.olegueyan.monstroclasse.json.ecrilu.EcriluWordJson;
import net.olegueyan.monstroclasse.json.ecritoire.Ecritoire;
import net.olegueyan.monstroclasse.json.ecritoire.EcritoireColumn;
import net.olegueyan.monstroclasse.json.ecritoire.EcritoireTone;
import net.olegueyan.monstroclasse.motor.core.*;
import net.olegueyan.monstroclasse.node.FixedNodeCreatorUtils;
import net.olegueyan.monstroclasse.node.ecrilu.card.SequenceAttributionCardNode;
import net.olegueyan.monstroclasse.node.ecrilu.grid.SequencePupilFollowGridPane;
import net.olegueyan.monstroclasse.node.content.ecrilu.ExerciseCardSingleActionNode;
import net.olegueyan.monstroclasse.node.content.ecrilu.WordCardSingleActionNode;
import net.olegueyan.monstroclasse.node.content.ecrilu.ecritoire.EcritoireNode;
import net.olegueyan.monstroclasse.node.content.ecrilu.ecritoire.EcritoireSectionNode;
import net.olegueyan.monstroclasse.node.partial.ButtonImage;
import net.olegueyan.monstroclasse.node.partial.PaginationCardNode;
import net.olegueyan.monstroclasse.node.partial.PaginatorNode;
import net.olegueyan.monstroclasse.node.partial.VerticalScrollPane;
import net.olegueyan.monstroclasse.node.content.PupilSequenceAttributionEditCard;
import net.olegueyan.monstroclasse.portal.Monstroclasse;
import net.olegueyan.monstroclasse.repository.PupilRepository;
import net.olegueyan.monstroclasse.screen.ScreenStructurationInfo;
import net.olegueyan.monstroclasse.service.DataService;
import net.olegueyan.monstroclasse.service.EntireEcritoireService;
import net.olegueyan.monstroclasse.utils.MonstroclasseUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("unused")
public class AdminModEcriluController extends ControllerIntegration
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
    @StylizedButtonBuild(
            backgroundHex = AppTheme.ECRILU_BUTTON_BACKGROUND_COLOR_HEX,
            foregroundHex = AppTheme.ECRILU_BUTTON_FOREGROUND_COLOR_HEX,
            widthRef = 640,
            heightRef = 90,
            fontRef = 36
    )
    @CoordinatesAdjustment(xRef = 320, yRef = 30)
    @DimensionAdjustment(fixing = FixingType.BOTH, hRef = 90, wRef = 640)
    @ApplyCss(classTab = {"node-flat", "-handed"})
    public Pane panePupilFollow;

    @FXML
    @StylizedButtonBuild(
            backgroundHex = AppTheme.ECRILU_BUTTON_BACKGROUND_COLOR_HEX,
            foregroundHex = AppTheme.ECRILU_BUTTON_FOREGROUND_COLOR_HEX,
            widthRef = 640,
            heightRef = 90,
            fontRef = 36
    )
    @CoordinatesAdjustment(xRef = 320, yRef = 170)
    @DimensionAdjustment(fixing = FixingType.BOTH, hRef = 90, wRef = 640)
    @ApplyCss(classTab = {"node-flat", "-handed"})
    public Pane paneWordCreation;

    @FXML
    @StylizedButtonBuild(
            backgroundHex = AppTheme.ECRILU_BUTTON_BACKGROUND_COLOR_HEX,
            foregroundHex = AppTheme.ECRILU_BUTTON_FOREGROUND_COLOR_HEX,
            widthRef = 640,
            heightRef = 90,
            fontRef = 36
    )
    @CoordinatesAdjustment(xRef = 320, yRef = 270)
    @DimensionAdjustment(fixing = FixingType.BOTH, hRef = 90, wRef = 640)
    @ApplyCss(classTab = {"node-flat", "-handed"})
    public Pane paneExerciseCreation;

    @FXML
    @StylizedButtonBuild(
            backgroundHex = AppTheme.ECRILU_BUTTON_BACKGROUND_COLOR_HEX,
            foregroundHex = AppTheme.ECRILU_BUTTON_FOREGROUND_COLOR_HEX,
            widthRef = 640,
            heightRef = 90,
            fontRef = 36
    )
    @DimensionAdjustment(fixing = FixingType.BOTH, wRef = 640, hRef = 90)
    @CoordinatesAdjustment(xRef = 320, yRef = 370)
    @ApplyCss(classTab = {"node-flat", "-handed"})
    public Pane paneSequenceCreation;

    @FXML
    @StylizedButtonBuild(
            backgroundHex = AppTheme.ECRILU_BUTTON_BACKGROUND_COLOR_HEX,
            foregroundHex = AppTheme.ECRILU_BUTTON_FOREGROUND_COLOR_HEX,
            widthRef = 640,
            heightRef = 90,
            fontRef = 36
    )
    @DimensionAdjustment(fixing = FixingType.BOTH, wRef = 640, hRef = 90)
    @CoordinatesAdjustment(xRef = 320, yRef = 496)
    @ApplyCss(classTab = {"node-flat", "-handed"})
    public Pane paneSequenceAttribution;

    /*

        // Words Manager Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.ECRILU_COLOR_HEX)
    public Pane paneWordsManager;

    @FXML
    @CorePaneStructuration
    public Pane corePaneWordsManager;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 15, yRef = 531)
    public Button backBtnWordsManager;

    @FXML
    @ImageViewBinder(fieldName = "backBtnWordsManager")
    public ImageView backBtnWordsManagerImage;

    @FXML
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    @DimensionAdjustment(wRef = 940, hRef = 586)
    @CoordinatesAdjustment(xRef = 170, yRef = 10)
    public Pane subPaneWordsManager;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 78)
    @CoordinatesAdjustment(xRef = 0, yRef = 0)
    public Pane internalPaneOneOfWordsManager;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Bold", fontRef = 30)
    @DimensionAdjustment(wRef = 192, hRef = 38)
    @CoordinatesAdjustment(xRef = 10, yRef = 20)
    public Label labelResearchWordsManager;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Regular", fontRef = 15)
    @DimensionAdjustment(wRef = 260, hRef = 38)
    @CoordinatesAdjustment(xRef = 213, yRef = 20)
    public TextField fieldResearchWordsManager;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Bold", fontRef = 30)
    @DimensionAdjustment(wRef = 334, hRef = 38)
    @CoordinatesAdjustment(xRef = 520, yRef = 20)
    public Label labelAddWordWordsManager;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 871, yRef = 9)
    public Button addWordBtnWordsManager;

    @FXML
    @ImageViewBinder(fieldName = "addWordBtnWordsManager")
    public ImageView addWordBtnWordsManagerImage;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 458)
    @CoordinatesAdjustment(xRef = 0, yRef = 78)
    public Pane internalPaneTwoOfWordsManager;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 50)
    @CoordinatesAdjustment(xRef = 0, yRef = 536)
    public Pane internalPaneThreeOfWordsManager;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @RoundedStyle(color = AppTheme.ECRILU_PAGINATION_COLOR_BUTTON, cornerRadius = 8)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    @CoordinatesAdjustment(xRef = 250, yRef = 5)
    public Pane firstPaginationPaneWordsManager;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    public Label firstPaginationLabelWordsManager;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 370, yRef = 5)
    public Button leftPaginationBtnWordsManager;

    @FXML
    @ImageViewBinder(fieldName = "leftPaginationBtnWordsManager")
    public ImageView leftPaginationBtnWordsManagerImage;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Bold", fontRef = 20)
    @DimensionAdjustment(wRef = 75, hRef = 38)
    @CoordinatesAdjustment(xRef = 432.5, yRef = 6)
    public TextField fieldPaginationWordsManager;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 530, yRef = 5)
    public Button rightPaginationBtnWordsManager;

    @FXML
    @ImageViewBinder(fieldName = "rightPaginationBtnWordsManager")
    public ImageView rightPaginationBtnWordsManagerImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @RoundedStyle(color = AppTheme.ECRILU_PAGINATION_COLOR_BUTTON, cornerRadius = 8)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    @CoordinatesAdjustment(xRef = 590, yRef = 5)
    public Pane lastPaginationPaneWordsManager;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    public Label lastPaginationLabelWordsManager;

    /*

        // Word Feature Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.ECRILU_COLOR_HEX)
    public Pane paneWordFeature;

    @FXML
    @CorePaneStructuration
    public Pane corePaneWordFeature;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 15, yRef = 531)
    public Button backBtnWordFeature;

    @FXML
    @ImageViewBinder(fieldName = "backBtnWordFeature")
    public ImageView backBtnWordFeatureImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 55, hRef = 55)
    @CoordinatesAdjustment(xRef = 22.5, yRef = 340)
    public Button leftArrowEcritoireBtnWordFeature;

    @FXML
    @ImageViewBinder(fieldName = "leftArrowEcritoireBtnWordFeature")
    public ImageView leftArrowEcritoireBtnWordFeatureImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 55, hRef = 55)
    @CoordinatesAdjustment(xRef = 22.5, yRef = 340)
    public Button rightArrowEcritoireBtnWordFeature;

    @FXML
    @ImageViewBinder(fieldName = "rightArrowEcritoireBtnWordFeature")
    public ImageView rightArrowEcritoireBtnWordFeatureImage;

    @FXML
    @DimensionAdjustment(wRef = 1150, hRef = 580)
    @CoordinatesAdjustment(xRef = 100, yRef = 18)
    public Pane subPaneWordFeature;

    @FXML
    @RoundedStyle(color = AppTheme.ECRILU_ADMIN_SUB_BANNER_COLOR_HEX, cornerRadius = 12)
    @DimensionAdjustment(wRef = 1150, hRef = 120)
    @CoordinatesAdjustment(xRef = 0, yRef = 0)
    public Pane subBannerPaneWordFeature;

    @FXML
    @DimensionAdjustment(wRef = 1150, hRef = 60)
    @CoordinatesAdjustment(xRef = 0, yRef = 0)
    public Pane internalPaneOneOfWordFeature;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Bold", fontRef = 22)
    @DimensionAdjustment(wRef = 46, hRef = 28)
    @CoordinatesAdjustment(xRef = 10, yRef = 16)
    public Label wordLabelWordFeature;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 350, hRef = 35)
    @CoordinatesAdjustment(xRef = 75, yRef = 12.5)
    public TextField wordFieldWordFeature;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Bold", fontRef = 22)
    @DimensionAdjustment(wRef = 133, hRef = 28)
    @CoordinatesAdjustment(xRef = 530, yRef = 16)
    public Label labelIdentifierWordFeature;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 350, hRef = 35)
    @CoordinatesAdjustment(xRef = 680, yRef = 12.5)
    public TextField fieldIdentifierWordFeature;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Italic", fontRef = 18)
    @DimensionAdjustment(wRef = 100, hRef = 30)
    @CoordinatesAdjustment(xRef = 10, yRef = 15)
    public Label labelAudioWordFeature;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 90, yRef = 10)
    public Button folderBtnWordFeature;

    @FXML
    @ImageViewBinder(fieldName = "folderBtnWordFeature")
    public ImageView folderBtnWordFeatureImage;

    @FXML
    @DimensionAdjustment(wRef = 1150, hRef = 60)
    @CoordinatesAdjustment(xRef = 0, yRef = 60)
    public Pane internalPaneTwoOfWordFeature;

    @FXML
    @DimensionAdjustment(wRef = 350, hRef = 40)
    @CoordinatesAdjustment(xRef = 530, yRef = 10)
    public Pane paneWordWrittenWordFeature;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 940, yRef = 10)
    public Button carriageReturnBtnWordFeature;

    @FXML
    @ImageViewBinder(fieldName = "carriageReturnBtnWordFeature")
    public ImageView carriageReturnBtnWordFeatureImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 990, yRef = 10)
    public Button restartBtnWordFeature;

    @FXML
    @ImageViewBinder(fieldName = "restartBtnWordFeature")
    public ImageView restartBtnWordFeatureImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 80, hRef = 80)
    @CoordinatesAdjustment(xRef = 1050, yRef = 20)
    public Button validateBtnWordFeature;

    @FXML
    @ImageViewBinder(fieldName = "validateBtnWordFeature")
    public ImageView validateBtnWordFeatureImage;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Regular", fontRef = 16)
    @DimensionAdjustment(wRef = 240, hRef = 40)
    @CoordinatesAdjustment(xRef = 240, yRef = 10)
    public Label labelAudioFilenameWordFeature;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Italic", fontRef = 16)
    @DimensionAdjustment(wRef = 240, hRef = 40)
    @CoordinatesAdjustment(xRef = 240, yRef = 10)
    public Label labelAudioFileIsTooHeavyWordFeature;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 140, yRef = 10)
    public Button playAudioFileBtnWordFeature;

    @FXML
    @ImageViewBinder(fieldName = "playAudioFileBtnWordFeature")
    public ImageView playAudioFileBtnWordFeatureImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 190, yRef = 10)
    public Button deleteAudioFileBtnWordFeature;

    @FXML
    @ImageViewBinder(fieldName = "deleteAudioFileBtnWordFeature")
    public ImageView deleteAudioFileBtnWordFeatureImage;

    @FXML
    @DimensionAdjustment(wRef = 1150, hRef = 460)
    @CoordinatesAdjustment(xRef = 0, yRef = 127)
    public Pane internalPaneThreeOfWordFeature;

    /*

        // Word Delete Confirm Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.ECRILU_COLOR_HEX)
    public Pane paneWordDeleteConfirm;

    @FXML
    @CorePaneStructuration
    public Pane corePaneWordDeleteConfirm;

    @FXML
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    @DimensionAdjustment(wRef = 538, hRef = 468)
    @CoordinatesAdjustment(xRef = 371, yRef = 74)
    public Pane subPaneWordDeleteConfirm;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Regular", fontRef = 30)
    @DimensionAdjustment(wRef = 472, hRef = 90)
    @CoordinatesAdjustment(xRef = 33, yRef = 20)
    public Label mainLabelWordDeleteConfirm;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Regular", fontRef = 30)
    @DimensionAdjustment(wRef = 430, hRef = 40)
    @CoordinatesAdjustment(xRef = 54, yRef = 320)
    public Label wordLabelWordDeleteConfirm;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 10, yRef = 398)
    public Button undoWordDeleteConfirmBtn;

    @FXML
    @ImageViewBinder(fieldName = "undoWordDeleteConfirmBtn")
    public ImageView undoWordDeleteConfirmBtnImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 468, yRef = 398)
    public Button checkWordDeleteConfirmBtn;

    @FXML
    @ImageViewBinder(fieldName = "checkWordDeleteConfirmBtn")
    public ImageView checkWordDeleteConfirmBtnImage;

    /*

        // Exercises Manager Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.ECRILU_COLOR_HEX)
    public Pane paneExercisesManager;

    @FXML
    @CorePaneStructuration
    public Pane corePaneExercisesManager;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 15, yRef = 531)
    public Button backBtnExercisesManager;

    @FXML
    @ImageViewBinder(fieldName = "backBtnExercisesManager")
    public ImageView backBtnExercisesManagerImage;

    @FXML
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    @DimensionAdjustment(wRef = 940, hRef = 586)
    @CoordinatesAdjustment(xRef = 170, yRef = 10)
    public Pane subPaneExercisesManager;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 78)
    @CoordinatesAdjustment(xRef = 0, yRef = 0)
    public Pane internalPaneOneOfExercisesManager;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Bold", fontRef = 30)
    @DimensionAdjustment(wRef = 192, hRef = 38)
    @CoordinatesAdjustment(xRef = 10, yRef = 20)
    public Label labelResearchExercisesManager;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Regular", fontRef = 15)
    @DimensionAdjustment(wRef = 280, hRef = 38)
    @CoordinatesAdjustment(xRef = 213, yRef = 20)
    public TextField fieldResearchExercisesManager;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Bold", fontRef = 30)
    @DimensionAdjustment(wRef = 334, hRef = 38)
    @CoordinatesAdjustment(xRef = 520, yRef = 20)
    public Label labelAddExerciseExercisesManager;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 871, yRef = 9)
    public Button addExerciseBtnExercisesManager;

    @FXML
    @ImageViewBinder(fieldName = "addExerciseBtnExercisesManager")
    public ImageView addExerciseBtnExercisesManagerImage;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 458)
    @CoordinatesAdjustment(xRef = 0, yRef = 78)
    public Pane internalPaneTwoOfExercisesManager;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 50)
    @CoordinatesAdjustment(xRef = 0, yRef = 536)
    public Pane internalPaneThreeOfExercisesManager;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @RoundedStyle(color = AppTheme.ECRILU_PAGINATION_COLOR_BUTTON, cornerRadius = 8)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    @CoordinatesAdjustment(xRef = 250, yRef = 5)
    public Pane firstPaginationPaneExercisesManager;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    public Label firstPaginationLabelExercisesManager;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 370, yRef = 5)
    public Button leftPaginationBtnExercisesManager;

    @FXML
    @ImageViewBinder(fieldName = "leftPaginationBtnExercisesManager")
    public ImageView leftPaginationBtnExercisesManagerImage;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Bold", fontRef = 20)
    @DimensionAdjustment(wRef = 75, hRef = 38)
    @CoordinatesAdjustment(xRef = 432.5, yRef = 6)
    public TextField fieldPaginationExercisesManager;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 530, yRef = 5)
    public Button rightPaginationBtnExercisesManager;

    @FXML
    @ImageViewBinder(fieldName = "rightPaginationBtnExercisesManager")
    public ImageView rightPaginationBtnExercisesManagerImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @RoundedStyle(color = AppTheme.ECRILU_PAGINATION_COLOR_BUTTON, cornerRadius = 8)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    @CoordinatesAdjustment(xRef = 590, yRef = 5)
    public Pane lastPaginationPaneExercisesManager;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    public Label lastPaginationLabelExercisesManager;

     /*

        // Exercise Feature Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.ECRILU_COLOR_HEX)
    public Pane paneExerciseFeature;

    @FXML
    @CorePaneStructuration
    public Pane corePaneExerciseFeature;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 15, yRef = 531)
    public Button backBtnExerciseFeature;

    @FXML
    @ImageViewBinder(fieldName = "backBtnExerciseFeature")
    public ImageView backBtnExerciseFeatureImage;

    @FXML
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    @DimensionAdjustment(wRef = 550, hRef = 580)
    @CoordinatesAdjustment(xRef = 110, yRef = 18)
    public Pane internalPaneOneOfExerciseFeature;

    @FXML
    @DimensionAdjustment(wRef = 550, hRef = 60)
    @CoordinatesAdjustment(xRef = 0, yRef = 0)
    public Pane internalTitlePaneExerciseFeature;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Bold", fontRef = 26)
    @DimensionAdjustment(wRef = 282, hRef = 35)
    @CoordinatesAdjustment(xRef = 134, yRef = 12.5)
    public Label internalTitleLabelExerciseFeature;

    @FXML
    @DimensionAdjustment(wRef = 550, hRef = 60)
    @CoordinatesAdjustment(xRef = 0, yRef = 60)
    public Pane subInternalOneofOneExerciseFeature;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Bold", fontRef = 22)
    @DimensionAdjustment(wRef = 141, hRef = 28)
    @CoordinatesAdjustment(xRef = 10, yRef = 16)
    public Label labelResearchExerciseFeature;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 330, hRef = 36)
    @CoordinatesAdjustment(xRef = 160, yRef = 12)
    public TextField fieldResearchWordExerciseFeature;

    @FXML
    @DimensionAdjustment(wRef = 550, hRef = 400)
    @CoordinatesAdjustment(xRef = 0, yRef = 120)
    public Pane subInternalTwoofOneExerciseFeature;

    @FXML
    @DimensionAdjustment(wRef = 550, hRef = 60)
    @CoordinatesAdjustment(xRef = 0, yRef = 520)
    public Pane subInternalThreeofOneExerciseFeature;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @RoundedStyle(color = AppTheme.ECRILU_PAGINATION_COLOR_BUTTON, cornerRadius = 8)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    @CoordinatesAdjustment(xRef = 25, yRef = 10)
    public Pane firstPaginationPaneExerciseFeature;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    public Label firstPaginationLabelExerciseFeature;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 180, yRef = 10)
    public Button leftPaginationBtnExerciseFeature;

    @FXML
    @ImageViewBinder(fieldName = "leftPaginationBtnExerciseFeature")
    public ImageView leftPaginationBtnExerciseFeatureImage;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Bold", fontRef = 20)
    @DimensionAdjustment(wRef = 75, hRef = 38)
    @CoordinatesAdjustment(xRef = 237.5, yRef = 11)
    public TextField fieldPaginationExerciseFeature;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 330, yRef = 10)
    public Button rightPaginationBtnExerciseFeature;

    @FXML
    @ImageViewBinder(fieldName = "rightPaginationBtnExerciseFeature")
    public ImageView rightPaginationBtnExerciseFeatureImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @RoundedStyle(color = AppTheme.ECRILU_PAGINATION_COLOR_BUTTON, cornerRadius = 8)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    @CoordinatesAdjustment(xRef = 430, yRef = 10)
    public Pane lastPaginationPaneExerciseFeature;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    public Label lastPaginationLabelExerciseFeature;

    @FXML
    @RoundedStyle(color = AppTheme.ECRILU_ADMIN_SUB_BANNER_COLOR_HEX, cornerRadius = 25)
    @DimensionAdjustment(wRef = 550, hRef = 120)
    @CoordinatesAdjustment(xRef = 680, yRef = 18)
    public Pane internalPaneTwoOfExerciseFeature;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Bold", fontRef = 22)
    @DimensionAdjustment(wRef = 155, hRef = 28)
    @CoordinatesAdjustment(xRef = 10, yRef = 16)
    public Label labelIdentifierExerciseFeature;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 300, hRef = 36)
    @CoordinatesAdjustment(xRef = 155, yRef = 12)
    public TextField fieldIdentifierExerciseFeature;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Bold", fontRef = 22)
    @DimensionAdjustment(wRef = 155, hRef = 28)
    @CoordinatesAdjustment(xRef = 10, yRef = 76)
    public Label labelTitleExerciseFeature;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 300, hRef = 36)
    @CoordinatesAdjustment(xRef = 155, yRef = 72)
    public TextField fieldTitleExerciseFeature;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 470, yRef = 25)
    public Button encodingBtnExerciseFeature;

    @FXML
    @ImageViewBinder(fieldName = "encodingBtnExerciseFeature")
    public ImageView encodingBtnExerciseFeatureImage;

    @FXML
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    @DimensionAdjustment(wRef = 550, hRef = 410)
    @CoordinatesAdjustment(xRef = 680, yRef = 148)
    public Pane internalPaneThreeOfExerciseFeature;

    @FXML
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    @DimensionAdjustment(wRef = 550, hRef = 30)
    @CoordinatesAdjustment(xRef = 680, yRef = 568)
    public HBox internalPaneFourOfExerciseFeature;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Bold", fontRef = 21)
    @DimensionAdjustment(wRef = 550, hRef = 30)
    public Label wordCountExerciseFeature;

    /*

        // Exercise Feature Encoding Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.ECRILU_COLOR_HEX)
    public Pane paneExerciseFeatureEncoding;

    @FXML
    @CorePaneStructuration
    public Pane corePaneExerciseFeatureEncoding;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 15, yRef = 531)
    public Button backBtnExerciseFeatureEncoding;

    @FXML
    @ImageViewBinder(fieldName = "backBtnExerciseFeatureEncoding")
    public ImageView backBtnExerciseFeatureEncodingImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 55, hRef = 55)
    @CoordinatesAdjustment(xRef = 22.5, yRef = 340)
    public Button leftArrowEcritoireBtnExerciseFeatureEncoding;

    @FXML
    @ImageViewBinder(fieldName = "leftArrowEcritoireBtnExerciseFeatureEncoding")
    public ImageView leftArrowEcritoireBtnExerciseFeatureEncodingImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 55, hRef = 55)
    @CoordinatesAdjustment(xRef = 22.5, yRef = 340)
    public Button rightArrowEcritoireBtnExerciseFeatureEncoding;

    @FXML
    @ImageViewBinder(fieldName = "rightArrowEcritoireBtnExerciseFeatureEncoding")
    public ImageView rightArrowEcritoireBtnExerciseFeatureEncodingImage;

    @FXML
    @DimensionAdjustment(wRef = 1150, hRef = 460)
    @CoordinatesAdjustment(xRef = 100, yRef = 145)
    public Pane ecritoireSubPaneExerciseFeatureEncoding;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 15, yRef = 451)
    public Button validateBtnExerciseFeatureEncoding;

    @FXML
    @ImageViewBinder(fieldName = "validateBtnExerciseFeatureEncoding")
    public ImageView validateBtnExerciseFeatureEncodingImage;

    /*

        // Exercise Delete Confirm Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.ECRILU_COLOR_HEX)
    public Pane paneExerciseDeleteConfirm;

    @FXML
    @CorePaneStructuration
    public Pane corePaneExerciseDeleteConfirm;

    @FXML
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    @DimensionAdjustment(wRef = 538, hRef = 468)
    @CoordinatesAdjustment(xRef = 371, yRef = 74)
    public Pane subPaneExerciseDeleteConfirm;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Regular", fontRef = 30)
    @DimensionAdjustment(wRef = 472, hRef = 90)
    @CoordinatesAdjustment(xRef = 33, yRef = 20)
    public Label mainLabelExerciseDeleteConfirm;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Regular", fontRef = 30)
    @DimensionAdjustment(wRef = 430, hRef = 40)
    @CoordinatesAdjustment(xRef = 54, yRef = 320)
    public Label exerciseLabelExerciseDeleteConfirm;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 10, yRef = 398)
    public Button undoExerciseDeleteConfirmBtn;

    @FXML
    @ImageViewBinder(fieldName = "undoExerciseDeleteConfirmBtn")
    public ImageView undoExerciseDeleteConfirmBtnImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 468, yRef = 398)
    public Button checkExerciseDeleteConfirmBtn;

    @FXML
    @ImageViewBinder(fieldName = "checkExerciseDeleteConfirmBtn")
    public ImageView checkExerciseDeleteConfirmBtnImage;

    /*

        // Sequences Manager Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.ECRILU_COLOR_HEX)
    public Pane paneSequencesManager;

    @FXML
    @CorePaneStructuration
    public Pane corePaneSequencesManager;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 15, yRef = 531)
    public Button backBtnSequencesManager;

    @FXML
    @ImageViewBinder(fieldName = "backBtnSequencesManager")
    public ImageView backBtnSequencesManagerImage;

    @FXML
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    @DimensionAdjustment(wRef = 940, hRef = 586)
    @CoordinatesAdjustment(xRef = 170, yRef = 10)
    public Pane subPaneSequencesManager;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 78)
    @CoordinatesAdjustment(xRef = 0, yRef = 0)
    public Pane internalPaneOneOfSequencesManager;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Bold", fontRef = 30)
    @DimensionAdjustment(wRef = 192, hRef = 38)
    @CoordinatesAdjustment(xRef = 10, yRef = 20)
    public Label labelResearchSequencesManager;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Regular", fontRef = 15)
    @DimensionAdjustment(wRef = 280, hRef = 38)
    @CoordinatesAdjustment(xRef = 213, yRef = 20)
    public TextField fieldResearchSequencesManager;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Bold", fontRef = 27)
    @DimensionAdjustment(wRef = 334, hRef = 38)
    @CoordinatesAdjustment(xRef = 520, yRef = 20)
    public Label labelAddSequenceSequencesManager;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 871, yRef = 9)
    public Button addSequenceBtnSequencesManager;

    @FXML
    @ImageViewBinder(fieldName = "addSequenceBtnSequencesManager")
    public ImageView addSequenceBtnSequencesManagerImage;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 458)
    @CoordinatesAdjustment(xRef = 0, yRef = 78)
    public Pane internalPaneTwoOfSequencesManager;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 50)
    @CoordinatesAdjustment(xRef = 0, yRef = 536)
    public Pane internalPaneThreeOfSequencesManager;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @RoundedStyle(color = AppTheme.ECRILU_PAGINATION_COLOR_BUTTON, cornerRadius = 8)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    @CoordinatesAdjustment(xRef = 250, yRef = 5)
    public Pane firstPaginationPaneSequencesManager;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    public Label firstPaginationLabelSequencesManager;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 370, yRef = 5)
    public Button leftPaginationBtnSequencesManager;

    @FXML
    @ImageViewBinder(fieldName = "leftPaginationBtnSequencesManager")
    public ImageView leftPaginationBtnSequencesManagerImage;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Bold", fontRef = 20)
    @DimensionAdjustment(wRef = 75, hRef = 38)
    @CoordinatesAdjustment(xRef = 432.5, yRef = 6)
    public TextField fieldPaginationSequencesManager;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 530, yRef = 5)
    public Button rightPaginationBtnSequencesManager;

    @FXML
    @ImageViewBinder(fieldName = "rightPaginationBtnSequencesManager")
    public ImageView rightPaginationBtnSequencesManagerImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @RoundedStyle(color = AppTheme.ECRILU_PAGINATION_COLOR_BUTTON, cornerRadius = 8)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    @CoordinatesAdjustment(xRef = 590, yRef = 5)
    public Pane lastPaginationPaneSequencesManager;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    public Label lastPaginationLabelSequencesManager;

    /*

        // Sequence Feature Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.ECRILU_COLOR_HEX)
    public Pane paneSequenceFeature;

    @FXML
    @CorePaneStructuration
    public Pane corePaneSequenceFeature;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 15, yRef = 531)
    public Button backBtnSequenceFeature;

    @FXML
    @ImageViewBinder(fieldName = "backBtnSequenceFeature")
    public ImageView backBtnSequenceFeatureImage;

    @FXML
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    @DimensionAdjustment(wRef = 550, hRef = 580)
    @CoordinatesAdjustment(xRef = 110, yRef = 18)
    public Pane internalPaneOneOfSequenceFeature;

    @FXML
    @DimensionAdjustment(wRef = 550, hRef = 60)
    @CoordinatesAdjustment(xRef = 0, yRef = 0)
    public Pane internalTitlePaneSequenceFeature;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Bold", fontRef = 26)
    @DimensionAdjustment(wRef = 318, hRef = 35)
    @CoordinatesAdjustment(xRef = 116, yRef = 12.5)
    public Label internalTitleLabelSequenceFeature;

    @FXML
    @DimensionAdjustment(wRef = 550, hRef = 60)
    @CoordinatesAdjustment(xRef = 0, yRef = 60)
    public Pane subInternalOneofOneSequenceFeature;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Bold", fontRef = 22)
    @DimensionAdjustment(wRef = 141, hRef = 28)
    @CoordinatesAdjustment(xRef = 10, yRef = 16)
    public Label labelResearchSequenceFeature;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 330, hRef = 36)
    @CoordinatesAdjustment(xRef = 160, yRef = 12)
    public TextField fieldResearchExerciseSequenceFeature;

    @FXML
    @DimensionAdjustment(wRef = 550, hRef = 400)
    @CoordinatesAdjustment(xRef = 0, yRef = 120)
    public Pane subInternalTwoofOneSequenceFeature;

    @FXML
    @DimensionAdjustment(wRef = 550, hRef = 60)
    @CoordinatesAdjustment(xRef = 0, yRef = 520)
    public Pane subInternalThreeofOneSequenceFeature;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @RoundedStyle(color = AppTheme.ECRILU_PAGINATION_COLOR_BUTTON, cornerRadius = 8)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    @CoordinatesAdjustment(xRef = 25, yRef = 10)
    public Pane firstPaginationPaneSequenceFeature;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    public Label firstPaginationLabelSequenceFeature;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 180, yRef = 10)
    public Button leftPaginationBtnSequenceFeature;

    @FXML
    @ImageViewBinder(fieldName = "leftPaginationBtnSequenceFeature")
    public ImageView leftPaginationBtnSequenceFeatureImage;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Bold", fontRef = 20)
    @DimensionAdjustment(wRef = 75, hRef = 38)
    @CoordinatesAdjustment(xRef = 237.5, yRef = 11)
    public TextField fieldPaginationSequenceFeature;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 330, yRef = 10)
    public Button rightPaginationBtnSequenceFeature;

    @FXML
    @ImageViewBinder(fieldName = "rightPaginationBtnSequenceFeature")
    public ImageView rightPaginationBtnSequenceFeatureImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @RoundedStyle(color = AppTheme.ECRILU_PAGINATION_COLOR_BUTTON, cornerRadius = 8)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    @CoordinatesAdjustment(xRef = 430, yRef = 10)
    public Pane lastPaginationPaneSequenceFeature;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    public Label lastPaginationLabelSequenceFeature;

    @FXML
    @RoundedStyle(color = AppTheme.ECRILU_ADMIN_SUB_BANNER_COLOR_HEX, cornerRadius = 25)
    @DimensionAdjustment(wRef = 550, hRef = 120)
    @CoordinatesAdjustment(xRef = 680, yRef = 18)
    public Pane internalPaneTwoOfSequenceFeature;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Bold", fontRef = 22)
    @DimensionAdjustment(wRef = 155, hRef = 28)
    @CoordinatesAdjustment(xRef = 10, yRef = 16)
    public Label labelIdentifierSequenceFeature;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 300, hRef = 36)
    @CoordinatesAdjustment(xRef = 155, yRef = 12)
    public TextField fieldIdentifierSequenceFeature;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Bold", fontRef = 22)
    @DimensionAdjustment(wRef = 155, hRef = 28)
    @CoordinatesAdjustment(xRef = 10, yRef = 76)
    public Label labelTitleSequenceFeature;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 300, hRef = 36)
    @CoordinatesAdjustment(xRef = 155, yRef = 72)
    public TextField fieldTitleSequenceFeature;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 470, yRef = 25)
    public Button validateBtnSequenceFeature;

    @FXML
    @ImageViewBinder(fieldName = "validateBtnSequenceFeature")
    public ImageView validateBtnSequenceFeatureImage;

    @FXML
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    @DimensionAdjustment(wRef = 550, hRef = 410)
    @CoordinatesAdjustment(xRef = 680, yRef = 148)
    public Pane internalPaneThreeOfSequenceFeature;

    @FXML
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    @DimensionAdjustment(wRef = 550, hRef = 30)
    @CoordinatesAdjustment(xRef = 680, yRef = 568)
    public HBox internalPaneFourOfSequenceFeature;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Bold", fontRef = 21)
    @DimensionAdjustment(wRef = 550, hRef = 30)
    public Label exerciseCountSequenceFeature;

    /*

        // Sequence Delete Confirm Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.ECRILU_COLOR_HEX)
    public Pane paneSequenceDeleteConfirm;

    @FXML
    @CorePaneStructuration
    public Pane corePaneSequenceDeleteConfirm;

    @FXML
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    @DimensionAdjustment(wRef = 538, hRef = 468)
    @CoordinatesAdjustment(xRef = 371, yRef = 74)
    public Pane subPaneSequenceDeleteConfirm;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Regular", fontRef = 30)
    @DimensionAdjustment(wRef = 472, hRef = 90)
    @CoordinatesAdjustment(xRef = 33, yRef = 20)
    public Label mainLabelSequenceDeleteConfirm;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Regular", fontRef = 30)
    @DimensionAdjustment(wRef = 430, hRef = 40)
    @CoordinatesAdjustment(xRef = 54, yRef = 320)
    public Label sequenceLabelSequenceDeleteConfirm;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 10, yRef = 398)
    public Button undoSequenceDeleteConfirmBtn;

    @FXML
    @ImageViewBinder(fieldName = "undoSequenceDeleteConfirmBtn")
    public ImageView undoSequenceDeleteConfirmBtnImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 468, yRef = 398)
    public Button checkSequenceDeleteConfirmBtn;

    @FXML
    @ImageViewBinder(fieldName = "checkSequenceDeleteConfirmBtn")
    public ImageView checkSequenceDeleteConfirmBtnImage;

    /*

        // Sequence Attribution Manager Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.ECRILU_COLOR_HEX)
    public Pane paneSequenceAttributionManager;

    @FXML
    @CorePaneStructuration
    public Pane corePaneSequenceAttributionManager;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 15, yRef = 531)
    public Button backBtnSequenceAttributionManager;

    @FXML
    @ImageViewBinder(fieldName = "backBtnSequenceAttributionManager")
    public ImageView backBtnSequenceAttributionManagerImage;

    @FXML
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    @DimensionAdjustment(wRef = 640, hRef = 586)
    @CoordinatesAdjustment(xRef = 320, yRef = 15)
    public Pane subPaneSequenceAttributionManager;

    @FXML
    @DimensionAdjustment(wRef = 640, hRef = 78)
    @CoordinatesAdjustment(xRef = 0, yRef = 0)
    public Pane internalPaneOneOfSequenceAttributionManager;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Bold", fontRef = 30)
    @DimensionAdjustment(wRef = 192, hRef = 38)
    @CoordinatesAdjustment(xRef = 10, yRef = 20)
    public Label labelResearchSequenceAttributionManager;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Regular", fontRef = 15)
    @DimensionAdjustment(wRef = 415, hRef = 38)
    @CoordinatesAdjustment(xRef = 213, yRef = 20)
    public TextField fieldResearchSequenceAttributionManager;

    @FXML
    @DimensionAdjustment(wRef = 640, hRef = 458)
    @CoordinatesAdjustment(xRef = 0, yRef = 78)
    public Pane internalPaneTwoOfSequenceAttributionManager;

    @FXML
    @DimensionAdjustment(wRef = 640, hRef = 50)
    @CoordinatesAdjustment(xRef = 0, yRef = 536)
    public Pane internalPaneThreeOfSequenceAttributionManager;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @RoundedStyle(color = AppTheme.ECRILU_PAGINATION_COLOR_BUTTON, cornerRadius = 8)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    @CoordinatesAdjustment(xRef = 107.5, yRef = 5)
    public Pane firstPaginationPaneSequenceAttributionManager;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    public Label firstPaginationLabelSequenceAttributionManager;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 222.5, yRef = 5)
    public Button leftPaginationBtnSequenceAttributionManager;

    @FXML
    @ImageViewBinder(fieldName = "leftPaginationBtnSequenceAttributionManager")
    public ImageView leftPaginationBtnSequenceAttributionManagerImage;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Bold", fontRef = 20)
    @DimensionAdjustment(wRef = 75, hRef = 38)
    @CoordinatesAdjustment(xRef = 282.5, yRef = 6)
    public TextField fieldPaginationSequenceAttributionManager;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 377.5, yRef = 5)
    public Button rightPaginationBtnSequenceAttributionManager;

    @FXML
    @ImageViewBinder(fieldName = "rightPaginationBtnSequenceAttributionManager")
    public ImageView rightPaginationBtnSequenceAttributionManagerImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @RoundedStyle(color = AppTheme.ECRILU_PAGINATION_COLOR_BUTTON, cornerRadius = 8)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    @CoordinatesAdjustment(xRef = 437.5, yRef = 5)
    public Pane lastPaginationPaneSequenceAttributionManager;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    public Label lastPaginationLabelSequenceAttributionManager;

     /*

        // Sequence Attribution Edit Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.ECRILU_COLOR_HEX)
    public Pane paneSequenceAttributionEdit;

    @FXML
    @CorePaneStructuration
    public Pane corePaneSequenceAttributionEdit;

    @FXML
    @DimensionAdjustment(wRef = 960, hRef = 528)
    @CoordinatesAdjustment(xRef = 160, yRef = 44)
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    public Pane internalPaneSequenceAttributionEdit;

    @FXML
    @DimensionAdjustment(wRef = 960, hRef = 60)
    public Pane titlePaneSequenceAttributionEdit;

    @FXML
    @DimensionAdjustment(wRef = 474, hRef = 44)
    @CoordinatesAdjustment(xRef = 243, yRef = 8)
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "bold", fontRef = 35)
    public Label titleLabelSequenceAttributionEdit;

    @FXML
    @DimensionAdjustment(wRef = 960, hRef = 468)
    @CoordinatesAdjustment(xRef = 0, yRef = 60)
    public Pane subPaneSequenceAttributionEdit;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 448)
    @CoordinatesAdjustment(xRef = 10, yRef = 10)
    public Pane layoutPaneSequenceAttributionEdit;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 94)
    @CoordinatesAdjustment(xRef = 0, yRef = 0)
    public Pane internalPaneIndicatorSequenceAttributionEdit;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 354)
    @CoordinatesAdjustment(xRef = 0, yRef = 94)
    public Pane internalPaneScrollSequenceAttributionEdit;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 15, yRef = 531)
    public Button backBtnSequenceAttributionEdit;

    @FXML
    @ImageViewBinder(fieldName = "backBtnSequenceAttributionEdit")
    public ImageView backBtnSequenceAttributionEditImage;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 14, yRef = 17)
    public Button uncheckAllBtnSequenceAttributionEdit;

    @FXML
    @ImageViewBinder(fieldName = "uncheckAllBtnSequenceAttributionEdit")
    public ImageView uncheckAllBtnSequenceAttributionEditImage;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 866, yRef = 17)
    public Button checkAllBtnSequenceAttributionEdit;

    @FXML
    @ImageViewBinder(fieldName = "checkAllBtnSequenceAttributionEdit")
    public ImageView checkAllBtnSequenceAttributionEditImage;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 1196, yRef = 532)
    public Button saveBtnSequenceAttributionEdit;

    @FXML
    @ImageViewBinder(fieldName = "saveBtnSequenceAttributionEdit")
    public ImageView saveBtnSequenceAttributionEditImage;

    @FXML
    @DimensionAdjustment(wRef = 240, hRef = 38)
    @CoordinatesAdjustment(xRef = 83, yRef = 28)
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "bold", fontRef = 30)
    public Label labelUncheckSequenceAttributionEdit;

    @FXML
    @DimensionAdjustment(wRef = 199, hRef = 38)
    @CoordinatesAdjustment(xRef = 650, yRef = 28)
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "bold", fontRef = 30)
    public Label labelCheckSequenceAttributionEdit;

    /*

        // Pupil Follow Sequence Selection Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.ECRILU_COLOR_HEX)
    public Pane panePupilFollowSequenceSelection;

    @FXML
    @CorePaneStructuration
    public Pane corePanePupilFollowSequenceSelection;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 15, yRef = 531)
    public Button backBtnPupilFollowSequenceSelection;

    @FXML
    @ImageViewBinder(fieldName = "backBtnPupilFollowSequenceSelection")
    public ImageView backBtnPupilFollowSequenceSelectionImage;

    @FXML
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    @DimensionAdjustment(wRef = 640, hRef = 586)
    @CoordinatesAdjustment(xRef = 320, yRef = 15)
    public Pane subPanePupilFollowSequenceSelection;

    @FXML
    @DimensionAdjustment(wRef = 640, hRef = 78)
    @CoordinatesAdjustment(xRef = 0, yRef = 0)
    public Pane internalPaneOneOfPupilFollowSequenceSelection;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Bold", fontRef = 30)
    @DimensionAdjustment(wRef = 192, hRef = 38)
    @CoordinatesAdjustment(xRef = 10, yRef = 20)
    public Label labelResearchPupilFollowSequenceSelection;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Regular", fontRef = 15)
    @DimensionAdjustment(wRef = 415, hRef = 38)
    @CoordinatesAdjustment(xRef = 213, yRef = 20)
    public TextField fieldResearchPupilFollowSequenceSelection;

    @FXML
    @DimensionAdjustment(wRef = 640, hRef = 458)
    @CoordinatesAdjustment(xRef = 0, yRef = 78)
    public Pane internalPaneTwoOfPupilFollowSequenceSelection;

    @FXML
    @DimensionAdjustment(wRef = 640, hRef = 50)
    @CoordinatesAdjustment(xRef = 0, yRef = 536)
    public Pane internalPaneThreeOfPupilFollowSequenceSelection;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @RoundedStyle(color = AppTheme.ECRILU_PAGINATION_COLOR_BUTTON, cornerRadius = 8)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    @CoordinatesAdjustment(xRef = 107.5, yRef = 5)
    public Pane firstPaginationPanePupilFollowSequenceSelection;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    public Label firstPaginationLabelPupilFollowSequenceSelection;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 222.5, yRef = 5)
    public Button leftPaginationBtnPupilFollowSequenceSelection;

    @FXML
    @ImageViewBinder(fieldName = "leftPaginationBtnPupilFollowSequenceSelection")
    public ImageView leftPaginationBtnPupilFollowSequenceSelectionImage;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Bold", fontRef = 20)
    @DimensionAdjustment(wRef = 75, hRef = 38)
    @CoordinatesAdjustment(xRef = 282.5, yRef = 6)
    public TextField fieldPaginationPupilFollowSequenceSelection;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 377.5, yRef = 5)
    public Button rightPaginationBtnPupilFollowSequenceSelection;

    @FXML
    @ImageViewBinder(fieldName = "rightPaginationBtnPupilFollowSequenceSelection")
    public ImageView rightPaginationBtnPupilFollowSequenceSelectionImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @RoundedStyle(color = AppTheme.ECRILU_PAGINATION_COLOR_BUTTON, cornerRadius = 8)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    @CoordinatesAdjustment(xRef = 437.5, yRef = 5)
    public Pane lastPaginationPanePupilFollowSequenceSelection;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 18)
    @DimensionAdjustment(wRef = 95, hRef = 40)
    public Label lastPaginationLabelPupilFollowSequenceSelection;

    /*

        // Pupil Follow Sequence Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.ECRILU_COLOR_HEX)
    public Pane panePupilFollowSequence;

    @FXML
    @CorePaneStructuration
    public Pane corePanePupilFollowSequence;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 15, yRef = 531)
    public Button backBtnPupilFollowSequence;

    @FXML
    @ImageViewBinder(fieldName = "backBtnPupilFollowSequence")
    public ImageView backBtnPupilFollowSequenceImage;

    @FXML
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    @DimensionAdjustment(wRef = 1020, hRef = 586)
    @CoordinatesAdjustment(xRef = 130, yRef = 15)
    public Pane subPanePupilFollowSequence;

    @FXML
    @DimensionAdjustment(wRef = 1020, hRef = 78)
    @CoordinatesAdjustment(xRef = 0, yRef = 0)
    public Pane internalPaneOneOfPupilFollowSequence;

    @FXML
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "Bold", fontRef = 40)
    @DimensionAdjustment(wRef = 1020, hRef = 78)
    public Label labelSequenceTitlePupilFollowSequence;

    @FXML
    @DimensionAdjustment(wRef = 1020, hRef = 458)
    @CoordinatesAdjustment(xRef = 0, yRef = 78)
    public Pane internalPaneTwoOfPupilFollowSequence;

    @FXML
    @DimensionAdjustment(wRef = 1020, hRef = 60)
    @CoordinatesAdjustment(xRef = 0, yRef = 0)
    public Pane paneExercisesInfoBarPupilFollowSequence;

    @FXML
    @DimensionAdjustment(wRef = 1020, hRef = 398)
    @CoordinatesAdjustment(xRef = 0, yRef = 60)
    public Pane panePupilNotesPupilFollowSequence;

    @FXML
    @DimensionAdjustment(wRef = 1020, hRef = 50)
    @CoordinatesAdjustment(xRef = 0, yRef = 536)
    public Pane internalPaneThreeOfPupilFollowSequence;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 412.5, yRef = 5)
    public Button leftPaginationBtnPupilFollowSequence;

    @FXML
    @ImageViewBinder(fieldName = "leftPaginationBtnPupilFollowSequence")
    public ImageView leftPaginationBtnPupilFollowSequenceImage;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Bold", fontRef = 20)
    @DimensionAdjustment(wRef = 75, hRef = 38)
    @CoordinatesAdjustment(xRef = 472.5, yRef = 6)
    public TextField fieldPaginationPupilFollowSequence;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 40, hRef = 40)
    @CoordinatesAdjustment(xRef = 567.5, yRef = 5)
    public Button rightPaginationBtnPupilFollowSequence;

    @FXML
    @ImageViewBinder(fieldName = "rightPaginationBtnPupilFollowSequence")
    public ImageView rightPaginationBtnPupilFollowSequenceImage;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 1195, yRef = 531)
    public Button exportBtnPupilFollowSequence;

    @FXML
    @ImageViewBinder(fieldName = "exportBtnPupilFollowSequence")
    public ImageView exportBtnPupilFollowSequenceImage;

    // ------- Logger of AdminModEcriluController ------- //
    public static final Logger LOGGER = LoggerFactory.getLogger(AdminModEcriluController.class);
    // -------------------------------------------------- //

    // ***************************************************************
    // AdminModEcriluController - Properties
    // ***************************************************************

    private static Font font;

    /* ============================================================ */
    // WORD                                                         //
    /* ============================================================ */

    private PaginatorNode<EcriluWordJson> paginatorNodeWordsManager;

    private static final ObservableList<EcritoireTone> wordCompositionFeatureWord = FXCollections.observableList(new ArrayList<>());

    // ------- The current exercise which is in modification ------- //
    private EcriluWordJson currentModifyWordJson;
    // ------------------------------------------------------------- //

    // ------- The current exercise which is in suppression ------- //
    private EcriluWordJson currentDeleteWordJson = null;
    // ------------------------------------------------------------ //

    private File currentAudioFileSelectedWordFeature = null;

    // ------- Word Feature Mode ------- //
    private FeatureMode wordFeatureMode = null;
    // ------------------------------------- //

    /* ============================================================ */
    // EXERCISE                                                     //
    /* ============================================================ */

    private PaginatorNode<EcriluExerciseJson> paginatorNodeExercisesManager;

    // ------- Paginator Node Exercise Feature ------- //
    private PaginatorNode<EcriluWordJson> paginatorNodeExerciseFeature;
    // ----------------------------------------------- //

    // ------- Composition of Exercise ------- //
    private final ObservableList<EcriluWordJson> ecritoireTonesExerciseFeature = FXCollections.observableList(new ArrayList<>());
    // --------------------------------------- //

    // ------- Define if the exercise feature ecritoire is in encoding ------- //
    private boolean isInEncodingEcritoireExerciseFeatureEncoding = false;
    // ----------------------------------------------------------------------- //

    // ------- Exercise Feature Mode ------- //
    private FeatureMode exerciseFeatureMode = null;
    // ------------------------------------- //

    // ------- The current exercise which is in modification ------- //
    private EcriluExerciseJson currentModifyExerciseJson;
    // ------------------------------------------------------------- //

    // ------- The current exercise which is in suppression ------- //
    private EcriluExerciseJson currentDeleteExerciseJson = null;
    // ------------------------------------------------------------ //

    /* ============================================================ */
    // SEQUENCE                                                     //
    /* ============================================================ */

    private PaginatorNode<EcriluSequenceJson> paginatorNodeSequencesManager;

    // ------- Paginator Node Sequence Feature ------- //
    private PaginatorNode<EcriluExerciseJson> paginatorNodeSequenceFeature;
    // ----------------------------------------------- //

    // ------- Sequence Feature Mode ------- //
    private FeatureMode sequenceFeatureMode = null;
    // ------------------------------------- //

    // ------- Composition of Sequence ------- //
    private final LinkedList<EcriluExerciseJson> exerciseJsonsSequenceFeature = new LinkedList<>();
    // --------------------------------------- //

    // ------- The current sequence which is in modification ------- //
    private EcriluSequenceJson currentModifySequenceJson;
    // ------------------------------------------------------------- //

    // ------- The current sequence which is in suppression ------- //
    private EcriluSequenceJson currentDeleteSequenceJson = null;
    // ------------------------------------------------------------ //

    /* ============================================================ */
    // SEQUENCE ATTRIBUTION MANAGER                                 //
    /* ============================================================ */

    // ------- Paginator Node Sequence Attribution Manager ------- //
    private PaginatorNode<EcriluSequenceJson> paginatorNodeSequenceAttributionManager;
    // ----------------------------------------------------------- //

    // ------- Current Sequence Attribution ------- //
    private EcriluSequenceJson currentSequenceAttribution;
    // -------------------------------------------- //

    /* ============================================================ */
    // PUPIL FOLLOW SEQUENCE SELECTION                              //
    /* ============================================================ */

    // ------- Paginator Node Pupil Follow Sequence Selection ------- //
    private PaginatorNode<EcriluSequenceJson> paginatorNodePupilFollowSequenceSelection;
    // -------------------------------------------------------------- //

    // ------- Current Sequence To Follow ------- //
    private EcriluSequenceJson currentSequenceToFollow;
    // -------------------------------------------- //

    // ***************************************************************
    // AdminModEcriluController - Properties
    // ***************************************************************

    @Override
    public void loadController()
    {
        double fontSize = ScreenStructurationInfo.getAdjustedFontSize(
                26,
                this.paneWordWrittenWordFeature.getPrefWidth(),
                this.paneWordWrittenWordFeature.getPrefHeight()
        );

        font = Font.font("Verdana", FontWeight.BOLD, fontSize);

        this.paneWordsManager.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (observableValue.getValue())
            {
                ArrayList<EcriluWordJson> wordJsons = DataService.readAllJsonOf(EcriluWordJson.class, "ecrilu", "words");

                wordJsons.sort(Comparator.comparing(o -> MonstroclasseUtils.accentRemover(o.word.toLowerCase())));

                double padding = 10;

                this.paginatorNodeWordsManager = new PaginatorNode.Builder<EcriluWordJson>()
                        .withToPaginate(wordJsons)
                        .withGridDisposition(8, 3)
                        .withDimension(
                                this.internalPaneTwoOfWordsManager.getPrefWidth() - padding * 2,
                                this.internalPaneTwoOfWordsManager.getPrefHeight() - padding * 2
                        )
                        .withNodeCreator((obj, width, height) ->
                        {
                            ButtonImage modifyBtn = new ButtonImage(Constants.EDIT_IMAGE);
                            ButtonImage deleteBtn = new ButtonImage(Constants.DELETE_IMAGE);

                            PaginationCardNode<EcriluWordJson> node = new PaginationCardNode<>(
                                    width, height, obj.word, modifyBtn, deleteBtn, obj
                            );

                            node.setOnClickModifyBtn(actionEvent ->
                            {
                                currentModifyWordJson = obj;

                                this.wordFeatureMode = FeatureMode.MODIFYING;

                                /* ------- SWITCH TO WORD FEATURE PANE ------- */
                                AdminModEcrilu.getInstance().paneHandler.show("wordFeature");
                                /* ------------------------------------------- */
                            });

                            node.setOnClickDeleteBtn(actionEvent ->
                            {
                                currentDeleteWordJson = obj;

                                /* ------- SWITCH TO WORD DELETE CONFIRM PANE ------- */
                                AdminModEcrilu.getInstance().paneHandler.show("wordDeleteConfirm");
                                /* -------------------------------------------------- */
                            });

                            return node;
                        })
                        .withFilterFunction((ecriluWordJson, s) -> ecriluWordJson.word.toLowerCase().contains(s.toLowerCase()))
                        .whenFiltered(node ->
                        {
                            if (node instanceof PaginationCardNode<?> wordCardNode)
                            {
                                String filter = this.paginatorNodeWordsManager.getPaginator().getCurrentFilter();

                                Text text = wordCardNode.getText();
                                String textContent = text.getText();

                                if (MonstroclasseUtils.accentRemover(textContent.toLowerCase())
                                        .contains(MonstroclasseUtils.accentRemover(filter.toLowerCase())))
                                {
                                    int startIndex = MonstroclasseUtils.accentRemover(textContent.toLowerCase())
                                            .indexOf(MonstroclasseUtils.accentRemover(filter.toLowerCase()));
                                    int endIndex = startIndex + filter.length();
                                    text.setSelectionStart(startIndex);
                                    text.setSelectionEnd(endIndex);
                                    text.setSelectionFill(Color.web(AppTheme.ECRILU_RESEARCH_HIGHLIGHT_COLOR_HEX));
                                }
                            }
                        })
                        .onPageChange((page, hasPrevious, hasNext) ->
                        {
                            this.fieldPaginationWordsManager.setText(String.valueOf(page));
                            this.leftPaginationBtnWordsManager.setVisible(hasPrevious);
                            this.rightPaginationBtnWordsManager.setVisible(hasNext);
                        })
                        .build();

                this.paginatorNodeWordsManager.setLayoutX(padding);
                this.paginatorNodeWordsManager.setLayoutY(padding);

                this.internalPaneTwoOfWordsManager.getChildren().add(this.paginatorNodeWordsManager);

                this.fieldPaginationWordsManager.setText(String.valueOf(this.paginatorNodeWordsManager.getPaginator().currentPage()));
            }
            else
            {
                this.internalPaneTwoOfWordsManager.getChildren().clear();
                this.fieldResearchWordsManager.setText("");
            }
        });

        this.fieldResearchWordsManager.textProperty().addListener((observableValue, s, t1) ->
        {
            if (this.fieldResearchWordsManager.getText().isEmpty())
            {
                this.paginatorNodeWordsManager.removeFilter();
            }
            else
            {
                this.paginatorNodeWordsManager.applyFilter(this.fieldResearchWordsManager.getText());
            }
        });

        this.fieldPaginationWordsManager.setOnKeyPressed(keyEvent ->
        {
            if (keyEvent.getCode() == KeyCode.ENTER)
            {
                try
                {
                    int entry = Integer.parseInt(this.fieldPaginationWordsManager.getText());
                    this.paginatorNodeWordsManager.getPaginator().setPage(entry);
                }
                catch (NumberFormatException ignored) {}
                finally
                {
                    this.fieldPaginationWordsManager.setText(String.valueOf(this.paginatorNodeWordsManager.getPaginator().currentPage()));
                    this.fieldPaginationWordsManager.positionCaret(this.fieldPaginationWordsManager.getText().length());
                }
            }
        });

        this.paneWordFeature.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (observableValue.getValue())
            {
                this.labelAudioFileIsTooHeavyWordFeature.setVisible(false);
                this.labelAudioFilenameWordFeature.setVisible(true);

                EcritoireNode ecritoireNode = new EcritoireNode.Builder()
                        .withDimension(this.internalPaneThreeOfWordFeature.getPrefWidth(), this.internalPaneThreeOfWordFeature.getPrefHeight())
                        .withEcritoire(EntireEcritoireService.getEcritoire())
                        .withPaneProcess(pane -> ScreenStructurationInfo.processCssRounded(pane, AppTheme.SUB_PANE_COLOR_HEX, 10))
                        .withSpacing(5)
                        .withEcritoireListener((tone, locked) -> wordCompositionFeatureWord.add(tone))
                        .build();
                ecritoireNode.bindSelector(this.leftArrowEcritoireBtnWordFeature, this.rightArrowEcritoireBtnWordFeature);
                this.internalPaneThreeOfWordFeature.getChildren().add(ecritoireNode);
                
                if (this.wordFeatureMode == FeatureMode.MODIFYING)
                {
                    this.wordFieldWordFeature.setText(currentModifyWordJson.word);
                    this.fieldIdentifierWordFeature.setText(currentModifyWordJson.id);

                    File file = new File(DataService.pathOf("ecrilu/audio/" + currentModifyWordJson.audioPath));
                    this.labelAudioFilenameWordFeature.setText(file.getName());
                    this.currentAudioFileSelectedWordFeature = file;

                    wordCompositionFeatureWord.addAll(currentModifyWordJson.composition);
                }
            }
            else
            {
                this.internalPaneThreeOfWordFeature.getChildren().clear();
                wordCompositionFeatureWord.clear();
                this.paneWordWrittenWordFeature.getChildren().clear();
                this.wordFieldWordFeature.setText("");
                this.fieldIdentifierWordFeature.setText("");
                this.labelAudioFilenameWordFeature.setText("");
                this.currentAudioFileSelectedWordFeature = null;
                if (PersistentData.anAudioIsPlaying) PersistentData.currentPlayedAudio.stop();
            }
        });

        wordCompositionFeatureWord.addListener((ListChangeListener<? super EcritoireTone>) change ->
        {
            boolean alternateColor = false;

            this.paneWordWrittenWordFeature.getChildren().clear();

            HBox hBox = new HBox();
            hBox.setPadding(Insets.EMPTY);
            hBox.setSpacing(0);

            FixedNodeCreatorUtils.forcePrefDimension(hBox);

            hBox.setPrefWidth(this.paneWordWrittenWordFeature.getPrefWidth());
            hBox.setPrefHeight(this.paneWordWrittenWordFeature.getPrefHeight());

            this.paneWordWrittenWordFeature.getChildren().add(hBox);

            for (EcritoireTone composite : wordCompositionFeatureWord)
            {
                Text text = new Text(composite.tone);
                text.setFont(font);

                if (alternateColor)
                {
                    text.setFill(Color.web(AppTheme.PEER_TONE_COLOR_HEX)); // Couleur alternée
                }
                else
                {
                    text.setFill(Color.web(AppTheme.ODD_TONE_COLOR_HEX));  // Couleur alternée
                }
                alternateColor = !alternateColor;
                hBox.getChildren().add(text);
            }
        });

        this.paneWordDeleteConfirm.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (observableValue.getValue()) this.wordLabelWordDeleteConfirm.setText(currentDeleteWordJson.word);
        });

        this.paneExercisesManager.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (observableValue.getValue())
            {
                this.internalPaneTwoOfExercisesManager.getChildren().clear();

                ArrayList<EcriluExerciseJson> exerciseJsons = DataService.readAllJsonOf(EcriluExerciseJson.class, "ecrilu", "exercises");

                exerciseJsons.sort(Comparator.comparing(o -> MonstroclasseUtils.accentRemover(o.id.toLowerCase())));

                double padding = 10;

                this.paginatorNodeExercisesManager = new PaginatorNode.Builder<EcriluExerciseJson>()
                        .withToPaginate(exerciseJsons)
                        .withGridDisposition(8, 3)
                        .withDimension(
                                this.internalPaneTwoOfExercisesManager.getPrefWidth() - padding * 2,
                                this.internalPaneTwoOfExercisesManager.getPrefHeight() - padding * 2
                        )
                        .withNodeCreator((obj, width, height) ->
                        {
                            ButtonImage modifyBtn = new ButtonImage(Constants.EDIT_IMAGE);
                            ButtonImage deleteBtn = new ButtonImage(Constants.DELETE_IMAGE);

                            PaginationCardNode<EcriluExerciseJson> node = new PaginationCardNode<>(
                                    width, height, obj.id, modifyBtn, deleteBtn, obj
                            );

                            node.setOnClickModifyBtn(actionEvent ->
                            {
                                this.currentModifyExerciseJson = obj;

                                this.exerciseFeatureMode = FeatureMode.MODIFYING;

                                /* ------- SWITCH TO MODIFY EXERCISE PANE ------- */
                                AdminModEcrilu.getInstance().paneHandler.show("exerciseFeature");
                                /* ---------------------------------------------- */
                            });

                            node.setOnClickDeleteBtn(actionEvent ->
                            {
                                this.currentDeleteExerciseJson = obj;

                                /* ------- SWITCH TO EXERCISE DELETE CONFIRM PANE ------- */
                                AdminModEcrilu.getInstance().paneHandler.show("exerciseDeleteConfirm");
                                /* ------------------------------------------------------ */
                            });

                            return node;
                        })
                        .withFilterFunction((ecriluExerciseJson, s) -> ecriluExerciseJson.id.toLowerCase().contains(s.toLowerCase()))
                        .whenFiltered(node ->
                        {
                            if (node instanceof PaginationCardNode<?> exerciseCardNode)
                            {
                                String filter = this.paginatorNodeExercisesManager.getPaginator().getCurrentFilter();

                                Text text = exerciseCardNode.getText();
                                String textContent = text.getText();

                                if (textContent.toLowerCase().contains(filter.toLowerCase()))
                                {
                                    int startIndex = textContent.toLowerCase().indexOf(filter.toLowerCase());
                                    int endIndex = startIndex + filter.length();
                                    text.setSelectionStart(startIndex);
                                    text.setSelectionEnd(endIndex);
                                    text.setSelectionFill(Color.web(AppTheme.ECRILU_RESEARCH_HIGHLIGHT_COLOR_HEX));
                                }
                            }
                        })
                        .onPageChange((page, hasPrevious, hasNext) ->
                        {
                            this.fieldPaginationExercisesManager.setText(String.valueOf(page));
                            this.leftPaginationBtnExercisesManager.setVisible(hasPrevious);
                            this.rightPaginationBtnExercisesManager.setVisible(hasNext);
                        })
                        .build();

                this.paginatorNodeExercisesManager.setLayoutX(padding);
                this.paginatorNodeExercisesManager.setLayoutY(padding);

                this.internalPaneTwoOfExercisesManager.getChildren().add(this.paginatorNodeExercisesManager);

                this.fieldPaginationExercisesManager.setText(String.valueOf(this.paginatorNodeExercisesManager.getPaginator().currentPage()));
            }
            else
            {
                this.fieldResearchWordsManager.setText("");
            }
        });

        this.fieldResearchExercisesManager.textProperty().addListener((observableValue, s, t1) ->
        {
            if (this.fieldResearchExercisesManager.getText().isEmpty())
            {
                this.paginatorNodeExercisesManager.removeFilter();
            }
            else
            {
                this.paginatorNodeExercisesManager.applyFilter(this.fieldResearchExercisesManager.getText());
            }
        });

        this.paneExerciseDeleteConfirm.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (observableValue.getValue())
            {
                this.exerciseLabelExerciseDeleteConfirm.setText(this.currentDeleteExerciseJson.id);
            }
            else
            {
                this.exerciseLabelExerciseDeleteConfirm.setText("");
            }
        });

        this.paneExerciseFeature.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (this.isInEncodingEcritoireExerciseFeatureEncoding) return;
            if (observableValue.getValue())
            {
                this.wordCountExerciseFeature.setText("Nombre de mots : 0");

                final double padding = 10;

                AtomicReference<WordCardSingleActionNode> currentNodeDraggedFromToAddWords = new AtomicReference<>();
                AtomicReference<WordCardSingleActionNode> currentNodeDraggedFromSelectedWords = new AtomicReference<>();

                double selectedWordsPaneWidth = this.internalPaneThreeOfExerciseFeature.getPrefWidth() - padding * 2;
                double selectedWordsPaneHeight = this.internalPaneThreeOfExerciseFeature.getPrefHeight() - padding * 2;

                VerticalScrollPane scrollPane = new VerticalScrollPane(selectedWordsPaneWidth, selectedWordsPaneHeight, 0);

                scrollPane.setOnChange(verticalScrollPane -> this.wordCountExerciseFeature.setText("Nombre de mots : " + verticalScrollPane.getContentSize()));

                final double selectedWordCardWidth = scrollPane.getPrefWidth();
                final double selectedWordCardHeight = scrollPane.getPrefHeight() / 8;

                scrollPane.setOnDragDropped(event ->
                {
                    Dragboard dragboard = event.getDragboard();
                    boolean success = false;
                    if (Objects.equals(dragboard.getString(), "FromToAddWords") && scrollPane.getContentSize() < Constants.LIMIT_WORD_PER_EXERCISE)
                    {
                        WordCardSingleActionNode draggedCard = currentNodeDraggedFromToAddWords.get();
                        if (draggedCard != null)
                        {
                            this.paginatorNodeExerciseFeature.getPaginator().extract(draggedCard.getEcriluWordJson());
                            ButtonImage selectedActionBtn = new ButtonImage(Constants.LEFT_ARROW);
                            ButtonImage selectedUpBtn = new ButtonImage(Constants.UP_ARROW_IMAGE);
                            ButtonImage selectedDownBtn = new ButtonImage(Constants.DOWN_ARROW_IMAGE);
                            WordCardSingleActionNode selectedNode = new WordCardSingleActionNode(
                                    selectedWordCardWidth, selectedWordCardHeight,
                                    selectedActionBtn, selectedUpBtn,
                                    selectedDownBtn, draggedCard.getEcriluWordJson()
                            );

                            selectedActionBtn.setOnAction(actionEvent ->
                            {
                                this.ecritoireTonesExerciseFeature.remove(draggedCard.getEcriluWordJson());
                                scrollPane.removeNode(selectedNode);
                                this.paginatorNodeExerciseFeature.getPaginator().insert(draggedCard.getEcriluWordJson());
                            });

                            selectedUpBtn.setOnAction(actionEvent -> scrollPane.moveNodeUp(selectedNode));
                            selectedDownBtn.setOnAction(actionEvent -> scrollPane.moveNodeDown(selectedNode));

                            selectedNode.setOnDragDetected(mouseEvent ->
                            {
                                Dragboard db = selectedNode.startDragAndDrop(TransferMode.MOVE);
                                ClipboardContent content = new ClipboardContent();
                                content.putString("FromSelectedWords");
                                db.setContent(content);
                                currentNodeDraggedFromSelectedWords.set(selectedNode);
                                event.consume();
                            });

                            this.ecritoireTonesExerciseFeature.add(draggedCard.getEcriluWordJson());

                            scrollPane.addNode(selectedNode);
                            currentNodeDraggedFromToAddWords.set(null);
                            success = true;
                        }
                    }
                    event.setDropCompleted(success);
                    event.consume();
                });

                scrollPane.setOnDragOver(event ->
                {
                    if (event.getGestureSource() != scrollPane && event.getDragboard().hasString())
                    {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                    event.consume();
                });

                scrollPane.setLayoutX(padding);
                scrollPane.setLayoutY(padding);

                this.internalPaneThreeOfExerciseFeature.getChildren().add(scrollPane);

                ArrayList<EcriluWordJson> wordJsons = DataService.readAllJsonOf(EcriluWordJson.class, "ecrilu", "words");

                wordJsons.sort(Comparator.comparing(o -> MonstroclasseUtils.accentRemover(o.word.toLowerCase())));

                this.paginatorNodeExerciseFeature = new PaginatorNode.Builder<EcriluWordJson>()
                        .withToPaginate(wordJsons)
                        .withGridDisposition(8, 2)
                        .withDimension(
                                this.subInternalTwoofOneExerciseFeature.getPrefWidth() - padding * 2,
                                this.subInternalTwoofOneExerciseFeature.getPrefHeight() - padding * 2
                        )
                        .withNodeCreator((obj, width, height) ->
                        {
                            ButtonImage actionBtn = new ButtonImage(Constants.RIGHT_ARROW);

                            WordCardSingleActionNode node = new WordCardSingleActionNode(width, height, actionBtn, obj);

                            actionBtn.setOnAction(actionEvent ->
                            {
                                if (scrollPane.getContentSize() < Constants.LIMIT_WORD_PER_EXERCISE)
                                {
                                    this.paginatorNodeExerciseFeature.getPaginator().extract(obj);

                                    double selectedWidth = scrollPane.getPrefWidth();
                                    double selectedHeight = scrollPane.getPrefHeight() / 8;

                                    ButtonImage selectedActionBtn = new ButtonImage(Constants.LEFT_ARROW);
                                    ButtonImage selectedUpBtn = new ButtonImage(Constants.UP_ARROW_IMAGE);
                                    ButtonImage selectedDownBtn = new ButtonImage(Constants.DOWN_ARROW_IMAGE);

                                    WordCardSingleActionNode selectedNode = new WordCardSingleActionNode(selectedWidth, selectedHeight, selectedActionBtn, selectedUpBtn, selectedDownBtn, obj);

                                    selectedActionBtn.setOnAction(event ->
                                    {
                                        this.ecritoireTonesExerciseFeature.remove(obj);
                                        scrollPane.removeNode(selectedNode);
                                        this.paginatorNodeExerciseFeature.getPaginator().insert(obj);
                                    });

                                    selectedUpBtn.setOnAction(event -> scrollPane.moveNodeUp(selectedNode));
                                    selectedDownBtn.setOnAction(event -> scrollPane.moveNodeDown(selectedNode));

                                    selectedNode.setOnDragDetected(event ->
                                    {
                                        Dragboard db = selectedNode.startDragAndDrop(TransferMode.MOVE);
                                        ClipboardContent content = new ClipboardContent();
                                        content.putString("FromSelectedWords");
                                        db.setContent(content);
                                        currentNodeDraggedFromSelectedWords.set(selectedNode);
                                        event.consume();
                                    });

                                    this.ecritoireTonesExerciseFeature.add(obj);

                                    scrollPane.addNode(selectedNode);
                                }

                            });

                            node.setOnDragDetected(event ->
                            {
                                Dragboard db = node.startDragAndDrop(TransferMode.MOVE);
                                ClipboardContent content = new ClipboardContent();
                                content.putString("FromToAddWords");
                                db.setContent(content);
                                currentNodeDraggedFromToAddWords.set(node);
                                event.consume();
                            });

                            return node;
                        })
                        .withFilterFunction((ecriluWordJson, s) -> ecriluWordJson.word.toLowerCase().contains(s.toLowerCase()))
                        .whenFiltered(node ->
                        {
                            if (node instanceof WordCardSingleActionNode wordCardSingleActionNode)
                            {
                                String filter = this.paginatorNodeExerciseFeature.getPaginator().getCurrentFilter();

                                Text text = wordCardSingleActionNode.getText();
                                String textContent = text.getText();

                                if (textContent.toLowerCase().contains(filter.toLowerCase()))
                                {
                                    int startIndex = textContent.toLowerCase().indexOf(filter.toLowerCase());
                                    int endIndex = startIndex + filter.length();
                                    text.setSelectionStart(startIndex);
                                    text.setSelectionEnd(endIndex);
                                    text.setSelectionFill(Color.web(AppTheme.ECRILU_RESEARCH_HIGHLIGHT_COLOR_HEX));
                                }
                            }
                        })
                        .onPageChange((page, hasPrevious, hasNext) ->
                        {
                            this.fieldPaginationExerciseFeature.setText(String.valueOf(page));
                            this.leftPaginationBtnExerciseFeature.setVisible(hasPrevious);
                            this.rightPaginationBtnExerciseFeature.setVisible(hasNext);
                        })
                        .build();

                this.paginatorNodeExerciseFeature.setLayoutX(padding);
                this.paginatorNodeExerciseFeature.setLayoutY(padding);

                this.paginatorNodeExerciseFeature.getPaginator().setSortMethod(Comparator.comparing(o -> MonstroclasseUtils.accentRemover(o.word.toLowerCase())));

                this.subInternalTwoofOneExerciseFeature.getChildren().add(this.paginatorNodeExerciseFeature);

                this.subInternalTwoofOneExerciseFeature.setOnDragDropped(event ->
                {
                    Dragboard dragboard = event.getDragboard();
                    boolean success = false;
                    if (Objects.equals(dragboard.getString(), "FromSelectedWords"))
                    {
                        WordCardSingleActionNode draggedCard = currentNodeDraggedFromSelectedWords.get();
                        if (draggedCard != null)
                        {
                            this.ecritoireTonesExerciseFeature.remove(draggedCard.getEcriluWordJson());
                            scrollPane.removeNode(draggedCard);
                            this.paginatorNodeExerciseFeature.getPaginator().insert(draggedCard.getEcriluWordJson());
                            currentNodeDraggedFromSelectedWords.set(null);
                            success = true;
                        }
                    }
                    event.setDropCompleted(success);
                    event.consume();
                });

                this.subInternalTwoofOneExerciseFeature.setOnDragOver(event ->
                {
                    if (event.getGestureSource() != this.subInternalTwoofOneExerciseFeature && event.getDragboard().hasString())
                    {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                    event.consume();
                });

                if (this.exerciseFeatureMode == FeatureMode.MODIFYING)
                {
                    this.fieldIdentifierExerciseFeature.setText(this.currentModifyExerciseJson.id);
                    this.fieldTitleExerciseFeature.setText(this.currentModifyExerciseJson.title);

                    ArrayList<WordCardSingleActionNode> toClicks = new ArrayList<>();

                    this.currentModifyExerciseJson.words.forEach(s ->
                        this.paginatorNodeExerciseFeature
                            .getChildren()
                            .forEach(node ->
                            {
                                if (node instanceof WordCardSingleActionNode wordCardSingleActionNode)
                                {
                                    if (Objects.equals(wordCardSingleActionNode.getEcriluWordJson().id, s))
                                    {
                                        toClicks.add(wordCardSingleActionNode);
                                    }
                                }
                            }));

                    toClicks.forEach(WordCardSingleActionNode::simulateClick);
                }
            }
            else
            {
                this.ecritoireTonesExerciseFeature.clear();
                this.internalPaneThreeOfExerciseFeature.getChildren().clear();
                this.subInternalTwoofOneExerciseFeature.getChildren().clear();
                fieldResearchWordExerciseFeature.setText("");
                fieldIdentifierExerciseFeature.setText("");
                this.fieldTitleExerciseFeature.setText("");
            }
        });

        this.fieldResearchWordExerciseFeature.textProperty().addListener((observableValue, s, t1) ->
        {
            if (this.fieldResearchWordExerciseFeature.getText().isEmpty())
            {
                this.paginatorNodeExerciseFeature.removeFilter();
            }
            else
            {
                this.paginatorNodeExerciseFeature.applyFilter(this.fieldResearchWordExerciseFeature.getText());
            }
        });

        this.paneExerciseFeatureEncoding.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (observableValue.getValue())
            {
                EcritoireNode ecritoireNode = new EcritoireNode.Builder()
                        .withDimension(this.ecritoireSubPaneExerciseFeatureEncoding.getPrefWidth(), this.ecritoireSubPaneExerciseFeatureEncoding.getPrefHeight())
                        .withEcritoire(EntireEcritoireService.getEcritoire())
                        .withPaneProcess(pane -> ScreenStructurationInfo.processCssRounded(pane, AppTheme.SUB_PANE_COLOR_HEX, 10))
                        .withSpacing(5)
                        .withEcritoireListener(null)
                        .build();
                ecritoireNode.bindSelector(this.leftArrowEcritoireBtnExerciseFeatureEncoding, this.rightArrowEcritoireBtnExerciseFeatureEncoding);
                this.ecritoireSubPaneExerciseFeatureEncoding.getChildren().add(ecritoireNode);

                this.ecritoireTonesExerciseFeature.forEach(ecriluWordJson ->
                {
                    for (EcritoireTone ecritoireTone : ecriluWordJson.composition)
                    {
                        ecritoireNode.applyForeach(ecritoireSectionNode ->
                        {
                            ecritoireSectionNode.setLockMode(EcritoireSectionNode.LockMode.LOCKED);
                            ecritoireSectionNode.forceLockTone(ecritoireTone);
                        });
                    }
                });

                if (this.exerciseFeatureMode == FeatureMode.MODIFYING)
                {
                    Ecritoire ecritoire = this.currentModifyExerciseJson.ecritoire;

                    ecritoire.writingTable.forEach(ecritoireColumn ->
                    {
                        ecritoireColumn.bottom.forEach(strings ->
                        {
                            strings.forEach(s ->
                            {
                                ecritoireNode.applyForeach(ecritoireSectionNode ->
                                {
                                    ecritoireSectionNode.setLockMode(EcritoireSectionNode.LockMode.LOCKED);
                                    ecritoireSectionNode.forceLockTone(
                                            String.join("/", ecritoireColumn.top), s
                                    );
                                });
                            });
                        });
                    });
                }
            }
            else
            {
                this.ecritoireSubPaneExerciseFeatureEncoding.getChildren().clear();
            }
        });

        this.paneSequencesManager.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (observableValue.getValue())
            {
                ArrayList<EcriluSequenceJson> sequenceJsons = DataService.readAllJsonOf(EcriluSequenceJson.class, "ecrilu", "sequences");

                sequenceJsons.sort(Comparator.comparing(o -> MonstroclasseUtils.accentRemover(o.id.toLowerCase())));

                double padding = 10;

                this.paginatorNodeSequencesManager = new PaginatorNode.Builder<EcriluSequenceJson>()
                        .withToPaginate(sequenceJsons)
                        .withGridDisposition(8, 3)
                        .withDimension(
                                this.internalPaneTwoOfSequencesManager.getPrefWidth() - padding * 2,
                                this.internalPaneTwoOfSequencesManager.getPrefHeight() - padding * 2
                        )
                        .withNodeCreator((obj, width, height) ->
                        {
                            ButtonImage modifyBtn = new ButtonImage(Constants.EDIT_IMAGE);
                            ButtonImage deleteBtn = new ButtonImage(Constants.DELETE_IMAGE);

                            PaginationCardNode<EcriluSequenceJson> node = new PaginationCardNode<>(
                                    width, height, obj.id, modifyBtn, deleteBtn, obj
                            );

                            node.setOnClickModifyBtn(actionEvent ->
                            {
                                this.currentModifySequenceJson = obj;

                                this.sequenceFeatureMode = FeatureMode.MODIFYING;

                                /* ------- SWITCH TO MODIFY SEQUENCE PANE ------- */
                                AdminModEcrilu.getInstance().paneHandler.show("sequenceFeature");
                                /* ---------------------------------------------- */
                            });

                            node.setOnClickDeleteBtn(actionEvent ->
                            {
                                this.currentDeleteSequenceJson = obj;

                                /* ------- SWITCH TO SEQUENCE DELETE CONFIRM PANE ------- */
                                AdminModEcrilu.getInstance().paneHandler.show("sequenceDeleteConfirm");
                                /* ------------------------------------------------------ */
                            });

                            return node;
                        })
                        .withFilterFunction((ecriluSequenceJson, s) -> ecriluSequenceJson.id.toLowerCase().contains(s.toLowerCase()))
                        .whenFiltered(node ->
                        {
                            if (node instanceof PaginationCardNode<?> sequenceCardNode)
                            {
                                String filter = this.paginatorNodeSequencesManager.getPaginator().getCurrentFilter();

                                Text text = sequenceCardNode.getText();
                                String textContent = text.getText();

                                if (textContent.toLowerCase().contains(filter.toLowerCase()))
                                {
                                    int startIndex = textContent.toLowerCase().indexOf(filter.toLowerCase());
                                    int endIndex = startIndex + filter.length();
                                    text.setSelectionStart(startIndex);
                                    text.setSelectionEnd(endIndex);
                                    text.setSelectionFill(Color.web(AppTheme.ECRILU_RESEARCH_HIGHLIGHT_COLOR_HEX));
                                }
                            }
                        })
                        .onPageChange((page, hasPrevious, hasNext) ->
                        {
                            this.fieldPaginationSequencesManager.setText(String.valueOf(page));
                            this.leftPaginationBtnSequencesManager.setVisible(hasPrevious);
                            this.rightPaginationBtnSequencesManager.setVisible(hasNext);
                        })
                        .build();

                this.paginatorNodeSequencesManager.setLayoutX(padding);
                this.paginatorNodeSequencesManager.setLayoutY(padding);

                this.internalPaneTwoOfSequencesManager.getChildren().add(this.paginatorNodeSequencesManager);

                this.fieldPaginationSequencesManager.setText(String.valueOf(this.paginatorNodeSequencesManager.getPaginator().currentPage()));
            }
            else
            {
                this.internalPaneTwoOfSequencesManager.getChildren().clear();
                this.fieldResearchSequencesManager.setText("");
            }
        });

        this.fieldResearchSequencesManager.textProperty().addListener((observableValue, s, t1) ->
        {
            if (this.fieldResearchSequencesManager.getText().isEmpty())
            {
                this.paginatorNodeSequencesManager.removeFilter();
            }
            else
            {
                this.paginatorNodeSequencesManager.applyFilter(this.fieldResearchSequencesManager.getText());
            }
        });

        this.paneSequenceFeature.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (observableValue.getValue())
            {
                this.exerciseCountSequenceFeature.setText("Nombre d'exercices : 0");

                final double padding = 10;

                AtomicReference<ExerciseCardSingleActionNode> currentNodeDraggedFromToAddExercises = new AtomicReference<>();
                AtomicReference<ExerciseCardSingleActionNode> currentNodeDraggedFromSelectedExercises = new AtomicReference<>();

                double selectedExercisesPaneWidth = this.internalPaneThreeOfSequenceFeature.getPrefWidth() - padding * 2;
                double selectedExercisesPaneHeight = this.internalPaneThreeOfSequenceFeature.getPrefHeight() - padding * 2;

                VerticalScrollPane scrollPane = new VerticalScrollPane(selectedExercisesPaneWidth, selectedExercisesPaneHeight, 0);

                scrollPane.setOnChange(verticalScrollPane -> this.exerciseCountSequenceFeature.setText("Nombre d'exercices : " + verticalScrollPane.getContentSize()));

                final double selectedExerciseCardWidth = scrollPane.getPrefWidth();
                final double selectedExerciseCardHeight = scrollPane.getPrefHeight() / 8;

                scrollPane.setOnDragDropped(event ->
                {
                    Dragboard dragboard = event.getDragboard();
                    boolean success = false;
                    if (Objects.equals(dragboard.getString(), "FromToAddExercises"))
                    {
                        ExerciseCardSingleActionNode draggedCard = currentNodeDraggedFromToAddExercises.get();
                        if (draggedCard != null)
                        {
                            this.paginatorNodeSequenceFeature.getPaginator().extract(draggedCard.getEcriluExerciseJson());
                            ButtonImage selectedActionBtn = new ButtonImage(Constants.LEFT_ARROW);
                            ButtonImage selectedUpBtn = new ButtonImage(Constants.UP_ARROW_IMAGE);
                            ButtonImage selectedDownBtn = new ButtonImage(Constants.DOWN_ARROW_IMAGE);
                            ExerciseCardSingleActionNode selectedNode = new ExerciseCardSingleActionNode(
                                    selectedExerciseCardWidth, selectedExerciseCardHeight,
                                    selectedActionBtn, selectedUpBtn,
                                    selectedDownBtn, draggedCard.getEcriluExerciseJson()
                            );

                            selectedActionBtn.setOnAction(actionEvent ->
                            {
                                this.exerciseJsonsSequenceFeature.remove(draggedCard.getEcriluExerciseJson());
                                scrollPane.removeNode(selectedNode);
                                this.paginatorNodeSequenceFeature.getPaginator().insert(draggedCard.getEcriluExerciseJson());
                            });

                            selectedUpBtn.setOnAction(actionEvent -> scrollPane.moveNodeUp(selectedNode));
                            selectedDownBtn.setOnAction(actionEvent -> scrollPane.moveNodeDown(selectedNode));

                            selectedNode.setOnDragDetected(mouseEvent ->
                            {
                                Dragboard db = selectedNode.startDragAndDrop(TransferMode.MOVE);
                                ClipboardContent content = new ClipboardContent();
                                content.putString("FromSelectedExercises");
                                db.setContent(content);
                                currentNodeDraggedFromSelectedExercises.set(selectedNode);
                                event.consume();
                            });

                            this.exerciseJsonsSequenceFeature.add(draggedCard.getEcriluExerciseJson());
                            scrollPane.addNode(selectedNode);
                            currentNodeDraggedFromToAddExercises.set(null);
                            success = true;
                        }
                    }
                    event.setDropCompleted(success);
                    event.consume();
                });

                scrollPane.setOnDragOver(event ->
                {
                    if (event.getGestureSource() != scrollPane && event.getDragboard().hasString())
                    {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                    event.consume();
                });

                scrollPane.setLayoutX(padding);
                scrollPane.setLayoutY(padding);

                this.internalPaneThreeOfSequenceFeature.getChildren().add(scrollPane);

                ArrayList<EcriluExerciseJson> exerciseJsons = DataService.readAllJsonOf(EcriluExerciseJson.class, "ecrilu", "exercises");

                exerciseJsons.sort(Comparator.comparing(o -> MonstroclasseUtils.accentRemover(o.id.toLowerCase())));

                this.paginatorNodeSequenceFeature = new PaginatorNode.Builder<EcriluExerciseJson>()
                        .withToPaginate(exerciseJsons)
                        .withGridDisposition(8, 2)
                        .withDimension(
                                this.subInternalTwoofOneSequenceFeature.getPrefWidth() - padding * 2,
                                this.subInternalTwoofOneSequenceFeature.getPrefHeight() - padding * 2
                        )
                        .withNodeCreator((obj, width, height) ->
                        {
                            ButtonImage actionBtn = new ButtonImage(Constants.RIGHT_ARROW);

                            ExerciseCardSingleActionNode node = new ExerciseCardSingleActionNode(width, height, actionBtn, obj);

                            actionBtn.setOnAction(actionEvent ->
                            {
                                this.paginatorNodeSequenceFeature.getPaginator().extract(obj);

                                double selectedWidth = scrollPane.getPrefWidth();
                                double selectedHeight = scrollPane.getPrefHeight() / 8;

                                ButtonImage selectedActionBtn = new ButtonImage(Constants.LEFT_ARROW);
                                ButtonImage selectedUpBtn = new ButtonImage(Constants.UP_ARROW_IMAGE);
                                ButtonImage selectedDownBtn = new ButtonImage(Constants.DOWN_ARROW_IMAGE);

                                ExerciseCardSingleActionNode selectedNode = new ExerciseCardSingleActionNode(
                                        selectedWidth, selectedHeight,
                                        selectedActionBtn, selectedUpBtn,
                                        selectedDownBtn, obj
                                );

                                selectedUpBtn.setOnAction(event -> scrollPane.moveNodeUp(selectedNode));
                                selectedDownBtn.setOnAction(event -> scrollPane.moveNodeDown(selectedNode));

                                selectedActionBtn.setOnAction(event ->
                                {
                                    this.exerciseJsonsSequenceFeature.remove(obj);
                                    scrollPane.removeNode(selectedNode);
                                    this.paginatorNodeSequenceFeature.getPaginator().insert(obj);
                                });

                                selectedNode.setOnDragDetected(event ->
                                {
                                    Dragboard db = selectedNode.startDragAndDrop(TransferMode.MOVE);
                                    ClipboardContent content = new ClipboardContent();
                                    content.putString("FromSelectedExercises");
                                    db.setContent(content);
                                    currentNodeDraggedFromSelectedExercises.set(selectedNode);
                                    event.consume();
                                });

                                this.exerciseJsonsSequenceFeature.add(obj);
                                scrollPane.addNode(selectedNode);
                            });

                            node.setOnDragDetected(event ->
                            {
                                Dragboard db = node.startDragAndDrop(TransferMode.MOVE);
                                ClipboardContent content = new ClipboardContent();
                                content.putString("FromToAddExercises");
                                db.setContent(content);
                                currentNodeDraggedFromToAddExercises.set(node);
                                event.consume();
                            });

                            return node;
                        })
                        .withFilterFunction((ecriluExerciseJson, s) -> MonstroclasseUtils.accentRemover(ecriluExerciseJson.id.toLowerCase()).contains(s.toLowerCase()))
                        .whenFiltered(node ->
                        {
                            if (node instanceof ExerciseCardSingleActionNode exerciseCardSingleActionNode)
                            {
                                String filter = this.paginatorNodeSequenceFeature.getPaginator().getCurrentFilter();

                                Text text = exerciseCardSingleActionNode.getText();
                                String textContent = text.getText();

                                if (textContent.toLowerCase().contains(filter.toLowerCase()))
                                {
                                    int startIndex = textContent.toLowerCase().indexOf(filter.toLowerCase());
                                    int endIndex = startIndex + filter.length();
                                    text.setSelectionStart(startIndex);
                                    text.setSelectionEnd(endIndex);
                                    text.setSelectionFill(Color.web(AppTheme.ECRILU_RESEARCH_HIGHLIGHT_COLOR_HEX));
                                }
                            }
                        })
                        .onPageChange((page, hasPrevious, hasNext) ->
                        {
                            this.fieldPaginationSequenceFeature.setText(String.valueOf(page));
                            this.leftPaginationBtnSequenceFeature.setVisible(hasPrevious);
                            this.rightPaginationBtnSequenceFeature.setVisible(hasNext);
                        })
                        .build();

                this.paginatorNodeSequenceFeature.setLayoutX(padding);
                this.paginatorNodeSequenceFeature.setLayoutY(padding);

                this.paginatorNodeSequenceFeature.getPaginator().setSortMethod(Comparator.comparing(o -> MonstroclasseUtils.accentRemover(o.id.toLowerCase())));

                this.subInternalTwoofOneSequenceFeature.getChildren().add(this.paginatorNodeSequenceFeature);

                this.subInternalTwoofOneSequenceFeature.setOnDragDropped(event ->
                {
                    Dragboard dragboard = event.getDragboard();
                    boolean success = false;
                    if (Objects.equals(dragboard.getString(), "FromSelectedExercises"))
                    {
                        ExerciseCardSingleActionNode draggedCard = currentNodeDraggedFromSelectedExercises.get();
                        if (draggedCard != null)
                        {
                            this.exerciseJsonsSequenceFeature.remove(draggedCard.getEcriluExerciseJson());
                            scrollPane.removeNode(draggedCard);
                            this.paginatorNodeSequenceFeature.getPaginator().insert(draggedCard.getEcriluExerciseJson());
                            currentNodeDraggedFromSelectedExercises.set(null);
                            success = true;
                        }
                    }
                    event.setDropCompleted(success);
                    event.consume();
                });

                this.subInternalTwoofOneSequenceFeature.setOnDragOver(event ->
                {
                    if (event.getGestureSource() != this.subInternalTwoofOneSequenceFeature && event.getDragboard().hasString())
                    {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                    event.consume();
                });

                if (this.sequenceFeatureMode == FeatureMode.MODIFYING)
                {
                    this.fieldIdentifierSequenceFeature.setText(this.currentModifySequenceJson.id);
                    this.fieldTitleSequenceFeature.setText(this.currentModifySequenceJson.title);

                    ArrayList<ExerciseCardSingleActionNode> toClicks = new ArrayList<>();

                    this.currentModifySequenceJson.exercises.forEach(s ->
                            this.paginatorNodeSequenceFeature
                                    .getChildren()
                                    .forEach(node ->
                                    {
                                        if (node instanceof ExerciseCardSingleActionNode exerciseCardSingleActionNode)
                                        {
                                            if (Objects.equals(exerciseCardSingleActionNode.getEcriluExerciseJson().id, s))
                                            {
                                                toClicks.add(exerciseCardSingleActionNode);
                                            }
                                        }
                                    }));

                    toClicks.forEach(ExerciseCardSingleActionNode::simulateClick);
                }
            }
            else
            {
                this.exerciseJsonsSequenceFeature.clear();
                this.internalPaneThreeOfSequenceFeature.getChildren().clear();
                this.subInternalTwoofOneSequenceFeature.getChildren().clear();
                fieldResearchExerciseSequenceFeature.setText("");
                fieldIdentifierSequenceFeature.setText("");
                this.fieldTitleSequenceFeature.setText("");
            }
        });

        this.fieldResearchExerciseSequenceFeature.textProperty().addListener((observableValue, s, t1) ->
        {
            if (this.fieldResearchExerciseSequenceFeature.getText().isEmpty())
            {
                this.paginatorNodeSequenceFeature.removeFilter();
            }
            else
            {
                this.paginatorNodeSequenceFeature.applyFilter(this.fieldResearchExerciseSequenceFeature.getText());
            }
        });

        this.paneSequenceDeleteConfirm.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (observableValue.getValue())
            {
                this.sequenceLabelSequenceDeleteConfirm.setText(this.currentDeleteSequenceJson.id);
            }
            else
            {
                this.sequenceLabelSequenceDeleteConfirm.setText("");
            }
        });

        this.paneSequenceAttributionManager.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (observableValue.getValue())
            {
                this.fieldResearchSequenceAttributionManager.requestFocus();

                ArrayList<EcriluSequenceJson> sequenceJsons = DataService.readAllJsonOf(EcriluSequenceJson.class, "ecrilu", "sequences");

                sequenceJsons.sort(Comparator.comparing(o -> MonstroclasseUtils.accentRemover(o.id.toLowerCase())));

                double padding = 15;

                this.paginatorNodeSequenceAttributionManager = new PaginatorNode.Builder<EcriluSequenceJson>()
                        .withToPaginate(sequenceJsons)
                        .withGridDisposition(6, 2)
                        .withDimension(
                                this.internalPaneTwoOfSequenceAttributionManager.getPrefWidth() - padding * 2,
                                this.internalPaneTwoOfSequenceAttributionManager.getPrefHeight() - padding * 2
                        )
                        .withNodeCreator((obj, width, height) -> new SequenceAttributionCardNode(width, height, obj, mouseEvent ->
                        {
                            this.currentSequenceAttribution = obj;

                            /* ------- SWITCH TO SEQUENCE ATTRIBUTION EDIT PANE ------- */
                            AdminModEcrilu.getInstance().paneHandler.show("sequenceAttributionEdit");
                            /* -------------------------------------------------------- */
                        }, AppTheme.MONSTROCLASSE_COLOR_HEX))
                        .withFilterFunction((ecriluSequenceJson, s) -> ecriluSequenceJson.id.toLowerCase().contains(s.toLowerCase()))
                        .whenFiltered(node ->
                        {
                            if (node instanceof SequenceAttributionCardNode sequenceAttributionCardNode)
                            {
                                String filter = this.paginatorNodeSequenceAttributionManager.getPaginator().getCurrentFilter();

                                Text text = sequenceAttributionCardNode.getText();
                                String textContent = text.getText();

                                if (textContent.toLowerCase().contains(filter.toLowerCase()))
                                {
                                    int startIndex = textContent.toLowerCase().indexOf(filter.toLowerCase());
                                    int endIndex = startIndex + filter.length();
                                    text.setSelectionStart(startIndex);
                                    text.setSelectionEnd(endIndex);
                                    text.setSelectionFill(Color.web(AppTheme.ECRILU_RESEARCH_HIGHLIGHT_COLOR_HEX));
                                }
                            }
                        })
                        .onPageChange((page, hasPrevious, hasNext) ->
                        {
                            this.fieldPaginationSequenceAttributionManager.setText(String.valueOf(page));
                            this.leftPaginationBtnSequenceAttributionManager.setVisible(hasPrevious);
                            this.rightPaginationBtnSequenceAttributionManager.setVisible(hasNext);
                        })
                        .build();

                this.paginatorNodeSequenceAttributionManager.setLayoutX(padding);
                this.paginatorNodeSequenceAttributionManager.setLayoutY(padding);

                this.internalPaneTwoOfSequenceAttributionManager.getChildren().add(this.paginatorNodeSequenceAttributionManager);

                this.fieldPaginationSequencesManager.setText(String.valueOf(this.paginatorNodeSequenceAttributionManager.getPaginator().currentPage()));
            }
            else
            {
                this.internalPaneTwoOfSequenceAttributionManager.getChildren().clear();
                this.fieldResearchSequenceAttributionManager.setText("");
            }
        });

        this.fieldResearchSequenceAttributionManager.textProperty().addListener((observableValue, s, t1) ->
        {
            if (this.fieldResearchSequenceAttributionManager.getText().isEmpty())
            {
                this.paginatorNodeSequenceAttributionManager.removeFilter();
            }
            else
            {
                this.paginatorNodeSequenceAttributionManager.applyFilter(this.fieldResearchSequenceAttributionManager.getText());
            }
        });

        this.paneSequenceAttributionEdit.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (observableValue.getValue())
            {
                this.titleLabelSequenceAttributionEdit.setText(this.currentSequenceAttribution.id);

                final double width = this.internalPaneScrollSequenceAttributionEdit.getPrefWidth();
                final double height = this.internalPaneScrollSequenceAttributionEdit.getPrefHeight();

                final double widthCard = this.internalPaneScrollSequenceAttributionEdit.getPrefWidth();
                final double heightCard = this.internalPaneScrollSequenceAttributionEdit.getPrefHeight() * 0.18f;

                VerticalScrollPane verticalScrollPane = new VerticalScrollPane(width, height, 5);

                Path pathOfSequenceAttributionCsv = Path.of(DataService.pathOf("attr_seq.csv", "ecrilu"));
                File sequenceAttributionCsvFile = new File(pathOfSequenceAttributionCsv.toUri());

                //noinspection deprecation
                try (Reader reader = new FileReader(sequenceAttributionCsvFile);
                     CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader()))
                {
                    for (CSVRecord csvRecord : csvParser)
                    {
                        int pupilId = Integer.parseInt(csvRecord.get("Pupil"));
                        Pupil pupil = PupilRepository.read(pupilId);
                        verticalScrollPane.addNode(new PupilSequenceAttributionEditCard(
                                Objects.requireNonNull(pupil),
                                widthCard,
                                heightCard,
                                this.currentSequenceAttribution,
                                Integer.parseInt(csvRecord.get(this.currentSequenceAttribution.id)) == 1)
                        );
                    }
                }
                catch (Exception e)
                {
                    System.err.println("Erreur lors de la lecture du fichier CSV : " + e.getMessage());
                }

                this.internalPaneScrollSequenceAttributionEdit.getChildren().add(verticalScrollPane);
            }
            else
            {
                this.titleLabelSequenceAttributionEdit.setText("");
                this.internalPaneScrollSequenceAttributionEdit.getChildren().clear();
            }
        });

        this.panePupilFollowSequenceSelection.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (observableValue.getValue())
            {
                this.fieldResearchPupilFollowSequenceSelection.requestFocus();

                ArrayList<EcriluSequenceJson> sequenceJsons = DataService.readAllJsonOf(EcriluSequenceJson.class, "ecrilu", "sequences");

                sequenceJsons.sort(Comparator.comparing(o -> MonstroclasseUtils.accentRemover(o.id.toLowerCase())));

                double padding = 15;

                this.paginatorNodePupilFollowSequenceSelection = new PaginatorNode.Builder<EcriluSequenceJson>()
                        .withToPaginate(sequenceJsons)
                        .withGridDisposition(6, 2)
                        .withDimension(
                                this.internalPaneTwoOfPupilFollowSequenceSelection.getPrefWidth() - padding * 2,
                                this.internalPaneTwoOfPupilFollowSequenceSelection.getPrefHeight() - padding * 2
                        )
                        .withNodeCreator((obj, width, height) -> new SequenceAttributionCardNode(width, height, obj, mouseEvent ->
                        {
                            this.currentSequenceToFollow = obj;

                            /* ------- SWITCH TO PUPIL FOLLOW SEQUENCE PANE ------- */
                            AdminModEcrilu.getInstance().paneHandler.show("pupilFollowSequence");
                            /* ---------------------------------------------------- */
                        }, AppTheme.ECRILU_STUDENT_SUB_NODE_COLOR_HEX))
                        .withFilterFunction((ecriluSequenceJson, s) -> ecriluSequenceJson.id.toLowerCase().contains(s.toLowerCase()))
                        .whenFiltered(node ->
                        {
                            if (node instanceof SequenceAttributionCardNode sequenceAttributionCardNode)
                            {
                                String filter = this.paginatorNodePupilFollowSequenceSelection.getPaginator().getCurrentFilter();

                                Text text = sequenceAttributionCardNode.getText();
                                String textContent = text.getText();

                                if (textContent.toLowerCase().contains(filter.toLowerCase()))
                                {
                                    int startIndex = textContent.toLowerCase().indexOf(filter.toLowerCase());
                                    int endIndex = startIndex + filter.length();
                                    text.setSelectionStart(startIndex);
                                    text.setSelectionEnd(endIndex);
                                    text.setSelectionFill(Color.web(AppTheme.ECRILU_RESEARCH_HIGHLIGHT_COLOR_HEX));
                                }
                            }
                        })
                        .onPageChange((page, hasPrevious, hasNext) ->
                        {
                            this.fieldPaginationPupilFollowSequenceSelection.setText(String.valueOf(page));
                            this.leftPaginationBtnPupilFollowSequenceSelection.setVisible(hasPrevious);
                            this.rightPaginationBtnPupilFollowSequenceSelection.setVisible(hasNext);
                        })
                        .build();

                this.paginatorNodePupilFollowSequenceSelection.setLayoutX(padding);
                this.paginatorNodePupilFollowSequenceSelection.setLayoutY(padding);

                this.internalPaneTwoOfPupilFollowSequenceSelection.getChildren().add(this.paginatorNodePupilFollowSequenceSelection);

                this.fieldPaginationPupilFollowSequenceSelection.setText(String.valueOf(this.paginatorNodePupilFollowSequenceSelection.getPaginator().currentPage()));
            }
            else
            {
                this.internalPaneTwoOfPupilFollowSequenceSelection.getChildren().clear();
                this.fieldResearchPupilFollowSequenceSelection.setText("");
            }
        });

        this.fieldResearchPupilFollowSequenceSelection.textProperty().addListener((observableValue, s, t1) ->
        {
            if (this.fieldResearchPupilFollowSequenceSelection.getText().isEmpty())
            {
                this.paginatorNodePupilFollowSequenceSelection.removeFilter();
            }
            else
            {
                this.paginatorNodePupilFollowSequenceSelection.applyFilter(this.fieldResearchPupilFollowSequenceSelection.getText());
            }
        });

        this.panePupilFollowSequence.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (observableValue.getValue())
            {
                this.labelSequenceTitlePupilFollowSequence.setText(this.currentSequenceToFollow.id);

                SequencePupilFollowGridPane sequencePupilFollowGridPane = new SequencePupilFollowGridPane(
                    this.paneExercisesInfoBarPupilFollowSequence,
                    this.panePupilNotesPupilFollowSequence,
                    Path.of(DataService.pathOf(this.currentSequenceToFollow.id + ".csv", "ecrilu", "attr"))
                );

                sequencePupilFollowGridPane.bindSelector(
                        this.leftPaginationBtnPupilFollowSequence, this.rightPaginationBtnPupilFollowSequence
                );
            }
            else
            {
                this.paneExercisesInfoBarPupilFollowSequence.getChildren().clear();
                this.panePupilNotesPupilFollowSequence.getChildren().clear();
                this.labelSequenceTitlePupilFollowSequence.setText("");
            }
        });

        appBanner.toFront(); // Force to be in front of all objects
        currentAccountImage.toFront(); // Force to be in front of all objects

        this.wordFieldWordFeature.setOnKeyTyped(event ->
        {
            String text = this.wordFieldWordFeature.getText();
            if (text.length() >= Constants.MAX_WORD_SIZE)
            {
                this.wordFieldWordFeature.setText(text.substring(0, Constants.MAX_WORD_SIZE));
                this.wordFieldWordFeature.positionCaret(this.wordFieldWordFeature.getLength());
            }
        });

        this.fieldIdentifierExerciseFeature.setOnKeyTyped(event ->
        {
            String text = this.fieldIdentifierExerciseFeature.getText();
            if (text.length() >= Constants.LIMIT_CHARACTERS_EXERCISE_ID)
            {
                this.fieldIdentifierExerciseFeature.setText(text.substring(0, Constants.LIMIT_CHARACTERS_EXERCISE_ID));
                this.fieldIdentifierExerciseFeature.positionCaret(this.fieldIdentifierExerciseFeature.getLength());
            }
        });

        this.fieldIdentifierSequenceFeature.setOnKeyTyped(event ->
        {
            String text = this.fieldIdentifierSequenceFeature.getText();
            if (text.length() >= Constants.LIMIT_CHARACTERS_SEQUENCE_ID)
            {
                this.fieldIdentifierSequenceFeature.setText(text.substring(0, Constants.LIMIT_CHARACTERS_SEQUENCE_ID));
                this.fieldIdentifierSequenceFeature.positionCaret(this.fieldIdentifierSequenceFeature.getLength());
            }
        });

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
    public void pupilFollowClick(MouseEvent mouseEvent)
    {
        /* ------- SWITCH TO PUPIL FOLLOW SEQUENCE SELECTION PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("pupilFollowSequenceSelection");
        /* -------------------------------------------------------------- */
    }

    @FXML
    public void wordCreationClick(MouseEvent mouseEvent)
    {
        /* ------- SWITCH TO WORDS MANAGER PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("wordsManager");
        /* -------------------------------------------- */
    }

    @FXML
    public void exerciseCreationClick(MouseEvent mouseEvent)
    {
        /* ------- SWITCH TO EXERCISES MANAGER PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("exercisesManager");
        /* ------------------------------------------------ */
    }

    @FXML
    public void sequenceCreationClick(MouseEvent mouseEvent)
    {
        /* ------- SWITCH TO SEQUENCES MANAGER PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("sequencesManager");
        /* ------------------------------------------------ */
    }

    @FXML
    public void sequenceAttributionClick(MouseEvent mouseEvent)
    {
        /* ------- SWITCH TO SEQUENCE ATTRIBUTION MANAGER PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("sequenceAttributionManager");
        /* ----------------------------------------------------------- */
    }

    /*

        // Words Manager External Events

    */

    @FXML
    public void backBtnWordsManagerClick(ActionEvent actionEvent)
    {
        this.wordFeatureMode = null;

        /* ------- SWITCH TO HOME PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("home");
        /* ----------------------------------- */
    }

    @FXML
    public void addWordBtnWordsManagerClick(ActionEvent actionEvent)
    {
        this.wordFeatureMode = FeatureMode.ADDING;

        /* ------- SWITCH TO WORD FEATURE PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("wordFeature");
        /* ------------------------------------------- */
    }

    @FXML
    public void firstPaginationPaneWordsManagerClick(MouseEvent mouseEvent)
    {
        this.paginatorNodeWordsManager.getPaginator().firstPage();
    }


    @FXML
    public void leftPaginationBtnWordsManagerClick(ActionEvent actionEvent)
    {
        this.paginatorNodeWordsManager.getPaginator().previousPage();
    }

    @FXML
    public void rightPaginationBtnWordsManagerClick(ActionEvent actionEvent)
    {
        this.paginatorNodeWordsManager.getPaginator().nextPage();
    }

    @FXML
    public void lastPaginationPaneWordsManagerClick(MouseEvent mouseEvent)
    {
        this.paginatorNodeWordsManager.getPaginator().lastPage();
    }

    /*

        // Word Feature External Events

    */

    @FXML
    public void backBtnWordFeatureClick(ActionEvent actionEvent)
    {
        if (this.wordFeatureMode == FeatureMode.MODIFYING)
        {
            this.currentModifyWordJson = null;
        }

        /* ------- SWITCH TO WORDS MANAGER PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("wordsManager");
        /* -------------------------------------------- */
    }

    @FXML
    public void carriageReturnBtnWordFeatureClick(ActionEvent actionEvent)
    {
        if (!wordCompositionFeatureWord.isEmpty()) wordCompositionFeatureWord.removeLast();
    }

    @FXML
    public void restartBtnWordFeatureClick(ActionEvent actionEvent)
    {
        wordCompositionFeatureWord.clear();
    }

    @FXML
    public void validateBtnWordFeatureClick(ActionEvent actionEvent)
    {
        if (!wordCompositionFeatureWord.isEmpty())
        {
            if (!this.wordFieldWordFeature.getText().isEmpty())
            {
                if (!this.fieldIdentifierWordFeature.getText().isEmpty())
                {
                    if (this.currentAudioFileSelectedWordFeature != null)
                    {
                        EcriluWordJson ecriluWordJson = new EcriluWordJson();
                        ecriluWordJson.id = this.fieldIdentifierWordFeature.getText();
                        ecriluWordJson.word = this.wordFieldWordFeature.getText();
                        ecriluWordJson.audioPath = this.currentAudioFileSelectedWordFeature.getName();
                        ecriluWordJson.composition = new LinkedList<>(wordCompositionFeatureWord);

                        // ------- Copy the audio file into audio folder ------- //
                        File dataFolder = new File(DataService.pathOf("ecrilu/audio"));
                        if (dataFolder.exists())
                        {
                            if (this.currentAudioFileSelectedWordFeature.getParentFile() != dataFolder)
                            {
                                try
                                {
                                    Path output = Path.of(dataFolder.getAbsolutePath() + "/" +
                                            this.currentAudioFileSelectedWordFeature.getName()
                                    );
                                    Files.copy(this.currentAudioFileSelectedWordFeature.toPath(), output);
                                }
                                catch (IOException e)
                                {
                                    Main.logger.error(e.getMessage());
                                }
                            }
                        }
                        // ----------------------------------------------------- //

                        if (this.wordFeatureMode == FeatureMode.MODIFYING)
                        {
                            DataService.delete(this.currentModifyWordJson.id + ".json", "ecrilu", "words");
                            DataService.saveJson(ecriluWordJson, ecriluWordJson.id, "ecrilu", "words");

                            EventListenerFocusedWord.callWordUpdated(this.currentModifyWordJson, ecriluWordJson);
                        }
                        else
                        {
                            DataService.saveJson(ecriluWordJson, ecriluWordJson.id, "ecrilu", "words");

                            EventListenerFocusedWord.callWordCreated(ecriluWordJson);
                        }

                        this.wordFeatureMode = null;

                        /* ------- SWITCH TO WORDS MANAGER PANE ------- */
                        AdminModEcrilu.getInstance().paneHandler.show("wordsManager");
                        /* -------------------------------------------- */
                    }
                }
            }
        }
    }

    @FXML
    public void folderBtnWordFeatureClick(ActionEvent actionEvent)
    {
        try
        {
            // ------- Creation of FileChooser ------- //
            FileChooser fileChooser = new FileChooser();
            // --------------------------------------- //

            fileChooser.setTitle("Sélectionnez un fichier");
            fileChooser.setInitialDirectory(new File(DataService.pathOf("ecrilu/audio")));

            String extensionFilterName = "Audio Files";
            FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter(extensionFilterName, Constants.AUDIO_EXTENSION_SUPPORTED);
            fileChooser.getExtensionFilters().add(ext);

            File selectedFile = fileChooser.showOpenDialog(PersistentData.stageHandler.getStage());

            // ***** GET THE EXTENSION OF THE FILE ***** //
            String extension = "*." + selectedFile.getName().split("\\.")[1].toLowerCase();
            // ***** GET THE EXTENSION OF THE FILE ***** //

            if (Arrays.asList(Constants.AUDIO_EXTENSION_SUPPORTED).contains(extension))
            {
                long size = selectedFile.length();
                double sizeInMO = (double) size / (1024 * 1024);

                if (sizeInMO > Constants.LIMIT_SIZE_AUDIO_IN_MO)
                {
                    this.labelAudioFilenameWordFeature.setVisible(false);
                    this.labelAudioFileIsTooHeavyWordFeature.setVisible(true);
                }
                else
                {
                    if (PersistentData.anAudioIsPlaying) PersistentData.currentPlayedAudio.stop();
                    this.labelAudioFilenameWordFeature.setVisible(true);
                    this.labelAudioFileIsTooHeavyWordFeature.setVisible(false);
                    this.labelAudioFilenameWordFeature.setText(selectedFile.getName());
                    this.currentAudioFileSelectedWordFeature = selectedFile;
                }
            }
            else
            {
                LOGGER.warn("No file selected or with an invalid extension !");
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage());
        }
    }

    @FXML
    public void playAudioFileBtnWordFeatureClick(ActionEvent actionEvent)
    {
        if (this.currentAudioFileSelectedWordFeature != null)
        {
            MonstroclasseUtils.playAudio(this.currentAudioFileSelectedWordFeature.getAbsolutePath());
        }
    }

    @FXML
    public void deleteAudioFileBtnWordFeatureClick(ActionEvent actionEvent)
    {
        if (PersistentData.anAudioIsPlaying) PersistentData.currentPlayedAudio.stop();
        this.currentAudioFileSelectedWordFeature = null;
        this.labelAudioFilenameWordFeature.setText("");
    }

    /*

        // Word Delete Confirm External Events

    */

    @FXML
    public void undoWordDeleteConfirmBtnClick(ActionEvent actionEvent)
    {
        this.currentDeleteWordJson = null;

        /* ------- SWITCH TO HOME PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("wordsManager");
        /* ----------------------------------- */
    }

    @FXML
    public void checkWordDeleteConfirmBtnClick(ActionEvent actionEvent)
    {
        DataService.delete(this.currentDeleteWordJson.id + ".json", "ecrilu", "words");
        // DataService.delete(currentDeleteWordJson.audioPath, "ecrilu", "audio");

        EventListenerFocusedWord.callWordDeleted(this.currentDeleteWordJson);

        currentDeleteWordJson = null;

        /* ------- SWITCH TO HOME PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("wordsManager");
        /* ----------------------------------- */
    }

    /*

        // Exercises Manager External Events

    */

    @FXML
    public void backBtnExercisesManagerClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO HOME PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("home");
        /* ----------------------------------- */
    }

    @FXML
    public void addExerciseBtnExercisesManagerClick(ActionEvent actionEvent)
    {
        this.exerciseFeatureMode = FeatureMode.ADDING;

        /* ------- SWITCH TO EXERCISE FEATURE PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("exerciseFeature");
        /* ----------------------------------------------- */
    }

    @FXML
    public void firstPaginationPaneExercisesManagerClick(MouseEvent mouseEvent)
    {
        this.paginatorNodeExercisesManager.getPaginator().firstPage();
    }


    @FXML
    public void leftPaginationBtnExercisesManagerClick(ActionEvent actionEvent)
    {
        this.paginatorNodeExercisesManager.getPaginator().previousPage();
    }

    @FXML
    public void fieldPaginationExercisesManagerKeyPressed(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
            try
            {
                int entry = Integer.parseInt(this.fieldPaginationExercisesManager.getText());
                this.paginatorNodeExercisesManager.getPaginator().setPage(entry);
            }
            catch (NumberFormatException ignored) {}
            finally
            {
                this.fieldPaginationExercisesManager.setText(String.valueOf(this.paginatorNodeExercisesManager.getPaginator().currentPage()));
                this.fieldPaginationExercisesManager.positionCaret(this.fieldPaginationExercisesManager.getText().length());
            }
        }
    }

    @FXML
    public void rightPaginationBtnExercisesManagerClick(ActionEvent actionEvent)
    {
        this.paginatorNodeExercisesManager.getPaginator().nextPage();
    }

    @FXML
    public void lastPaginationPaneExercisesManagerClick(MouseEvent mouseEvent)
    {
        this.paginatorNodeExercisesManager.getPaginator().lastPage();
    }

    /*

        // Exercise Feature External Events

    */

    @FXML
    public void backBtnExerciseFeatureClick(ActionEvent actionEvent)
    {
        this.isInEncodingEcritoireExerciseFeatureEncoding = false;
        this.exerciseFeatureMode = null;

        /* ------- SWITCH TO EXERCISES MANAGER PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("exercisesManager");
        /* ------------------------------------------------ */
    }

    @FXML
    public void encodingBtnExerciseFeatureClick(ActionEvent actionEvent)
    {
        if (!this.ecritoireTonesExerciseFeature.isEmpty())
        {
            if (!this.fieldIdentifierExerciseFeature.getText().isEmpty())
            {
                if (!this.fieldTitleExerciseFeature.getText().isEmpty())
                {
                    this.isInEncodingEcritoireExerciseFeatureEncoding = true;

                    /* ------- SWITCH TO EXERCISE FEATURE ENCODING PANE ------- */
                    AdminModEcrilu.getInstance().paneHandler.show("exerciseFeatureEncoding");
                    /* -------------------------------------------------------- */
                }
            }
        }
    }

    @FXML
    public void firstPaginationPaneExerciseFeatureClick(MouseEvent mouseEvent)
    {
        this.paginatorNodeExerciseFeature.getPaginator().firstPage();
    }


    @FXML
    public void leftPaginationBtnExerciseFeatureClick(ActionEvent actionEvent)
    {
        this.paginatorNodeExerciseFeature.getPaginator().previousPage();
    }

    @FXML
    public void rightPaginationBtnExerciseFeatureClick(ActionEvent actionEvent)
    {
        this.paginatorNodeExerciseFeature.getPaginator().nextPage();
    }

    @FXML
    public void lastPaginationPaneExerciseFeatureClick(MouseEvent mouseEvent)
    {
        this.paginatorNodeExerciseFeature.getPaginator().lastPage();
    }

    @FXML
    public void fieldPaginationExerciseFeatureKeyPressed(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
            try
            {
                int entry = Integer.parseInt(this.fieldPaginationExerciseFeature.getText());
                this.paginatorNodeExerciseFeature.getPaginator().setPage(entry);
            }
            catch (NumberFormatException ignored) {}
            finally
            {
                this.fieldPaginationExerciseFeature.setText(String.valueOf(this.paginatorNodeExerciseFeature.getPaginator().currentPage()));
                this.fieldPaginationExerciseFeature.positionCaret(this.fieldPaginationExerciseFeature.getText().length());
            }
        }
    }

    /*

        // Exercise Feature Encoding External Events

    */

    @FXML
    public void backBtnExerciseFeatureEncodingClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO EXERCISE FEATURE PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("exerciseFeature");
        /* ----------------------------------------------- */
    }

    @FXML
    public void validateBtnExerciseFeatureEncodingClick(ActionEvent actionEvent)
    {
        if (!this.ecritoireTonesExerciseFeature.isEmpty())
        {
            if (!this.fieldIdentifierExerciseFeature.getText().isEmpty())
            {
                if (!this.fieldTitleExerciseFeature.getText().isEmpty())
                {
                    EcriluExerciseJson ecriluExerciseJson = new EcriluExerciseJson();

                    ecriluExerciseJson.id = this.fieldIdentifierExerciseFeature.getText();
                    ecriluExerciseJson.title = this.fieldTitleExerciseFeature.getText();
                    ecriluExerciseJson.words = new LinkedList<>();

                    if (this.internalPaneThreeOfExerciseFeature.getChildren().getFirst() instanceof VerticalScrollPane verticalScrollPane)
                    {
                        // ------- Add all words id selected into exercise ------- //
                        for (WordCardSingleActionNode wordCardSingleActionNode : verticalScrollPane.getOrderedContent(WordCardSingleActionNode.class))
                        {
                            ecriluExerciseJson.words.add(wordCardSingleActionNode.getEcriluWordJson().id);
                        }
                        // ---------------------------------------------------------- //
                    }

                    Ecritoire ecritoire = new Ecritoire();
                    ecritoire.writingTable = new LinkedList<>();

                    if (this.ecritoireSubPaneExerciseFeatureEncoding.getChildren().getFirst() instanceof EcritoireNode ecritoireNode)
                    {
                        ecritoireNode.applyForeach(ecritoireSectionNode ->
                        {
                            EcritoireColumn ecritoireColumn = ecritoireSectionNode.loadLockedAsEcritoireBigColumn();
                            if (ecritoireColumn != null) ecritoire.writingTable.add(ecritoireColumn);
                        });
                    }

                    ecriluExerciseJson.ecritoire = ecritoire;

                    if (this.exerciseFeatureMode == FeatureMode.MODIFYING)
                    {
                        DataService.delete(this.currentModifyExerciseJson.id + ".json", "ecrilu", "exercises");
                        DataService.saveJson(ecriluExerciseJson, ecriluExerciseJson.id, "ecrilu", "exercises");

                        EventListenerFocusedExercise.callExerciseUpdated(this.currentModifyExerciseJson, ecriluExerciseJson);
                    }
                    else
                    {
                        DataService.saveJson(ecriluExerciseJson, ecriluExerciseJson.id, "ecrilu", "exercises");

                        EventListenerFocusedExercise.callExerciseCreated(ecriluExerciseJson);
                    }

                    this.exerciseFeatureMode = null;

                    this.isInEncodingEcritoireExerciseFeatureEncoding = false;

                    this.ecritoireTonesExerciseFeature.clear();

                    this.internalPaneThreeOfExerciseFeature.getChildren().clear();
                    this.subInternalTwoofOneExerciseFeature.getChildren().clear();
                    fieldResearchWordExerciseFeature.setText("");
                    fieldIdentifierExerciseFeature.setText("");
                    this.fieldTitleExerciseFeature.setText("");

                    /* ------- SWITCH TO EXERCISES MANAGER PANE ------- */
                    AdminModEcrilu.getInstance().paneHandler.show("exercisesManager");
                    /* ------------------------------------------------ */
                }
            }
        }
    }

    /*

        // Exercise Delete Confirm External Events

    */

    @FXML
    public void undoExerciseDeleteConfirmBtnClick(ActionEvent actionEvent)
    {
        this.currentDeleteExerciseJson = null;

        /* ------- SWITCH TO EXERCISES MANAGER PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("exercisesManager");
        /* ------------------------------------------------ */
    }

    @FXML
    public void checkExerciseDeleteConfirmBtnClick(ActionEvent actionEvent)
    {
        DataService.delete(this.currentDeleteExerciseJson.id + ".json", "ecrilu", "exercises");

        EventListenerFocusedExercise.callExerciseDeleted(this.currentDeleteExerciseJson);

        this.currentDeleteExerciseJson = null;

        /* ------- SWITCH TO EXERCISES MANAGER PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("exercisesManager");
        /* ------------------------------------------------ */
    }

    /*

        // Sequences Manager External Events

    */

    @FXML
    public void backBtnSequencesManagerClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO HOME PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("home");
        /* ----------------------------------- */
    }

    @FXML
    public void addSequenceBtnSequencesManagerClick(ActionEvent actionEvent)
    {
        this.sequenceFeatureMode = FeatureMode.ADDING;

        /* ------- SWITCH TO SEQUENCE FEATURE PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("sequenceFeature");
        /* ----------------------------------------------- */
    }

    @FXML
    public void firstPaginationPaneSequencesManagerClick(MouseEvent mouseEvent)
    {
        this.paginatorNodeSequencesManager.getPaginator().firstPage();
    }

    @FXML
    public void leftPaginationBtnSequencesManagerClick(ActionEvent actionEvent)
    {
        this.paginatorNodeSequencesManager.getPaginator().previousPage();
    }

    @FXML
    public void fieldPaginationSequencesManagerKeyPressed(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
            try
            {
                int entry = Integer.parseInt(this.fieldPaginationSequencesManager.getText());
                this.paginatorNodeSequencesManager.getPaginator().setPage(entry);
            }
            catch (NumberFormatException ignored) {}
            finally
            {
                this.fieldPaginationSequencesManager.setText(String.valueOf(this.paginatorNodeSequencesManager.getPaginator().currentPage()));
                this.fieldPaginationSequencesManager.positionCaret(this.fieldPaginationSequencesManager.getText().length());
            }
        }
    }

    @FXML
    public void rightPaginationBtnSequencesManagerClick(ActionEvent actionEvent)
    {
        this.paginatorNodeSequencesManager.getPaginator().nextPage();
    }

    @FXML
    public void lastPaginationPaneSequencesManagerClick(MouseEvent mouseEvent)
    {
        this.paginatorNodeSequencesManager.getPaginator().lastPage();
    }

     /*

        // Sequence Feature External Events

    */

    @FXML
    public void backBtnSequenceFeatureClick(ActionEvent actionEvent)
    {
        // ------- The user is not in Sequence Feature Pane ------- //
        this.sequenceFeatureMode = null;
        // -------------------------------------------------------- //

        /* ------- SWITCH TO SEQUENCES MANAGER PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("sequencesManager");
        /* ------------------------------------------------ */
    }

    @FXML
    public void validateBtnSequenceFeatureClick(ActionEvent actionEvent)
    {
        // Verify if all fields are filled to process Creation / Modification
        if (!this.exerciseJsonsSequenceFeature.isEmpty())
        {
            if (!this.fieldIdentifierSequenceFeature.getText().isEmpty())
            {
                if (!this.fieldTitleSequenceFeature.getText().isEmpty())
                {
                    // ------- Creation of Json that represent an Ecrilu sequence ------- //
                    EcriluSequenceJson ecriluSequenceJson = new EcriluSequenceJson();
                    ecriluSequenceJson.id = this.fieldIdentifierSequenceFeature.getText();
                    ecriluSequenceJson.title = this.fieldTitleSequenceFeature.getText();
                    ecriluSequenceJson.exercises = new LinkedList<>();
                    // ------------------------------------------------------------------ //

                    if (this.internalPaneThreeOfSequenceFeature.getChildren().getFirst() instanceof VerticalScrollPane verticalScrollPane)
                    {
                        // ------- Add all exercises id selected into sequence ------- //
                        for (ExerciseCardSingleActionNode exerciseCardSingleActionNode : verticalScrollPane.getOrderedContent(ExerciseCardSingleActionNode.class))
                        {
                            ecriluSequenceJson.exercises.add(exerciseCardSingleActionNode.getEcriluExerciseJson().id);
                        }
                        // ---------------------------------------------------------- //
                    }

                    if (this.sequenceFeatureMode == FeatureMode.MODIFYING)
                    {
                        // ------- Modify the file in recreated it ------- //
                        DataService.delete(this.currentModifySequenceJson.id + ".json", "ecrilu", "sequences");
                        DataService.saveJson(ecriluSequenceJson, ecriluSequenceJson.id, "ecrilu", "sequences");

                        EventListenerFocusedSequence.callSequenceUpdated(this.currentModifySequenceJson, ecriluSequenceJson);
                    }
                    else
                    {
                        DataService.saveJson(ecriluSequenceJson, ecriluSequenceJson.id, "ecrilu", "sequences");

                        EventListenerFocusedSequence.callSequenceCreated(ecriluSequenceJson);
                    }

                    // ------- Clean data of Sequence Feature Pane ------- //
                    this.sequenceFeatureMode = null;
                    this.exerciseJsonsSequenceFeature.clear();
                    this.internalPaneThreeOfSequenceFeature.getChildren().clear();
                    this.subInternalTwoofOneSequenceFeature.getChildren().clear();
                    fieldResearchExerciseSequenceFeature.setText("");
                    fieldIdentifierSequenceFeature.setText("");
                    this.fieldTitleSequenceFeature.setText("");
                    // --------------------------------------------------- //

                    /* ------- SWITCH TO SEQUENCES MANAGER PANE ------- */
                    AdminModEcrilu.getInstance().paneHandler.show("sequencesManager");
                    /* ------------------------------------------------ */
                }
            }
        }
    }

    @FXML
    public void firstPaginationPaneSequenceFeatureClick(MouseEvent mouseEvent)
    {
        this.paginatorNodeSequenceFeature.getPaginator().firstPage();
    }


    @FXML
    public void leftPaginationBtnSequenceFeatureClick(ActionEvent actionEvent)
    {
        this.paginatorNodeSequenceFeature.getPaginator().previousPage();
    }

    @FXML
    public void fieldPaginationSequenceFeatureKeyPressed(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
            try
            {
                int entry = Integer.parseInt(this.fieldPaginationSequenceFeature.getText());
                this.paginatorNodeSequenceFeature.getPaginator().setPage(entry);
            }
            catch (NumberFormatException ignored) {}
            finally
            {
                this.fieldPaginationSequenceFeature.setText(String.valueOf(this.paginatorNodeSequenceFeature.getPaginator().currentPage()));
                this.fieldPaginationSequenceFeature.positionCaret(this.fieldPaginationSequenceFeature.getText().length());
            }
        }
    }

    @FXML
    public void rightPaginationBtnSequenceFeatureClick(ActionEvent actionEvent)
    {
        this.paginatorNodeSequenceFeature.getPaginator().nextPage();
    }

    @FXML
    public void lastPaginationPaneSequenceFeatureClick(MouseEvent mouseEvent)
    {
        this.paginatorNodeSequenceFeature.getPaginator().lastPage();
    }

    /*

        // Sequence Delete Confirm External Events

    */

    @FXML
    public void undoSequenceDeleteConfirmBtnClick(ActionEvent actionEvent)
    {
        // ------- Reset data (no more use of the sequence) ------- //
        this.currentDeleteSequenceJson = null;
        // -------------------------------------------------------- //

        /* ------- SWITCH TO SEQUENCES MANAGER PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("sequencesManager");
        /* ------------------------------------------------ */
    }

    @FXML
    public void checkSequenceDeleteConfirmBtnClick(ActionEvent actionEvent)
    {
        // ------- Suppression of the sequence being deleted ------- //
        DataService.delete(this.currentDeleteSequenceJson.id + ".json", "ecrilu", "sequences");
        // --------------------------------------------------------- //

        // ------- Call event "onSequenceDelete" ------- //
        EventListenerFocusedSequence.callSequenceDeleted(this.currentDeleteSequenceJson);
        // --------------------------------------------- //

        // ------- Reset data (no more use of the sequence) ------- //
        this.currentDeleteSequenceJson = null;
        // -------------------------------------------------------- //

        /* ------- SWITCH TO SEQUENCES MANAGER PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("sequencesManager");
        /* ------------------------------------------------ */
    }

    /*

        // Sequence Attribution Manager External Events

    */

    @FXML
    public void backBtnSequenceAttributionManagerClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO HOME PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("home");
        /* ----------------------------------- */
    }

    @FXML
    public void firstPaginationPaneSequenceAttributionManagerClick(MouseEvent mouseEvent)
    {
        this.paginatorNodeSequenceAttributionManager.getPaginator().firstPage();
    }


    @FXML
    public void leftPaginationBtnSequenceAttributionManagerClick(ActionEvent actionEvent)
    {
        this.paginatorNodeSequenceAttributionManager.getPaginator().previousPage();
    }

    @FXML
    public void fieldPaginationSequenceAttributionManagerKeyPressed(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
            try
            {
                int entry = Integer.parseInt(this.fieldPaginationSequenceAttributionManager.getText());
                this.paginatorNodeSequenceAttributionManager.getPaginator().setPage(entry);
            }
            catch (NumberFormatException ignored) {}
            finally
            {
                this.fieldPaginationSequenceAttributionManager.setText(String.valueOf(this.paginatorNodeSequenceAttributionManager.getPaginator().currentPage()));
                this.fieldPaginationSequenceAttributionManager.positionCaret(this.fieldPaginationSequenceAttributionManager.getText().length());
            }
        }
    }

    @FXML
    public void rightPaginationBtnSequenceAttributionManagerClick(ActionEvent actionEvent)
    {
        this.paginatorNodeSequenceAttributionManager.getPaginator().nextPage();
    }

    @FXML
    public void lastPaginationPaneSequenceAttributionManagerClick(MouseEvent mouseEvent)
    {
        this.paginatorNodeSequenceAttributionManager.getPaginator().lastPage();
    }

    /*

        // Sequence Attribution Edit External Events

    */

    @FXML
    public void backBtnSequenceAttributionEditClick(ActionEvent actionEvent)
    {
        this.currentSequenceAttribution = null;

        /* ------- SWITCH TO SEQUENCE ATTRIBUTION MANAGER PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("sequenceAttributionManager");
        /* ----------------------------------------------------------- */
    }

    @FXML
    public void uncheckAllBtnSequenceAttributionEditClick(ActionEvent actionEvent)
    {
        if (this.internalPaneScrollSequenceAttributionEdit.getChildren().getFirst() instanceof VerticalScrollPane pane)
        {
            pane.forEachContent(PupilSequenceAttributionEditCard.class, pupilModuleAccessEditCard ->
            {
                pupilModuleAccessEditCard.getCheckBoxModuleEdit().uncheck();
            });
        }
    }

    @FXML
    public void checkAllBtnSequenceAttributionEditClick(ActionEvent actionEvent)
    {
        if (this.internalPaneScrollSequenceAttributionEdit.getChildren().getFirst() instanceof VerticalScrollPane pane)
        {
            pane.forEachContent(PupilSequenceAttributionEditCard.class, pupilModuleAccessEditCard ->
            {
                pupilModuleAccessEditCard.getCheckBoxModuleEdit().check();
            });
        }
    }

    @FXML
    public void saveBtnSequenceAttributionEditClick(ActionEvent actionEvent)
    {
        Path pathOfSequenceAttributionCsv = Path.of(DataService.pathOf("attr_seq.csv", "ecrilu"));
        File sequenceAttributionCsvFile = new File(pathOfSequenceAttributionCsv.toUri());

        // Vérifier si le fichier CSV existe
        if (!sequenceAttributionCsvFile.exists())
        {
            System.err.println("Le fichier CSV n'existe pas.");
            return;
        }

        if (this.internalPaneScrollSequenceAttributionEdit.getChildren().getFirst() instanceof VerticalScrollPane pane)
        {
            LinkedList<CSVRecord> csvRecords = new LinkedList<>();
            LinkedList<List<String>> newRecords = new LinkedList<>();

            try (Reader reader = new FileReader(sequenceAttributionCsvFile);
                 CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT))
            {
                List<CSVRecord> csvRecordArrayList = csvParser.getRecords();

                for (var i = 0; i < csvRecordArrayList.size(); i++)
                {
                    if (i != 0)
                    {
                        csvRecords.add(csvRecordArrayList.get(i));
                    }
                    else
                    {
                        newRecords.add(List.of(csvRecordArrayList.get(i).values()));
                    }
                }
            }
            catch (Exception e)
            {
                System.err.println("Erreur lors de la lecture du fichier CSV : " + e.getMessage());
            }

            pane.forEachContent(PupilSequenceAttributionEditCard.class, card -> csvRecords.forEach(csvRecord ->
            {
                try
                {
                    if (Integer.parseInt(csvRecord.get(0)) == card.getPupil().getIdPupil())
                    {
                        String[] values = csvRecord.values();
                        String val = card.getSequence().id;

                        int index = -1;

                        for (int i = 0; i < newRecords.getFirst().size(); i++)
                        {
                            if (newRecords.getFirst().get(i).equals(val))
                            {
                                index = i;
                                break;
                            }
                        }

                        if (index != -1)
                        {
                            values[index] = card.getCheckBoxModuleEdit().isEnable() ? "1" : "0";
                        }

                        newRecords.add(List.of(values));
                    }
                }
                catch (NumberFormatException ignored) {}
            }));

            try
            {
                Files.delete(sequenceAttributionCsvFile.toPath());
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }

            try (Writer writer = new FileWriter(sequenceAttributionCsvFile);
                 CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT))
            {

                for (List<String> newRecord : newRecords)
                {
                    csvPrinter.printRecord(newRecord);
                }

                Main.logger.info("[attr_seq.csv] created !");
            }
            catch (IOException e)
            {
                Main.logger.error("Error in creation of [attr_seq.csv] !");
            }
        }

        /* ------- SWITCH TO SEQUENCE ATTRIBUTION MANAGER PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("sequenceAttributionManager");
        /* ----------------------------------------------------------- */
    }

    /*

        // Pupil Follow Sequence Selection External Events

    */

    @FXML
    public void backBtnPupilFollowSequenceSelectionClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO HOME PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("home");
        /* ----------------------------------- */
    }

    @FXML
    public void firstPaginationPanePupilFollowSequenceSelectionClick(MouseEvent mouseEvent)
    {
        this.paginatorNodePupilFollowSequenceSelection.getPaginator().firstPage();
    }


    @FXML
    public void leftPaginationBtnPupilFollowSequenceSelectionClick(ActionEvent actionEvent)
    {
        this.paginatorNodePupilFollowSequenceSelection.getPaginator().previousPage();
    }

    @FXML
    public void fieldPaginationPupilFollowSequenceSelectionKeyPressed(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
            try
            {
                int entry = Integer.parseInt(this.fieldPaginationPupilFollowSequenceSelection.getText());
                this.paginatorNodePupilFollowSequenceSelection.getPaginator().setPage(entry);
            }
            catch (NumberFormatException ignored) {}
            finally
            {
                this.fieldPaginationPupilFollowSequenceSelection.setText(String.valueOf(this.paginatorNodePupilFollowSequenceSelection.getPaginator().currentPage()));
                this.fieldPaginationPupilFollowSequenceSelection.positionCaret(this.fieldPaginationPupilFollowSequenceSelection.getText().length());
            }
        }
    }

    @FXML
    public void rightPaginationBtnPupilFollowSequenceSelectionClick(ActionEvent actionEvent)
    {
        this.paginatorNodePupilFollowSequenceSelection.getPaginator().nextPage();
    }

    @FXML
    public void lastPaginationPanePupilFollowSequenceSelectionClick(MouseEvent mouseEvent)
    {
        this.paginatorNodePupilFollowSequenceSelection.getPaginator().lastPage();
    }

    /*

        // Pupil Follow Sequence External Events

    */

    @FXML
    public void backBtnPupilFollowSequenceClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO PUPIL FOLLOW SEQUENCE SELECTION PANE ------- */
        AdminModEcrilu.getInstance().paneHandler.show("pupilFollowSequenceSelection");
        /* -------------------------------------------------------------- */
    }

    @FXML
    public void fieldPaginationPupilFollowSequenceKeyPressed(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
            try
            {
                int entry = Integer.parseInt(this.fieldPaginationPupilFollowSequenceSelection.getText());
                this.paginatorNodePupilFollowSequenceSelection.getPaginator().setPage(entry);
            }
            catch (NumberFormatException ignored) {}
            finally
            {
                this.fieldPaginationPupilFollowSequenceSelection.setText(String.valueOf(this.paginatorNodePupilFollowSequenceSelection.getPaginator().currentPage()));
                this.fieldPaginationPupilFollowSequenceSelection.positionCaret(this.fieldPaginationPupilFollowSequenceSelection.getText().length());
            }
        }
    }

    @FXML
    public void exportBtnPupilFollowSequenceClick(ActionEvent actionEvent)
    {
        // CSV to XLS
        String csvFilePath = DataService.pathOf(this.currentSequenceToFollow.id + ".csv", "ecrilu", "attr");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter le suivi de la séquence");
        fileChooser.setInitialFileName(this.currentSequenceToFollow.id + ".xls");

        String extensionFilterName = "XLS File";
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter(extensionFilterName, "*.xls");
        fileChooser.getExtensionFilters().add(ext);

        File xlsFile = fileChooser.showSaveDialog(PersistentData.stageHandler.getStage());

        try (HSSFWorkbook workbook = new HSSFWorkbook())
        {
            HSSFPalette palette = workbook.getCustomPalette();

            palette.setColorAtIndex((byte) 41, (byte) 178, (byte) 178, (byte) 178);
            palette.setColorAtIndex((byte) 42, (byte) 255, (byte) 215, (byte) 215);
            palette.setColorAtIndex((byte) 43, (byte) 255, (byte) 255, (byte) 166);
            palette.setColorAtIndex((byte) 44, (byte) 175, (byte) 208, (byte) 149);
            palette.setColorAtIndex((byte) 45, (byte) 114, (byte) 159, (byte) 207);

            Sheet sheet = workbook.createSheet("Sheet1");

            sheet.setColumnWidth(0, 900);
            sheet.setColumnWidth(1, 3600);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));

            int startColumn = 3;
            int rowNum = 0;

            //# STYLE CELL BAREME #//

            CellStyle styleBareme = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font font = workbook.createFont();
            font.setColor(IndexedColors.WHITE.getIndex());
            styleBareme.setFont(font);
            styleBareme.setFillForegroundColor(IndexedColors.BLACK.getIndex());
            styleBareme.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            styleBareme.setAlignment(HorizontalAlignment.LEFT);

            //# STYLE CELL BAREME #//

            //# Cell bareme #//

            Row legendRow1 = sheet.createRow(0);
            Cell legendCell1 = legendRow1.createCell(0);
            legendCell1.setCellValue("Barème :");
            legendCell1.setCellStyle(styleBareme);

            //# Cell bareme #//

            //# Cell 0 #//

            Row legendRow21 = sheet.createRow(1);
            Cell legendCell21 = legendRow21.createCell(0, CellType.NUMERIC);
            legendCell21.setCellValue(0);

            CellStyle cell21Style = createStandardColorStyleScoreCell(workbook, palette, 41);

            cell21Style.setBorderTop(BorderStyle.THIN);
            cell21Style.setBorderLeft(BorderStyle.THIN);

            cell21Style.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cell21Style.setLeftBorderColor(IndexedColors.BLACK.getIndex());

            legendCell21.setCellStyle(cell21Style);

            //# Cell 0 #//

            //# Cell Non commencé #//

            Row legendRow22 = sheet.getRow(1);
            Cell legendCell22 = legendRow22.createCell(1);
            legendCell22.setCellValue("Non commencé");

            CellStyle cell22Style = workbook.createCellStyle();

            cell22Style.setBorderTop(BorderStyle.THIN);
            cell22Style.setBorderRight(BorderStyle.THIN);

            cell22Style.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cell22Style.setRightBorderColor(IndexedColors.BLACK.getIndex());

            legendCell22.setCellStyle(cell22Style);

            //# Cell Non commencé #//

            Row legendRow31 = sheet.createRow(2);
            Cell legendCell31 = legendRow31.createCell(0, CellType.NUMERIC);
            legendCell31.setCellValue(1);

            CellStyle cell31Style = createStandardColorStyleScoreCell(workbook, palette, 42);

            cell31Style.setBorderLeft(BorderStyle.THIN);
            cell31Style.setLeftBorderColor(IndexedColors.BLACK.getIndex());

            legendCell31.setCellStyle(cell31Style);

            Row legendRow32 = sheet.getRow(2);
            Cell legendCell32 = legendRow32.createCell(1);
            legendCell32.setCellValue("NA");

            CellStyle cell32Style = workbook.createCellStyle();
            cell32Style.setBorderRight(BorderStyle.THIN);
            cell32Style.setRightBorderColor(IndexedColors.BLACK.getIndex());

            legendCell32.setCellStyle(cell32Style);

            Row legendRow41 = sheet.createRow(3);
            Cell legendCell41 = legendRow41.createCell(0, CellType.NUMERIC);
            legendCell41.setCellValue(2);

            CellStyle cell41Style = createStandardColorStyleScoreCell(workbook, palette, 43);

            cell41Style.setBorderLeft(BorderStyle.THIN);
            cell41Style.setLeftBorderColor(IndexedColors.BLACK.getIndex());

            legendCell41.setCellStyle(cell41Style);

            Row legendRow42 = sheet.getRow(3);
            Cell legendCell42 = legendRow42.createCell(1);
            legendCell42.setCellValue("ECA");

            CellStyle cell42Style = workbook.createCellStyle();
            cell42Style.setBorderRight(BorderStyle.THIN);
            cell42Style.setRightBorderColor(IndexedColors.BLACK.getIndex());

            legendCell42.setCellStyle(cell42Style);

            Row legendRow51 = sheet.createRow(4);
            Cell legendCell51 = legendRow51.createCell(0, CellType.NUMERIC);
            legendCell51.setCellValue(3);

            CellStyle cell51Style = createStandardColorStyleScoreCell(workbook, palette, 44);

            cell51Style.setBorderLeft(BorderStyle.THIN);
            cell51Style.setLeftBorderColor(IndexedColors.BLACK.getIndex());

            legendCell51.setCellStyle(cell51Style);

            Row legendRow52 = sheet.getRow(4);
            Cell legendCell52 = legendRow52.createCell(1);
            legendCell52.setCellValue("A");

            CellStyle cell52Style = workbook.createCellStyle();
            cell52Style.setBorderRight(BorderStyle.THIN);
            cell52Style.setRightBorderColor(IndexedColors.BLACK.getIndex());

            legendCell52.setCellStyle(cell52Style);

            Row legendRow61 = sheet.createRow(5);
            Cell legendCell61 = legendRow61.createCell(0, CellType.NUMERIC);
            legendCell61.setCellValue(4);

            CellStyle cell61Style = createStandardColorStyleScoreCell(workbook, palette, 45);

            cell61Style.setBorderBottom(BorderStyle.THIN);
            cell61Style.setBorderLeft(BorderStyle.THIN);

            cell61Style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cell61Style.setLeftBorderColor(IndexedColors.BLACK.getIndex());

            legendCell61.setCellStyle(cell61Style);

            Row legendRow62 = sheet.getRow(5);
            Cell legendCell62 = legendRow62.createCell(1);
            legendCell62.setCellValue("A+");

            CellStyle cell62Style = workbook.createCellStyle();

            cell62Style.setBorderRight(BorderStyle.THIN);
            cell62Style.setBorderBottom(BorderStyle.THIN);

            cell62Style.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cell62Style.setBottomBorderColor(IndexedColors.BLACK.getIndex());

            legendCell62.setCellStyle(cell62Style);

            // Utilisation d'un fichier CSV pour illustrer l'exemple
            try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath)))
            {
                String line;
                while ((line = reader.readLine()) != null)
                {
                    Row row = sheet.getRow(rowNum);
                    if (row == null) row = sheet.createRow(rowNum);

                    String[] values = line.split(",");
                    for (int i = 0; i < values.length; i++)
                    {
                        if (i == 0 && rowNum != 0)
                        {
                            Cell cell = row.createCell(i + startColumn);

                            // Convertir l'ID de l'élève en son nom (existant dans PupilRepository)
                            cell.setCellValue(Objects.requireNonNull(
                                    PupilRepository.read(Integer.parseInt(values[i]))).getName()
                            );

                            cell.setCellStyle(borderedBlackCellStyle(workbook.createCellStyle()));
                        }
                        else
                        {
                            if (rowNum == 0)
                            {
                                Cell cell = row.createCell(i + startColumn, CellType.STRING);
                                cell.setCellValue(values[i]);
                                cell.setCellStyle(borderedBlackCellStyle(workbook.createCellStyle()));
                            }
                            else
                            {
                                int integer = Integer.parseInt(values[i]);
                                Cell cell = row.createCell(i + startColumn, CellType.NUMERIC);
                                cell.setCellValue(integer);

                                int indexColor;

                                switch (integer)
                                {
                                    case 1 -> indexColor = 42;
                                    case 2 -> indexColor = 43;
                                    case 3 -> indexColor = 44;
                                    case 4 -> indexColor = 45;
                                    default -> indexColor = 41;
                                }

                                cell.setCellStyle(borderedBlackCellStyle(
                                        createStandardColorStyleScoreCell(workbook, palette, indexColor)
                                ));
                            }
                        }
                    }

                    rowNum++;
                }
            }

            try (FileOutputStream outputStream = new FileOutputStream(xlsFile))
            {
                workbook.write(outputStream);
            }

            Main.logger.debug("[" + this.currentSequenceToFollow.id + ".xls] was created !");
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    private static CellStyle createStandardColorStyleScoreCell(Workbook workbook, HSSFPalette palette, int indexColor /* cf custom palette */)
    {
        CellStyle cellStyle = workbook.createCellStyle();
        org.apache.poi.ss.usermodel.Font font = workbook.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        cellStyle.setFont(font);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.RIGHT);
        cellStyle.setFillForegroundColor(palette.getColor(indexColor).getIndex());
        return cellStyle;
    }

    private static CellStyle borderedBlackCellStyle(CellStyle cellStyle)
    {
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);

        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());

        return cellStyle;
    }

    // ***************************************************************
    // AdminModEcriluController - ENUMS
    // ***************************************************************

    public enum FeatureMode
    {
        ADDING,
        MODIFYING
    }

    // ***************************************************************
    // AdminModEcriluController - ENUMS
    // ***************************************************************
}