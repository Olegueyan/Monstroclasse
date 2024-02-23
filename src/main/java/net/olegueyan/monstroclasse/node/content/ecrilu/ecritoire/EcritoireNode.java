package net.olegueyan.monstroclasse.node.content.ecrilu.ecritoire;

import com.speedment.common.tuple.Tuple2;
import com.speedment.common.tuple.Tuples;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import net.olegueyan.monstroclasse.json.ecritoire.Ecritoire;
import net.olegueyan.monstroclasse.json.ecritoire.EcritoireColumn;
import net.olegueyan.monstroclasse.function.Selector;
import net.olegueyan.monstroclasse.node.FixedNodeCreatorUtils;
import net.olegueyan.monstroclasse.utils.MonstroclasseUtils;

import java.util.*;
import java.util.function.Consumer;

public class EcritoireNode extends Pane
{
    private final LinkedList<EcritoireSectionNode> linkedList = new LinkedList<>();

    private final Selector<Integer> pageSelector;

    private final Map<Integer, GridPane> gridPaneMap;

    private EcritoireNode(FastProps fastProps)
    {
        double widthThis = fastProps.dimension().get0();
        double heightThis = fastProps.dimension().get1();

        FixedNodeCreatorUtils.fixeRegionNode(this, widthThis, heightThis);

        Map<Integer, LinkedList<EcritoireColumn>> toLoad = new HashMap<>();

        if (fastProps.filterEcritoireColumnDisposition() == null)
        {
            int scolCounter = 0;

            toLoad.put(0, new LinkedList<>());

            for (EcritoireColumn ecritoireColumn : fastProps.ecritoire().writingTable)
            {
                if (scolCounter >= fastProps.tinyColPerPane())
                {
                    scolCounter = 0;
                    toLoad.put(toLoad.size(), new LinkedList<>());
                }

                toLoad.get(toLoad.size() - 1).add(ecritoireColumn);

                scolCounter += ecritoireColumn.specs.scol;
            }
        }
        else
        {
            Map<Integer, String[]> filterEcritoireColumnDisposition = fastProps.filterEcritoireColumnDisposition();

            for (var i = 0; i < filterEcritoireColumnDisposition.size(); i++)
            {
                toLoad.put(i, new LinkedList<>());
            }

            for (Map.Entry<Integer, String[]> entry : filterEcritoireColumnDisposition.entrySet())
            {
                fastProps.ecritoire().writingTable.stream()
                        .filter(ecritoireColumn ->
                        {
                            return MonstroclasseUtils.containsValue(entry.getValue(), String.join("", ecritoireColumn.top));
                        })
                        .forEach(ecritoireColumn -> toLoad.get(entry.getKey()).add(ecritoireColumn));
            }
        }

        int expectedPaneOnSinglePage = fastProps.panePerPage();

        int pages = Math.max(1, toLoad.size() / expectedPaneOnSinglePage);

        ArrayList<Integer> pageAsArray = new ArrayList<>();

        for (var p = 0; p < pages; p++) {pageAsArray.add(p);}
        this.pageSelector = new Selector<>(pageAsArray);

        this.gridPaneMap = new HashMap<>();

        int paneCounter = 0;

        for (var i = 0; i < pages; i++)
        {
            GridPane gridPane = new GridPane();

            FixedNodeCreatorUtils.fixeRegionNode(gridPane, widthThis, heightThis);

            gridPane.setHgap(fastProps.spacing());
            gridPane.setVgap(fastProps.spacing());

            for (var k = 0; k < expectedPaneOnSinglePage; k++)
            {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setPercentHeight(100.0 / expectedPaneOnSinglePage);
                gridPane.getRowConstraints().add(rowConstraints);
            }

            ColumnConstraints columnConstraintsGridPane = new ColumnConstraints();
            columnConstraintsGridPane.setPercentWidth(100.0);
            gridPane.getColumnConstraints().add(columnConstraintsGridPane);

            for (var j = 0; j < expectedPaneOnSinglePage; j++)
            {
                if (paneCounter >= toLoad.size()) break;

                LinkedList<EcritoireColumn> ecritoireColumns = toLoad.get(paneCounter);

                GridPane subPaneEcritoire = new GridPane();

                for (int colIndex = 0; colIndex < fastProps.tinyColPerPane(); colIndex++)
                {
                    ColumnConstraints columnConstraints = new ColumnConstraints();
                    columnConstraints.setPercentWidth(100.0 / fastProps.tinyColPerPane());
                    subPaneEcritoire.getColumnConstraints().add(columnConstraints);
                }

                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setPercentHeight(100.0);
                subPaneEcritoire.getRowConstraints().add(rowConstraints);

                fastProps.paneProcess().accept(subPaneEcritoire);

                gridPane.add(subPaneEcritoire, 0, j);

                int index = 0;

                for (EcritoireColumn ecritoireColumn : ecritoireColumns)
                {
                    EcritoireSectionNode node = new EcritoireSectionNode.Builder()
                            .withEcritoireBigColumn(ecritoireColumn)
                            .withToneOnColumn(fastProps.toneOnColumn())
                            .withEcritoireListener(fastProps.ecritoireListener())
                            .build();

                    this.linkedList.add(node);

                    GridPane.setColumnSpan(node, ecritoireColumn.specs.scol);

                    subPaneEcritoire.add(node, index, 0);

                    index += ecritoireColumn.specs.scol;
                }

                paneCounter++;
            }

            this.gridPaneMap.put(i, gridPane);
            this.getChildren().add(gridPane);
        }

        this.pageSelector.setOnChange(integerSelector ->
        {
            this.gridPaneMap.values().forEach(gridPane -> gridPane.setVisible(false));
            this.gridPaneMap.entrySet().stream().filter(integerGridPaneEntry -> Objects.equals(integerGridPaneEntry.getKey(), pageSelector.current())).forEach(integerGridPaneEntry -> integerGridPaneEntry.getValue().setVisible(true));
        });

        this.gridPaneMap.values().forEach(gridPane -> gridPane.setVisible(false));
        this.gridPaneMap.entrySet().stream().filter(integerGridPaneEntry -> Objects.equals(integerGridPaneEntry.getKey(), pageSelector.current())).forEach(integerGridPaneEntry -> integerGridPaneEntry.getValue().setVisible(true));
    }

