package net.olegueyan.monstroclasse.module.content;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import net.olegueyan.monstroclasse.Main;
import net.olegueyan.monstroclasse.common.*;
import net.olegueyan.monstroclasse.entity.Avatar;
import net.olegueyan.monstroclasse.entity.Module;
import net.olegueyan.monstroclasse.entity.Pupil;
import net.olegueyan.monstroclasse.integration.ControllerIntegration;
import net.olegueyan.monstroclasse.function.Selector;
import net.olegueyan.monstroclasse.module.IsModule;
import net.olegueyan.monstroclasse.module.ModuleRegisterBus;
import net.olegueyan.monstroclasse.motor.core.*;
import net.olegueyan.monstroclasse.node.module.ModulePaneGrid;
import net.olegueyan.monstroclasse.node.partial.VerticalScrollPane;
import net.olegueyan.monstroclasse.node.content.PupilModuleAccessEditCard;
import net.olegueyan.monstroclasse.portal.Monstroclasse;
import net.olegueyan.monstroclasse.repository.AvatarRepository;
import net.olegueyan.monstroclasse.repository.ModuleRepository;
import net.olegueyan.monstroclasse.repository.PupilModuleAccessRepository;
import net.olegueyan.monstroclasse.repository.PupilRepository;
import net.olegueyan.monstroclasse.screen.ScreenStructurationInfo;
import net.olegueyan.monstroclasse.utils.ImageUtils;

