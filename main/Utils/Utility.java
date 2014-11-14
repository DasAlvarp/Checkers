package Utils;


import Utils.LocationManipulation.LocationSet;

/**
 * Created by alvaro on 10/30/14.
 * <p/>
 * Basically useful functions that I don't know where to put anywhere else.
 */
public class Utility
{
    public LocationSet[] deleteIndex(LocationSet[] o, int i)
    {

        if (o.length > 1)
        {
            LocationSet[] newEnd = new LocationSet[o.length - 1];
            for (int x = 0; x < i; x++)
            {
                newEnd[x] = o[x];
            }
            for (int x = i; x < o.length - 1; x++)
            {
                newEnd[x] = o[x + 1];
            }

            return newEnd;
        } else
        {
            return null;
        }
    }

    private Object[] arrayFuser(Object[] a1, Object[] a2)// combines 2 int arrays into
    // one. Not used...yet.
    {
        Object[] returned = new Object[a1.length + a2.length];
        for (int x = 0; x < a1.length; x++)
        {
            returned[x] = a1[x];
        }

        for (int x = a1.length; x < a1.length + a2.length; x++)
        {
            returned[x] = a2[x - a1.length];
        }
        return returned;
    }
}
