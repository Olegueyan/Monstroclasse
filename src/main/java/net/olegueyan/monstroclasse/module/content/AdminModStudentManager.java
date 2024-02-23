package net.olegueyan.monstroclasse.module.content;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.olegueyan.monstroclasse.Main;
import net.olegueyan.monstroclasse.common.Constants;
import net.olegueyan.monstroclasse.common.PersistentData;
import net.olegueyan.monstroclasse.common.MonstroclasseMemory;
import net.olegueyan.monstroclasse.entity.Pupil;
import net.olegueyan.monstroclasse.event.SqlEventListener;
import net.olegueyan.monstroclasse.event.SqlEventType;
import net.olegueyan.monstroclasse.integration.SceneIntegration;
import net.olegueyan.monstroclasse.module.IsModule;
import net.olegueyan.monstroclasse.node.partial.ButtonImage;
import net.olegueyan.monstroclasse.node.partial.VerticalScrollPane;
import net.olegueyan.monstroclasse.node.content.PupilCardAdminPupilManager;
import net.olegueyan.monstroclasse.utils.ImageUtils;

import java.io.InputStream;
import java.util.Objects;

public class AdminModStudentManager extends SceneIntegration<AdminModStudentManagerController> implements IsModule, SqlEventListener
{
    private static AdminModStudentManager INSTANCE;

    private static final double margin = 20;

    private VerticalScrollPane verticalScrollPane;

    @Override
    public void onActive()
    {
        double widthPane = this.controller.internalPaneScrollAccounts.getPrefWidth() - margin;
        double heightPane = this.controller.internalPaneScrollAccounts.getPrefHeight() - margin;

        this.verticalScrollPane = new VerticalScrollPane(widthPane, heightPane, 0);
        this.verticalScrollPane.setLayoutX(margin / 2);
        this.verticalScrollPane.setLayoutY(margin / 2);

        this.controller.internalPaneScrollAccounts.getChildren().add(verticalScrollPane);

        this.makePupilFlatScrollPane();

        this.controller.currentAccountImage.setImage(Constants.ADMIN_IMAGE);

        // Load pane handler

        this.paneHandler.addPane("home", this.controller.paneHome);
        this.paneHandler.addPane("addProfile", this.controller.paneAddProfile);
        this.paneHandler.addPane("deleteProfile", this.controller.paneDeleteProfile);
        this.paneHandler.addPane("modifyProfile", this.controller.paneModifyProfile);
        this.paneHandler.addPane("moduleAccess", this.controller.paneModuleAccess);
        this.paneHandler.addPane("moduleAccessEdit", this.controller.paneModuleAccessEdit);

        this.paneHandler.hideAll();
        this.paneHandler.show("home");
    }

    @Override
    public void onDisable()
    {
        // TODO
    }

    private AdminModStudentManager()
    {
        super(Objects.requireNonNull(AdminModStudentManager.class.getResource("amodpm.fxml")));
    }

    @Override
    public String name()
    {
        return "Gestion des profils";
    }

    @Override
    public String reference()
    {
        return "A1GS";
    }

    @Override
    public SceneIntegration<?> scene()
    {
        return this;
    }

    @Override
    public Image avatar()
    {
        InputStream is = Objects.requireNonNull(Main.class.getResourceAsStream("module/studentManager.png"));
        return new Image(is);
    }

    @Override
    public boolean admin()
    {
        return true;
    }

    @Override
    public void onSqlPerform(Object[] entities, SqlEventType sqlEventType)
    {
        if (entities[0] instanceof Pupil pupil)
        {
            this.makePupilFlatScrollPane();
        }
    }

    /**
     * Singleton
     * @return AdminModStudentManager
     */
    public static AdminModStudentManager getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new AdminModStudentManager();
        }
        return INSTANCE;
    }

    private void makePupilFlatScrollPane()
    {
        if (PersistentData.stageHandler.getCurrent() == this)
        {
            double widthCard = this.controller.internalPaneScrollAccounts.getPrefWidth() - margin;
            double heightCard = this.controller.internalPaneScrollAccounts.getPrefHeight() * 0.15f;

            this.verticalScrollPane.clear();

            for (Pupil pupil : MonstroclasseMemory.getInstance().getPupils())
            {
                ButtonImage modifyBtn = new ButtonImage(Constants.EDIT_IMAGE);
                ButtonImage deleteBtn = new ButtonImage(Constants.DELETE_IMAGE);

                ImageView avatarView = new ImageView(ImageUtils.byteArrayToImage(pupil.getAvatar().getData()));
                PupilCardAdminPupilManager pupilCardAdminPupilManager = new PupilCardAdminPupilManager(avatarView, modifyBtn, deleteBtn, pupil, widthCard, heightCard);

                pupilCardAdminPupilManager.setOnClickDeleteBtn(actionEvent ->
                {
                    Button clickedButton = (Button) actionEvent.getSource();
                    AdminModStudentManagerController.pupilBeingDeleted = (Pupil) clickedButton.getUserData();
                    AdminModStudentManager.getInstance().paneHandler.show("deleteProfile");
                });

                pupilCardAdminPupilManager.setOnClickModifyBtn(actionEvent ->
                {
                    Button clickedButton = (Button) actionEvent.getSource();
                    AdminModStudentManagerController.pupilBeingModified = (Pupil) clickedButton.getUserData();
                    AdminModStudentManager.getInstance().paneHandler.show("modifyProfile");
                });

                this.verticalScrollPane.addNode(pupilCardAdminPupilManager);
            }
        }
    }
}