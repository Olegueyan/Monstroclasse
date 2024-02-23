package net.olegueyan.monstroclasse.node.partial;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import net.olegueyan.monstroclasse.node.FixedNodeCreatorUtils;
import net.olegueyan.monstroclasse.utils.ImageUtils;

import java.util.function.Consumer;

public class HorizontalScrollPane extends ScrollPane
{
    private final HBox contentPane;

    public HorizontalScrollPane(double width, double height, double spacing)
    {
        this.contentPane = new HBox();
        this.contentPane.setSpacing(spacing);

        this.contentPane.setOnScroll(scrollEvent ->
        {
            if (scrollEvent.getDeltaX() != 0) scrollEvent.consume();
        });

        FixedNodeCreatorUtils.fixeRegionNode(this, width, height);

        this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        this.setFitToWidth(true);
        this.setFitToHeight(true);

        this.setContent(contentPane);

        this.setFocused(false);
        this.setFocusTraversable(false);

        this.setStyle("-fx-background: none;");
    }

    public void addNode(Node node)
    {
        this.contentPane.getChildren().add(node);
    }

    public void removeNode(Node node)
    {
        this.contentPane.getChildren().remove(node);
    }

    public void clear()
    {
        this.contentPane.getChildren().clear();
    }

    // Méthode pour ajouter un nœud à une position spécifique
    public void addNodeAt(Node node, int index) {
        contentPane.getChildren().add(index, node);
    }

    // Méthode pour déplacer un nœud vers le haut
    public void moveNodeUp(Node node) {
        int index = contentPane.getChildren().indexOf(node);
        if (index > 0) {
            contentPane.getChildren().remove(node);
            contentPane.getChildren().add(index - 1, node);
        }
    }

    // Méthode pour déplacer un nœud vers le bas
    public void moveNodeDown(Node node) {
        int index = contentPane.getChildren().indexOf(node);
        if (index < contentPane.getChildren().size() - 1) {
            contentPane.getChildren().remove(node);
            contentPane.getChildren().add(index + 1, node);
        }
    }

    public <T extends Node> void forEachContent(Class<T> tClass, Consumer<T> consumer)
    {
        this.contentPane.getChildren().forEach(node ->
        {
            try
            {
                consumer.accept(tClass.cast(node));
            }
            catch (ClassCastException e)
            {
                throw new RuntimeException(e);
            }
        });
    }
}