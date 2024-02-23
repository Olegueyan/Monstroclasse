package net.olegueyan.monstroclasse.screen;

import com.speedment.common.tuple.Tuple2;
import com.speedment.common.tuple.Tuples;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import net.olegueyan.monstroclasse.motor.core.*;

@SuppressWarnings("unused")
public final class ScreenStructurationInfo
{
    /***
     * Screen resolution of the current screen (The first)
     ***/

    public static Screen screen = Screen.getPrimary();

    /***
     * Screen resolution of application edited in scene builder DEFAULT (1280, 720)
     ***/

    private static final int widthScreenReference = 1280;
    private static final int heightScreenReference = 720;

    /****
     * Graphics data
        |-> Dimension of GridPane of a/the Module Access
     ***/

    public static final int moduleAccessRow = 3;
    public static final int moduleAccessColumn = 6;

    /***
     * Fake title bar to separate screen in two part (Do not appear in application)
        * Part - Top : Banner, Image of current connected account, ...
        * Part - Down : Application core
     * Tuple2<Double, Double> : Put the dimension of dummy title bar in scene builder
     * DEFAULT (1280, 104) | According to screen reference resolution
     ***/

    public static Tuple2<Double, Double> dummyTitleBarSize = Tuples.of(1280.0, 104.0);

    /***
     * Initialisation process of the fake title bar | accorded to screen resolution
     ***/

    public static void processInitDummyTitleBar()
    {
        double width = screen.getBounds().getWidth() * dummyTitleBarSize.get0() / widthScreenReference;
        double height = screen.getBounds().getHeight() * dummyTitleBarSize.get1() / heightScreenReference;
        dummyTitleBarSize = Tuples.of(width, height);
    }

    /***
     * Allow to a banner to be resized and adapted to the new screen resolution
     * The banner must have dimension : (1000, 170)
     ***/

    public static void processInitBanner(ImageView banner)
    {
        final float dimWidthBanner = 1000;
        final float dimHeightBanner = 170;
        double margin = dummyTitleBarSize.get1() * 0.15f;
        double factor = dummyTitleBarSize.get1() * 0.85f / dimHeightBanner;
        double width = dimWidthBanner * factor;
        double height = dummyTitleBarSize.get1() - margin;
        int x = (int) ((screen.getBounds().getWidth() - width) / 2f);
        int y = (int) (margin / 2f);
        banner.setFitWidth(width);
        banner.setFitHeight(height);
        banner.setLayoutX(x);
        banner.setLayoutY(y);
    }

    /***
     *
     ***/

    public static void processInitAnchoredPane(Pane pane)
    {
        pane.setPrefWidth(screen.getBounds().getWidth());
        pane.setPrefHeight(screen.getBounds().getHeight());
        pane.setLayoutX(0);
        pane.setLayoutY(0);
    }

    /***
     *
     ***/

    public static void processInitCorePane(Pane corePane)
    {
        corePane.setPrefWidth(screen.getBounds().getWidth());
        corePane.setPrefHeight(screen.getBounds().getHeight() - dummyTitleBarSize.get1());
        corePane.setLayoutX(0);
        corePane.setLayoutY(dummyTitleBarSize.get1());
    }

    /* Fix Structuration */

    /***
     *
     ***/

    public static void processAdjustmentDimensionForRegion(Region region, DimensionAdjustment adjustment)
    {
        double widthFinal = screen.getBounds().getWidth() * adjustment.wRef() / widthScreenReference;
        double heightFinal = screen.getBounds().getHeight() * adjustment.hRef() / heightScreenReference;
        switch (adjustment.fixing())
        {
            case MIN ->
            {
                if (widthFinal <= heightFinal)
                {
                    region.setPrefSize(widthFinal, widthFinal);
                }
                else
                {
                    region.setPrefSize(heightFinal, heightFinal);
                }
            }
            case MAX ->
            {
                if (widthFinal >= heightFinal)
                {
                    region.setPrefSize(widthFinal, widthFinal);
                }
                else
                {
                    region.setPrefSize(heightFinal, heightFinal);
                }
            }
            case BOTH -> region.setPrefSize(widthFinal, heightFinal);
        }
    }

