package Utils;

/**
 * Created by alvaro on 10/30/14.
 */
public class LocationSet
{
    private Location locationStart;
    private Location[] locationEnd;

    public LocationSet(Location start, Location[] ends)
    {
        locationStart = start;
        locationEnd = ends;
    }


    public Location getStart()
    {
        return locationStart;
    }

    public Location getEnd(int x)
    {
        return locationEnd[x];
    }

    public void addEnd(Location l)
    {
        Location lEnd[] = new Location[locationEnd.length + 1];
        lEnd[locationEnd.length] = l;
    }

    public Location getIndex(int x)
    {
        return locationEnd[x];
    }

    public int destinationNum()
    {
        return locationEnd.length;
    }

    public void removeIndex(int i)
    {
        Location[] newEnd = new Location[locationEnd.length - 1];
        for(int x = 0; x < i; x++)
        {
            newEnd[x] = locationEnd[x];
        }
        for(int x = i; x < locationEnd.length - 1; x++)
        {
            newEnd[x - 1] = locationEnd[x];
        }

        locationEnd = newEnd;
    }



}