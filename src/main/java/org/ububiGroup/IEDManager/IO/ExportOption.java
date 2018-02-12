package org.ububiGroup.IEDManager.IO;

import lombok.Getter;
import org.ububiGroup.IEDManager.IO.generic.baseExporter;
import org.ububiGroup.IEDManager.IO.generic.baseImporter;

public abstract class ExportOption
{
    @Getter
    private String name;
    @Getter private String extension;
    protected ExportOption(String name, String extension)
    {
        this.name=name;
        this.extension=extension;
    }

    public abstract baseExporter getExporter();
    public abstract baseImporter getImporter();
}
