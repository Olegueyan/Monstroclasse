package net.olegueyan.monstroclasse.node.partial;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import net.olegueyan.monstroclasse.node.FixedNodeCreatorUtils;

import java.util.LinkedList;
import java.util.function.Consumer;

public class VerticalScrollPane extends ScrollPane
{
    private Consumer<VerticalScrollPane> verticalScrollPaneConsumer;

    private final VBox contentPane;

    public VerticalScrollPane(double width, double height, double spacing)
    {
        this.contentPane = new VBox();
        this.contentPane.setSpacing(spacing);

        this.setPadding(new Insets(0));

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
        if (this.verticalScrollPaneConsumer != null) this.verticalScrollPaneConsumer.accept(this);
    }

    public void removeNode(Node node)
    {
        this.contentPane.getChildren().remove(node);
        if (this.verticalScrollPaneConsumer != null) this.verticalScrollPaneConsumer.accept(this);
    }

    public void clear()
    {
        this.contentPane.getChildren().clear();
        if (this.verticalScrollPaneConsumer != null) this.verticalScrollPaneConsumer.accept(this);
    }

    // Méthode pour ajouter un nœud à une position spécifique
    public void addNodeAt(Node node, int index) {
        contentPane.getChildren().add(index, node);
        if (this.verticalScrollPaneConsumer != null) this.verticalScrollPaneConsumer.accept(this);
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

    public void setOnChange(Consumer<VerticalScrollPane> verticalScrollPaneConsumer)
    {
        this.verticalScrollPaneConsumer = verticalScrollPaneConsumer;
    }

    public int getContentSize()
    {
        return this.contentPane.getChildren().size();
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

    public <T> LinkedList<T> getOrderedContent(Class<T> tClass)
    {
        LinkedList<T> orderedContent = new LinkedList<>();
        contentPane.getChildren().forEach(node -> {
            try
            {
                orderedContent.add(tClass.cast(node));
            }
            catch (ClassCastException e)
            {
                throw new RuntimeException(e);
            }
        });
        return orderedContent;
    }
}