    /***
     *
     ***/

    public static void processAdjustmentDimensionForImageView(ImageView imageView, DimensionAdjustment adjustment)
    {
        imageView.setFitWidth(screen.getBounds().getWidth() * adjustment.wRef() / widthScreenReference);
        imageView.setFitHeight(screen.getBounds().getHeight() * adjustment.hRef() / heightScreenReference);
    }

    /***
     *
     ***/

    public static void processAdjustmentCoordinates(Node node, CoordinatesAdjustment adjustment)
    {
        node.setLayoutX(screen.getBounds().getWidth() * adjustment.xRef() / widthScreenReference);
        node.setLayoutY(screen.getBounds().getHeight() * adjustment.yRef() / heightScreenReference);
    }

    /***
     *
     ***/

    public static void processAdjustmentOffsetForRegion(Region region, OffsetAdjustment adjustment)
    {
        if (region.getParent() == null) throw new RuntimeException("The node " + region.getId() + " doesn't have parent !");
        if (region.getParent() instanceof Region parent)
        {
            // Offset on X axis

            if (adjustment.offsetX() == 0)
            {
                region.setLayoutX(0);
            }
            else if (adjustment.offsetX() < 0)
            {
                region.setLayoutX(parent.getPrefWidth() - region.getPrefWidth() + adjustment.offsetX());
            }
            else
            {
                region.setLayoutX(adjustment.offsetX());
            }

            // Offset on Y axis

            if (adjustment.offsetY() == 0)
            {
                region.setLayoutY(0);
            }
            else if (adjustment.offsetY() < 0)
            {
                region.setLayoutY(parent.getPrefHeight() - region.getPrefHeight() + adjustment.offsetY());
            }
            else
            {
                region.setLayoutY(adjustment.offsetY());
            }
        }
    }

    /***
     *
     ***/

    public static void processAdjustmentOffsetForImageView(ImageView imageView, OffsetAdjustment adjustment)
    {
        if (imageView.getParent() == null) throw new RuntimeException("The node " + imageView.getId() + " doesn't have parent !");
        if (imageView.getParent() instanceof Region parent)
        {
            // Offset on X axis

            if (adjustment.offsetX() == 0)
            {
                imageView.setLayoutX(0);
            }
            else if (adjustment.offsetX() < 0)
            {
                imageView.setLayoutX(parent.getPrefWidth() - imageView.getFitWidth() + adjustment.offsetX());
            }
            else
            {
                imageView.setLayoutX(adjustment.offsetX());
            }

            // Offset on Y axis

            if (adjustment.offsetY() == 0)
            {
                imageView.setLayoutY(0);
            }
            else if (adjustment.offsetY() < 0)
            {
                imageView.setLayoutY(parent.getPrefHeight() - imageView.getFitHeight() + adjustment.offsetY());
            }
            else
            {
                imageView.setLayoutY(adjustment.offsetY());
            }
        }
    }

    /***
     *
     ***/

    private static Font calcAdjustmentFont(FontAdjustment adjustment)
    {
        return calcAdjustmentFont(adjustment.fontFamily(), adjustment.fontWeight(), adjustment.fontRef());
    }

    /***
     *
     ***/

    private static Font calcAdjustmentFont(String fontFamily, String fontWeight, double fontRef)
    {
        double fontScaleFactor = Math.min(screen.getBounds().getWidth() / widthScreenReference, screen.getBounds().getHeight() / heightScreenReference);
        double newFontSize = fontRef * fontScaleFactor;
        return Font.font(fontFamily, FontWeight.findByName(fontWeight), newFontSize);
    }

    /***
     *
     ***/

    public static void processAdjustmentFontForLabel(Label label, FontAdjustment adjustment)
    {
        label.setFont(calcAdjustmentFont(adjustment));
    }

    /***
     *
     ***/

    public static void processAdjustmentFontForLabel(Label label, String fontFamily, String fontWeight, double fontRef)
    {
        label.setFont(calcAdjustmentFont(fontFamily, fontWeight, fontRef));
    }

    /***
     *
     ***/

