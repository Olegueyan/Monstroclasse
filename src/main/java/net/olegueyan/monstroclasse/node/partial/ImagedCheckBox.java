package net.olegueyan.monstroclasse.node.partial;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import net.olegueyan.monstroclasse.common.Constants;
import net.olegueyan.monstroclasse.node.FixedNodeCreatorUtils;
import net.olegueyan.monstroclasse.node.partial.BetterCheckBox;
import net.olegueyan.monstroclasse.screen.ScreenStructurationInfo;

public class ImagedCheckBox extends Pane
{
    private final BetterCheckBox checkBoxNode;

    public ImagedCheckBox(double width, double height, Image image, String name)
    {
        FixedNodeCreatorUtils.fixeRegionNode(this, width, height);

        final double padding = 5;

        double squareSize = height - padding * 2;

        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(squareSize);
        imageView.setFitHeight(squareSize);

        this.checkBoxNode = new BetterCheckBox(Constants.CHECK_IMAGE, Constants.CLOSE_IMAGE, false);

        this.checkBoxNode.setPrefWidth(squareSize);
        this.checkBoxNode.setPrefHeight(squareSize);

        this.checkBoxNode.setCursor(Cursor.HAND);

        Label label = new Label();

        label.setPrefWidth(width - (squareSize * 2 + padding * 8));
        label.setPrefHeight(squareSize);

        double fontSize = ScreenStructurationInfo.getAdjustedFontSize(50, label.getPrefWidth(), label.getPrefHeight());
        Font font = Font.font("Verdana", FontWeight.BOLD, fontSize);

        label.setFont(font);
        label.setText(name);

        imageView.setLayoutX(padding);
        imageView.setLayoutY(padding);

        label.setLayoutX(padding * 4 + imageView.getFitWidth());
        label.setLayoutY(padding);

        this.checkBoxNode.setLayoutX(padding * 7 + label.getPrefWidth() + imageView.getFitWidth());
        this.checkBoxNode.setLayoutY(padding);

        this.getChildren().addAll(imageView, label, this.checkBoxNode);
    }

    public BetterCheckBox getCheckBoxNode()
    {
        return checkBoxNode;
    }
}