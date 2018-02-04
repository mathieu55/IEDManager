package org.ububiGroup.IEDManager.IO.BIM;

import lombok.Getter;
import org.ububiGroup.IEDManager.IO.generic.BIMDataFactory;
import org.ububiGroup.IEDManager.IO.generic.IEDImportHandler;
import org.ububiGroup.IEDManager.IO.generic.baseImporter;
import org.ububiGroup.IEDManager.Utils.ZipFileUtil;
import org.ububiGroup.IEDManager.model.BIM.BIMMaterial;
import org.ububiGroup.IEDManager.model.BIM.BIMObject;
import org.ububiGroup.IEDManager.model.BIM.BIMObjectType;
import org.ububiGroup.IEDManager.model.generic.BIMData;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Handler;

public class IEDImporter extends baseImporter
{
    private File tmpFile;
    private Node dataNode;
    private Document doc=null;

    @Override
    public void init(String filepath) throws IOException
    {
        super.init(filepath);
        HashMap<String,File> files=ZipFileUtil.extractFiles(filepath);
        tmpFile=files.getOrDefault("Data.xml",null);

        if(tmpFile==null) throw new IOException(filepath+" is not a valid IED file");

        try {
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            doc = dBuilder.parse(tmpFile);
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        catch (SAXException e)
        {
            e.printStackTrace();
        }

        dataNode = doc.getDocumentElement();
    }

    @Override
    public boolean ProcessAll(IEDImportHandler<BIMMaterial> bimMaterialHandler, IEDImportHandler<BIMObject> bimObjectHandler, IEDImportHandler<BIMObjectType> bimObjectTypeHandler)
    {
        boolean succeed = true;
        NodeList lstNode = dataNode.getChildNodes();

        String[] headerMaterial = escapeHeader(BIMMaterial.getFactory().create().getHeaders());
        String[] headerObject = escapeHeader(BIMObject.getFactory().create().getHeaders());
        String[] headerObjectType = escapeHeader(BIMObjectType.getFactory().create().getHeaders());

        for(int i=0;i<lstNode.getLength();i++)
        {
            Node currentNode = lstNode.item(i);
            String tagName = currentNode.getNodeName();
            if(tagName.compareTo(BIMMaterial.class.getSimpleName())==0)
            {
                if(bimMaterialHandler!=null)
                {
                    BIMMaterial tmpObj = new BIMMaterial();
                    ProcessElement(currentNode, tmpObj, headerMaterial);
                    bimMaterialHandler.processData(tmpObj);
                }
            }
            else if(tagName.compareTo(BIMObject.class.getSimpleName())==0)
            {
                if(bimObjectHandler!=null)
                {
                    BIMObject tmpObj = new BIMObject();
                    ProcessElement(currentNode, tmpObj, headerObject);
                    bimObjectHandler.processData(tmpObj);
                }
            }
            else if(tagName.compareTo(BIMObjectType.class.getSimpleName())==0)
            {
                if(bimObjectTypeHandler!=null)
                {
                    BIMObjectType tmpObj = new BIMObjectType();
                    ProcessElement(currentNode, tmpObj, headerObjectType);
                    bimObjectTypeHandler.processData(tmpObj);
                }
            }
            else
            {
                System.out.println("Processing error, unknown tag :"+tagName);
                succeed = false;
            }
        }

        return succeed;
    }

    protected boolean ProcessElement(Node xmlData,BIMData dataObj,String[] headers)
    {
        String[] data = new String[headers.length];
        NamedNodeMap attributes =xmlData.getAttributes();

        for(int i =0;i<headers.length;i++)
        {
            data[i]=attributes.getNamedItem(headers[i]).getNodeValue();
        }
        dataObj.load(data);

        return true;
    }

    @Override
    public void close() throws IOException
    {
        tmpFile.deleteOnExit();
    }

    protected String[] escapeHeader(String[] headers)
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
