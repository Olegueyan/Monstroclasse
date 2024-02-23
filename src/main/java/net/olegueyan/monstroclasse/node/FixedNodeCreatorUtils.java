package net.olegueyan.monstroclasse.node;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import net.olegueyan.monstroclasse.common.Constants;
import net.olegueyan.monstroclasse.screen.ScreenStructurationInfo;

public class FixedNodeCreatorUtils
{
    public static void forcePrefDimension(Region region)
    {
        region.minWidthProperty().bind(region.prefWidthProperty());
        region.minHeightProperty().bind(region.prefHeightProperty());
        region.maxWidthProperty().bind(region.prefWidthProperty());
        region.maxHeightProperty().bind(region.prefHeightProperty());
    }

    public static void bindImageViewDimensionToRegion(ImageView imageView, Region region)
    {
        imageView.fitWidthProperty().bind(region.widthProperty());
        imageView.fitHeightProperty().bind(region.heightProperty());
    }

    public static Label prepareLabel(double width, double height, int chainMax, FontWeight fontWeight)
    {
        Label label = new Label();
        FixedNodeCreatorUtils.forcePrefDimension(label);
        label.setPrefWidth(width);
        label.setPrefHeight(height);
        double fontSize = ScreenStructurationInfo.getAdjustedFontSize(chainMax, width, height);
        Font font = Font.font(Constants.FONT_FAMILY, fontWeight, fontSize);
        label.setFont(font);
        return label;
    }

    public static void fixeRegionNode(Region region, double width, double height)
    {
        FixedNodeCreatorUtils.forcePrefDimension(region);
        region.setPrefWidth(width);
        region.setPrefHeight(height);
    }
}