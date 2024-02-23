package net.olegueyan.monstroclasse.function;

import javafx.scene.Node;

/**
 * It is a specific expression to make PaginatorNode work
 * <br>
 * Expression to create a node thanks to a typed element and dimension
 * @param <T> type of given element
 */
public interface NodeFunctionCreator<T>
{
    Node create(T obj, double width, double height);
}