public class AdminModStudentManagerController extends ControllerIntegration
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
    @ThemeBackground(color = AppTheme.MONSTROCLASSE_ADMIN_COLOR_HEX)
    public Pane paneHome;

    @FXML
    @CorePaneStructuration
    public Pane corePaneHome;

    @FXML
    @DimensionAdjustment(wRef = 704, hRef = 561.6)
    @CoordinatesAdjustment(xRef = 288, yRef = 27.2)
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    public Pane subPaneHome;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 15, yRef = 531)
    public Button closeModuleBtnHome;

    @FXML
    @ImageViewBinder(fieldName = "closeModuleBtnHome")
    public ImageView closeModuleBtnHomeImage;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 634, yRef = 12.12)
    public Button addProfileBtnHome;

    @FXML
    @ImageViewBinder(fieldName = "addProfileBtnHome")
    public ImageView addProfileBtnHomeImage;

    @FXML
    @DimensionAdjustment(wRef = 704, hRef = 84.24)
    @CoordinatesAdjustment(xRef = 0, yRef = 0)
    public Pane internalPaneOneOfHome;

    @FXML
    @DimensionAdjustment(wRef = 704, hRef = 477.36)
    @CoordinatesAdjustment(xRef = 0, yRef = 84.24)
    public Pane internalPaneScrollAccounts;

    @FXML
    @DimensionAdjustment(wRef = 316, hRef = 43)
    @CoordinatesAdjustment(xRef = 194, yRef = 20.62)
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "bold", fontRef = 34)
    public Label addProfileHomeLabel;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 1195, yRef = 531)
    public Button moduleAccessBtnHome;

    @FXML
    @ImageViewBinder(fieldName = "moduleAccessBtnHome")
    public ImageView moduleAccessBtnHomeImage;

    @FXML
    @DimensionAdjustment(wRef = 159, hRef = 56)
    @CoordinatesAdjustment(xRef = 1020, yRef = 545)
    @FontAdjustment(fontFamily = Constants.FONT_FAMILY, fontWeight = "bold", fontRef = 21)
    public Label moduleAttributionLabelHome;

    /*

        // Add Profile Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.MONSTROCLASSE_ADMIN_COLOR_HEX)
    public Pane paneAddProfile;

    @FXML
    @CorePaneStructuration
    public Pane corePaneAddProfile;

    @FXML
    @DimensionAdjustment(wRef = 512, hRef = 504)
    @CoordinatesAdjustment(xRef = 384, yRef = 56)
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    public Pane subPaneAddProfile;

    @FXML
    @DimensionAdjustment(wRef = 512, hRef = 75.16)
    @CoordinatesAdjustment(xRef = 0, yRef = 0)
    public Pane internalPaneOneOfAddProfile;

    @FXML
    @DimensionAdjustment(wRef = 512, hRef = 428.84)
    @CoordinatesAdjustment(xRef = 0, yRef = 75.16)
    public Pane internalPaneTwoOfAddProfile;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 10, yRef = 358.84)
    public Button backBtnAddProfile;

    @FXML
    @ImageViewBinder(fieldName = "backBtnAddProfile")
    public ImageView backBtnAddProfileImage;

    @FXML
    @DimensionAdjustment(wRef = 282, hRef = 43)
    @CoordinatesAdjustment(xRef = 115, yRef = 16)
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "bold", fontRef = 34)
    public Label labelAddProfileTop;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 442, yRef = 358.84)
    public Button checkBtnAddProfile;

    @FXML
    @ImageViewBinder(fieldName = "checkBtnAddProfile")
    public ImageView checkBtnAddProfileImage;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 28, yRef = 277)
    public Button leftArrowBtnAddProfile;

    @FXML
    @ImageViewBinder(fieldName = "leftArrowBtnAddProfile")
    public ImageView leftArrowBtnAddProfileImage;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 427, yRef = 277)
    public Button rightArrowBtnAddProfile;

    @FXML
    @ImageViewBinder(fieldName = "rightArrowBtnAddProfile")
    public ImageView rightArrowBtnAddProfileImage;

    @FXML
    @DimensionAdjustment(wRef = 221, hRef = 40)
    @CoordinatesAdjustment(xRef = 145.5, yRef = 32)
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "regular", fontRef = 32)
    public Label labelAddProfileEnterName;

    @FXML
    @DimensionAdjustment(wRef = 332.8, hRef = 42)
    @CoordinatesAdjustment(xRef = 90, yRef = 108)
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "regular", fontRef = 22)
    public TextField fieldAddProfileEnterName;

    @FXML
    @DimensionAdjustment(wRef = 331, hRef = 40)
    @CoordinatesAdjustment(xRef = 90.5, yRef = 174)
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "regular", fontRef = 32)
    public Label labelAddProfileSelectAvatar;

    @FXML
    @DimensionAdjustment(wRef = 180, hRef = 180)
    @CoordinatesAdjustment(xRef = 166, yRef = 235)
    public ImageView imageAddProfileSelectAvatar;

    /*

        // Modify Profile Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.MONSTROCLASSE_ADMIN_COLOR_HEX)
    public Pane paneModifyProfile;

    @FXML
    @CorePaneStructuration
    public Pane corePaneModifyProfile;

    @FXML
    @DimensionAdjustment(wRef = 512, hRef = 504)
    @CoordinatesAdjustment(xRef = 384, yRef = 56)
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    public Pane subPaneModifyProfile;

    @FXML
    @DimensionAdjustment(wRef = 512, hRef = 75.16)
    @CoordinatesAdjustment(xRef = 0, yRef = 0)
    public Pane internalPaneOneOfModifyProfile;

    @FXML
    @DimensionAdjustment(wRef = 512, hRef = 428.84)
    @CoordinatesAdjustment(xRef = 0, yRef = 75.16)
    public Pane internalPaneTwoOfModifyProfile;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 10, yRef = 358.84)
    public Button backBtnModifyProfile;

    @FXML
    @ImageViewBinder(fieldName = "backBtnModifyProfile")
    public ImageView backBtnModifyProfileImage;

    @FXML
    @DimensionAdjustment(wRef = 316, hRef = 43)
    @CoordinatesAdjustment(xRef = 98, yRef = 16)
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "bold", fontRef = 34)
    public Label labelModifyProfileTop;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 442, yRef = 358.84)
    public Button checkBtnModifyProfile;

    @FXML
    @ImageViewBinder(fieldName = "checkBtnModifyProfile")
    public ImageView checkBtnModifyProfileImage;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 28, yRef = 277)
    public Button leftArrowBtnModifyProfile;

    @FXML
    @ImageViewBinder(fieldName = "leftArrowBtnModifyProfile")
    public ImageView leftArrowBtnModifyProfileImage;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 427, yRef = 277)
    public Button rightArrowBtnModifyProfile;

    @FXML
    @ImageViewBinder(fieldName = "rightArrowBtnModifyProfile")
    public ImageView rightArrowBtnModifyProfileImage;

    @FXML
    @DimensionAdjustment(wRef = 221, hRef = 40)
    @CoordinatesAdjustment(xRef = 145.5, yRef = 32)
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "regular", fontRef = 32)
    public Label labelModifyProfileEnterName;

    @FXML
    @DimensionAdjustment(wRef = 332.8, hRef = 42)
    @CoordinatesAdjustment(xRef = 90, yRef = 108)
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "regular", fontRef = 22)
    public TextField fieldModifyProfileEnterName;

    @FXML
    @DimensionAdjustment(wRef = 331, hRef = 40)
    @CoordinatesAdjustment(xRef = 90.5, yRef = 174)
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "regular", fontRef = 32)
    public Label labelModifyProfileSelectAvatar;

    @FXML
    @DimensionAdjustment(wRef = 180, hRef = 180)
    @CoordinatesAdjustment(xRef = 166, yRef = 235)
    public ImageView imageModifyProfileSelectAvatar;

    /*

        // Delete Profile Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.MONSTROCLASSE_ADMIN_COLOR_HEX)
    public Pane paneDeleteProfile;

    @FXML
    @CorePaneStructuration
    public Pane corePaneDeleteProfile;

    @FXML
    @DimensionAdjustment(wRef = 512, hRef = 504)
    @CoordinatesAdjustment(xRef = 384, yRef = 56)
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    public Pane subPaneDeleteProfile;

    @FXML
    @DimensionAdjustment(wRef = 512, hRef = 75.16)
    @CoordinatesAdjustment(xRef = 0, yRef = 0)
    public Pane internalPaneOneOfDeleteProfile;

    @FXML
    @DimensionAdjustment(wRef = 512, hRef = 428.84)
    @CoordinatesAdjustment(xRef = 0, yRef = 75.16)
    public Pane internalPaneTwoOfDeleteProfile;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 442, yRef = 358.84)
    public Button checkDeleteProfileBtn;

    @FXML
    @ImageViewBinder(fieldName = "checkDeleteProfileBtn")
    public ImageView checkDeleteProfileBtnImage;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 10, yRef = 358.84)
    public Button undoDeleteProfileBtn;

    @FXML
    @ImageViewBinder(fieldName = "undoDeleteProfileBtn")
    public ImageView undoDeleteProfileBtnImage;

    @FXML
    @DimensionAdjustment(wRef = 221, hRef = 40)
    @CoordinatesAdjustment(xRef = 145.5, yRef = 290)
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "regular", fontRef = 32)
    public Label labelStudentNameDeleteProfile;

    @FXML
    @DimensionAdjustment(wRef = 180, hRef = 180)
    @CoordinatesAdjustment(xRef = 166, yRef = 80)
    public ImageView imageStudentDeleteProfile;

    @FXML
    @DimensionAdjustment(wRef = 360, hRef = 43)
    @CoordinatesAdjustment(xRef = 76, yRef = 16)
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "bold", fontRef = 34)
    public Label labelDeleteProfileTop;

    /*

        // Module Access Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.MONSTROCLASSE_ADMIN_COLOR_HEX)
    public Pane paneModuleAccess;

    @FXML
    @CorePaneStructuration
    public Pane corePaneModuleAccess;

    @FXML
    @DimensionAdjustment(wRef = 960, hRef = 468)
    @CoordinatesAdjustment(xRef = 160, yRef = 74)
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    public Pane subPaneModuleAccess;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 448)
    @CoordinatesAdjustment(xRef = 10, yRef = 10)
    public Pane moduleAccessVue;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 15, yRef = 531)
    public Button backBtnModuleAccess;

    @FXML
    @ImageViewBinder(fieldName = "backBtnModuleAccess")
    public ImageView backBtnModuleAccessImage;

    /*

        // Module Access Edit Pane

    */

    @FXML
    @AnchoredPaneStructuration
    @ThemeBackground(color = AppTheme.MONSTROCLASSE_ADMIN_COLOR_HEX)
    public Pane paneModuleAccessEdit;

    @FXML
    @CorePaneStructuration
    public Pane corePaneModuleAccessEdit;

    @FXML
    @DimensionAdjustment(wRef = 960, hRef = 528)
    @CoordinatesAdjustment(xRef = 160, yRef = 44)
    @RoundedStyle(color = AppTheme.SUB_PANE_COLOR_HEX, cornerRadius = 25)
    public Pane internalPaneModuleAccessEdit;

    @FXML
    @DimensionAdjustment(wRef = 960, hRef = 60)
    public Pane titlePaneModuleAccessEdit;

    @FXML
    @DimensionAdjustment(wRef = 474, hRef = 44)
    @CoordinatesAdjustment(xRef = 243, yRef = 8)
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "bold", fontRef = 35)
    public Label titleLabelModuleAccessEdit;

    @FXML
    @DimensionAdjustment(wRef = 960, hRef = 468)
    @CoordinatesAdjustment(xRef = 0, yRef = 60)
    public Pane subPaneModuleAccessEdit;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 448)
    @CoordinatesAdjustment(xRef = 10, yRef = 10)
    public Pane layoutPaneModuleAccessEdit;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 94)
    @CoordinatesAdjustment(xRef = 0, yRef = 0)
    public Pane internalPaneIndicatorModuleAccessEdit;

    @FXML
    @DimensionAdjustment(wRef = 940, hRef = 354)
    @CoordinatesAdjustment(xRef = 0, yRef = 94)
    public Pane internalPaneScrollModuleAccessEdit;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 15, yRef = 531)
    public Button backBtnModuleAccessEdit;

    @FXML
    @ImageViewBinder(fieldName = "backBtnModuleAccessEdit")
    public ImageView backBtnModuleAccessEditImage;

    @FXML
    @DimensionAdjustment(wRef = 125, hRef = 125)
    @CoordinatesAdjustment(xRef = 1140, yRef = 10)
    public ImageView currentEditModuleAccessImage;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 14, yRef = 17)
    public Button uncheckAllBtnModuleAccessEdit;

    @FXML
    @ImageViewBinder(fieldName = "uncheckAllBtnModuleAccessEdit")
    public ImageView uncheckAllBtnModuleAccessEditImage;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 60, hRef = 60)
    @CoordinatesAdjustment(xRef = 866, yRef = 17)
    public Button checkAllBtnModuleAccessEdit;

    @FXML
    @ImageViewBinder(fieldName = "checkAllBtnModuleAccessEdit")
    public ImageView checkAllBtnModuleAccessEditImage;

    @FXML
    @ApplyCss(classTab = {"node-flat"})
    @DimensionAdjustment(wRef = 70, hRef = 70)
    @CoordinatesAdjustment(xRef = 1196, yRef = 532)
    public Button saveBtnModuleAccessEdit;

    @FXML
    @ImageViewBinder(fieldName = "saveBtnModuleAccessEdit")
    public ImageView saveBtnModuleAccessEditImage;

    @FXML
    @DimensionAdjustment(wRef = 240, hRef = 38)
    @CoordinatesAdjustment(xRef = 83, yRef = 28)
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "bold", fontRef = 30)
    public Label labelUncheckModuleAccessEdit;

    @FXML
    @DimensionAdjustment(wRef = 199, hRef = 38)
    @CoordinatesAdjustment(xRef = 650, yRef = 28)
    @FontAdjustment(fontFamily = "Verdana", fontWeight = "bold", fontRef = 30)
    public Label labelCheckModuleAccessEdit;

    // ***************************************************************
    // AdminModStudentManagerController - Properties
    // ***************************************************************

    // ----------------------------------------------------------
    // Modify Profile Properties
    // ----------------------------------------------------------

    protected static Pupil pupilBeingModified = null;

    // ----------------------------------------------------------
    // Modify Profile Properties
    // ----------------------------------------------------------

    // ----------------------------------------------------------
    // Delete Profile Properties
    // ----------------------------------------------------------

    protected static Pupil pupilBeingDeleted = null;

    // ----------------------------------------------------------
    // Delete Profile Properties
    // ----------------------------------------------------------

    // ----------------------------------------------------------
    // Module Access Edit Properties
    // ----------------------------------------------------------

    public static IsModule currentModuleEditAccessibility = null;

    // ----------------------------------------------------------
    // Module Access Edit Properties
    // ----------------------------------------------------------

    // ***************************************************************
    // AdminModStudentManagerController - Properties
    // ***************************************************************

    @Override
    public void loadController()
    {
        /*

            // Apply Events

        */

        paneAddProfile.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (observableValue.getValue())
            {
                Selector<Avatar> avatarSelector = MonstroclasseMemory.getInstance().getAvatarSelector();
                avatarSelector.reset();
                fieldAddProfileEnterName.setText("");
                showCurrentAvatarIn(imageAddProfileSelectAvatar);
                leftArrowBtnAddProfile.setVisible(avatarSelector.hasPrevious());
                rightArrowBtnAddProfile.setVisible(avatarSelector.hasNext());
            }
        });

        paneDeleteProfile.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (observableValue.getValue())
            {
                Pupil pupil = pupilBeingDeleted;
                labelStudentNameDeleteProfile.setText(pupil.getName());
                Image avatar = ImageUtils.byteArrayToImage(pupil.getAvatar().getData());
                imageStudentDeleteProfile.setImage(avatar);
            }
        });

        paneModifyProfile.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (observableValue.getValue())
            {
                Pupil pupil = pupilBeingModified;
                fieldModifyProfileEnterName.setText(pupil.getName());

                Selector<Avatar> avatarSelector = MonstroclasseMemory.getInstance().getAvatarSelector();

                avatarSelector.push(pupil.getAvatar());
                avatarSelector.reset();

                showCurrentAvatarIn(imageModifyProfileSelectAvatar);
                leftArrowBtnModifyProfile.setVisible(avatarSelector.hasPrevious());
                rightArrowBtnModifyProfile.setVisible(avatarSelector.hasNext());
            }
        });

        paneModuleAccess.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (observableValue.getValue())
            {
                int rows = ScreenStructurationInfo.moduleAccessRow;
                int cols = ScreenStructurationInfo.moduleAccessColumn;
                ModulePaneGrid paneGrid = new ModulePaneGrid(moduleAccessVue, rows, cols);
                for (IsModule module : ModuleRegisterBus.modulesNonAdmin())
                {
                    paneGrid.addModuleCard(module, mouseEvent ->
                    {
                        currentModuleEditAccessibility = module;
                        AdminModStudentManager.getInstance().paneHandler.show("moduleAccessEdit");
                    });
                }
            }
        });

        paneModuleAccessEdit.visibleProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (observableValue.getValue())
            {
                IsModule module = currentModuleEditAccessibility;
                this.currentEditModuleAccessImage.setImage(module.avatar());

                internalPaneScrollModuleAccessEdit.getChildren().clear();

                final double width = internalPaneScrollModuleAccessEdit.getPrefWidth();
                final double height = internalPaneScrollModuleAccessEdit.getPrefHeight();

                final double widthCard = internalPaneScrollModuleAccessEdit.getPrefWidth();
                final double heightCard = internalPaneScrollModuleAccessEdit.getPrefHeight() * 0.18f;

                VerticalScrollPane verticalScrollPane = new VerticalScrollPane(width, height, 5);

                for (Pupil pupil : MonstroclasseMemory.getInstance().getPupils())
                {
                    verticalScrollPane.addNode(new PupilModuleAccessEditCard(pupil, widthCard, heightCard, module));
                }

                internalPaneScrollModuleAccessEdit.getChildren().add(verticalScrollPane);
            }
            else
            {
                currentModuleEditAccessibility = null;
                this.internalPaneScrollModuleAccessEdit.getChildren().clear();
            }
        });

        /*

            // Last Things

        */

        final int CHARACTER_LIMIT = 20;
        fieldAddProfileEnterName.setTextFormatter(new TextFormatter<>(change ->
        {
            String newText = change.getControlNewText();
            if (newText.length() <= CHARACTER_LIMIT) return change;
            return null;
        }));

        appBanner.toFront(); // Force to be in front of all objects
        currentAccountImage.toFront(); // Force to be in front of all objects

        Main.logger.info(this.getClass().getName() + " loaded !");
    }

    private void showCurrentAvatarIn(ImageView view)
    {
        Selector<Avatar> avatarSelector = MonstroclasseMemory.getInstance().getAvatarSelector();
        if (avatarSelector.empty())
        {
            view.setImage(null);
        }
        else
        {
            Avatar avatar = avatarSelector.current();
            Image image = ImageUtils.byteArrayToImage(avatar.getData());
            view.setImage(image);
        }
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
    public void addProfileBtnHomeClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO ADD PROFILE PANE ------- */
        AdminModStudentManager.getInstance().paneHandler.show("addProfile");
        /* ------- SWITCH TO ADD PROFILE PANE ------- */
    }

    @FXML
    public void moduleAccessBtnHomeClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO MODULE ACCESS PANE ------- */
        AdminModStudentManager.getInstance().paneHandler.show("moduleAccess");
        /* ------- SWITCH TO MODULE ACCESS PANE ------- */
    }

    /*

        // Add Profile External Events

    */

    @FXML
    public void backBtnAddProfileClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO HOME PANE ------- */
        AdminModStudentManager.getInstance().paneHandler.show("home");
        /* ------- SWITCH TO HOME PANE ------- */
    }

    @FXML
    public void checkBtnAddProfileClick(ActionEvent actionEvent)
    {
        Selector<Avatar> avatarSelector = MonstroclasseMemory.getInstance().getAvatarSelector();
        if (!fieldAddProfileEnterName.getText().isEmpty() && !avatarSelector.empty())
        {
            // ***** GET SELECTED AVATAR ***** //
            Avatar avatar = avatarSelector.retrieve();
            avatar.setAvailable(0);
            // ***** GET SELECTED AVATAR ***** //

            // ***** CREATION OF THE NEW PUPIL ***** //
            Pupil pupil = new Pupil();
            pupil.setName(fieldAddProfileEnterName.getText());
            pupil.setAvatar(avatar);
            // ***** CREATION OF THE NEW PUPIL ***** //

            // ***** UPDATE THE AVATAR_REPOSITORY ***** //
            AvatarRepository.update(avatar);
            // ***** UPDATE THE AVATAR_REPOSITORY ***** //

            // ***** UPDATE THE PUPIL_REPOSITORY ***** //
            PupilRepository.create(pupil);
            // ***** UPDATE THE PUPIL_REPOSITORY ***** //

            // ***** GIVE ALL NON ADMIN MODULES ***** //
            ModuleRegisterBus.modulesNonAdmin().forEach(isModule ->
            {
                Module module = ModuleRepository.read(isModule.reference());
                PupilModuleAccessRepository.create(pupil, module);
            });
            // ***** GIVE ALL NON ADMIN MODULES ***** //

            /* ------- SWITCH TO HOME PANE ------- */
            AdminModStudentManager.getInstance().paneHandler.show("home");
            /* ------- SWITCH TO HOME PANE ------- */
        }
    }

    @FXML
    public void leftArrowBtnAddProfileClick(ActionEvent actionEvent)
    {
        Selector<Avatar> avatarSelector = MonstroclasseMemory.getInstance().getAvatarSelector();

        // ***** SELECT PREVIOUS AVATAR ***** //
        avatarSelector.previous();
        // ***** SELECT PREVIOUS AVATAR ***** //

        // ***** UPDATE ARROW SELECTORS VISIBILITY ***** //
        leftArrowBtnAddProfile.setVisible(avatarSelector.hasPrevious());
        rightArrowBtnAddProfile.setVisible(avatarSelector.hasNext());
        // ***** UPDATE ARROW SELECTORS VISIBILITY ***** //

        // ***** SHOW CURRENT AVATAR SELECTED ***** //
        showCurrentAvatarIn(imageAddProfileSelectAvatar);
        // ***** SHOW CURRENT AVATAR SELECTED ***** //
    }

    @FXML
    public void rightArrowBtnAddProfileClick(ActionEvent actionEvent)
    {
        Selector<Avatar> avatarSelector = MonstroclasseMemory.getInstance().getAvatarSelector();

        // ***** SELECT NEXT AVATAR ***** //
        avatarSelector.next();
        // ***** SELECT NEXT AVATAR ***** //

        // ***** UPDATE ARROW SELECTORS VISIBILITY ***** //
        leftArrowBtnAddProfile.setVisible(avatarSelector.hasPrevious());
        rightArrowBtnAddProfile.setVisible(avatarSelector.hasNext());
        // ***** UPDATE ARROW SELECTORS VISIBILITY ***** //

        // ***** SHOW CURRENT AVATAR SELECTED ***** //
        showCurrentAvatarIn(imageAddProfileSelectAvatar);
        // ***** SHOW CURRENT AVATAR SELECTED ***** //
    }

    @FXML
    public void fieldAddProfileEnterNameKeyPressed(KeyEvent event)
    {
        // ***** ENTER SHORTCUT ***** //
        Selector<Avatar> avatarSelector = MonstroclasseMemory.getInstance().getAvatarSelector();
        if (event.getCode() == KeyCode.ENTER && !fieldAddProfileEnterName.getText().isEmpty() && !avatarSelector.empty()) checkBtnAddProfile.fire();
        // ***** ENTER SHORTCUT ***** //
    }

    /*

        // Delete Profile External Events

    */

    @FXML
    public void undoDeleteProfileBtnClick(ActionEvent actionEvent)
    {
        // ***** RESET THE DATA ***** //
        pupilBeingDeleted = null;
        // ***** RESET THE DATA ***** //

        /* ------- SWITCH TO HOME PANE ------- */
        AdminModStudentManager.getInstance().paneHandler.show("home");
        /* ------- SWITCH TO HOME PANE ------- */
    }

    @FXML
    public void checkDeleteProfileBtnClick(ActionEvent actionEvent)
    {
        if (pupilBeingDeleted != null)
        {
            // ***** MAKE AVATAR AVAILABLE ***** //
            Avatar avatar = pupilBeingDeleted.getAvatar();
            avatar.setAvailable(1);
            // ***** MAKE AVATAR AVAILABLE ***** //

            // ***** UPDATE THE AVATAR_REPOSITORY ***** //
            AvatarRepository.update(avatar);
            // ***** UPDATE THE AVATAR_REPOSITORY ***** //

            // ***** UPDATE THE PUPIL_REPOSITORY ***** //
            PupilRepository.delete(pupilBeingDeleted);
            // ***** UPDATE THE PUPIL_REPOSITORY ***** //

            // ***** RESET THE DATA ***** //
            pupilBeingDeleted = null;
            // ***** RESET THE DATA ***** //

            /* ------- SWITCH TO HOME PANE ------- */
            AdminModStudentManager.getInstance().paneHandler.show("home");
            /* ------- SWITCH TO HOME PANE ------- */
        }
    }

    /*

        // Modify Profile External Events

    */

    @FXML
    public void backBtnModifyProfileClick(ActionEvent actionEvent)
    {
        // ***** REMOVE FIRST AVATAR TO SELECTOR ***** //
        MonstroclasseMemory.getInstance().getAvatarSelector().pull();
        // ***** REMOVE FIRST AVATAR TO SELECTOR ***** //

        // ***** RESET THE DATA ***** //
        pupilBeingModified = null;
        // ***** RESET THE DATA ***** //

        /* ------- SWITCH TO HOME PANE ------- */
        AdminModStudentManager.getInstance().paneHandler.show("home");
        /* ------- SWITCH TO HOME PANE ------- */
    }

    @FXML
    public void checkBtnModifyProfileClick(ActionEvent actionEvent)
    {
        Selector<Avatar> avatarSelector = MonstroclasseMemory.getInstance().getAvatarSelector();
        if (!fieldModifyProfileEnterName.getText().isEmpty() && !avatarSelector.empty())
        {
            // ***** MAKE NEW AVATAR UNAVAILABLE ***** //
            Avatar newAvatar = avatarSelector.retrieve();
            newAvatar.setAvailable(0);
            // ***** MAKE NEW AVATAR UNAVAILABLE ***** //

            // ***** MAKE OLD AVATAR AVAILABLE ***** //
            Avatar oldAvatar = pupilBeingModified.getAvatar();
            oldAvatar.setAvailable(1);
            // ***** MAKE OLD AVATAR AVAILABLE ***** //

            // ***** MODIFY THE ACCOUNT ***** //
            pupilBeingModified.setName(fieldModifyProfileEnterName.getText());
            pupilBeingModified.setAvatar(newAvatar);
            // ***** MODIFY THE ACCOUNT ***** //

            // ***** UPDATE THE AVATAR_REPOSITORY ***** //
            AvatarRepository.update(newAvatar);
            AvatarRepository.update(oldAvatar);
            // ***** UPDATE THE AVATAR_REPOSITORY ***** //

            // ***** UPDATE THE PUPIL_REPOSITORY ***** //
            PupilRepository.update(pupilBeingModified);
            // ***** UPDATE THE PUPIL_REPOSITORY ***** //

            // ***** RESET THE DATA ***** //
            pupilBeingModified = null;
            // ***** RESET THE DATA ***** //

            /* ------- SWITCH TO HOME PANE ------- */
            AdminModStudentManager.getInstance().paneHandler.show("home");
            /* ------- SWITCH TO HOME PANE ------- */
        }
    }

    @FXML
    public void leftArrowBtnModifyProfileClick(ActionEvent actionEvent)
    {
        Selector<Avatar> avatarSelector = MonstroclasseMemory.getInstance().getAvatarSelector();

        // ***** SELECT PREVIOUS AVATAR ***** //
        avatarSelector.previous();
        // ***** SELECT PREVIOUS AVATAR ***** //

        // ***** UPDATE ARROW SELECTORS VISIBILITY ***** //
        leftArrowBtnModifyProfile.setVisible(avatarSelector.hasPrevious());
        rightArrowBtnModifyProfile.setVisible(avatarSelector.hasNext());
        // ***** UPDATE ARROW SELECTORS VISIBILITY ***** //

        // ***** SHOW CURRENT AVATAR SELECTED ***** //
        showCurrentAvatarIn(imageModifyProfileSelectAvatar);
        // ***** SHOW CURRENT AVATAR SELECTED ***** //
    }

    @FXML
    public void rightArrowBtnModifyProfileClick(ActionEvent actionEvent)
    {
        Selector<Avatar> avatarSelector = MonstroclasseMemory.getInstance().getAvatarSelector();

        // ***** SELECT NEXT AVATAR ***** //
        avatarSelector.next();
        // ***** SELECT NEXT AVATAR ***** //

        // ***** UPDATE ARROW SELECTORS VISIBILITY ***** //
        leftArrowBtnModifyProfile.setVisible(avatarSelector.hasPrevious());
        rightArrowBtnModifyProfile.setVisible(avatarSelector.hasNext());
        // ***** UPDATE ARROW SELECTORS VISIBILITY ***** //

        // ***** SHOW CURRENT AVATAR SELECTED ***** //
        showCurrentAvatarIn(imageModifyProfileSelectAvatar);
        // ***** SHOW CURRENT AVATAR SELECTED ***** //
    }

    @FXML
    public void fieldModifyProfileEnterNameKeyPressed(KeyEvent event)
    {
        // ***** ENTER SHORTCUT ***** //
        if (event.getCode() == KeyCode.ENTER) checkBtnModifyProfile.fire();
        // ***** ENTER SHORTCUT ***** //
    }

    /*

        // Module Access External Events

    */

    @FXML
    public void backBtnModuleAccessClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO HOME PANE ------- */
        AdminModStudentManager.getInstance().paneHandler.show("home");
        /* ------- SWITCH TO HOME PANE ------- */
    }

    /*

        // Module Access Edit External Events

    */

    @FXML
    public void backBtnModuleAccessEditClick(ActionEvent actionEvent)
    {
        /* ------- SWITCH TO MODULE ACCESS PANE ------- */
        AdminModStudentManager.getInstance().paneHandler.show("moduleAccess");
        /* ------- SWITCH TO MODULE ACCESS PANE ------- */
    }

    @FXML
    public void uncheckAllBtnModuleAccessEditClick(ActionEvent actionEvent)
    {
        if (internalPaneScrollModuleAccessEdit.getChildren().get(0) instanceof VerticalScrollPane pane)
        {
            pane.forEachContent(PupilModuleAccessEditCard.class, pupilModuleAccessEditCard ->
            {
                pupilModuleAccessEditCard.getCheckBoxModuleEdit().uncheck();
            });
        }
    }

    @FXML
    public void checkAllBtnModuleAccessEditClick(ActionEvent actionEvent)
    {
        if (internalPaneScrollModuleAccessEdit.getChildren().get(0) instanceof VerticalScrollPane pane)
        {
            pane.forEachContent(PupilModuleAccessEditCard.class, pupilModuleAccessEditCard ->
            {
                pupilModuleAccessEditCard.getCheckBoxModuleEdit().check();
            });
        }
    }

    @FXML
    public void saveBtnModuleAccessEditClick(ActionEvent actionEvent)
    {
        if (internalPaneScrollModuleAccessEdit.getChildren().get(0) instanceof VerticalScrollPane pane)
        {
            pane.forEachContent(PupilModuleAccessEditCard.class, PupilModuleAccessEditCard::save);
        }
        AdminModStudentManager.getInstance().paneHandler.show("moduleAccess");
    }
}