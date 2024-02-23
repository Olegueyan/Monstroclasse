package net.olegueyan.monstroclasse.integration;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import net.olegueyan.monstroclasse.component.PaneHandlerComponent;
import net.olegueyan.monstroclasse.screen.ScreenStructurationInfo;

import java.io.IOException;
import java.net.URL;

public abstract class SceneIntegration<T extends ControllerIntegration>
{
    /*
        Allow doing something when the scene is activated
    */
    public abstract void onActive();

    /*
        Allow doing something when the scene is disabled
    */
    public abstract void onDisable();

    /**
     * URL of .fxml scene
     */
    protected final URL fxmlUrl;

    private Scene scene;

    protected T controller;
    public PaneHandlerComponent paneHandler;

    /**
     * Constructor
     * @param urlOfFXML URL of .fxml scene
     */
    public SceneIntegration(URL urlOfFXML)
    {
        this.fxmlUrl = urlOfFXML;
    }

    /**
     *
     */
    public void loadScene()
    {
        this.paneHandler = new PaneHandlerComponent();

        FXMLLoader fxmlLoader = new FXMLLoader(this.fxmlUrl);

        double width = ScreenStructurationInfo.screen.getBounds().getWidth();
        double height = ScreenStructurationInfo.screen.getBounds().getHeight();

        try
        {
            this.scene = new Scene(fxmlLoader.load(), width, height);
            this.controller = fxmlLoader.getController();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        this.controller.loadIntegrations();
        this.controller.loadController();
    }

    /**
     *
     */
    public void unloadScene()
    {
        this.scene = null;
        this.paneHandler = null;
        this.controller = null;
    }

    /**
     * Getter of Scene
     * @return Scene
     */
    public Scene getScene()
    {
        return this.scene;
    }
}