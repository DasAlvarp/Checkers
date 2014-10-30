package Utils;



/**
 * Created by alvaro on 10/30/14.
 */
public class Utility {
    public Object[] deleteIndex(Object[] o, int i) {


        Object newEnd[] = new Object[o.length - 1];
        for (int x = 0; x < i; x++) {
            newEnd[x] = o[x];
        }
        for (int x = i; x < o.length - 1; x++) {
            newEnd[x - 1] = o[x];
        }

        return newEnd;
    }
}
