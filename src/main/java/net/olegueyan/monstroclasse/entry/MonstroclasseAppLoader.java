package net.olegueyan.monstroclasse.entry;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.olegueyan.monstroclasse.Main;
import net.olegueyan.monstroclasse.common.PersistentData;
import net.olegueyan.monstroclasse.component.StageHandlerComponent;
import net.olegueyan.monstroclasse.common.MonstroclasseMemory;
import net.olegueyan.monstroclasse.event.ecrilu.EventListenerFocusedExercise;
import net.olegueyan.monstroclasse.event.ecrilu.EventListenerFocusedSequence;
import net.olegueyan.monstroclasse.event.SqlEventHubAdapter;
import net.olegueyan.monstroclasse.event.ecrilu.EventListenerFocusedWord;
import net.olegueyan.monstroclasse.module.ModuleRegisterBus;
import net.olegueyan.monstroclasse.module.content.AdminModEcrilu;
import net.olegueyan.monstroclasse.module.content.AdminModStudentManager;
import net.olegueyan.monstroclasse.module.content.PupilModEcrilu;
import net.olegueyan.monstroclasse.portal.Monstroclasse;
import net.olegueyan.monstroclasse.screen.ScreenStructurationInfo;

import java.util.Objects;

/**
 * Loader of the Monstroclasse application
 */
public class MonstroclasseAppLoader extends Application
{
    // ***************************************************************
    // MonstroclasseAppLoader - MAIN
    // ***************************************************************

    public static void main(String[] args)
    {
        launch(args); // Entry point
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // MonstroclasseAppLoader - INIT
    // ***************************************************************

    @Override
    public void init()
    {
        // ------- Sql Events registration ------- //
        SqlEventHubAdapter.getInstance().addSdlEventListener(MonstroclasseMemory.getInstance());
        SqlEventHubAdapter.getInstance().addSdlEventListener(Monstroclasse.getInstance());
        SqlEventHubAdapter.getInstance().addSdlEventListener(AdminModStudentManager.getInstance());
        SqlEventHubAdapter.getInstance().addSdlEventListener(AdminModEcrilu.getInstance());
        // --------------------------------------- //

        // ------- Module registration ------- //
        ModuleRegisterBus.registerModule(AdminModStudentManager.getInstance());
        ModuleRegisterBus.registerModule(AdminModEcrilu.getInstance());
        ModuleRegisterBus.registerModule(PupilModEcrilu.getInstance());
        // ----------------------------------- //

        // ------- Sequence Events registration ------- //
        EventListenerFocusedSequence.events.add(AdminModEcrilu.getInstance());
        // -------------------------------------------- //

        // ------- Exercise Events registration ------- //
        EventListenerFocusedExercise.events.add(AdminModEcrilu.getInstance());
        // -------------------------------------------- //

        // ------- Word Events registration ------- //
        EventListenerFocusedWord.events.add(AdminModEcrilu.getInstance());
        // ---------------------------------------- //
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // MonstroclasseAppLoader - START
    // ***************************************************************

    @Override
    public void start(Stage stage)
    {
        // ------- Define the size of the title bar ------- //
        ScreenStructurationInfo.processInitDummyTitleBar();
        // ------------------------------------------------ //

        // ------- Get the css of Monstroclasse application ------- //
        String cssMonstroclasse = Objects.requireNonNull(Main.class.getResource("css/Monstroclasse.css")).toExternalForm();
        // -------------------------------------------------------- //

        // ------- Creation of the StageHandler of Monstroclasse application ------- //
        PersistentData.stageHandler = new StageHandlerComponent(stage);
        // ------------------------------------------------------------------------- //

        // ------- Add the css to Monstroclasse application ------- //
        PersistentData.stageHandler.addStylesheet(cssMonstroclasse);
        // -------------------------------------------------------- //

        // ------- Get the icon of Monstroclasse Application ------- //
        Image icon = new Image(Objects.requireNonNull(
                Main.class.getResourceAsStream("assets/monstroclasse.png")
        ));
        // --------------------------------------------------------- //

        // ------- Add the loaded icon to Monstroclasse Application (icon in taskbar) ------- //
        stage.getIcons().add(icon);
        // ---------------------------------------------------------------------------------- //

        // ------- Set the title of Monstroclasse Application ------- //
        stage.setTitle("Monstroclasse");
        // ---------------------------------------------------------- //

        // ------- Make the application borderless ------- //
        stage.initStyle(StageStyle.UNDECORATED);
        // ----------------------------------------------- //

        // ------- Make the application maximized ------- //
        stage.setMaximized(true);
        // ---------------------------------------------- //

        // ------- Load Monstroclasse Module (Portal) ------- //
        PersistentData.stageHandler.load(Monstroclasse.getInstance());
        // -------------------------------------------------- //
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // MonstroclasseAppLoader - STOP
    // ***************************************************************

    @Override
    public void stop() throws Exception
    {

    }

    // ***************************************************************
    // END
    // ***************************************************************
}