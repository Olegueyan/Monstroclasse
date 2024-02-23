package net.olegueyan.monstroclasse.node.content;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import net.olegueyan.monstroclasse.common.Constants;
import net.olegueyan.monstroclasse.entity.Module;
import net.olegueyan.monstroclasse.entity.Pupil;
import net.olegueyan.monstroclasse.module.IsModule;
import net.olegueyan.monstroclasse.module.ModuleRegisterBus;
import net.olegueyan.monstroclasse.node.partial.BetterCheckBox;
import net.olegueyan.monstroclasse.repository.ModuleRepository;
import net.olegueyan.monstroclasse.repository.PupilModuleAccessRepository;
import net.olegueyan.monstroclasse.screen.ScreenStructurationInfo;
import net.olegueyan.monstroclasse.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class PupilModuleAccessEditCard extends Pane
{
    private final Pupil pupil;

    private final Module module;
    private boolean hasAccess;

    private final BetterCheckBox checkBoxModuleEdit;

    private final boolean hasAlreadyAccess;

    public PupilModuleAccessEditCard(Pupil pupil, double width, double height, IsModule isModule)
    {
        // Initialization of an account

        this.pupil = pupil;
        this.module = ModuleRepository.read(isModule.reference());

        List<Module> modules = PupilModuleAccessRepository.readModules(this.pupil);
        ArrayList<IsModule> currentAccessOfPupil = ModuleRegisterBus.modulesAvailable(modules);

        this.hasAccess = currentAccessOfPupil.stream().anyMatch(m -> m.equals(isModule));
        this.hasAlreadyAccess = this.hasAccess;

        // Creation of card

        this.setPrefWidth(width);
        this.setPrefHeight(height);

        this.minWidthProperty().bind(this.prefWidthProperty());
        this.minHeightProperty().bind(this.prefHeightProperty());

        this.maxWidthProperty().bind(this.prefWidthProperty());
        this.maxHeightProperty().bind(this.prefHeightProperty());

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

    public BetterCheckBox getCheckBoxModuleEdit()
    {
        return checkBoxModuleEdit;
    }

    public void save()
    {
        if (this.checkBoxModuleEdit.isEnable() && !this.hasAlreadyAccess)
        {
            PupilModuleAccessRepository.create(this.pupil, this.module);
        }
        else
        {
            if (!this.checkBoxModuleEdit.isEnable() && this.hasAlreadyAccess)
            {
                PupilModuleAccessRepository.delete(this.pupil, this.module);
            }
        }
    }
}