    public static void processAdjustmentFontForTextField(TextField textField, FontAdjustment adjustment)
    {
        textField.setFont(calcAdjustmentFont(adjustment));
    }

    /*/ Css Style Application /*/

    public static void processCssRounded(Node node, RoundedStyle style)
    {
        processCssRounded(node, style.color(), style.cornerRadius());
    }

    /*/ Centering /*/

    /**
     * Center horizontally given region
     * @param region Region to center
     */

    public static void processCenterHorizontally(Region region)
    {
        if (region.getParent() != null)
        {
            if (region.getParent() instanceof Region parent)
            {
                region.setLayoutX((parent.getPrefWidth() - region.getPrefWidth()) / 2);
            }
        }
        else
        {
            region.setLayoutX((screen.getBounds().getWidth() - region.getPrefWidth()) / 2);
        }
    }

    /**
     * Center horizontally given imageView
     * @param imageView ImageView to center
     */

    public static void processCenterHorizontally(ImageView imageView)
    {
        if (imageView.getParent() != null)
        {
            if (imageView.getParent() instanceof Region parent)
            {
                imageView.setLayoutX((parent.getPrefWidth() - imageView.getFitWidth()) / 2);
            }
        }
        else
        {
            imageView.setLayoutX((screen.getBounds().getWidth() - imageView.getFitWidth()) / 2);
        }
    }

    /**
     * Center vertically given region
     * @param region Region to center
     */

    public static void processCenterVertically(Region region)
    {
        if (region.getParent() != null)
        {
            if (region.getParent() instanceof Region parent)
            {
                region.setLayoutY((parent.getPrefHeight() - region.getPrefHeight()) / 2);
            }
        }
        else
        {
            region.setLayoutY((screen.getBounds().getHeight() - region.getPrefHeight()) / 2);
        }
    }

    /**
     * Center vertically given imageView
     * @param imageView ImageView to center
     */

    public static void processCenterVertically(ImageView imageView)
    {
        if (imageView.getParent() != null)
        {
            if (imageView.getParent() instanceof Region parent)
            {
                imageView.setLayoutX((parent.getPrefHeight() - imageView.getFitHeight()) / 2);
            }
        }
        else
        {
            imageView.setLayoutX((screen.getBounds().getHeight() - imageView.getFitHeight()) / 2);
        }
    }

    // Custom //

    // Need Button -> Pane -> Label | No error handling

    // To update

    public static void processInitStylizedTypeButton(Pane button, StylizedButtonBuild stylizedButtonBuild)
    {
        double widthFinal = screen.getBounds().getWidth() * stylizedButtonBuild.widthRef() / widthScreenReference;
        double heightFinal = screen.getBounds().getHeight() * stylizedButtonBuild.heightRef() / heightScreenReference;

        button.setPrefWidth(widthFinal);
        button.setPrefHeight(heightFinal);

        ScreenStructurationInfo.processCssRounded(button, stylizedButtonBuild.backgroundHex(), 18);

        if (button.getChildrenUnmodifiable().get(0) instanceof Pane pane)
        {
            final double margin = 7;

            double widthPane = widthFinal - margin * 2;
            double heightPane = heightFinal - margin * 2;

            pane.setPrefWidth(widthPane);
            pane.setPrefHeight(heightPane);

            pane.setLayoutX(margin);
            pane.setLayoutY(margin);

            ScreenStructurationInfo.processCssRounded(pane, stylizedButtonBuild.foregroundHex(), 12);

            if (pane.getChildrenUnmodifiable().get(0) instanceof Label label) {
                final float percentageOfPane = 0.85f;

                double widthLabel = widthPane * percentageOfPane;
                double heightLabel = heightPane * percentageOfPane;

                label.setPrefWidth(widthLabel);
                label.setPrefHeight(heightLabel);

                double fontScaleFactor = Math.min(screen.getBounds().getWidth() / widthScreenReference, screen.getBounds().getHeight() / heightScreenReference);
                String fontFamily = label.getFont().getFamily();
                String fontWeight = label.getFont().getStyle();
                double fontSize = stylizedButtonBuild.fontRef();
                double newFontSize = fontSize * fontScaleFactor;
                label.setFont(Font.font(fontFamily, FontWeight.findByName(fontWeight), newFontSize));

                ScreenStructurationInfo.processCenterHorizontally(label);
                ScreenStructurationInfo.processCenterVertically(label);
            }
        }
    }

