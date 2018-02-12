package org.ububiGroup.IEDManager.IO.BIM;

import org.ububiGroup.IEDManager.IO.generic.baseExporter;
import org.ububiGroup.IEDManager.Utils.ZipFileUtil;
import org.ububiGroup.IEDManager.model.BIM.BIMMaterial;
import org.ububiGroup.IEDManager.model.BIM.BIMObject;
import org.ububiGroup.IEDManager.model.BIM.BIMObjectType;
import org.ububiGroup.IEDManager.model.generic.BIMData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class IED2Exporter extends baseExporter
{
    private DocumentBuilder docBuilder;
    private Document doc;
    private Element dataElement;

    private String[] headerMaterial=null;
    private String[] headerObject=null;
    private String[] headerObjectType=null;

    public final static String version="0.0.2";

    @Override
    public void init(String filename) throws IOException
    {
        super.init(filename);
        try
        {
            docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        doc = docBuilder.newDocument();
        dataElement = doc.createElement("data");
        doc.appendChild(dataElement);
        dataElement.setAttribute("version",version);
    }

    @Override
    public boolean ExportBIMMaterial(BIMMaterial bimMaterial)
    {
        if(headerMaterial==null)
        {
            headerMaterial = escapeHeader(bimMaterial.getHeaders());
        }
        return ExportBIMData(headerMaterial,bimMaterial);
    }

    @Override
    public boolean ExportBIMObject(BIMObject bimObject)
    {
        if(headerObject==null)
        {
            headerObject = escapeHeader(bimObject.getHeaders());
        }
        return  ExportBIMData(headerObject,bimObject);
    }

    @Override
    public boolean ExportBIMObjectType(BIMObjectType bimObjectType)
    {
        if(headerObjectType==null)
        {
            headerObjectType = escapeHeader(bimObjectType.getHeaders());
        }
        return  ExportBIMData(headerObjectType,bimObjectType);
    }

    @Override
    public void close() throws IOException
    {
        File tmpOut=File.createTempFile("tmp",".xml");
        try
        {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "false");
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            // send DOM to file
            tr.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(tmpOut)));
        }
        catch (TransformerException te) {
            te.printStackTrace();
        }

        HashMap<String,String> files = new HashMap<>();
        files.put("Data.xml",tmpOut.getPath());
        ZipFileUtil.create(files,getFilePath());
    }

    private boolean ExportBIMData(String[] headers, BIMData bimData)
    {
        String[] data = bimData.export();
        String typeName = bimData.getClass().getSimpleName();

        //Create XML Element for the object
        Element elem = doc.createElement(typeName);
        dataElement.appendChild(elem);

        //Write all attribute of the object
        for(int i = 0 ; i < headers.length;i++)
        {
            elem.setAttribute(headers[i],data[i]);
        }
        return true;
    }

    private String[] escapeHeader(String[] headers)
    {
        if(headers==null)
            return new String[]{};

        String[] escapedHeaders = new String[headers.length];
        for(int i = 0; i < headers.length; i++)
        {
            escapedHeaders[i]=headers[i].replaceAll(" ","");
        }
        return escapedHeaders;
    }
}