    public void nextPage()
    {
        this.pageSelector.next();
    }

    public void previousPage()
    {
        this.pageSelector.previous();
    }

    public void bindSelector(Node left, Node right)
    {
        left.setVisible(this.pageSelector.hasPrevious());
        right.setVisible(this.pageSelector.hasNext());
        left.setOnMouseClicked(mouseEvent ->
        {
            this.previousPage();
            left.setVisible(this.pageSelector.hasPrevious());
            right.setVisible(this.pageSelector.hasNext());
        });
        right.setOnMouseClicked(mouseEvent ->
        {
            this.nextPage();
            left.setVisible(this.pageSelector.hasPrevious());
            right.setVisible(this.pageSelector.hasNext());
        });
    }


    public void applyForeach(Consumer<EcritoireSectionNode> consumer)
    {
        this.linkedList.forEach(consumer);
    }

    public static class Builder
    {
        // ***************************************************************
        // EcritoirePane - Builder - Attributes
        // ***************************************************************

        private Tuple2<Double, Double> dimension = null;

        private Ecritoire ecritoire;

        private int panePerPage = 2;

        private double spacing = 0;

        private Consumer<Pane> paneProcess;

        private int tinyColPerPane = 20;

        private EcritoireSectionNode.EcritoireListener ecritoireListener;

        private int toneOnColumn = 15;

        private Map<Integer, String[]> filterEcritoireColumnDisposition = null;

        // ***************************************************************
        // EcritoirePane - Builder - Attributes
        // ***************************************************************

        public Builder withDimension(double width, double height)
        {
            this.dimension = Tuples.of(width, height);

            return this;
        }

        public Builder withEcritoire(Ecritoire ecritoire)
        {
            this.ecritoire = ecritoire;

            return this;
        }

        public Builder withPanePerPage(int panePerPage)
        {
            this.panePerPage = panePerPage;

            return this;
        }

        public Builder withSpacing(double spacing)
        {
            this.spacing = spacing;

            return this;
        }

        public Builder withPaneProcess(Consumer<Pane> paneProcess)
        {
            this.paneProcess = paneProcess;

            return this;
        }

        public Builder withTinyColPerPane(int tinyColPerPane)
        {
            this.tinyColPerPane = tinyColPerPane;

            return this;
        }

        public Builder withEcritoireListener(EcritoireSectionNode.EcritoireListener ecritoireListener)
        {
            this.ecritoireListener = ecritoireListener;

            return this;
        }

        public Builder withToneOnColumn(int toneOnColumn)
        {
            this.toneOnColumn = toneOnColumn;

            return this;
        }

        public Builder withFilterEcritoireColumnDisposition(Map<Integer, String[]> filterEcritoireColumnDisposition)
        {
            this.filterEcritoireColumnDisposition = filterEcritoireColumnDisposition;

            return this;
        }

        public EcritoireNode build()
        {
            if (this.ecritoire == null)
            {
                throw new RuntimeException("Builder execption : Ecritoire is required !");
            }

            return new EcritoireNode(new FastProps()
            {
                @Override
                public Tuple2<Double, Double> dimension()
                {
                    return dimension;
                }

                @Override
                public Ecritoire ecritoire()
                {
                    return ecritoire;
                }

                @Override
                public int panePerPage()
                {
                    return panePerPage;
                }

                @Override
                public double spacing()
                {
                    return spacing;
                }

                @Override
                public Consumer<Pane> paneProcess()
                {
                    return paneProcess;
                }

                @Override
                public int tinyColPerPane()
                {
                    return tinyColPerPane;
                }

                @Override
                public EcritoireSectionNode.EcritoireListener ecritoireListener()
                {
                    return ecritoireListener;
                }

                @Override
                public int toneOnColumn()
                {
                    return toneOnColumn;
                }

                @Override
                public Map<Integer, String[]> filterEcritoireColumnDisposition() {
                    return filterEcritoireColumnDisposition;
                }
            });
        }
    }

    private interface FastProps
    {
        Tuple2<Double, Double> dimension();
        Ecritoire ecritoire();
        int panePerPage();
        double spacing();
        Consumer<Pane> paneProcess();
        int tinyColPerPane();
        EcritoireSectionNode.EcritoireListener ecritoireListener();
        int toneOnColumn();
        Map<Integer, String[]> filterEcritoireColumnDisposition();
    }
}