package org.ububiGroup.IEDManager.IO.BIM;

import org.ububiGroup.IEDManager.IO.generic.IEDImportHandler;
import org.ububiGroup.IEDManager.IO.generic.baseImporter;
import org.ububiGroup.IEDManager.Utils.ZipFileUtil;
import org.ububiGroup.IEDManager.model.BIM.BIMMaterial;
import org.ububiGroup.IEDManager.model.BIM.BIMObject;
import org.ububiGroup.IEDManager.model.BIM.BIMObjectType;
import org.ububiGroup.IEDManager.model.generic.BIMData;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class IEDImporter extends baseImporter
{
    private File tmpFile;
    private Node dataNode;
    private Document doc=null;
    private final static String OBJECTS_TAG="Objects";
    private final static String MATERIALS_TAG="Materials";

    private String[] headerMaterial = escapeHeader(BIMMaterial.getFactory().create().getHeaders());
    private String[] headerObject = escapeHeader(BIMObject.getFactory().create().getHeaders());
    private String[] headerObjectType = escapeHeader(BIMObjectType.getFactory().create().getHeaders());

    private IEDImportHandler<BIMMaterial> bimMaterialHandler;
    private IEDImportHandler<BIMObject> bimObjectHandler;
    private IEDImportHandler<BIMObjectType> bimObjectTypeHandler;

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
        boolean result=true;
        this.bimMaterialHandler=bimMaterialHandler;
        this.bimObjectHandler = bimObjectHandler;
        this.bimObjectTypeHandler = bimObjectTypeHandler;

        NodeList lstNode = dataNode.getChildNodes();

        //For all object types node
        for(int i=0;i<lstNode.getLength();i++)
        {
            result&=processObjectType(lstNode.item(i));
        }

        return result;
    }

    protected boolean processObjectType(Node objectTypeNode)
    {
        boolean result=true;
        if(bimObjectTypeHandler!=null)
        {
            BIMObjectType tmpObj = new BIMObjectType();
            result&=ProcessElement(objectTypeNode, tmpObj, headerObjectType);
            if(tmpObj.getId()!=-1)bimObjectTypeHandler.processData(tmpObj);
        }

        //Process Objects and Materials
        if(objectTypeNode.hasChildNodes())
        {
            NodeList lstNode = objectTypeNode.getChildNodes();
            for(int i=0;i<lstNode.getLength();i++)
            {
                Node currentNode = lstNode.item(i);
                String tagName = currentNode.getNodeName();
                if(OBJECTS_TAG.compareTo(tagName)==0)
                {
                    result&=processObjects(currentNode);
                }
                else if(MATERIALS_TAG.compareTo(tagName)==0)
                {
                    result&=processMaterials(currentNode);
                }
            }
        }

        return result;
    }

    protected boolean processObjects(Node objectsNode)
    {
        if(bimObjectHandler==null)
            return true;

        boolean result=true;
        if(objectsNode.hasChildNodes())
        {
            NodeList lstNode = objectsNode.getChildNodes();
            for (int i = 0; i < lstNode.getLength(); i++)
            {
                BIMObject tmpObj = new BIMObject();
                ProcessElement(lstNode.item(i), tmpObj, headerObject);
                bimObjectHandler.processData(tmpObj);
            }
        }

        return result;
    }

    protected boolean processMaterials(Node materialsNode)
    {
        if(bimMaterialHandler==null)
            return true;

        boolean result=true;
        if(materialsNode.hasChildNodes())
        {
            NodeList lstNode = materialsNode.getChildNodes();
            for (int i = 0; i < lstNode.getLength(); i++)
            {
                BIMMaterial tmpObj = new BIMMaterial();
                ProcessElement(lstNode.item(i), tmpObj, headerMaterial);
                bimMaterialHandler.processData(tmpObj);
            }
        }

        return result;
    }

    protected boolean ProcessElement(Node xmlData,BIMData dataObj,String[] headers)
    {
        String[] data = new String[headers.length];
        NamedNodeMap attributes =xmlData.getAttributes();

        for(int i =0;i<headers.length;i++)
        {
            Node tmp = attributes.getNamedItem(headers[i]);
            if(tmp!=null)
                data[i]=attributes.getNamedItem(headers[i]).getNodeValue();
            else
                data[i]="";
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
