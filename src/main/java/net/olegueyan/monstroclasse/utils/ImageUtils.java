package net.olegueyan.monstroclasse.utils;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;

public final class ImageUtils
{
    // ***************************************************************
    // ImageUtils - METHODS # STATIC
    // ***************************************************************

    /**
     * Transform a byte[] into image
     * @param bytes data of image
     * @return Image
     */
    public static Image byteArrayToImage(byte[] bytes)
    {
        return new Image(new ByteArrayInputStream(bytes));
    }

    // ***************************************************************
    // END
    // ***************************************************************
}