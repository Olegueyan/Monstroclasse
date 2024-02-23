package net.olegueyan.monstroclasse.motor;

/**
 * Give a priority to a motor annotation
 * Specify if the motor must operate before or after all
 */
public enum MotorPriority
{
    VERY_LOW(6),
    LOW(5),
    MEDIUM(4),
    HIGH(3),
    VERY_HIGH(2),
    IMMEDIATELY(1);

    private final int order;

    MotorPriority(int order)
    {
        this.order = order;
    }

    public int getOrder()
    {
        return this.order;
    }
}