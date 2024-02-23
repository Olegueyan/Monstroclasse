package net.olegueyan.monstroclasse.node.content.ecrilu;

import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import net.olegueyan.monstroclasse.common.AppTheme;
import net.olegueyan.monstroclasse.common.Constants;
import net.olegueyan.monstroclasse.json.ecrilu.EcriluExerciseJson;
import net.olegueyan.monstroclasse.node.FixedNodeCreatorUtils;
import net.olegueyan.monstroclasse.node.partial.ButtonImage;
import net.olegueyan.monstroclasse.screen.ScreenStructurationInfo;

public class ExerciseCardSingleActionNode extends Pane
{
    private final Text text;
    private final EcriluExerciseJson ecriluExerciseJson;

    private final ButtonImage actionBtn;

    public ExerciseCardSingleActionNode(double width, double height, ButtonImage actionBtn, EcriluExerciseJson exercise)
    {
        this.setPrefWidth(width);
        this.setPrefHeight(height);

        double padding = 5;

        double buttonSquareSize;

        if (width > height)
        {
            buttonSquareSize = 0.7 * height - padding;
        }
        else
        {
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

        textPane.setPrefWidth(subPane.getPrefWidth() - padding * 2 - buttonSquareSize);
        textPane.setPrefHeight(subPane.getPrefHeight());

        FixedNodeCreatorUtils.forcePrefDimension(textPane);

        subPane.getChildren().add(textPane);

        Font font = Font.font("Verdana", FontWeight.BOLD, ScreenStructurationInfo.getAdjustedFontSize(Constants.MAX_WORD_SIZE, textPane.getPrefWidth(), textPane.getPrefHeight()));

        Text text = new Text();

        this.text = text;

        text.setText(exercise.id);
        text.setFont(font);

        TextFlow textFlow = new TextFlow(text);

        textPane.getChildren().add(textFlow);

        // TextFlow adjust coordinates //

        textFlow.setLayoutX((textPane.getPrefWidth() - textFlow.getBoundsInParent().getWidth()) / 2);
        textFlow.setLayoutY((textPane.getPrefHeight() - textFlow.getBoundsInParent().getHeight()) / 2);

        // TextFlow adjust coordinates //

        /* Action Button Part */

        actionBtn.setPrefWidth(buttonSquareSize);
        actionBtn.setPrefHeight(buttonSquareSize);

        actionBtn.setUserData(exercise);

        subPane.getChildren().add(actionBtn);

        // ------- Screen Structuration ACTION_BTN ------- //
        ScreenStructurationInfo.processApplyOffsetXForRegion(actionBtn, padding);
        ScreenStructurationInfo.processCenterVertically(actionBtn);
        // ----------------------------------------------- //

        ScreenStructurationInfo.processCenterVertically(subPane);
        ScreenStructurationInfo.processCenterHorizontally(subPane);

        this.ecriluExerciseJson = exercise;

        this.actionBtn = actionBtn;
    }

    public ExerciseCardSingleActionNode(double width, double height, ButtonImage actionBtn, ButtonImage upBtn, ButtonImage downBtn, EcriluExerciseJson exercise)
    {
        this.setPrefWidth(width);
        this.setPrefHeight(height);

        double padding = 5;

        double buttonSquareSize;

        if (width > height)
        {
            buttonSquareSize = 0.7 * height - padding;
        }
        else
        {
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

        textPane.setPrefWidth(subPane.getPrefWidth() - padding * 4 - buttonSquareSize * 3);
        textPane.setPrefHeight(subPane.getPrefHeight());

        FixedNodeCreatorUtils.forcePrefDimension(textPane);

        subPane.getChildren().add(textPane);

        Font font = Font.font("Verdana", FontWeight.BOLD, ScreenStructurationInfo.getAdjustedFontSize(Constants.MAX_WORD_SIZE, textPane.getPrefWidth(), textPane.getPrefHeight()));

        Text text = new Text();

        this.text = text;

        text.setText(exercise.id);
        text.setFont(font);

        TextFlow textFlow = new TextFlow(text);

        textPane.getChildren().add(textFlow);

        // TextFlow adjust coordinates //

        textFlow.setLayoutX((textPane.getPrefWidth() - textFlow.getBoundsInParent().getWidth()) / 2);
        textFlow.setLayoutY((textPane.getPrefHeight() - textFlow.getBoundsInParent().getHeight()) / 2);

        // TextFlow adjust coordinates //

        /* Action Button Part */

        actionBtn.setPrefWidth(buttonSquareSize);
        actionBtn.setPrefHeight(buttonSquareSize);

        actionBtn.setUserData(exercise);

        subPane.getChildren().add(actionBtn);

        // ------- Screen Structuration ACTION_BTN ------- //
        ScreenStructurationInfo.processApplyOffsetXForRegion(actionBtn, padding);
        ScreenStructurationInfo.processCenterVertically(actionBtn);
        // ----------------------------------------------- //

        /* Down Button Part */

        downBtn.setPrefWidth(buttonSquareSize);
        downBtn.setPrefHeight(buttonSquareSize);

        subPane.getChildren().add(downBtn);

        // ------- Screen Structuration DOWN_BTN ------- //
        ScreenStructurationInfo.processApplyOffsetXForRegion(downBtn, padding * 2 + buttonSquareSize);
        ScreenStructurationInfo.processCenterVertically(downBtn);
        // ----------------------------------------------- //

        /* Up Button Part */

        upBtn.setPrefWidth(buttonSquareSize);
        upBtn.setPrefHeight(buttonSquareSize);

        subPane.getChildren().add(upBtn);

        // ------- Screen Structuration UP_BTN ------- //
        ScreenStructurationInfo.processApplyOffsetXForRegion(upBtn, padding * 3 + buttonSquareSize * 2);
        ScreenStructurationInfo.processCenterVertically(upBtn);
        // ----------------------------------------------- //

        ScreenStructurationInfo.processCenterVertically(subPane);
        ScreenStructurationInfo.processCenterHorizontally(subPane);

        this.ecriluExerciseJson = exercise;

        this.actionBtn = actionBtn;
    }

    public Text getText()
    {
        return this.text;
    }

    public EcriluExerciseJson getEcriluExerciseJson()
    {
        return this.ecriluExerciseJson;
    }

    public void simulateClick()
    {
        this.actionBtn.fire();
    }
}