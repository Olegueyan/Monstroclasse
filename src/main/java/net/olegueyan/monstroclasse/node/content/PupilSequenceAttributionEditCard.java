package net.olegueyan.monstroclasse.node.content;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import net.olegueyan.monstroclasse.common.Constants;
import net.olegueyan.monstroclasse.entity.Pupil;
import net.olegueyan.monstroclasse.json.ecrilu.EcriluSequenceJson;
import net.olegueyan.monstroclasse.node.partial.BetterCheckBox;
import net.olegueyan.monstroclasse.screen.ScreenStructurationInfo;
import net.olegueyan.monstroclasse.node.FixedNodeCreatorUtils;
import net.olegueyan.monstroclasse.utils.ImageUtils;

public class PupilSequenceAttributionEditCard extends Pane
{
    private final Pupil pupil;

    private final EcriluSequenceJson sequence;
    private boolean hasAccess;

    private final BetterCheckBox checkBoxModuleEdit;

    public PupilSequenceAttributionEditCard(Pupil pupil, double width, double height, EcriluSequenceJson sequence, boolean defaultState)
    {
        this.pupil = pupil;
        this.sequence = sequence;

        this.hasAccess = defaultState;

        // Creation of card

        FixedNodeCreatorUtils.fixeRegionNode(this, width, height);

        double insideMargin = 6;
        double squareSize = height - insideMargin;

        this.checkBoxModuleEdit = new BetterCheckBox(Constants.CHECK_IMAGE, Constants.CLOSE_IMAGE, this.hasAccess);

        /* Avatar Part */

        Image image = ImageUtils.byteArrayToImage(this.pupil.getAvatar().getData());
        ImageView avatar = new ImageView(image);

        avatar.setFitWidth(squareSize);
        avatar.setFitHeight(squareSize);

        /* Label Part */

        Label label = new Label();
        label.setText(pupil.getName());

        double widthLabel = this.getPrefWidth() - (avatar.getFitWidth() + avatar.getX());
        double heightLabel = this.getPrefHeight() - insideMargin * 3;

        label.setPrefWidth(widthLabel);
        label.setPrefHeight(heightLabel);
        label.setFont(Font.font("Verdana", ScreenStructurationInfo.getAdjustedFontSize(Constants.STUDENT_MAX_LENGTH_NAME, widthLabel, heightLabel)));

        label.setLayoutX(avatar.getLayoutX() + avatar.getFitWidth() + insideMargin);

        /* CheckBox Part */

        this.checkBoxModuleEdit.setPrefWidth(squareSize);
        this.checkBoxModuleEdit.setPrefHeight(squareSize);

        this.checkBoxModuleEdit.setCursor(Cursor.HAND);

        /* Add Children */

        this.getChildren().addAll(avatar, label, this.checkBoxModuleEdit);

        /* Screen Structuration */

        avatar.setLayoutX(10);
        ScreenStructurationInfo.processCenterVertically(avatar);

        label.setLayoutX(avatar.getFitWidth() + 10 + 20);
        ScreenStructurationInfo.processCenterVertically(label);

        ScreenStructurationInfo.processApplyOffsetXForRegion(this.checkBoxModuleEdit, 10);
        ScreenStructurationInfo.processCenterVertically(this.checkBoxModuleEdit);
    }

    public Pupil getPupil()
    {
        return pupil;
    }

    public EcriluSequenceJson getSequence()
    {
        return this.sequence;
    }

    public BetterCheckBox getCheckBoxModuleEdit()
    {
        return checkBoxModuleEdit;
    }
}