package net.olegueyan.monstroclasse.node.content;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import net.olegueyan.monstroclasse.common.AppTheme;
import net.olegueyan.monstroclasse.common.Constants;
import net.olegueyan.monstroclasse.entity.Pupil;
import net.olegueyan.monstroclasse.node.partial.ButtonImage;
import net.olegueyan.monstroclasse.screen.ScreenStructurationInfo;
import net.olegueyan.monstroclasse.node.FixedNodeCreatorUtils;

public class PupilCardAdminPupilManager extends Pane
{
    // ***************************************************************
    // StudentCard - ATTRIBUTES
    // ***************************************************************

    private final ImageView avatar;

    private final ButtonImage deleteBtn;

    private final ButtonImage modifyBtn;

    // ------- Pupil of this card ------- //
    private final Pupil pupil;
    // ---------------------------------- //

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // StudentCard - CONSTANTS
    // ***************************************************************

    // ------- Padding of a card ------- //
    private static final double PADDING = 6;
    // --------------------------------- //

    // ***************************************************************
    // END
    // ***************************************************************

    public PupilCardAdminPupilManager(ImageView avatar, ButtonImage modifyBtn, ButtonImage deleteBtn, Pupil pupil, double width, double height)
    {
        FixedNodeCreatorUtils.fixeRegionNode(this, width, height);
        
        double squareSize = height - PADDING;

        Pane subPane = new Pane();

        FixedNodeCreatorUtils.fixeRegionNode(subPane, width - (squareSize * 2 + PADDING * 2) - 20, squareSize);

        ScreenStructurationInfo.processCssRounded(subPane, AppTheme.MONSTROCLASSE_COLOR_HEX, 10);

        /* Label Part One */

        Label label = new Label();
        label.setText(pupil.getName());

        subPane.getChildren().addAll(avatar, label);
        this.getChildren().addAll(subPane, modifyBtn, deleteBtn);

        subPane.setLayoutX(10);
        ScreenStructurationInfo.processCenterVertically(subPane);

        /* Avatar Part */

        avatar.setFitWidth(squareSize - PADDING);
        avatar.setFitHeight(squareSize - PADDING);

        avatar.setLayoutX(PADDING / 2);
        avatar.setLayoutY(PADDING / 2);

        ScreenStructurationInfo.processCenterVertically(avatar);

        /* Label Part Two */

        double widthLabel = subPane.getPrefWidth() - (PADDING * 2 + avatar.getFitWidth() + avatar.getX());
        double heightLabel = subPane.getPrefHeight() - PADDING * 4;

        label.setPrefWidth(widthLabel);
        label.setPrefHeight(heightLabel);
        label.setFont(Font.font("Verdana", ScreenStructurationInfo.getAdjustedFontSize(Constants.STUDENT_MAX_LENGTH_NAME, widthLabel, heightLabel)));

        label.setLayoutX(avatar.getLayoutX() + avatar.getFitWidth() + PADDING);
        ScreenStructurationInfo.processCenterVertically(label);

        /* Delete Button Part */

        deleteBtn.setPrefWidth(squareSize);
        deleteBtn.setPrefHeight(squareSize);

        ScreenStructurationInfo.processApplyOffsetXForRegion(deleteBtn, 5);
        ScreenStructurationInfo.processCenterVertically(deleteBtn);

        deleteBtn.setUserData(pupil);

        /* Modify Button Part */

        modifyBtn.setPrefWidth(squareSize);
        modifyBtn.setPrefHeight(squareSize);

        ScreenStructurationInfo.processApplyOffsetXForRegion(modifyBtn, deleteBtn.getPrefWidth() + PADDING + 10);
        ScreenStructurationInfo.processCenterVertically(modifyBtn);

        modifyBtn.setUserData(pupil);

        this.avatar = avatar;
        this.deleteBtn = deleteBtn;
        this.modifyBtn = modifyBtn;
        this.pupil = pupil;
    }

    public void setOnClickDeleteBtn(EventHandler<ActionEvent> eventEventHandler)
    {
        this.deleteBtn.setOnAction(eventEventHandler);
    }

    public void setOnClickModifyBtn(EventHandler<ActionEvent> eventEventHandler)
    {
        this.modifyBtn.setOnAction(eventEventHandler);
    }

    // ***************************************************************
    // StudentCard - GETTERS
    // ***************************************************************

    /**
     * Get avatar
     * @return ImageView
     */
    public ImageView getAvatar()
    {
        return this.avatar;
    }

    /**
     * Get delete button
     * @return ButtonImage
     */
    public ButtonImage getDeleteBtn()
    {
        return this.deleteBtn;
    }

    /**
     * Get modify button
     * @return ButtonImage
     */
    public ButtonImage getModifyBtn()
    {
        return this.modifyBtn;
    }

    /**
     * Get pupil
     * @return Pupil
     */
    public Pupil getPupil()
    {
        return this.pupil;
    }

    // ***************************************************************
    // END
    // ***************************************************************
}