    /* Dynamic Structuration | applied with annotations */

    public static void processApplyOffsetXForRegion(Region region, double offset)
    {
        if (region.getParent() != null)
        {
            if (region.getParent() instanceof Region parent)
            {
                region.setLayoutX(parent.getPrefWidth() - region.getPrefWidth() - offset);
            }
        }
        else
        {
            region.setLayoutX(screen.getBounds().getWidth() - region.getPrefWidth() - offset);
        }
    }

    public static void processApplyOffsetYForRegion(Region region, double offset)
    {
        if (region.getParent() != null)
        {
            if (region.getParent() instanceof Region parent)
            {
                region.setLayoutY(parent.getPrefHeight() - region.getPrefHeight() - offset);
            }
        }
        else
        {
            region.setLayoutY(screen.getBounds().getHeight() - region.getPrefHeight() - offset);
        }
    }

    public static void processApplyOffsetXForImageView(ImageView imageView, double offset)
    {
        if (imageView.getParent() != null)
        {
            if (imageView.getParent() instanceof Region parent)
            {
                imageView.setLayoutX(parent.getPrefWidth() - imageView.getFitWidth() - offset);
            }
        }
        else
        {
            imageView.setLayoutX(screen.getBounds().getWidth() - imageView.getFitWidth() - offset);
        }
    }

    public static void processApplyOffsetYForImageView(ImageView imageView, double offset)
    {
        if (imageView.getParent() != null)
        {
            if (imageView.getParent() instanceof Region parent)
            {
                imageView.setLayoutY(parent.getPrefHeight() - imageView.getFitHeight() - offset);
            }
        }
        else
        {
            imageView.setLayoutY(screen.getBounds().getHeight() - imageView.getFitHeight() - offset);
        }
    }

    public static void processCssRounded(Node node, String hex, int cornerRadius)
    {
        node.setStyle("-fx-background-color: " + hex + "; -fx-background-radius: " + cornerRadius + ";");
        node.applyCss();
    }

    /*

        // Adjustment of font size

     */

    /**
     *
     * @param chainLength
     * @param width
     * @param height
     * @return
     */
    // Ne prend pas en compte le retour a ligne et les fontWeights bold et italic

    public static double getAdjustedFontSize(int chainLength, double width, double height)
    {
        double minSize = 1; // Taille de police minimale
        double maxSize = 100; // Taille de police maximale

        // Effectuer une recherche binaire pour trouver la taille de police adaptée
        double fontSize = (minSize + maxSize) / 2;
        double epsilon = 0.1; // Tolérance pour la précision de l'ajustement

        while (Math.abs(maxSize - minSize) > epsilon) {
            Font font = Font.font(fontSize);
            double textWidth = getTextWidth(chainLength, font);
            double textHeight = getTextHeight(chainLength, font);

            if (textWidth > width || textHeight > height) {
                maxSize = fontSize;
            } else {
                minSize = fontSize;
            }

            fontSize = (minSize + maxSize) / 2;
        }

        return fontSize;
    }

    // Méthode utilitaire pour calculer la largeur du texte en fonction de la police
    private static double getTextWidth(int chainLength, Font font)
    {
        Text text = new Text();
        text.setFont(font);
        text.setText("#".repeat(chainLength)); // Chaîne de caractères de longueur donnée
        return text.getLayoutBounds().getWidth();
    }

    // Méthode utilitaire pour calculer la hauteur du texte en fonction de la police
    private static double getTextHeight(int chainLength, Font font)
    {
        Text text = new Text();
        text.setFont(font);
        text.setText("#".repeat(chainLength)); // Chaîne de caractères de longueur donnée
        return text.getLayoutBounds().getHeight();
    }

    public static double refactoringBasedOnWidth(double value)
    {
        return screen.getBounds().getWidth() * value / widthScreenReference;
    }

    public static double refactoringBasedOnHeight(double value)
    {
        return screen.getBounds().getHeight() * value / heightScreenReference;
    }
}