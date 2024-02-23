package net.olegueyan.monstroclasse.node.partial;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import net.olegueyan.monstroclasse.common.AppTheme;
import net.olegueyan.monstroclasse.common.Constants;
import net.olegueyan.monstroclasse.node.FixedNodeCreatorUtils;
import net.olegueyan.monstroclasse.screen.ScreenStructurationInfo;

public class PaginationCardNode<T> extends Pane
{
    private T data;

    private final Text text;

    private final ButtonImage deleteBtn;
    private final ButtonImage modifyBtn;

    public PaginationCardNode(double width, double height, String title, ButtonImage modifyBtn, ButtonImage deleteBtn, T t)
    {
        this.setPrefWidth(width);
        this.setPrefHeight(height);

        double padding = 5;

        double buttonSquareSize;

        if (width > height) {
            buttonSquareSize = 0.7 * height - padding;
        } else {
            buttonSquareSize = 0.7 * width - padding;
        }

        FixedNodeCreatorUtils.forcePrefDimension(this);

        Pane subPane = new Pane();

        FixedNodeCreatorUtils.forcePrefDimension(subPane);

        subPane.setPrefWidth(width - padding);
        subPane.setPrefHeight(height - padding);

        ScreenStructurationInfo.processCssRounded(subPane, AppTheme.MONSTROCLASSE_COLOR_HEX, 6);

        this.getChildren().add(subPane);

        /* Text Part */

        Pane textPane = new Pane();

        textPane.setPrefWidth(subPane.getPrefWidth() - padding * 3 - buttonSquareSize * 2);
        textPane.setPrefHeight(subPane.getPrefHeight());

        FixedNodeCreatorUtils.forcePrefDimension(textPane);

        subPane.getChildren().add(textPane);

        Font font = Font.font("Verdana", FontWeight.BOLD, ScreenStructurationInfo.getAdjustedFontSize(Constants.MAX_WORD_SIZE, textPane.getPrefWidth(), textPane.getPrefHeight()));

        Text text = new Text();
        text.setText(title);

        this.text = text;

        text.setFont(font);

        TextFlow textFlow = new TextFlow(text);

        textPane.getChildren().add(textFlow);

        // TextFlow adjust coordinates //

        textFlow.setLayoutX((textPane.getPrefWidth() - textFlow.getBoundsInParent().getWidth()) / 2);
        textFlow.setLayoutY((textPane.getPrefHeight() - textFlow.getBoundsInParent().getHeight()) / 2);

        // TextFlow adjust coordinates //

        /* Delete Button Part */

        deleteBtn.setPrefWidth(buttonSquareSize);
        deleteBtn.setPrefHeight(buttonSquareSize);

        deleteBtn.setUserData(t);

        subPane.getChildren().add(deleteBtn);

        // ------- Screen Structuration DELETE_BTN ------- //
        ScreenStructurationInfo.processApplyOffsetXForRegion(deleteBtn, padding);
        ScreenStructurationInfo.processCenterVertically(deleteBtn);
        // ----------------------------------------------- //

        /* Modify Button Part */

        modifyBtn.setPrefWidth(buttonSquareSize);
        modifyBtn.setPrefHeight(buttonSquareSize);

        modifyBtn.setUserData(t);

        subPane.getChildren().add(modifyBtn);

        ScreenStructurationInfo.processApplyOffsetXForRegion(modifyBtn, deleteBtn.getPrefWidth() + padding * 2);
        ScreenStructurationInfo.processCenterVertically(modifyBtn);

        ScreenStructurationInfo.processCenterVertically(subPane);
        ScreenStructurationInfo.processCenterHorizontally(subPane);

        this.deleteBtn = deleteBtn;
        this.modifyBtn = modifyBtn;
    }

    public void setOnClickDeleteBtn(EventHandler<ActionEvent> eventEventHandler)
    {
        this.deleteBtn.setOnAction(eventEventHandler);
    }

    public void setOnClickModifyBtn(EventHandler<ActionEvent> eventEventHandler)
    {
        this.modifyBtn.setOnAction(eventEventHandler);
    }

    public Text getText()
    {
        return this.text;
    }

    public T getData()
    {
        return this.data;
    }
}