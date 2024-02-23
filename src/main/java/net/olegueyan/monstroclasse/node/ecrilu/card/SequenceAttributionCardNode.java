package net.olegueyan.monstroclasse.node.ecrilu.card;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import net.olegueyan.monstroclasse.common.Constants;
import net.olegueyan.monstroclasse.json.ecrilu.EcriluSequenceJson;
import net.olegueyan.monstroclasse.node.FixedNodeCreatorUtils;
import net.olegueyan.monstroclasse.screen.ScreenStructurationInfo;

import java.util.function.Consumer;

public class SequenceAttributionCardNode extends Pane
{
    private final Text text;

    public SequenceAttributionCardNode(double width, double height, EcriluSequenceJson sequence, Consumer<MouseEvent> onClick, String color)
    {
        this.setPrefWidth(width);
        this.setPrefHeight(height);

        double padding = 5;

        FixedNodeCreatorUtils.forcePrefDimension(this);

        Pane subPane = new Pane();

        FixedNodeCreatorUtils.forcePrefDimension(subPane);

        subPane.setPrefWidth(width - padding);
        subPane.setPrefHeight(height - padding);

        ScreenStructurationInfo.processCssRounded(subPane, color, 6);

        this.getChildren().add(subPane);

        /* Text Part */

        Pane textPane = new Pane();

        textPane.setPrefWidth(subPane.getPrefWidth() - padding * 2);
        textPane.setPrefHeight(subPane.getPrefHeight());

        FixedNodeCreatorUtils.forcePrefDimension(textPane);

        subPane.getChildren().add(textPane);

        Font font = Font.font("Verdana", FontWeight.BOLD, ScreenStructurationInfo.getAdjustedFontSize(Constants.MAX_WORD_SIZE, textPane.getPrefWidth(), textPane.getPrefHeight()));

        Text text = new Text();

        this.text = text;

        text.setText(sequence.id);
        text.setFont(font);

        TextFlow textFlow = new TextFlow(text);

        textPane.getChildren().add(textFlow);

        // TextFlow adjust coordinates //

        textFlow.setLayoutX((textPane.getPrefWidth() - textFlow.getBoundsInParent().getWidth()) / 2);
        textFlow.setLayoutY((textPane.getPrefHeight() - textFlow.getBoundsInParent().getHeight()) / 2);

        // TextFlow adjust coordinates //

        ScreenStructurationInfo.processCenterVertically(subPane);
        ScreenStructurationInfo.processCenterHorizontally(subPane);

        this.setOnMouseClicked(onClick::accept);

        this.getStyleClass().addAll("node-flat", "-handed");
        this.applyCss();
    }

    public Text getText()
    {
        return this.text;
    }
}