package net.olegueyan.monstroclasse.node.partial;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.olegueyan.monstroclasse.node.FixedNodeCreatorUtils;

public class BetterCheckBox extends Button
{
    // ***************************************************************
    // BetterCheckBox - ATTRIBUTES
    // ***************************************************************

    // ------- ImageView check graphic ------- //
    private final ImageView check;
    // --------------------------------------- //

    // ------- ImageView uncheck graphic ------- //
    private final ImageView uncheck;
    // ----------------------------------------- //

    // ------- State that represent if the checkbox is checked or not ------- //
    private boolean enable;
    // ---------------------------------------------------------------------- //

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // BetterCheckBox - CONSTRUCTORS
    // ***************************************************************

    public BetterCheckBox(Image check, Image unchecked, boolean enable)
    {
        this.enable = enable;

        this.check = new ImageView(check);
        this.check.setPreserveRatio(true);
        this.check.setSmooth(true);

        this.uncheck = new ImageView(unchecked);
        this.uncheck.setPreserveRatio(true);
        this.uncheck.setSmooth(true);

        this.setGraphic(enable ? this.check : this.uncheck);

        FixedNodeCreatorUtils.forcePrefDimension(this);

        this.check.fitWidthProperty().bind(this.prefWidthProperty());
        this.check.fitHeightProperty().bind(this.prefHeightProperty());

        this.uncheck.fitWidthProperty().bind(this.prefWidthProperty());
        this.uncheck.fitHeightProperty().bind(this.prefHeightProperty());

        this.setFocused(false);
        this.setFocusTraversable(false);

        this.setOnMouseClicked(mouseEvent ->
        {
            if (this.enable) this.uncheck();
            else this.check();
        });
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // BetterCheckBox - METHODS
    // ***************************************************************

    /**
     * Check the CheckBox
     */
    public void check()
    {
        this.enable = true;
        this.setGraphic(this.check);
    }

    /**
     * Uncheck the CheckBox
     */
    public void uncheck()
    {
        this.enable = false;
        this.setGraphic(this.uncheck);
    }

    /**
     * Get if the CheckBox is checked or not
     * @return boolean
     */
    public boolean isEnable()
    {
        return this.enable;
    }

    // ***************************************************************
    // END
    // ***************************************************************
}