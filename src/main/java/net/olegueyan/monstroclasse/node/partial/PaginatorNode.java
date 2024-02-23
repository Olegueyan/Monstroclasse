package net.olegueyan.monstroclasse.node.partial;

import com.speedment.common.tuple.Tuple2;
import com.speedment.common.tuple.Tuples;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import net.olegueyan.monstroclasse.function.DualCounter;
import net.olegueyan.monstroclasse.function.NodeFunctionCreator;
import net.olegueyan.monstroclasse.function.BiFunction;
import net.olegueyan.monstroclasse.component.PaginatorComponent;
import net.olegueyan.monstroclasse.node.FixedNodeCreatorUtils;

import java.util.ArrayList;
import java.util.function.Consumer;

public class PaginatorNode<T> extends Pane
{
    private final PaginatorComponent<T> paginatorComponent;

    private final double nodeWidth;
    private final double nodeHeight;

    private final BiFunction<T, String, Boolean> filterFunction;

    private PaginatorNode(ArrayList<T> toPaginate, NodeFunctionCreator<T> nodeCreator, Tuple2<Double, Double> dimension, Tuple2<Integer, Integer> gridDisposition, BiFunction<T, String, Boolean> filterFunction, Consumer<Node> whenFiltered, PaginatorNodeListener onPageChange)
    {
        int perPage = gridDisposition.get0() * gridDisposition.get1();

        final double width = dimension.get0();
        final double height = dimension.get1();

        FixedNodeCreatorUtils.fixeRegionNode(this, width, height);

        this.nodeWidth = width / gridDisposition.get1();
        this.nodeHeight = height / gridDisposition.get0();

        this.filterFunction = filterFunction;

        this.paginatorComponent = new PaginatorComponent<>(toPaginate, perPage, (index, page, filtered, hasPrevious, hasNext) ->
        {
            if (onPageChange != null)
            {
                onPageChange.onPageChange(index, hasPrevious, hasNext);
            }

            DualCounter dualCounter = new DualCounter(gridDisposition.get0(), gridDisposition.get1());

            getChildren().clear();

            page.forEach(t ->
            {
                Node node = nodeCreator.create(t, nodeWidth, nodeHeight);

                node.setLayoutX(dualCounter.current().get1() * nodeWidth);
                node.setLayoutY(dualCounter.current().get0() * nodeHeight);

                if (filtered)
                {
                    whenFiltered.accept(node);
                }

                getChildren().add(node);

                dualCounter.increment();
            });
        });
    }

    public PaginatorComponent<T> getPaginator()
    {
        return this.paginatorComponent;
    }

    public void applyFilter(String filter)
    {
        this.paginatorComponent.filter(filter, this.filterFunction);
        this.paginatorComponent.firstPage();
    }

    public void removeFilter()
    {
        this.paginatorComponent.removeFilter();
        this.paginatorComponent.firstPage();
    }

    public interface PaginatorNodeListener
    {
        void onPageChange(int index, boolean hasPrevious, boolean hasNext);
    }

    // ***************************************************************
    // EcritoireSectionNode - Builder
    // ***************************************************************

    /**
     * Builder that can easily make PaginatorNode
     */
    public static class Builder<T>
    {
        // ***************************************************************
        // PaginatorNode - Builder - Attributes
        // ***************************************************************

        private ArrayList<T> toPaginate = null;

        private NodeFunctionCreator<T> nodeCreator = null;

        private Tuple2<Double, Double> dimension = null;

        private Tuple2<Integer, Integer> gridDisposition = null;

        private BiFunction<T, String, Boolean> filterFunction = null;

        private Consumer<Node> whenFiltered = null;

        private PaginatorNodeListener paginatorNodeListener = null;

        // ***************************************************************
        // PaginatorNode - Builder - Attributes
        // ***************************************************************

        // ***************************************************************
        // PaginatorNode - Builder - Methods
        // ***************************************************************

        public Builder<T> withToPaginate(ArrayList<T> toPaginate)
        {
            this.toPaginate = toPaginate;
            return this;
        }

        public Builder<T> withNodeCreator(NodeFunctionCreator<T> nodeCreator)
        {
            this.nodeCreator = nodeCreator;
            return this;
        }

        public Builder<T> withDimension(double width, double height)
        {
            this.dimension = Tuples.of(width, height);
            return this;
        }

        public Builder<T> withGridDisposition(int row, int col)
        {
            this.gridDisposition = Tuples.of(row, col);
            return this;
        }

        public Builder<T> withFilterFunction(BiFunction<T, String, Boolean> filterFunction)
        {
            this.filterFunction = filterFunction;
            return this;
        }

        public Builder<T> whenFiltered(Consumer<Node> whenFiltered)
        {
            this.whenFiltered = whenFiltered;
            return this;
        }

        public Builder<T> onPageChange(PaginatorNodeListener paginatorNodeListener)
        {
            this.paginatorNodeListener = paginatorNodeListener;
            return this;
        }

        // ***************************************************************
        // EcritoireSectionNode - Builder - Methods
        // ***************************************************************

        // ***************************************************************
        // EcritoireSectionNode - Builder - Build Method
        // ***************************************************************

        public PaginatorNode<T> build()
        {
            if (this.toPaginate == null)
            {
                throw new RuntimeException("Cannot build |-> ToPaginate is missing !");
            }

            if (this.nodeCreator == null)
            {
                throw new RuntimeException("Cannot build |-> NodeCreator is missing !");
            }

            if (this.dimension == null)
            {
                throw new RuntimeException("Cannot build |-> Dimensions are missing !");
            }

            if (this.gridDisposition == null)
            {
                throw new RuntimeException("Cannot build |-> GridDisposition is missing !");
            }

//            if (this.filterFunction == null)
//            {
//                throw new RuntimeException("Cannot build |-> FilterFunction is missing !");
//            }
//
//            if (this.whenFiltered == null)
//            {
//                throw new RuntimeException("Cannot build |-> WhenFiltered is missing !");
//            }
//
//            if (this.paginatorNodeListener == null)
//            {
//                throw new RuntimeException("Cannot build |-> PaginatorNodeListener is missing !");
//            }

            return new PaginatorNode<>(this.toPaginate, this.nodeCreator, this.dimension, this.gridDisposition, this.filterFunction, this.whenFiltered, this.paginatorNodeListener);
        }

        // ***************************************************************
        // PaginatorNode - Builder - Build Method
        // ***************************************************************
    }

    // ***************************************************************
    // PaginatorNode - Builder
    // ***************************************************************
}