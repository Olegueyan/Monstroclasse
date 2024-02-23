package net.olegueyan.monstroclasse.portal;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.olegueyan.monstroclasse.Main;
import net.olegueyan.monstroclasse.common.MonstroclasseMemory;
import net.olegueyan.monstroclasse.common.PersistentData;
import net.olegueyan.monstroclasse.entity.GridCell;
import net.olegueyan.monstroclasse.entity.GridCol;
import net.olegueyan.monstroclasse.entity.GridRow;
import net.olegueyan.monstroclasse.entity.Pupil;
import net.olegueyan.monstroclasse.event.SqlEventListener;
import net.olegueyan.monstroclasse.event.SqlEventType;
import net.olegueyan.monstroclasse.integration.SceneIntegration;
import net.olegueyan.monstroclasse.node.partial.ButtonImage;
import net.olegueyan.monstroclasse.repository.GridRepository;
import net.olegueyan.monstroclasse.utils.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Monstroclasse extends SceneIntegration<MonstroclasseController> implements SqlEventListener
{
    // ------- Logger of Monstroclasse ------- //
    private static final Logger LOGGER = LoggerFactory.getLogger(Monstroclasse.class);
    // --------------------------------------- //

    // ------- make Monstroclasse a Singleton ------- //
    private static Monstroclasse INSTANCE;
    // ---------------------------------------------- //

    /***
     * Graphics data
     |-> Dimension of GridPane of a/the Student Table
     ***/

    public static final int studentGridPaneRow = 6;
    public static final int studentGridPaneColumn = 9;

    public double cellSizeWidth;
    public double cellSizeHeight;

    private String currentPane = "home";

    @Override
    public void onActive()
    {
        /*

            // Fixed Element of Main Table

        */

        cellSizeWidth = this.controller.studentGridPane.getPrefWidth() / studentGridPaneColumn;
        cellSizeHeight = this.controller.studentGridPane.getPrefHeight() / studentGridPaneRow;

        this.controller.studentGridPane.getChildren().clear();

        for (GridRow gridRow : GridRepository.readAllGridRow())
        {
            Image image = ImageUtils.byteArrayToImage(gridRow.getAvatar().getData());
            ImageView cell = new ImageView(image);
            cell.setFitWidth(cellSizeWidth);
            cell.setFitHeight(cellSizeHeight);
            cell.setLayoutX(cellSizeWidth * gridRow.getAxisX());
            cell.setLayoutY(0);
            this.controller.studentGridPane.getChildren().add(cell);
        }

        for (GridCol gridCol : GridRepository.readAllGridCol())
        {
            Image image = ImageUtils.byteArrayToImage(gridCol.getAvatar().getData());
            ImageView cell = new ImageView(image);
            cell.setFitWidth(cellSizeWidth);
            cell.setFitHeight(cellSizeHeight);
            cell.setLayoutX(0);
            cell.setLayoutY(cellSizeHeight * gridCol.getAxisY());
            this.controller.studentGridPane.getChildren().add(cell);
        }

        for (Pupil pupil : MonstroclasseMemory.getInstance().getPupils())
        {
            this.createPupil(pupil);
        }

        // ------- Initialisation of PaneHandler component ------- //
        this.paneHandler.addPane("home", this.controller.paneHome);
        this.paneHandler.addPane("adminConnexion", this.controller.paneAdminConnexion);
        this.paneHandler.addPane("accountAdmin", this.controller.paneAccountAdmin);
        this.paneHandler.addPane("accountStudent", this.controller.paneAccountStudent);
        this.paneHandler.addPane("closeConfirm", this.controller.paneCloseConfirm);
        this.paneHandler.addPane("accountStudentConfirm", this.controller.paneAccountStudentConfirm);
        this.paneHandler.addPane("dataExportImport", this.controller.paneDataExportImport);
        // ------------------------------------------------------- //

        // ------- Initialisation of PaneHandler component ------- //
        this.paneHandler.setOnChange((s, pane) -> this.currentPane = s);

        // ------- Show home pane (entry pane) ------- //
        this.paneHandler.hideAll();
        this.paneHandler.show(currentPane);
        // ------------------------------------------- //
    }

    @Override
    public void onDisable()
    {
        this.controller.studentGridPane.getChildren().clear();
    }

    private Monstroclasse()
    {
        super(Objects.requireNonNull(Main.class.getResource("monstroclasse.fxml")));
    }

    /**
     * Singleton
     * @return Monstroclasse
     */
    public static Monstroclasse getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new Monstroclasse();
        }
        return INSTANCE;
    }

    @Override
    public void onSqlPerform(Object[] entities, SqlEventType sqlEventType)
    {
        if (PersistentData.stageHandler.getCurrent() == this)
        {
            if (entities[0] instanceof Pupil pupil)
            {
                switch (sqlEventType)
                {
                    case INSERT -> this.createPupil(pupil);
                    case DELETE -> this.deletePupil(pupil);
                    case UPDATE ->
                    {
                        this.deletePupil(pupil);
                        this.createPupil(pupil);
                    }
                }
            }
        }
    }

    private void createPupil(Pupil pupil)
    {
        GridCell cell = GridRepository.readAllGridCell().stream()
                .filter(gridCell -> Objects.equals(gridCell.getAvatar().getIdAvatar(), pupil.getAvatar().getIdAvatar()))
                .findFirst()
                .orElseThrow();

        int column = cell.getAxisX();
        int row = cell.getAxisY();

        ButtonImage buttonImage = generateButtonImage(pupil);

        buttonImage.setLayoutX(cellSizeWidth * column);
        buttonImage.setLayoutY(cellSizeHeight * row);

        this.controller.studentGridPane.getChildren().add(buttonImage);
    }

    private void deletePupil(Pupil pupil)
    {
        this.controller.studentGridPane.getChildren().removeIf(node ->
        {
            if (node instanceof ButtonImage buttonImage)
            {
                return ((Pupil) buttonImage.getUserData()).getIdPupil().equals(pupil.getIdPupil());
            }
            return false;
        });
    }

    /**
     *
     * @param pupil
     * @return
     */
    private ButtonImage generateButtonImage(Pupil pupil)
    {
        Image image = ImageUtils.byteArrayToImage(pupil.getAvatar().getData());

        ButtonImage buttonImage = new ButtonImage(image);

        buttonImage.getStyleClass().add("-transparent");
        buttonImage.applyCss();

        buttonImage.setPrefWidth(cellSizeWidth);
        buttonImage.setPrefHeight(cellSizeHeight);

        buttonImage.setUserData(pupil);
        buttonImage.setOnAction(this.controller::studentAvatarBtnClick);

        return buttonImage;
    }
}