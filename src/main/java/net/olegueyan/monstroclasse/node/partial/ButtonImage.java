package net.olegueyan.monstroclasse.node.partial;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.olegueyan.monstroclasse.node.FixedNodeCreatorUtils;

public class ButtonImage extends Button
{
    public ButtonImage(Image image)
    {
        ImageView imageView = new ImageView(image);

        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        setGraphic(imageView);

        FixedNodeCreatorUtils.forcePrefDimension(this);
        FixedNodeCreatorUtils.bindImageViewDimensionToRegion(imageView, this);

        this.setFocused(false);
        this.setFocusTraversable(false);

        this.getStyleClass().addAll("node-flat", "-handed");
    }
}