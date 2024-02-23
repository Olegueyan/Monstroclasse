package net.olegueyan.monstroclasse.module.content;

import javafx.scene.image.Image;
import net.olegueyan.monstroclasse.Main;
import net.olegueyan.monstroclasse.common.PersistentData;
import net.olegueyan.monstroclasse.integration.SceneIntegration;
import net.olegueyan.monstroclasse.module.IsModule;

import java.io.InputStream;
import java.util.Objects;

public class PupilModEcrilu extends SceneIntegration<PupilModEcriluController> implements IsModule
{
    private static PupilModEcrilu INSTANCE;

    @Override
    public void onActive()
    {
        this.controller.currentAccountImage.setImage(PersistentData.currentConnectedAccount.image());
        this.controller.currentAccountName.setText(PersistentData.currentConnectedAccount.name());

        // Load pane handler

        this.paneHandler.addPane("home", this.controller.paneHome);
        this.paneHandler.addPane("exerciseSelection", this.controller.paneExerciseSelection);
        this.paneHandler.addPane("exerciseExecution", this.controller.paneExerciseExecution);
        this.paneHandler.addPane("closeExerciseExecutionConfirm", this.controller.paneCloseExerciseExecutionConfirm);

        this.paneHandler.hideAll();
        this.paneHandler.show("home");
    }

    @Override
    public void onDisable()
    {
        // TODO
    }

    public PupilModEcrilu()
    {
        super(Objects.requireNonNull(AdminModStudentManager.class.getResource("smodecrilu.fxml")));
    }

    @Override
    public String name()
    {
        return "Ecrilu";
    }

    @Override
    public String reference()
    {
        return "ES2M";
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
        return false;
    }

    /**
     * Singleton
     * @return PupilModEcrilu
     */
    public static PupilModEcrilu getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new PupilModEcrilu();
        }
        return INSTANCE;
    }
}