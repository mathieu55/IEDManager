package IED.IO.generic;

/**
 * Created by mathieu on 6/19/2016.
 */
public abstract class IEDFile{

    protected static final byte LineSeparatorAnsiCode = 29;
    protected static final byte FieldSeparatorAnsiCode = 30;

    protected static final String LineSeparator = ""+((char)LineSeparatorAnsiCode);
    protected static final String FieldSeparator = ""+((char)FieldSeparatorAnsiCode);

}
