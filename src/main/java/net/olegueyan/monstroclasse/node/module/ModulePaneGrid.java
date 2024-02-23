package net.olegueyan.monstroclasse.node.module;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import net.olegueyan.monstroclasse.function.DualCounter;
import net.olegueyan.monstroclasse.module.IsModule;
import net.olegueyan.monstroclasse.node.FixedNodeCreatorUtils;
import net.olegueyan.monstroclasse.screen.ScreenStructurationInfo;

import java.util.function.Consumer;

/**
 * Linked to a parent
 */
public class ModulePaneGrid extends Pane
{
    private static final double margin = 5;
    private static final double labelHeightPercent = 0.25f;

    private final double moduleCardWidth;
    private final double moduleCardHeight;

    private final DualCounter dualCounter;

    public ModulePaneGrid(Pane parent, int rows, int cols)
    {
        this.moduleCardWidth = parent.getPrefWidth() / cols;
        this.moduleCardHeight = parent.getPrefHeight() / rows;
        parent.getChildren().add(this);
        this.dualCounter = new DualCounter(rows, cols);
    }

    public void addModuleCard(IsModule module, Consumer<MouseEvent> onClick)
    {
        Pane pane = new Pane();

        FixedNodeCreatorUtils.fixeRegionNode(pane, this.moduleCardWidth, this.moduleCardHeight);

        pane.setLayoutX(this.dualCounter.current().get1() * this.moduleCardWidth);
        pane.setLayoutY(this.dualCounter.current().get0() * this.moduleCardHeight);

        ImageView view = new ImageView(module.avatar());
        Label label = new Label(module.name());

        pane.getChildren().addAll(view, label);

        double squareSize = Math.min(
                this.moduleCardWidth - 2 * margin,
                (this.moduleCardHeight - (1 + labelHeightPercent) * margin) * (1 - labelHeightPercent)
        );

        view.setFitWidth(squareSize);
        view.setFitHeight(squareSize);

        ScreenStructurationInfo.processCenterHorizontally(view);
        view.setLayoutY(margin);

        double labelWidth = this.moduleCardWidth - margin * 2;
        double labelHeight = this.moduleCardHeight * labelHeightPercent;

        double fontSize = ScreenStructurationInfo.getAdjustedFontSize(22, labelWidth, labelHeight);
        label.setFont(Font.font("Verdana", FontWeight.BOLD, fontSize));

        label.setPrefWidth(labelWidth);
        label.setPrefHeight(labelHeight);

        ScreenStructurationInfo.processCenterHorizontally(label);
        ScreenStructurationInfo.processApplyOffsetYForRegion(label, margin);

        pane.setFocusTraversable(false);
        pane.setCursor(Cursor.HAND);

        label.setStyle("-fx-alignment: center;");
        label.applyCss();

        pane.getStyleClass().addAll("node-flat", "-transparent");
        pane.applyCss();

        pane.setOnMouseClicked(onClick::accept);

        this.getChildren().add(pane);

        this.dualCounter.increment();
    }
}