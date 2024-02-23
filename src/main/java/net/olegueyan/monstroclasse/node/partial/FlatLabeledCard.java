package net.olegueyan.monstroclasse.node.partial;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.FontWeight;
import net.olegueyan.monstroclasse.node.FixedNodeCreatorUtils;

public class FlatLabeledCard extends Pane
{
    public FlatLabeledCard(double width, double height, String text)
    {
        FixedNodeCreatorUtils.fixeRegionNode(this, width, height);

        Label titleLabel = FixedNodeCreatorUtils.prepareLabel(width, height, 21, FontWeight.BOLD);
        titleLabel.setText(text);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setPadding(new Insets(5));

        this.getChildren().add(titleLabel);
    }
}