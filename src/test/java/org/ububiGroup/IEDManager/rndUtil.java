package org.ububiGroup.IEDManager;

import lombok.Getter;
import org.ububiGroup.IEDManager.model.BIM.BIMMaterial;
import org.ububiGroup.IEDManager.model.BIM.BIMObject;
import org.ububiGroup.IEDManager.model.BIM.BIMObjectType;
import org.ububiGroup.IEDManager.model.BIM.BIMProject;

import java.util.*;

public class rndUtil
{
    @Getter
    private static long seed=new Date().getTime();
    private static Random rnd=new Random(seed);

    private final static String specialChar = "%$?&[]{}<>|_";
    private final static String escapeChar = "\r\n\t";

    private final static String number = "0123456789";
    private final static String operator = "+-/\\*^()=";


    private final static String lowerAlpha = "abcdefghijklmnopqrstuvwxyz";
    private final static String upperAlpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static String puntuation = ",.;:\"'!?";
    private final static String whitespace = " ";

    private static String[] lstCharSet=new String[]{specialChar, escapeChar, number, operator, lowerAlpha, upperAlpha, puntuation, whitespace};

    public static void setSeed(Long seed)
    {
        rndUtil.seed=seed;
        rnd=new Random(seed);
    }

    public static String getRandomString(int minimumLength, int maximumLength)
    {
        return getRandomString(minimumLength,maximumLength,charSet.alphaNumSpaceFlags);
    }

    public static String getRandomString(int minimumLength, int maximumLength, int flagCharSet)
    {
        String charset = getCharSet(flagCharSet);
        int charsetLen = charset.length();

        int len = getRandomInt(minimumLength,maximumLength);
        char[] rndString = new char[len];

        for(int i =0; i<len; i++)
            rndString[i]=charset.charAt(getRandomInt(0,charsetLen-1));

        return new String(rndString);
    }

    public static String getCharSet(int flagCharSet)
    {
        String tmpCharSet="";
        for(charSet i:charSet.values())
        {
            if((flagCharSet & i.flag) == i.flag)
            {
                tmpCharSet+=lstCharSet[i.ordinal()];
            }
        }
        return tmpCharSet;
    }

    public static int getRandomInt(int minimum, int maximum)
    {
        return rnd.nextInt((maximum - minimum) + 1) + minimum;
    }

    public static double getRandomDouble(double minimum, double maximum)
    {
        return minimum + ((maximum - minimum) * rnd.nextDouble());
    }

    public static BIMProject[] randomProjects(int minimum, int maximum)
    {
        int nbMaterial= minimum==maximum ? minimum : rndUtil.getRandomInt(minimum,maximum);
        BIMProject[] lstProject = new BIMProject[nbMaterial];

        for(int i=0; i<lstProject.length; i++)
        {
            String UUID =rndUtil.getRandomString(25,50,charSet.alphaNumFlags);
            Double lifeTime = rndUtil.getRandomDouble(1,100);
            Integer numberOfStorey = rndUtil.getRandomInt(1,100);;
            String buildingFunction=rndUtil.getRandomString(25,50,charSet.alphaNumSpaceFlags);
            Double latitude = rndUtil.getRandomDouble(0,1000);
            Double longitude = rndUtil.getRandomDouble(0,1000);
            String address = rndUtil.getRandomString(25,150,charSet.alphaNumSpaceFlags);
            String structureType = rndUtil.getRandomString(25,50,charSet.alphaNumSpaceFlags);;
            lstProject[i]=new BIMProject();
        }

        return lstProject;
    }

