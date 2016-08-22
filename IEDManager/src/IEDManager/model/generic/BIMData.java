package IEDManager.model.generic;

import IEDManager.Annotation.SerializableVar;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by mathieu on 7/12/2016.
 */
public abstract class BIMData implements Comparable<BIMData>
{

    public abstract long getId();

    protected static Map<Integer,Field> loadMap(BIMData src)
    {
        Map<Integer,Field> map = new TreeMap<Integer, Field>((o1, o2) -> o1.compareTo(o2));
        for(Field field : src.getClass().getDeclaredFields())
        {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for(Annotation annotation : annotations)
            {
                if(annotation instanceof SerializableVar)
                {
                    SerializableVar serializableField = (SerializableVar)annotation;
                    field.setAccessible(true);
                    map.put(serializableField.Order(),field);
                }
            }
        }
        return map;
    }

    public int compareTo(BIMData obj)
    {
        if(obj==null)return 1;

        return new Long(this.getId()).compareTo(obj.getId());
    }

    public String[] export()
    {
        Map<Integer,Field> map = getMap();
        String[] data = new String[map.size()];

        int i=0;
        Iterator it = map.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry<Integer,Field> pair = (Map.Entry<Integer,Field>)it.next();
            try
            {
                data[i]=pair.getValue().get(this).toString();
            }
            catch (Exception e)
            {
                data[i]="";
            }
            i++;
        }

        return data;
    }

    public String[] getHeaders()
    {
        Map<Integer,Field> map = getMap();
        String[] headers = new String[map.size()];

        int i=0;
        Iterator it = map.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry<Integer,Field> pair = (Map.Entry<Integer,Field>)it.next();

            Annotation[] annotations = pair.getValue().getDeclaredAnnotations();
            for(Annotation annotation : annotations)
            {
                if(annotation instanceof SerializableVar)
                {
                    SerializableVar serializableField = (SerializableVar)annotation;
                    headers[i] = serializableField.Name();
                }
            }
            i++;
        }

        return headers;
    }

    public abstract Map<Integer,Field> getMap();
    public abstract boolean load(String[] data);
}
