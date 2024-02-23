package net.olegueyan.monstroclasse.function;

import com.speedment.common.tuple.Tuple2;
import com.speedment.common.tuple.Tuples;

/**
 * A DualCounter is a counter like binary incrementation system
 * <br>
 * Example of usage: creation of X axis and Y axis with single loop
 * <code>
 * <br>
 * DualCounter dualCounter = new DualCounter(x, y);
 * <br>
 * for (var i = 0; i < (x * y); i++)
 * <br>
 * {
 * <br>
 * --- int xCurr = dualCounter.current().get0();
 * <br>
 * --- int yCurr = dualCounter.current().get1();
 * <br>
 * --- dualCounter.increment();
 * <br>
 * }
 * </code>
 */
public class DualCounter
{
    // ***************************************************************
    // DualCounter - ATTRIBUTES
    // ***************************************************************

    // ------- ------- //
    private int a;

    private int b;

    private final int maxA;
    private final int maxB;

    // ***************************************************************
    // END
    // ***************************************************************

    public DualCounter(int maxA, int maxB)
    {
        this.a = 0;
        this.b = 0;

        this.maxA = maxA;
        this.maxB = maxB;
    }

    public void reset()
    {
        this.a = 0;
        this.b = 0;
    }

    public void increment()
    {
        if (b < maxB - 1)
        {
            b++;
        }
        else
        {
            b = 0;
            if (a < maxA - 1)
            {
                a++;
            }
            else
            {
                this.reset();
                // throw new RuntimeException(String.format("Can't be over %s / %s", this.maxA - 1, this.maxB - 1));
            }
        }
    }

    public Tuple2<Integer, Integer> current()
    {
        return Tuples.of(this.a, this.b);
    }
}