    public static HashMap<Long,BIMMaterial> randomMaterials(int minimum, int maximum)
    {
        HashMap<Long,BIMMaterial> lstMaterial = new HashMap<>();

        int nbMaterial=rndUtil.getRandomInt(minimum,maximum);

        long id=0;
        for(;id<nbMaterial;id++)
        {
            long ownerId = (long)rndUtil.getRandomInt(0,10000);
            String name = rndUtil.getRandomString(10,100);
            double area = rndUtil.getRandomDouble(1.0,100.0);
            double volume = rndUtil.getRandomDouble(1.0,10.0);
            double ratioArea  = rndUtil.getRandomDouble(0,1);
            double ratioVolume = rndUtil.getRandomDouble(1.0,70);
            String description = rndUtil.getRandomString(50,200,rndUtil.charSet.all & ~rndUtil.charSet.escapeChar.flag);
            String manufacturer = rndUtil.getRandomString(10,100);
            String mark = rndUtil.getRandomString(10,100);
            String function = rndUtil.getRandomString(10,100);
            lstMaterial.put(id,new BIMMaterial(id,ownerId,name,area,volume,ratioArea,ratioVolume,description,manufacturer,mark,function));
        }

        return lstMaterial;
    }

    public static HashMap<Long,BIMObject> randomObjects(int minimum, int maximum)
    {
        HashMap<Long,BIMObject> lstObject = new HashMap<>();

        int nbObj=rndUtil.getRandomInt(minimum,maximum);

        long id=0;
        for(;id<nbObj;id++)
        {
            String name = rndUtil.getRandomString(10,100);
            long typeId = rndUtil.getRandomInt(0,10000);
            String objectType = rndUtil.getRandomString(0,100);
            String category = rndUtil.getRandomString(0,100);
            String uniformatCode = rndUtil.getRandomString(0,7, charSet.alphaNumFlags);
            double area = rndUtil.getRandomDouble(1.0,100.0);
            double volume = rndUtil.getRandomDouble(1.0,10.0);

            lstObject.put(id,new BIMObject(id,name,typeId,objectType,category,uniformatCode,area,volume));
        }

        return lstObject;
    }

    public static HashMap<Long,BIMObjectType> randomObjectTypes(int minimum, int maximum)
    {
        HashMap<Long,BIMObjectType> lstObjectType = new HashMap<>();

        int nbObjType=rndUtil.getRandomInt(minimum,maximum);

        long id=0;
        for(;id<nbObjType;id++)
        {
            String name = rndUtil.getRandomString(10,100);
            String uniformatCode = rndUtil.getRandomString(0,7, charSet.alphaNumFlags);
            String uniformatDesc = rndUtil.getRandomString(20,100);
            String familyName = rndUtil.getRandomString(20,50);

            lstObjectType.put(id,new BIMObjectType(id,name,uniformatCode,uniformatDesc,familyName));
        }

        return lstObjectType;
    }

    public enum charSet
    {
        specialChar,
        escapeChar,
        number,
        operator,
        lowerAlpha ,
        upperAlpha,
        puntuation,
        whitespace;

        public final int flag;
        charSet()
        {
            this.flag=1<<this.ordinal();
        }

        public static int getFlags(charSet... args)
        {
            int flags=0;
            for(charSet i : args)
            {
                flags|=i.flag;
            }
            return flags;
        }

        public static List<charSet> convertToEnumList(int flags)
        {
            List<charSet> lstCharSet = new ArrayList<>();

            for(charSet i: charSet.values())
            {
                if((flags & i.flag)==i.flag)
                {
                    lstCharSet.add(i);
                }
            }

            return lstCharSet;
        }

        public final static  int all = ~0;
        public final static int mathFlags = charSet.getFlags(charSet.number,charSet.operator);
        public final static int alphaFlags = charSet.getFlags(charSet.upperAlpha,charSet.lowerAlpha);
        public final static int alphaSpaceFlags = charSet.getFlags(charSet.upperAlpha,charSet.lowerAlpha,charSet.whitespace);
        public final static int alphaNumFlags = charSet.getFlags(charSet.upperAlpha,charSet.lowerAlpha,charSet.number);
        public final static int alphaNumSpaceFlags = charSet.getFlags(charSet.upperAlpha,charSet.lowerAlpha,charSet.number, charSet.whitespace);

    }
}
