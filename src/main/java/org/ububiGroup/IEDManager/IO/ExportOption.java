package org.ububiGroup.IEDManager.IO;

import lombok.Getter;
import org.ububiGroup.IEDManager.IO.generic.baseExporter;
import org.ububiGroup.IEDManager.IO.generic.baseImporter;

public abstract class ExportOption
{
    @Getter private String name;
    @Getter private String extension;
    @Getter private boolean projectSupported;


    protected ExportOption(String name, String extension, boolean projectSupported)
    {
        this.name=name;
        this.extension=extension;
        this.projectSupported=projectSupported;
    }

    protected ExportOption(String name, String extension)
    {
        this(name,extension,true);
    }

    public abstract baseExporter getExporter();
    public abstract baseImporter getImporter();
}
