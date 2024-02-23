package net.olegueyan.monstroclasse.component;

import javafx.scene.Cursor;
import javafx.stage.Stage;
import net.olegueyan.monstroclasse.integration.SceneIntegration;

import java.util.ArrayList;

/**
 * The PaneHandlerComponent is a component that allows to manipulate the stage in changing the scene vue
 * <br>
 * All scenes are loaded with a set of stylesheets
 */
public class StageHandlerComponent
{
    // ***************************************************************
    // StageHandlerComponent - ATTRIBUTES
    // ***************************************************************

    // ------- Stage to apply scene change ------- //
    private final Stage stage;
    // ------------------------------------------- //

    // ------- CSS Stylesheets to load for each scene ------- //
    private final ArrayList<String> stylesheets;
    // ------------------------------------------------------ //

    // ------- Current scene showed ------- //
    private SceneIntegration<?> current;
    // ------------------------------------ //

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // StageHandlerComponent - CONSTRUCTOR
    // ***************************************************************

    public StageHandlerComponent(Stage stage)
    {
        this.stage = stage;
        this.stylesheets = new ArrayList<>();
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // StageHandlerComponent - METHODS
    // ***************************************************************

    /**
     * Add css stylesheet
     * @param stylesheet to external form
     */
    public void addStylesheet(String stylesheet)
    {
        this.stylesheets.add(stylesheet);
    }

    /**
     * Load a scene from a sceneIntegration
     * @param sceneIntegration scene to load
     */
    public void load(SceneIntegration<?> sceneIntegration)
    {
        if (this.current != null)
        {
            this.current.onDisable();
            this.current.unloadScene();
        }
        this.current = sceneIntegration;
        this.current.loadScene();
        this.current.onActive();
        this.stylesheets.forEach(s -> this.current.getScene().getStylesheets().add(s));
        this.current.getScene().setCursor(Cursor.DEFAULT);
        this.stage.setScene(this.current.getScene());
        if (!this.stage.showingProperty().getValue()) this.stage.show();
    }

    /**
     * Get stage of application
     * @return Stage
     */
    public Stage getStage()
    {
        return this.stage;
    }

    /**
     * Get the current SceneIntegration<?> showed
     * @return SceneIntegration<?>
     */
    public SceneIntegration<?> getCurrent()
    {
        return this.current;
    }

    // ***************************************************************
    // END
    // ***************************************************************
}