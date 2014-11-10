package Utils.LocationManipulation;

/**
 * Created by alvaro on 10/30/14.
 */
public class LocationSet {
    private Location locationStart;
    private Location[] locationEnd;

    /**
     *
     * @param start starting location
     * @param ends all the possible points to move to.
     */
    public LocationSet(Location start, Location[] ends) {
        locationStart = start;
        locationEnd = ends;
    }
    public  LocationSet(Location start)
    {
        this(start, null);
    }

    public LocationSet()
    {
        this(null);
    }


    public Location getStart() {
        return locationStart;
    }

    public void setLocationStart(Location l)
    {
        locationStart = l;
    }

    public Location getEnd(int x) {
        return locationEnd[x];
    }

    public void addEnd(Location l) {
        if (locationEnd != null) {
            Location[] newEnd = new Location[locationEnd.length + 1];

            newEnd[locationEnd.length] = l;
            for (int x = 0; x < locationEnd.length; x++) {
                newEnd[x] = locationEnd[x];
            }
            locationEnd = newEnd;

        } else {
            Location[] newEnd = new Location[1];
            newEnd[0] = l;
            locationEnd = newEnd;

        }

    }

    public Location getIndex(int x) {
        return locationEnd[x];
    }

    public int destinationNum() {
        if (locationEnd == null || locationEnd.length == 0) {
            return 0;
        } else {
            return locationEnd.length;
        }
    }

    public void removeIndex(int i) {
        if (locationEnd.length > 1) {
            Location[] newEnd = new Location[locationEnd.length - 1];
            for (int x = 0; x < i; x++) {
                newEnd[x] = locationEnd[x];
            }
            for (int x = i; x < locationEnd.length - 1; x++) {
                newEnd[x] = locationEnd[x + 1];
            }

            locationEnd = newEnd;
        } else {
            locationEnd = null;
        }
    }


}