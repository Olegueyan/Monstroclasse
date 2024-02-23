package net.olegueyan.monstroclasse.module;

import javafx.scene.image.Image;
import net.olegueyan.monstroclasse.integration.SceneIntegration;

/**
 * Indicates to a class that is a Module of Monstroclasse
 */
public interface IsModule
{
    // ------- Name of the module ------- //
    String name();
    // ---------------------------------- //

    // ------- Reference of the module | Make a link with the database ------- //
    String reference();
    // ----------------------------------------------------------------------- //

    // ------- The wrapped scene to make module and graphics registration ------- //
    SceneIntegration<?> scene();
    // -------------------------------------------------------------------------- //

    // ------- Avatar rendered in module selection ------- //
    Image avatar();
    // --------------------------------------------------- //

    // ------- Allow or not the access to pupils ------- //
    boolean admin();
    // ------------------------------------------------- //
}