package IED.IO.generic;

/**
 * Created by mathieu on 6/19/2016.
 */
public abstract class IEDFile{

    protected static final byte DefaultLineSeparatorAnsiCode = 29;
    protected static final byte DefaultFieldSeparatorAnsiCode = 30;

    protected static final String DefaultLineSeparator = ""+((char)DefaultLineSeparatorAnsiCode);
    protected static final String DefaultFieldSeparator = ""+((char)DefaultFieldSeparatorAnsiCode);

    protected byte lineSeparatorAnsiCode = DefaultLineSeparatorAnsiCode;
    protected byte fieldSeparatorAnsiCode = DefaultFieldSeparatorAnsiCode;

    protected String lineSeparator = ""+((char) lineSeparatorAnsiCode);
    protected String fieldSeparator = ""+((char) fieldSeparatorAnsiCode);

    public void setSeparators(char fieldSeparator, char  lineSeparator)
    {
        setFieldSeparator(fieldSeparator);
        setLineSeparator(lineSeparator);
    }

    public void setFieldSeparator(char fieldSeparator)
    {
        this.fieldSeparator=new Character(fieldSeparator).toString();
        this.fieldSeparatorAnsiCode = (byte)fieldSeparator;
    }

    public void setLineSeparator(char  lineSeparator)
    {
        this.lineSeparator=new Character(lineSeparator).toString();
        this.lineSeparatorAnsiCode = (byte)lineSeparator;
    }
}
