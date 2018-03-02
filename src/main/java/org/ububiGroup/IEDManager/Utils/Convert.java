package org.ububiGroup.IEDManager.Utils;

public final class Convert
{
    public static Double toDouble(String data)
    {
        return toDouble(data,null);
    }

    public static Double toDouble(String data, Double defaultValue)
    {
        Double value = defaultValue;

        if(data!=null && "".compareTo(data)!=0)
        {
            try
            {
                value = Double.parseDouble(data);
            }
            catch(NumberFormatException nfe) {}
        }

        return value;
    }

    public static Integer toInteger(String data)
    {
        return toInteger(data,null);
    }

    public static Integer toInteger(String data, Integer defaultValue)
    {
        Integer value = defaultValue;

        if(data!=null && "".compareTo(data)!=0)
        {
            try
            {
                value = Integer.parseInt(data,10);
            }
            catch(NumberFormatException nfe) {}
        }

        return value;
    }
}
