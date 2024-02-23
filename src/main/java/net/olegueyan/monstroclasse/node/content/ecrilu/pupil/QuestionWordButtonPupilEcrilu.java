package net.olegueyan.monstroclasse.node.content.ecrilu.pupil;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import net.olegueyan.monstroclasse.json.ecrilu.EcriluWordJson;
import net.olegueyan.monstroclasse.node.FixedNodeCreatorUtils;

import java.util.function.Consumer;

public class QuestionWordButtonPupilEcrilu extends Pane
{
    private final Image notDone;
    private final Image inDoing;
    private final Image done;

    private final EcriluWordJson ecriluWordJson;
    private final Consumer<QuestionWordButtonPupilEcrilu> onClickAction;
    private final ImageView buttonImageView;

    public QuestionWordButtonPupilEcrilu(Image notDone, Image inDoing, Image done, EcriluWordJson ecriluWordJson, Consumer<QuestionWordButtonPupilEcrilu> onClick)
    {
        this.notDone = notDone;
        this.inDoing = inDoing;
        this.done = done;
        this.ecriluWordJson = ecriluWordJson;
        this.onClickAction = onClick;

        buttonImageView = new ImageView(notDone);

        buttonImageView.setOnMouseClicked(this::handleMouseClick);

        getChildren().add(buttonImageView);
        FixedNodeCreatorUtils.bindImageViewDimensionToRegion(buttonImageView, this);
    }

    private void handleMouseClick(MouseEvent event)
    {
        onClickAction.accept(this);
    }

    public void setNotDone()
    {
        this.buttonImageView.setImage(notDone);
    }

    public void setInDoing()
    {
        this.buttonImageView.setImage(inDoing);
    }

    public void setDone()
    {
        this.buttonImageView.setImage(done);
    }

    public EcriluWordJson getEcriluWordJson()
    {
        return this.ecriluWordJson;
    }
}