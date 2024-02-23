package net.olegueyan.monstroclasse.utils;

import java.awt.*;

public class ColorXLSUtils
{
    public static short hexToShort(String hexColor) {
        // Supprimez le '#' si présent
        if (hexColor.startsWith("#")) {
            hexColor = hexColor.substring(1);
        }

        // Convertit la valeur hexadécimale en couleur
        Color color = Color.decode(hexColor);

        // Récupère les composantes RVB
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        // Convertit les composantes RVB en valeur short
        return (short) (redToShort(red) << 10 | greenToShort(green) << 5 | blueToShort(blue));
    }

    private static short redToShort(int red) {
        return (short) (red / 8); // Divise par 8 pour obtenir une valeur short
    }

    private static short greenToShort(int green) {
        return (short) (green / 8); // Divise par 8 pour obtenir une valeur short
    }

    private static short blueToShort(int blue) {
        return (short) (blue / 8); // Divise par 8 pour obtenir une valeur short
    }
}