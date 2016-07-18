package IED.model.generic;

/**
 * Created by mathieu on 7/12/2016.
 */
public abstract class BIMFactory<E extends BIMData>
{
    public abstract E create();
}