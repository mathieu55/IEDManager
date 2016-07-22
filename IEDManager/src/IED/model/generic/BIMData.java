package IED.model.generic;

/**
 * Created by mathieu on 7/12/2016.
 */
public abstract class BIMData implements Comparable<BIMData>
{
    public abstract long getId();

    public int compareTo(BIMData obj)
    {
        if(obj==null)return 1;

        return new Long(this.getId()).compareTo(obj.getId());
    }
}
