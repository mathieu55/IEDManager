package org.ububiGroup.IEDManager.IO.BIM;

import lombok.Getter;
import lombok.Setter;
import org.ububiGroup.IEDManager.IO.generic.baseExporter;
import org.ububiGroup.IEDManager.Utils.ZipFileUtil;
import org.ububiGroup.IEDManager.model.BIM.BIMMaterial;
import org.ububiGroup.IEDManager.model.BIM.BIMObject;
import org.ububiGroup.IEDManager.model.BIM.BIMObjectType;
import org.ububiGroup.IEDManager.model.BIM.BIMProject;
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

public class IEDExporter extends baseExporter
{
    private final static String OBJECTS_TAG="Objects";
    private final static String MATERIALS_TAG="Materials";

    private DocumentBuilder docBuilder;
    private Document doc;

    private Element fileElement;
    private Element dataElement;

    private boolean BIMProjectExported=false;

    private String[] headerProject=null;
    private String[] headerMaterial=null;
    private String[] headerObject=null;
    private String[] headerObjectType=null;

    private HashMap<Long, typeElem> lstType=new HashMap<>();

    public final static String version="0.0.3";

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
        fileElement = doc.createElement("IEDFile");
        doc.appendChild(fileElement);

        dataElement = doc.createElement("data");
        fileElement.appendChild(dataElement);
        dataElement.setAttribute("version",version);
    }

    @Override
    public boolean ExportBIMProject(BIMProject bimProject) throws IOException
    {
        if(BIMProjectExported)
            throw new IOException("You can export only one BIMproject");

        if(headerProject==null)
        {
            headerProject = escapeHeader(bimProject.getHeaders());
        }

        BIMProjectExported=ExportBIMData(headerProject,bimProject,fileElement)!=null;
        return BIMProjectExported;
    }

    @Override
    public boolean ExportBIMMaterial(BIMMaterial bimMaterial)
    {
        if(headerMaterial==null)
        {
            headerMaterial = escapeHeader(bimMaterial.getHeaders());
        }
        typeElem typeMap=getOrCreateTypeElem(bimMaterial.getTypeId());
        return ExportBIMData(headerMaterial,bimMaterial,typeMap.getMaterialsElement())!=null;
    }

    @Override
    public boolean ExportBIMObject(BIMObject bimObject)
    {
        if(headerObject==null)
        {
            headerObject = escapeHeader(bimObject.getHeaders());
        }

        typeElem typeMap=getOrCreateTypeElem(bimObject.getTypeId());
        return  ExportBIMData(headerObject,bimObject,typeMap.getObjectsElement())!=null;
    }

    @Override
    public boolean ExportBIMObjectType(BIMObjectType bimObjectType)
    {
        if(headerObjectType==null)
        {
            headerObjectType = escapeHeader(bimObjectType.getHeaders());
        }

        typeElem typeMap = getOrCreateTypeElem(bimObjectType.getId());
        ExportAttributesFromBIMData(headerObjectType,bimObjectType,typeMap.getObjectTypeElement());

        return true;
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

    private typeElem getOrCreateTypeElem(long id)
    {
        typeElem elemMap = lstType.getOrDefault(id,null);

        if(elemMap==null)
        {
            Element elem = doc.createElement(BIMObjectType.class.getSimpleName());
            dataElement.appendChild(elem);

            Element elemObjects = doc.createElement(OBJECTS_TAG);
            elem.appendChild(elemObjects);
            Element elemMaterials = doc.createElement(MATERIALS_TAG);
            elem.appendChild(elemMaterials);

            elemMap=new typeElem(elem,elemObjects,elemMaterials);
            this.lstType.put(id,elemMap);
        }
        return elemMap;
    }

    private Element ExportBIMData(String[] headers, BIMData bimData, Element root)
    {
        String typeName = bimData.getClass().getSimpleName();

        //Create XML Element for the object
        Element elem = doc.createElement(typeName);
        if(root!=null)root.appendChild(elem);

        ExportAttributesFromBIMData(headers, bimData, elem);
        return elem;
    }

    private void ExportAttributesFromBIMData(String[] headers, BIMData bimData, Element elem)
    {
        String[] data = bimData.export();
        //Write all attribute of the object
        for(int i = 0 ; i < headers.length;i++)
        {
            elem.setAttribute(headers[i],data[i]);
        }
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

    public class typeElem
    {
        public typeElem(Element objectTypeElement, Element objectsElement, Element materialsElement)
        {
            this.objectTypeElement=objectTypeElement;
            this.objectsElement=objectsElement;
            this.materialsElement=materialsElement;
        }

        @Getter @Setter private Element objectTypeElement;
        @Getter @Setter private Element objectsElement;
        @Getter @Setter private Element materialsElement;

    }
}
