package com.rumpus.common.util.FileIO;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.UUID;

import com.rumpus.common.ICommon;
import com.rumpus.common.Logger.AbstractCommonLogger.LogLevel;
import com.rumpus.common.Model.AbstractModel;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;

/**
 * TODO: Look into using com.fasterxml.jackson.dataformat.xml.XmlMapper for parsing XML content.
 */
final public class XmlReader extends AbstractFileIO {

    private XmlReader() {
        super("XmlReader");
    }

    public static XmlReader create() {
        return new XmlReader();
    }

    @Override
    public <MODEL extends AbstractModel<MODEL, UUID>> Optional<MODEL[]> readModelsFromFile(final String filePath, final Type type) {

        // If the file content is empty, log an error and return an empty Optional
        if (filePath.isEmpty()) {
            LOG_THIS(LogLevel.ERROR, "File content is empty or could not be read: " + filePath);
            return Optional.empty();
        }

        // Parse the XML content into an array of models
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            LOG_THIS(LogLevel.ERROR, "Error creating XML document builder: ", e.getClass().getSimpleName(), e.getMessage());
            return Optional.empty();
        }

        Document doc;
        try {
            doc = builder.parse(filePath);
        } catch (SAXException | IOException e) {
            LOG_THIS(LogLevel.ERROR, "Error parsing XML from file: " + filePath, e.getClass().getSimpleName(), e.getMessage());
            return Optional.empty();
        }

        // TODO: Stopped here. Need to implement parsing of XML content into an array of models.
        // I'm looking into different ways to do this. I stopped when I was looking through buildSrc. 
        // I should use this: com.fasterxml.jackson.dataformat.xml.XmlMapper but I need to add it to the buildSrc. Trying to figure out the best spot.
        doc.getDocumentElement().normalize();

        return Optional.empty();
    }

    private static void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(XmlReader.class, level, args);
    }

    @Override
    public <MODEL extends AbstractModel<MODEL, UUID>> Optional<MODEL> readModelFromFile(String filePath, Type type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readModelFromFile'");
    }

    @Override
    public <MODEL extends AbstractModel<MODEL, UUID>> boolean writeModelsToFile(String filePath, MODEL[] models) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeModelsToFile'");
    }    
}
