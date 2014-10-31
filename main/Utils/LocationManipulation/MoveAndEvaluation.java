package Utils.LocationManipulation;

/**
 * Created by Alvarp on 10/30/2014.
 */
public class MoveAndEvaluation {
    private Location lStart;
    private Location lEnd;
    private double eval;
    public MoveAndEvaluation(Location start, Location end, double evaluation)
    {
        lStart = start;
        lEnd = end;
        eval =  evaluation;
    }

    public double getEval()
    {
        return eval;
    }

    public Location[] getDirections()
    {
       Location[] locs = new Location[2];
        locs[0] = lStart;
        locs[1] = lEnd;
        return locs;
    }
}
