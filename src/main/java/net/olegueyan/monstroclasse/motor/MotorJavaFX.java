package net.olegueyan.monstroclasse.motor;

import com.speedment.common.tuple.Tuple2;
import com.speedment.common.tuple.Tuples;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import net.olegueyan.monstroclasse.integration.ControllerIntegration;
import net.olegueyan.monstroclasse.motor.core.*;
import net.olegueyan.monstroclasse.node.FixedNodeCreatorUtils;
import net.olegueyan.monstroclasse.screen.ScreenStructurationInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

@SuppressWarnings({"CodeBlock2Expr", "unused"})
public class MotorJavaFX
{
    // ***************************************************************
    // MotorJavaFX - ATTRIBUTES
    // ***************************************************************

    // ------- A MotorJavaFX operate only on controllerIntegration ------- //
    private final ControllerIntegration controllerIntegration;
    // ------------------------------------------------------------------- //

    // ------- A chain that represent all field to motorize ------- //
    private final Map<MotorPriority, ArrayList<Tuple2<Field, Annotation>>> motorChain;
    // ------------------------------------------------------------ //

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // MotorJavaFX - CONSTRUCTOR
    // ***************************************************************

    public MotorJavaFX(ControllerIntegration controllerIntegration)
    {
        this.controllerIntegration = controllerIntegration;
        this.motorChain = new HashMap<>();
        Arrays.stream(MotorPriority.values()).forEach(motorPriority -> this.motorChain.put(motorPriority, new ArrayList<>()));
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // MotorJavaFX - METHODS
    // ***************************************************************

    /**
     * Add to the chain one motorization
     * @param motor motor annotation
     * @param field field annotated
     * @param annotation motor annotation (with a better type precision)
     */
    public void addToChain(Motor motor, Field field, Annotation annotation)
    {
        this.motorChain.get(motor.priority()).add(Tuples.of(field, annotation));
    }

    /**
     * Execute all motorization on all fields presents in the chain
     */
    public void executeAndClean()
    {
        Arrays.stream(MotorPriority.values())
                .sorted(Comparator.comparingInt(MotorPriority::getOrder))
                .forEach(motorPriority -> this.motorChain.remove(motorPriority).forEach(this::tryExecute));
    }

    // TODO : Priority handling

    /*

        // Motor section

     */

    private static final ArrayList<MotorPart<?, ?>> motorParts = new ArrayList<>();

    public static MotorPart<Pane, AnchoredPaneStructuration> ANCHORED_PANE_STRUCTURATION = registerMotorPart(new MotorPart<>(Pane.class, AnchoredPaneStructuration.class, motorConsumer ->
    {
        ScreenStructurationInfo.processInitAnchoredPane(motorConsumer.field());
    }));

    public static MotorPart<Pane, CorePaneStructuration> CORE_PANE_STRUCTURATION = registerMotorPart(new MotorPart<>(Pane.class, CorePaneStructuration.class, motorConsumer ->
    {
        ScreenStructurationInfo.processInitCorePane(motorConsumer.field());
    }));

    public static MotorPart<ImageView, BannerStructuration> BANNER_STRUCTURATION = registerMotorPart(new MotorPart<>(ImageView.class, BannerStructuration.class, motorConsumer ->
    {
        ScreenStructurationInfo.processInitBanner(motorConsumer.field());
    }));

    public static MotorPart<Node, CoordinatesAdjustment> COORDINATES_ADJUSTMENT = registerMotorPart(new MotorPart<>(Node.class, CoordinatesAdjustment.class, motorConsumer ->
    {
        ScreenStructurationInfo.processAdjustmentCoordinates(motorConsumer.field(), motorConsumer.annotation());
    }));

    public static MotorPart<Region, DimensionAdjustment> DIMENSION_ADJUSTMENT_REGION = registerMotorPart(new MotorPart<>(Region.class, DimensionAdjustment.class, motorConsumer ->
    {
        ScreenStructurationInfo.processAdjustmentDimensionForRegion(motorConsumer.field(), motorConsumer.annotation());
    }));

    public static MotorPart<ImageView, DimensionAdjustment> DIMENSION_ADJUSTMENT_IMAGE_VIEW = registerMotorPart(new MotorPart<>(ImageView.class, DimensionAdjustment.class, motorConsumer ->
    {
        ScreenStructurationInfo.processAdjustmentDimensionForImageView(motorConsumer.field(), motorConsumer.annotation());
    }));

    public static MotorPart<Label, FontAdjustment> FONT_ADJUSTMENT_LABEL = registerMotorPart(new MotorPart<>(Label.class, FontAdjustment.class, motorConsumer ->
    {
        ScreenStructurationInfo.processAdjustmentFontForLabel(motorConsumer.field(), motorConsumer.annotation());
    }));

    public static MotorPart<TextField, FontAdjustment> FONT_ADJUSTMENT_TEXT_FIELD = registerMotorPart(new MotorPart<>(TextField.class, FontAdjustment.class, motorConsumer ->
    {
        ScreenStructurationInfo.processAdjustmentFontForTextField(motorConsumer.field(), motorConsumer.annotation());
    }));

    public static MotorPart<Region, OffsetAdjustment> OFFSET_ADJUSTMENT_REGION = registerMotorPart(new MotorPart<>(Region.class, OffsetAdjustment.class, motorConsumer ->
    {
        ScreenStructurationInfo.processAdjustmentOffsetForRegion(motorConsumer.field(), motorConsumer.annotation());
    }));

    public static MotorPart<ImageView, OffsetAdjustment> OFFSET_ADJUSTMENT_IMAGE_VIEW = registerMotorPart(new MotorPart<>(ImageView.class, OffsetAdjustment.class, motorConsumer ->
    {
        ScreenStructurationInfo.processAdjustmentOffsetForImageView(motorConsumer.field(), motorConsumer.annotation());
    }));

    public static MotorPart<Node, ApplyCss> APPLY_CSS = registerMotorPart(new MotorPart<>(Node.class, ApplyCss.class, motorConsumer ->
    {
        Arrays.stream(motorConsumer.annotation().classTab()).forEach(s -> motorConsumer.field().getStyleClass().add(s));
        motorConsumer.field().applyCss();
    }));

    public static MotorPart<Region, ThemeBackground> THEME_BACKGROUND = registerMotorPart(new MotorPart<>(Region.class, ThemeBackground.class, motorConsumer ->
    {
        motorConsumer.field().setBackground(Background.fill(Color.web(motorConsumer.annotation().color())));
    }));

    public static MotorPart<Node, RoundedStyle> ROUNDED_STYLE = registerMotorPart(new MotorPart<>(Node.class, RoundedStyle.class, motorConsumer ->
    {
        ScreenStructurationInfo.processCssRounded(motorConsumer.field(), motorConsumer.annotation());
    }));

    public static MotorPart<ImageView, ImageViewBinder> IMAGE_VIEW_BINDER = registerMotorPart(new MotorPart<>(ImageView.class, ImageViewBinder.class, motorConsumer ->
    {
        Region region = motorConsumer.getFromAndForceCast(Region.class, motorConsumer.annotation().fieldName());
        FixedNodeCreatorUtils.bindImageViewDimensionToRegion(motorConsumer.field(), region);
    }));

    public static MotorPart<Pane, StylizedButtonBuild> STYLIZED_BUTTON_BUILD = registerMotorPart(new MotorPart<>(Pane.class, StylizedButtonBuild.class, motorConsumer ->
    {
        ScreenStructurationInfo.processInitStylizedTypeButton(motorConsumer.field(), motorConsumer.annotation());
    }));

    public static <T extends Node, A extends Annotation> MotorPart<T, A> registerMotorPart(MotorPart<T, A> motorPart)
    {
        motorParts.add(motorPart);
        return motorPart;
    }

    /**
     * Execute motorization on the field
     * @param tuple2 field with his motor annotation
     */
    public void tryExecute(Tuple2<Field, Annotation> tuple2)
    {
        motorParts
                .stream()
                .filter(motorPart -> motorPart.applicableTo().isAssignableFrom(tuple2.get0().getType())
                        && motorPart.annotator().isAssignableFrom(tuple2.get1().annotationType()))
                .forEach(motorPart -> motorPart.forceApply(this.controllerIntegration, tuple2.get0(), tuple2.get1()));
    }

    // ***************************************************************
    // END
    // ***************************************************************
}