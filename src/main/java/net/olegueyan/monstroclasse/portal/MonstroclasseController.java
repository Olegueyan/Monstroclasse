package net.olegueyan.monstroclasse.portal;

import com.speedment.common.tuple.Tuple2;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import net.olegueyan.monstroclasse.Main;
import net.olegueyan.monstroclasse.common.AppTheme;
import net.olegueyan.monstroclasse.common.Constants;
import net.olegueyan.monstroclasse.common.PersistentData;
import net.olegueyan.monstroclasse.component.ZipperComponent;
import net.olegueyan.monstroclasse.entity.Account;
import net.olegueyan.monstroclasse.entity.Pupil;
import net.olegueyan.monstroclasse.integration.ControllerIntegration;
import net.olegueyan.monstroclasse.module.IsModule;
import net.olegueyan.monstroclasse.module.ModuleRegisterBus;
import net.olegueyan.monstroclasse.motor.core.*;
import net.olegueyan.monstroclasse.node.module.ModulePaneGrid;
import net.olegueyan.monstroclasse.node.partial.ImagedCheckBox;
import net.olegueyan.monstroclasse.node.partial.VerticalScrollPane;
import net.olegueyan.monstroclasse.repository.PupilModuleAccessRepository;
import net.olegueyan.monstroclasse.screen.ScreenStructurationInfo;
import net.olegueyan.monstroclasse.service.DataService;
import net.olegueyan.monstroclasse.utils.ImageUtils;
import net.olegueyan.monstroclasse.utils.MonstroclasseDatabaseUtils;
import net.olegueyan.monstroclasse.utils.ZippingUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class MonstroclasseController extends ControllerIntegration
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
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 70, hRef = 70, fixing = FixingType.MIN)
    @OffsetAdjustment(offsetX = -10, offsetY = 10)
    public Button closeBtnHome;

    @FXML
    @ImageViewBinder(fieldName = "closeBtnHome")
    public ImageView closeBtnImage;

    /*

        // Home Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.MONSTROCLASSE_COLOR_HEX)
    public Pane paneHome;

    @FXML
    @CorePaneStructuration
    public Pane corePaneHome;

    @FXML
    @DimensionAdjustment(wRef = 882, hRef = 588)
    @CoordinatesAdjustment(xRef = 199, yRef = 14)
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    public Pane subPaneHome;

    @FXML
    @DimensionAdjustment(wRef = 862, hRef = 568)
    @CoordinatesAdjustment(xRef = 10, yRef = 10)
    public Pane studentGridPane;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-transparent", "-handed"})
    @DimensionAdjustment(wRef = 110, hRef = 110, fixing = FixingType.MAX)
    @OffsetAdjustment(offsetX = -10, offsetY = -10)
    public Button adminBtn;

    @FXML
    @ImageViewBinder(fieldName = "adminBtn")
    public ImageView adminBtnImage;

    /*

        // Admin Connexion Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.MONSTROCLASSE_ADMIN_COLOR_HEX)
    public Pane paneAdminConnexion;

    @FXML
    @CorePaneStructuration
    public Pane corePaneAdminConnexion;

    @FXML
    @DimensionAdjustment(wRef = 691.2, hRef = 489.6)
    @CoordinatesAdjustment(xRef = 294.4, yRef = 63.2)
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    public Pane subPaneAdminConnexion;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60, fixing = FixingType.MIN)
    @OffsetAdjustment(offsetX = 10, offsetY = -10)
    public Button backBtnAdminConnexion;

    @FXML
    @ImageViewBinder(fieldName = "backBtnAdminConnexion")
    public ImageView backBtnAdminConnexionImage;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @OffsetAdjustment(offsetX = -10, offsetY = -10)
    public Button validateBtnAdminConnexion;

    @FXML
    @ImageViewBinder(fieldName = "validateBtnAdminConnexion")
    public ImageView validateBtnAdminConnexionImage;

    @FXML
    @DimensionAdjustment(wRef = 210, hRef = 210, fixing = FixingType.MAX)
    @CoordinatesAdjustment(xRef = 240.6, yRef = 20)
    public ImageView adminConnexionImage;

    @FXML
    @DimensionAdjustment(wRef = 262, hRef = 29)
    @CoordinatesAdjustment(xRef = 214.6, yRef = 450.6)
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "italic", fontRef = 23)
    public Label labelAdminConnexionWrongPassword;

    @FXML
    @DimensionAdjustment(wRef = 448, hRef = 56)
    @CoordinatesAdjustment(xRef = 121.6, yRef = 340)
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "italic", fontRef = 23)
    public PasswordField fieldPasswordAdminConnexion;

    @FXML
    @DimensionAdjustment(wRef = 354, hRef = 63)
    @CoordinatesAdjustment(xRef = 168.6, yRef = 245)
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "bold", fontRef = 48)
    public Label labelPasswordEntryAdminConnexion;

    /*

        // Account Admin Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.MONSTROCLASSE_ADMIN_COLOR_HEX)
    public Pane paneAccountAdmin;

    @FXML
    @CorePaneStructuration
    public Pane corePaneAccountAdmin;

    @FXML
    @DimensionAdjustment(wRef = 960, hRef = 468)
    @CoordinatesAdjustment(xRef = 160, yRef = 74)
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    public Pane subPaneAccountAdmin;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 448)
    @CoordinatesAdjustment(xRef = 10, yRef = 10)
    public Pane moduleAccessAdmin;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 15, yRef = 531)
    public Button backBtnAccountAdmin;

    @FXML
    @ImageViewBinder(fieldName = "backBtnAccountAdmin")
    public ImageView backBtnAccountAdminImage;

    @FXML
    @DimensionAdjustment(wRef = 92, hRef = 92)
    @OffsetAdjustment(offsetX = 5, offsetY = 5)
    public ImageView adminAccountCurrentImage;

    @FXML
    @DimensionAdjustment(wRef = 240, hRef = 28)
    @CoordinatesAdjustment(xRef = 102, yRef = 70)
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "bold", fontRef = 21)
    public Label adminAccountCurrentNameAccountAdmin;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(fixing = FixingType.MIN, wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 1195, yRef = 531)
    public Button dataBtnAccountAdmin;

    @FXML
    @ImageViewBinder(fieldName = "dataBtnAccountAdmin")
    public ImageView dataBtnAccountAdminImage;

    @FXML
    @DimensionAdjustment(wRef = 200, hRef = 28)
    @CoordinatesAdjustment(xRef = 980, yRef = 571)
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "bold", fontRef = 21)
    public Label importExportLabelAccountAdmin;

    /*

        // Account Student Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.MONSTROCLASSE_COLOR_HEX)
    public Pane paneAccountStudent;

    @FXML
    @CorePaneStructuration
    public Pane corePaneAccountStudent;

    @FXML
    @DimensionAdjustment(wRef = 960, hRef = 468)
    @CoordinatesAdjustment(xRef = 160, yRef = 74)
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    public Pane subPaneAccountStudent;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 448)
    @CoordinatesAdjustment(xRef = 10, yRef = 10)
    public Pane moduleAccessStudent;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 15, yRef = 531)
    public Button backBtnAccountStudent;

    @FXML
    @ImageViewBinder(fieldName = "backBtnAccountStudent")
    public ImageView backBtnAccountStudentImage;

    @FXML
    @DimensionAdjustment(wRef = 92, hRef = 92)
    @OffsetAdjustment(offsetX = 5, offsetY = 5)
    public ImageView studentAccountCurrentImage;

    @FXML
    @DimensionAdjustment(wRef = 240, hRef = 28)
    @CoordinatesAdjustment(xRef = 102, yRef = 70)
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "bold", fontRef = 21)
    public Label studentAccountCurrentNameAccountStudent;

    /*

        // Close Confirm Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.MONSTROCLASSE_COLOR_HEX)
    public Pane paneCloseConfirm;

    @FXML
    @CorePaneStructuration
    public Pane corePaneCloseConfirm;

    @FXML
    @DimensionAdjustment(wRef = 538, hRef = 302.4)
    @CoordinatesAdjustment(xRef = 371, yRef = 156.8)
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    public Pane subPaneCloseConfirm;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 10, yRef = 232.4)
    public Button undoCloseConfirmBtn;

    @FXML
    @ImageViewBinder(fieldName = "undoCloseConfirmBtn")
    public ImageView undoCloseConfirmBtnImage;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 468, yRef = 232.4)
    public Button checkCloseConfirmBtn;

    @FXML
    @ImageViewBinder(fieldName = "checkCloseConfirmBtn")
    public ImageView checkCloseConfirmBtnImage;

    @FXML
    @DimensionAdjustment(wRef = 200, hRef = 150)
    @CoordinatesAdjustment(xRef = 180, yRef = 108)
    public ImageView appIconCloseConfirmImage;

    @FXML
    @DimensionAdjustment(wRef = 342, hRef = 38)
    @CoordinatesAdjustment(xRef = 98, yRef = 22)
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "bold", fontRef = 30)
    public Label labelCloseConfirm;

    /*

        // Account Student Confirm Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.MONSTROCLASSE_COLOR_HEX)
    public Pane paneAccountStudentConfirm;

    @FXML
    @CorePaneStructuration
    public Pane corePaneAccountStudentConfirm;

    @FXML
    @DimensionAdjustment(wRef = 537.6, hRef = 468)
    @CoordinatesAdjustment(xRef = 371.2, yRef = 74)
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    public Pane subPaneAccountStudentConfirm;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 10, yRef = 398)
    public Button undoAccountStudentConfirmBtn;

    @FXML
    @ImageViewBinder(fieldName = "undoAccountStudentConfirmBtn")
    public ImageView undoAccountStudentConfirmBtnImage;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 467.6, yRef = 398)
    public Button checkAccountStudentConfirmBtn;

    @FXML
    @ImageViewBinder(fieldName = "checkAccountStudentConfirmBtn")
    public ImageView checkAccountStudentConfirmBtnImage;

    @FXML
    @DimensionAdjustment(wRef = 310, hRef = 310)
    @CoordinatesAdjustment(xRef = 116, yRef = 10)
    public ImageView studentAccountConfirmImage;

    @FXML
    @DimensionAdjustment(wRef = 342, hRef = 38)
    @CoordinatesAdjustment(xRef = 100, yRef = 348)
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "regular", fontRef = 30)
    public Label accountStudentConfirmLabel;

    /*

        // Data Export Import Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.MONSTROCLASSE_ADMIN_COLOR_HEX)
    public Pane paneDataExportImport;

    @FXML
    @CorePaneStructuration
    public Pane corePaneDataExportImport;

    @FXML
    @DimensionAdjustment(wRef = 92, hRef = 92)
    @OffsetAdjustment(offsetX = 5, offsetY = 5)
    public ImageView dataExportImportAdminCurrentImage;

    @FXML
    @DimensionAdjustment(wRef = 240, hRef = 28)
    @CoordinatesAdjustment(xRef = 102, yRef = 70)
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "bold", fontRef = 21)
    public Label adminAccountCurrentNameDataExportImport;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 15, yRef = 531)
    public Button backBtnDataExportImport;

    @FXML
    @ImageViewBinder(fieldName = "backBtnDataExportImport")
    public ImageView backBtnDataExportImportImage;

    @FXML
    @DimensionAdjustment(wRef = 960, hRef = 528)
    @CoordinatesAdjustment(xRef = 160, yRef = 44)
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    public Pane internalPaneDataExportImport;

    @FXML
    @DimensionAdjustment(wRef = 960, hRef = 60)
    public Pane titlePaneDataExportImport;

    @FXML
    @DimensionAdjustment(wRef = 774, hRef = 44)
    @CoordinatesAdjustment(xRef = 93, yRef = 8)
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "bold", fontRef = 35)
    public Label titleLabelDataExportImport;

    @FXML
    @DimensionAdjustment(wRef = 960, hRef = 468)
    @CoordinatesAdjustment(xRef = 0, yRef = 60)
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    public Pane subPaneDataExportImport;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 448)
    @CoordinatesAdjustment(xRef = 10, yRef = 10)
    public Pane layoutPaneDataExportImport;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 94)
    @CoordinatesAdjustment(xRef = 0, yRef = 0)
    public Pane internalPaneIndicatorDataExportImport;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 354)
    @CoordinatesAdjustment(xRef = 0, yRef = 94)
    public Pane internalPaneScrollDataExportImport;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 354)
    @CoordinatesAdjustment(xRef = 0, yRef = 94)
    public Pane internalPaneWarningUsage;

    @FXML
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 24)
    @DimensionAdjustment(wRef = 840, hRef = 254)
    @CoordinatesAdjustment(xRef = 50, yRef = 0)
    public Label warningMessageLabel;

    @FXML
    @ApplyCss(classTab = {"node-flat", "-handed"})
    @RoundedStyle(color = AppTheme.ECRILU_PAGINATION_COLOR_BUTTON, cornerRadius = 10)
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "Regular", fontRef = 32)
    @DimensionAdjustment(wRef = 200, hRef = 61)
    @CoordinatesAdjustment(xRef = 720, yRef = 280)
    public Button warningContinueButton;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 14, yRef = 17)
    public Button uncheckAllBtnDataExportImport;

    @FXML
    @ImageViewBinder(fieldName = "uncheckAllBtnDataExportImport")
    public ImageView uncheckAllBtnDataExportImportImage;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 866, yRef = 17)
    public Button checkAllBtnDataExportImport;

    @FXML
    @ImageViewBinder(fieldName = "checkAllBtnDataExportImport")
    public ImageView checkAllBtnDataExportImportImage;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 1195, yRef = 531)
    public Button exportBtnDataExportImport;

    @FXML
    @ImageViewBinder(fieldName = "exportBtnDataExportImport")
    public ImageView exportBtnDataExportImportImage;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 1195, yRef = 451)
    public Button importBtnDataExportImport;

    @FXML
    @ImageViewBinder(fieldName = "importBtnDataExportImport")
    public ImageView importBtnDataExportImportImage;

    @FXML
    @DimensionAdjustment(wRef = 240, hRef = 38)
    @CoordinatesAdjustment(xRef = 83, yRef = 28)
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "bold", fontRef = 30)
    public Label labelUncheckDataExportImport;

    @FXML
    @DimensionAdjustment(wRef = 199, hRef = 38)
    @CoordinatesAdjustment(xRef = 650, yRef = 28)
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "bold", fontRef = 30)
    public Label labelCheckDataExportImport;

    /*

        // Controller entry point

    */

    @Override
    public void loadController()
    {
        // ###################################################
        // Pane Home Events
        // ###################################################

        paneHome.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (observableValue.getValue())
            {
                // ------- Show application close button ------- //
                closeBtnHome.visibleProperty().set(true);
                // --------------------------------------------- //

                // ------- Disconnect connected account ------- //
                PersistentData.currentConnectedAccount = null;
                // -------------------------------------------- //
            }
        });

        // ###################################################
        // END
        // ###################################################

        // ###################################################
        // Pane Admin Connexion Events
        // ###################################################

        paneAdminConnexion.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            // ------- Hide application close button ------- //
            closeBtnHome.visibleProperty().set(aBoolean);
            // --------------------------------------------- //

            if (observableValue.getValue())
            {
                // ------- Dump all fields of Admin Connexion ------- //
                labelAdminConnexionWrongPassword.setVisible(false);
                fieldPasswordAdminConnexion.setText("");
                fieldPasswordAdminConnexion.requestFocus();
                // -------------------------------------------------- //

                // ------- Disconnect connected account ------- //
                PersistentData.currentConnectedAccount = null;
                // -------------------------------------------- //
            }
        });

        fieldPasswordAdminConnexion.textProperty().addListener((observableValue, s, t1) ->
        {
            // ------- Hide Wrong Password Label when user is typing ------- //
            labelAdminConnexionWrongPassword.setVisible(false);
            // ------------------------------------------------------------- //
        });

        // ###################################################
        // END
        // ###################################################

        // ###################################################
        // Pane Close Confirm Events
        // ###################################################

        paneCloseConfirm.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            // ------- Hide application close button ------- //
            closeBtnHome.visibleProperty().set(aBoolean);
            // --------------------------------------------- //
        });

        // ###################################################
        // END
        // ###################################################

        // ###################################################
        // Pane Account Student Confirm Events
        // ###################################################

        paneAccountStudentConfirm.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            // ------- Hide application close button ------- //
            closeBtnHome.visibleProperty().set(aBoolean);
            // --------------------------------------------- //

            if (observableValue.getValue())
            {
                // ------- Show student account information ------- //
                studentAccountConfirmImage.setImage(PersistentData.currentConnectedAccount.image());
                accountStudentConfirmLabel.setText(PersistentData.currentConnectedAccount.name());
                accountStudentConfirmLabel.setAlignment(Pos.CENTER);
                // ------------------------------------------------ //
            }
        });

        // ###################################################
        // END
        // ###################################################

        paneAccountVisible(paneAccountAdmin, moduleAccessAdmin, adminAccountCurrentImage, adminAccountCurrentNameAccountAdmin);
        paneAccountVisible(paneAccountStudent, moduleAccessStudent, studentAccountCurrentImage, studentAccountCurrentNameAccountStudent);

        // ###################################################
        // Pane Data Export Import Events
        // ###################################################

        paneDataExportImport.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (observableValue.getValue())
            {
                this.dataExportImportAdminCurrentImage.setImage(PersistentData.currentConnectedAccount.image());
                this.adminAccountCurrentNameDataExportImport.setText(PersistentData.currentConnectedAccount.name());

                final double widthCard = internalPaneScrollDataExportImport.getPrefWidth();
                final double heightCard = internalPaneScrollDataExportImport.getPrefHeight() * 0.18f;

                final double width = internalPaneScrollDataExportImport.getPrefWidth();
                final double height = internalPaneScrollDataExportImport.getPrefHeight();

                VerticalScrollPane scrollPane = new VerticalScrollPane(width, height, 5);

                /**
                 * Add data to export
                 */

                InputStream is1 = Objects.requireNonNull(Main.class.getResourceAsStream("monstroclasse.gif"));
                ImagedCheckBox imagedCheckBox1 = new ImagedCheckBox(widthCard, heightCard, new Image(is1), "Monstroclasse");
                imagedCheckBox1.setUserData("monstroclasse.db");
                scrollPane.addNode(imagedCheckBox1);

                InputStream is2 = Objects.requireNonNull(Main.class.getResourceAsStream("module/ecrilu.png"));
                ImagedCheckBox imagedCheckBox2 = new ImagedCheckBox(widthCard, heightCard, new Image(is2), "Ecrilu");
                imagedCheckBox2.setUserData("ecrilu");
                scrollPane.addNode(imagedCheckBox2);

                internalPaneScrollDataExportImport.getChildren().add(scrollPane);

                this.internalPaneWarningUsage.setVisible(true);
                this.internalPaneScrollDataExportImport.setVisible(false);
                this.exportBtnDataExportImport.setVisible(false);
                this.importBtnDataExportImport.setVisible(false);
                this.internalPaneIndicatorDataExportImport.setVisible(false);
            }
            else
            {
                this.dataExportImportAdminCurrentImage.setImage(null);
                this.adminAccountCurrentNameDataExportImport.setText("");
                this.internalPaneScrollDataExportImport.getChildren().clear();
            }
        });

        // ###################################################
        // END
        // ###################################################

        appBanner.toFront(); // Force to be in front of all objects
        closeBtnHome.toFront(); // Force to be in front of all objects

        Main.logger.info(this.getClass().getName() + " loaded !");
    }

    /*

        // Home External Events

    */

    @FXML
    public void closeBtnClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO CLOSE CONFIRM PANE ------- */
        Monstroclasse.getInstance().paneHandler.show("closeConfirm");
        /* -------------------------------------------- */
    }

    @FXML
    public void adminBtnClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO ADMIN CONNEXION PANE ------- */
        Monstroclasse.getInstance().paneHandler.show("adminConnexion");
        /* ---------------------------------------------- */
    }

    @FXML
    public void studentAvatarBtnClick(ActionEvent actionEvent)
    {
        Button clickedButton = (Button) actionEvent.getSource();
        Pupil pupil = (Pupil) clickedButton.getUserData();
        Image image = ImageUtils.byteArrayToImage(pupil.getAvatar().getData());
        ArrayList<IsModule> isModules = ModuleRegisterBus.modulesAvailable(PupilModuleAccessRepository.readModules(pupil));
        PersistentData.currentConnectedAccount = new Account(pupil.getName(), image, isModules);
        PersistentData.currentConnectedAccount.setAccountData(pupil);

        /* ------- SWITCH TO ACCOUNT STUDENT CONFIRM PANE ------- */
        Monstroclasse.getInstance().paneHandler.show("accountStudentConfirm");
        /* ------------------------------------------------------ */
    }

    /*

        // Account Admin External Events

    */

    @FXML
    public void backBtnAccountAdminClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO HOME PANE ------- */
        Monstroclasse.getInstance().paneHandler.show("home");
        /* ----------------------------------- */
    }

    @FXML
    public void dataBtnAccountAdminClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO DATA EXPORT IMPORT PANE ------- */
        Monstroclasse.getInstance().paneHandler.show("dataExportImport");
        /* ------------------------------------------------- */
    }

    /*

        // Admin Connexion External Events

    */

    @FXML
    public void backBtnAdminConnexionClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO HOME PANE ------- */
        Monstroclasse.getInstance().paneHandler.show("home");
        /* ----------------------------------- */
    }

    @FXML
    public void validateBtnAdminConnexionClick(ActionEvent actionEvent)
    {
        if (fieldPasswordAdminConnexion.getText().equals(Constants.ADMIN_PASSWORD))
        {
            ArrayList<IsModule> modules = ModuleRegisterBus.modulesAvailable(Constants.ADMIN_MODULES);
            PersistentData.currentConnectedAccount = new Account("Admin", Constants.ADMIN_IMAGE, modules);
            Monstroclasse.getInstance().paneHandler.show("accountAdmin");
        }
        else
        {
            // ------- Show Wrong Password Label (Wrote password is invalid) ------- //
            fieldPasswordAdminConnexion.setText("");
            labelAdminConnexionWrongPassword.setVisible(true);
            // --------------------------------------------------------------------- //
        }
    }

    @FXML
    public void fieldPasswordAdminConnexionKeyPressed(KeyEvent event)
    {
        // ***** ENTER SHORTCUT ***** //
        if (event.getCode() == KeyCode.ENTER) validateBtnAdminConnexion.fire();
        // ***** ENTER SHORTCUT ***** //
    }

    /*

        // Account Student External Events

    */

    @FXML
    public void backBtnAccountStudentClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO HOME PANE ------- */
        Monstroclasse.getInstance().paneHandler.show("home");
        /* ----------------------------------- */
    }

    @FXML
    public void undoAccountStudentConfirmBtnClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO HOME PANE ------- */
        Monstroclasse.getInstance().paneHandler.show("home");
        /* ----------------------------------- */
    }

    @FXML
    public void checkAccountStudentConfirmBtnClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO ACCOUNT STUDENT PANE ------- */
        Monstroclasse.getInstance().paneHandler.show("accountStudent");
        /* ---------------------------------------------- */
    }

    /*

        // Close Confirm External Events

    */

    @FXML
    public void undoCloseConfirmBtnClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO HOME PANE ------- */
        Monstroclasse.getInstance().paneHandler.show("home");
        /* ----------------------------------- */
    }

    @FXML
    public void checkCloseConfirmBtnClick(ActionEvent actionEvent)
    {
        // ------- Exit the platform and close JavaFX ------- //
        Platform.exit();
        // -------------------------------------------------- //
    }

    /*

        // Data Export Import External Events

    */

    @FXML
    public void backBtnDataExportImportClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO ACCOUNT ADMIN PANE ------- */
        Monstroclasse.getInstance().paneHandler.show("accountAdmin");
        /* -------------------------------------------- */
    }

    @FXML
    public void uncheckAllBtnDataExportImportClick(ActionEvent actionEvent)
    {
        // ------- Uncheck all CheckBox of DataExportCard ------- //
        if (this.internalPaneScrollDataExportImport.getChildren().getFirst() instanceof VerticalScrollPane scrollPane)
        {
            scrollPane.forEachContent(ImagedCheckBox.class, imagedCheckBox -> imagedCheckBox.getCheckBoxNode().uncheck());
        }
        // ------------------------------------------------------ //
    }

    @FXML
    public void checkAllBtnDataExportImportClick(ActionEvent actionEvent)
    {
        // ------- Check all CheckBox of DataExportCard ------- //
        if (this.internalPaneScrollDataExportImport.getChildren().getFirst() instanceof VerticalScrollPane scrollPane)
        {
            scrollPane.forEachContent(ImagedCheckBox.class, imagedCheckBox -> imagedCheckBox.getCheckBoxNode().check());
        }
        // ---------------------------------------------------- //
    }

    @FXML
    public void importBtnDataExportImportClick(ActionEvent actionEvent)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importer les données");

        String extensionFilterName = "Zip File";
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter(extensionFilterName, "*.zip");
        fileChooser.getExtensionFilters().add(ext);

        File zipFile = fileChooser.showOpenDialog(PersistentData.stageHandler.getStage());

        if (zipFile != null)
        {
            File dataFolder = new File(String.join("/", "data"));
            ZipperComponent zipperComponent = new ZipperComponent(dataFolder);

            ArrayList<Tuple2<Path, Path>> databases = zipperComponent.decompressFolder(
                    zipFile.toPath(), new String[]{"monstroclasse.db"}
            );

            for (Tuple2<Path, Path> tuple : databases)
            {
                Path firstPath = tuple.get0();
                Path secondPath = tuple.get1();

                try
                {
                    Connection connection1 = DriverManager.getConnection("jdbc:sqlite:" + firstPath.toString());
                    Connection connection2 = DriverManager.getConnection("jdbc:sqlite:" + secondPath.toString());

                    MonstroclasseDatabaseUtils.fusionDatabases(connection1, connection2);

                    connection1.close();
                    connection2.close();

                    Files.deleteIfExists(secondPath);
                }
                catch (SQLException | IOException e)
                {
                    e.fillInStackTrace();
                }
                finally
                {
                    /* ------- SWITCH TO ACCOUNT ADMIN PANE ------- */
                    Monstroclasse.getInstance().paneHandler.show("accountAdmin");
                    /* -------------------------------------------- */
                }
            }
        }
    }

    @FXML
    public void exportBtnDataExportImportClick(ActionEvent actionEvent)
    {
        ArrayList<File> files = new ArrayList<>();

        if (this.internalPaneScrollDataExportImport.getChildren().getFirst() instanceof VerticalScrollPane scrollPane)
        {
            scrollPane.forEachContent(ImagedCheckBox.class, imagedCheckBox ->
            {
                if (imagedCheckBox.getCheckBoxNode().isEnable())
                {
                    String resource = (String) imagedCheckBox.getUserData();
                    files.add(new File(DataService.pathOf(resource)));
                }
            });
        }

        if (files.isEmpty()) return;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter les données");
        fileChooser.setInitialFileName("data.zip");

        String extensionFilterName = "Zip File";
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter(extensionFilterName, "*.zip");
        fileChooser.getExtensionFilters().add(ext);

        // ------- Show the file chooser and get the selected file (path) ------- //
        File file = fileChooser.showSaveDialog(PersistentData.stageHandler.getStage());
        // ---------------------------------------------------------------------- //

        if (file != null)
        {
            // ------- Compress all files and save it into given path ------- //
            ZippingUtils.compressAll(file.toPath(), files);
            // -------------------------------------------------------------- //

            /* ------- SWITCH TO ACCOUNT ADMIN PANE ------- */
            Monstroclasse.getInstance().paneHandler.show("accountAdmin");
            /* -------------------------------------------- */
        }
    }

    @FXML
    public void warningContinueButtonClick(ActionEvent actionEvent)
    {
        this.internalPaneWarningUsage.setVisible(false);
        this.internalPaneScrollDataExportImport.setVisible(true);
        this.exportBtnDataExportImport.setVisible(true);
        this.importBtnDataExportImport.setVisible(true);
        this.internalPaneIndicatorDataExportImport.setVisible(true);
    }

    /*

        // Utils

    */

    private void paneAccountVisible(Pane paneAccount, Pane paneModuleAccess, ImageView accountCurrentImage, Label accountCurrentLabel)
    {
        paneAccount.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            closeBtnHome.visibleProperty().set(aBoolean);
            if (observableValue.getValue())
            {
                Account account = PersistentData.currentConnectedAccount;
                accountCurrentImage.setImage(account.image());
                accountCurrentLabel.setText(account.name());
                int rows = ScreenStructurationInfo.moduleAccessRow;
                int cols = ScreenStructurationInfo.moduleAccessColumn;
                ModulePaneGrid paneGrid = new ModulePaneGrid(paneModuleAccess, rows, cols);
                for (IsModule module : account.modules())
                {
                   paneGrid.addModuleCard(module, mouseEvent -> PersistentData.stageHandler.load(module.scene()));
                }
            }
            else
            {
                paneModuleAccess.getChildren().clear();
                accountCurrentImage.setImage(null);
                accountCurrentLabel.setText("");
            }
        });
    }
}