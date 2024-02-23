package net.olegueyan.monstroclasse.node.content.ecrilu.ecritoire;

import com.speedment.common.tuple.Tuple2;
import com.speedment.common.tuple.Tuples;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import net.olegueyan.monstroclasse.common.AppTheme;
import net.olegueyan.monstroclasse.json.ecritoire.EcritoireColumn;
import net.olegueyan.monstroclasse.json.ecritoire.EcritoireColumnSpecs;
import net.olegueyan.monstroclasse.json.ecritoire.EcritoireTone;
import net.olegueyan.monstroclasse.node.FixedNodeCreatorUtils;
import net.olegueyan.monstroclasse.screen.ScreenStructurationInfo;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class EcritoireSectionNode extends Pane
{
    // ***************************************************************
    // EcritoireSectionNode - Attributes
    // ***************************************************************

    // ------- Associated EcritoireBigColumn that represents a column in an Ecritoire ------- //
    private final EcritoireColumn ecritoireColumn;
    // -------------------------------------------------------------------------------------- //

    // ------- All labels in tone row (tone only) | they are associated with a tone  ------- //
    private final Map<Label, EcritoireTone> toneLabelMap = new HashMap<>();
    // ------------------------------------------------------------------------------------- //

    // ------- All labels in this bottom column (tone excluded) | they are associated with a tone  ------- //
    private final Map<Label, EcritoireTone> scolLabelMap = new HashMap<>();
    // --------------------------------------------------------------------------------------------------- //

    private final Map<Label, EcritoireTone> allLabelMap = new HashMap<>();

    private final Map<Label, EcritoireTone> locked = new HashMap<>();

    private LockMode lockMode = LockMode.UNLOCKED;

    // ----------------------------------------------------------
    // Redraw Properties
    // ----------------------------------------------------------

    private Tuple2<Pane, RequestRedraw> tonePane;
    private final LinkedList<Tuple2<Label, RequestRedraw>> toneLabels = new LinkedList<>();

    private final LinkedList<Tuple2<Pane, RequestRedraw>> scolPanes = new LinkedList<>();
    private final LinkedList<Tuple2<Label, RequestRedraw>> scolLabels = new LinkedList<>();

    // ----------------------------------------------------------
    // Redraw Properties
    // ----------------------------------------------------------

    // ***************************************************************
    // EcritoireSectionNode - Attributes
    // ***************************************************************

    // ***************************************************************
    // EcritoireSectionNode - Constructor
    // ***************************************************************

    private EcritoireSectionNode(int toneOnColumn, EcritoireColumn ecritoireColumn, EcritoireListener ecritoireListener)
    {
        this.ecritoireColumn = ecritoireColumn;

        final double toneNumber = ecritoireColumn.top.size();

        this.tonePane = Tuples.of(new Pane(), (width, height, layoutX, layoutY) ->
        {
            this.tonePane.get0().setPrefWidth(width);
            this.tonePane.get0().setPrefHeight(height);
            this.tonePane.get0().setLayoutX(0);
            this.tonePane.get0().setLayoutY(0);
        });

        FixedNodeCreatorUtils.forcePrefDimension(this.tonePane.get0());

        for (var i = 0; i < toneNumber; i++)
        {
            Label toneLabel = new Label();

            FixedNodeCreatorUtils.forcePrefDimension(toneLabel);

            toneLabel.setText(ecritoireColumn.top.get(i));
            toneLabel.setAlignment(Pos.CENTER);

            this.applyLabelFeature(toneLabel, ecritoireListener);

            this.tonePane.get0().getChildren().add(toneLabel);

            this.toneLabels.add(Tuples.of(toneLabel, (width, height, layoutX, layoutY) ->
            {
                toneLabel.setPrefWidth(width);
                toneLabel.setPrefHeight(height);
                toneLabel.setLayoutX(layoutX);
                toneLabel.setLayoutY(layoutY);

                double fontSize = ScreenStructurationInfo.getAdjustedFontSize(2, width, height);
                toneLabel.setFont(Font.font("Verdana", FontWeight.BOLD, fontSize));
            }));

            EcritoireTone ecritoireTone = new EcritoireTone();
            ecritoireTone.col = String.join("/", ecritoireColumn.top);
            ecritoireTone.tone = ecritoireColumn.top.get(i);

            toneLabel.setUserData(new LabelComportment(true));

            this.toneLabelMap.put(toneLabel, ecritoireTone);
        }

        this.getChildren().add(this.tonePane.get0());

        for (var j = 0; j < ecritoireColumn.specs.scol; j++)
        {
            Pane scolPane = new Pane();
            FixedNodeCreatorUtils.forcePrefDimension(scolPane);
            this.scolPanes.add(Tuples.of(scolPane, (width, height, layoutX, layoutY) ->
            {
                scolPane.setPrefWidth(width);
                scolPane.setPrefHeight(height);
                scolPane.setLayoutX(layoutX);
                scolPane.setLayoutY(layoutY);
            }));

            for (var k = 0; k < ecritoireColumn.bottom.get(j).size(); k++)
            {
                Label scolLabel = new Label();

                FixedNodeCreatorUtils.forcePrefDimension(scolLabel);

                String tone = ecritoireColumn.bottom.get(j).get(k);

                if (!tone.isEmpty()) this.applyLabelFeature(scolLabel, ecritoireListener);

                this.scolLabels.add(Tuples.of(scolLabel, (width, height, layoutX, layoutY) ->
                {
                    scolLabel.setPrefWidth(width);
                    scolLabel.setPrefHeight(height);
                    scolLabel.setLayoutX(layoutX);
                    scolLabel.setLayoutY(layoutY);

                    if (!tone.isEmpty())
                    {
                        scolLabel.setText(tone);
                        scolLabel.setAlignment(Pos.CENTER);

                        double fontSize = ScreenStructurationInfo.getAdjustedFontSize(7, width, height);
                        scolLabel.setFont(Font.font("Verdana", FontWeight.BOLD, fontSize));
                    }
                }));

                scolPane.getChildren().add(scolLabel);

                EcritoireTone ecritoireTone = new EcritoireTone();
                ecritoireTone.col = String.join("/", ecritoireColumn.top);
                ecritoireTone.tone = ecritoireColumn.bottom.get(j).get(k);

                scolLabel.setUserData(new LabelComportment(false));

                this.scolLabelMap.put(scolLabel, ecritoireTone);
            }

            this.getChildren().add(scolPane);
        }

        this.allLabelMap.putAll(this.toneLabelMap);
        this.allLabelMap.putAll(this.scolLabelMap);

        this.layoutBoundsProperty().addListener((observableValue, bounds, t1) ->
        {
            final double width = observableValue.getValue().getWidth();
            final double height = observableValue.getValue().getHeight();

            double rowHeight = height / (toneOnColumn + 1.5);

            double tonePaneHeight = rowHeight * 1.5;

            this.tonePane.get1().redraw(width, tonePaneHeight, 0, 0);

            final double widthToneLabel = width / toneNumber;

            for (var i = 0; i < this.toneLabels.size(); i++)
            {
                this.toneLabels.get(i).get1().redraw(widthToneLabel, tonePaneHeight, i * widthToneLabel, 0);
            }

            final double widthScolPane = width / ecritoireColumn.specs.scol;
            final double heightScolPane = rowHeight * toneOnColumn;

            int scolPaneIndex = 0;
            int scolLabelIndex = 0;

            for (var j = 0; j < ecritoireColumn.specs.scol; j++)
            {
                this.scolPanes.get(scolPaneIndex).get1().redraw(widthScolPane, heightScolPane, j * widthScolPane, tonePaneHeight);

                for (var k = 0; k < ecritoireColumn.bottom.get(j).size(); k++)
                {
                    this.scolLabels.get(scolLabelIndex).get1().redraw(widthScolPane, rowHeight, 0, k * rowHeight);
                    scolLabelIndex++;
                }

                scolPaneIndex++;
            }
        });
    }

    // ***************************************************************
    // EcritoireSectionNode - Constructor
    // ***************************************************************

    // ***************************************************************
    // EcritoireSectionNode - Methods
    // ***************************************************************

    public void setLockMode(LockMode lockMode)
    {
        this.lockMode = lockMode;
    }

    public LockMode getLockMode()
    {
        return this.lockMode;
    }

    private void applyLabelFeature(Label label, EcritoireListener ecritoireListener)
    {
        label.setOnMouseClicked(mouseEvent ->
        {
            if (this.lockMode == LockMode.LOCKED)
            {
                LabelComportment labelComportment = (LabelComportment) label.getUserData();

                if (labelComportment.isLocked)
                {
                    if (labelComportment.isTopTone)
                    {
                        boolean onlyTopLabel = this.locked.keySet().stream().noneMatch(l ->
                        {
                            LabelComportment lc = (LabelComportment) l.getUserData();
                            return !lc.isTopTone;
                        });

                        if (onlyTopLabel)
                        {
                            this.toneLabelMap.forEach((l, et) ->
                            {
                                LabelComportment lc = (LabelComportment) l.getUserData();
                                lc.setLocked(false);
                                l.setBackground(Background.fill(Color.TRANSPARENT));
                                this.locked.remove(l);
                            });
                        }
                    }
                    else
                    {
                        labelComportment.setLocked(false);
                        label.setBackground(Background.fill(Color.TRANSPARENT));
                        this.locked.remove(label);
                    }
                }
                else
                {
                    labelComportment.setLocked(true);
                    label.setBackground(Background.fill(Color.web(AppTheme.ECRILU_ECRITOIRE_LABEL_HIGHLIGHT)));

                    this.toneLabelMap.forEach((l, et) ->
                    {
                        LabelComportment lc = (LabelComportment) l.getUserData();
                        lc.setLocked(true);
                        l.setBackground(Background.fill(Color.web(AppTheme.ECRILU_ECRITOIRE_LABEL_HIGHLIGHT)));
                    });

                    this.locked.put(label, this.allLabelMap.get(label));
                }
            }
            else
            {
                if (ecritoireListener != null)
                {
                    ecritoireListener.onToneClick(this.allLabelMap.get(label), this.lockMode);
                }
            }
        });

        label.setOnMouseEntered(mouseEvent ->
        {
            LabelComportment labelComportment = (LabelComportment) label.getUserData();

            if (!labelComportment.getLocked())
            {
                label.setBackground(Background.fill(Color.web(AppTheme.ECRILU_ECRITOIRE_LABEL_HIGHLIGHT)));
            }
        });

        label.setOnMouseExited(mouseEvent ->
        {
            LabelComportment labelComportment = (LabelComportment) label.getUserData();

            if (!labelComportment.getLocked())
            {
                label.setBackground(Background.fill(Color.TRANSPARENT));
            }
        });
    }

    public @Nullable EcritoireColumn loadLockedAsEcritoireBigColumn()
    {
        if (this.locked.isEmpty()) return null;

        EcritoireColumn toOut = new EcritoireColumn();

        // ------- Fixing bug linked to Java References ------- //
        // We need to create new EcritoireColumnSpecs
        // Bug linked to copy per reference and deep copy

        EcritoireColumnSpecs specs = new EcritoireColumnSpecs();
        specs.scol = this.ecritoireColumn.specs.scol;
        specs.tone = this.ecritoireColumn.specs.tone;

        // ---------------------------------------------------- //

        toOut.specs = specs;
        toOut.specs.scol = specs.scol / 2;
        toOut.top = this.ecritoireColumn.top;

        LinkedList<LinkedList<String>> toOutBottom = new LinkedList<>();

        int counter = 0;

        for (var i = 0; i < this.ecritoireColumn.bottom.size(); i += 2)
        {
            LinkedList<String> strings = new LinkedList<>();

            for (var k = 0; k < 2; k++)
            {
                for (var j = 0; j < this.ecritoireColumn.bottom.get(counter).size(); j++)
                {
                    String tone = this.ecritoireColumn.bottom.get(counter).get(j);
                    if (this.locked.keySet().stream().anyMatch(label -> label.getText().equals(tone)))
                    {
                        strings.add(tone);
                    }
                }
                counter++;
            }

            toOutBottom.add(strings);
        }

        toOut.bottom = toOutBottom;

        return toOut;
    }

    public void forceLockTone(EcritoireTone ecritoireTone)
    {
        this.allLabelMap
                .entrySet()
                .stream()
                .filter(labelEcritoireToneEntry -> Objects.equals(labelEcritoireToneEntry.getValue(), ecritoireTone))
                .forEach(labelEcritoireToneEntry ->
                {
                    Label label = labelEcritoireToneEntry.getKey();

                    LabelComportment labelComportment = (LabelComportment) label.getUserData();

                    this.toneLabelMap.forEach((l, et) ->
                    {
                        LabelComportment lc = (LabelComportment) l.getUserData();
                        lc.setLocked(true);
                        l.setBackground(Background.fill(Color.web(AppTheme.ECRILU_ECRITOIRE_LABEL_HIGHLIGHT)));
                        this.locked.put(l, this.allLabelMap.get(l));
                    });

                    labelComportment.setLocked(true);
                    label.setBackground(Background.fill(Color.web(AppTheme.ECRILU_ECRITOIRE_LABEL_HIGHLIGHT)));

                    if (!labelComportment.isTopTone)
                    {
                        this.locked.put(label, this.allLabelMap.get(label));
                    }
                });
    }

    public void forceLockTone(String topTone, String scolTone)
    {
        if (this.toneLabelMap
                .values()
                .stream()
                .noneMatch(ecritoireTone -> Objects.equals(ecritoireTone.col, topTone))) return;

        this.allLabelMap
                .entrySet()
                .stream()
                .filter(labelEcritoireToneEntry -> Objects.equals(labelEcritoireToneEntry.getValue().tone, scolTone))
                .forEach(labelEcritoireToneEntry ->
                {
                    Label label = labelEcritoireToneEntry.getKey();

                    LabelComportment labelComportment = (LabelComportment) label.getUserData();

                    this.toneLabelMap.forEach((l, et) ->
                    {
                        LabelComportment lc = (LabelComportment) l.getUserData();
                        lc.setLocked(true);
                        l.setBackground(Background.fill(Color.web(AppTheme.ECRILU_ECRITOIRE_LABEL_HIGHLIGHT)));
                        this.locked.put(l, this.allLabelMap.get(l));
                    });

                    labelComportment.setLocked(true);
                    label.setBackground(Background.fill(Color.web(AppTheme.ECRILU_ECRITOIRE_LABEL_HIGHLIGHT)));

                    if (!labelComportment.isTopTone)
                    {
                        this.locked.put(label, this.allLabelMap.get(label));
                    }
                });
    }

    // ***************************************************************
    // EcritoireSectionNode - Methods
    // ***************************************************************

    // ***************************************************************
    // EcritoireSectionNode - Builder
    // ***************************************************************

    /**
     * Builder that can easily make EcritoireSectionNode
     */
    public static class Builder
    {
        // ***************************************************************
        // EcritoireSectionNode - Builder - Attributes
        // ***************************************************************

        // ------- Define how many tones a column will have ------- //
        private int toneOnColumn = 15;
        // -------------------------------------------------------- //

        // ------- Associated EcritoireBigColumn that represents a column in an Ecritoire ------- //
        private EcritoireColumn ecritoireColumn = null;
        // -------------------------------------------------------------------------------------- //

        // ------- Listener which capture tone click ------- //
        private EcritoireListener ecritoireListener = null;
        // ------------------------------------------------- //

        // ***************************************************************
        // EcritoireSectionNode - Builder - Attributes
        // ***************************************************************

        // ***************************************************************
        // EcritoireSectionNode - Builder - Methods
        // ***************************************************************

        /**
         * Add to the builder toneOnColumn
         * @param toneOnColumn number of tones on a column
         * @return Builder
         */
        public Builder withToneOnColumn(int toneOnColumn)
        {
            this.toneOnColumn = toneOnColumn;
            return this;
        }

        public Builder withEcritoireListener(EcritoireListener ecritoireListener)
        {
            this.ecritoireListener = ecritoireListener;
            return this;
        }

        public Builder withEcritoireBigColumn(EcritoireColumn ecritoireColumn)
        {
            this.ecritoireColumn = ecritoireColumn;
            return this;
        }

        // ***************************************************************
        // EcritoireSectionNode - Builder - Methods
        // ***************************************************************

        // ***************************************************************
        // EcritoireSectionNode - Builder - Build Method
        // ***************************************************************

        public EcritoireSectionNode build()
        {
            if (this.ecritoireColumn == null)
            {
                throw new RuntimeException("Cannot build |-> EcritoireBigColumn is missing !");
            }

            return new EcritoireSectionNode(this.toneOnColumn, this.ecritoireColumn, this.ecritoireListener);
        }

        // ***************************************************************
        // EcritoireSectionNode - Builder - Build Method
        // ***************************************************************
    }

    // ***************************************************************
    // EcritoireSectionNode - Builder
    // ***************************************************************

    // ***************************************************************
    // EcritoireSectionNode - EcritoireListener
    // ***************************************************************


    public interface EcritoireListener
    {
        /* ------- Raised when a label with tone is clicked ------- */
        void onToneClick(EcritoireTone tone, LockMode lockMode);
        /* -------------------------------------------------------- */
    }

    // ***************************************************************
    // EcritoireSectionNode - EcritoireListener
    // ***************************************************************

    public interface RequestRedraw
    {
        void redraw(double width, double height, double layoutX, double layoutY);
    }

    public enum LockMode
    {
        LOCKED,
        UNLOCKED
    }

    private static class LabelComportment
    {
        private final boolean isTopTone;

        private boolean isLocked;

        public LabelComportment(boolean isTopTone)
        {
            this.isTopTone = isTopTone;
            this.isLocked = false;
        }

        public void setLocked(boolean isLocked)
        {
            this.isLocked = isLocked;
        }

        public boolean getLocked()
        {
            return this.isLocked;
        }

        public boolean getIsTopTone()
        {
            return this.isTopTone;
        }
    }
}