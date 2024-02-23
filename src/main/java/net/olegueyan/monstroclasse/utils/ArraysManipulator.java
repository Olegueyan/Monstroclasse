package net.olegueyan.monstroclasse.utils;

public final class ArraysManipulator
{
    public static <T> int getIndexOf(T[] array, T value)
    {
        int index = -1;

        for (int i = 0; i < array.length; i++)
        {
            if (array[i].equals(value))
            {
                index = i;
                break;
            }
        }

        if (index != -1)
        {
            return index;
        }
        else
        {
            throw new RuntimeException("The value is not in array !");
        }
    }
}