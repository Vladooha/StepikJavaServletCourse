package com.vladooha.backend.services.realization;
import com.vladooha.backend.services.ResourceService;
import com.vladooha.helpers.ReflectionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class ResourceServiceImpl implements ResourceService {
    private static final Logger logger = LogManager.getLogger(ResourceServiceImpl.class);

    private class SAXParseHandler extends DefaultHandler {
        private final String classtype = "class";

        private String field;
        private String value;
        private Object resultObject;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            logger.info("SAX inputs: \nqName: " + qName + "\nattr[0]: " + attributes.getValue(0));

            if (qName.equals(classtype)) {
                logger.info("SAX found a class: " + attributes.getValue(0));

                resultObject = ReflectionHelper.newInstance(attributes.getValue(0));
            } else {
                logger.info("SAX found a field: " + qName);

                field = qName;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            field = null;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (field != null && resultObject != null) {
                char[] valueChars = new char[length];
                for (int i = start, j = 0; i < start + length; ++i, ++j) {
                    valueChars[j] = ch[i];
                }
                value = new String(valueChars);

                logger.info("It's value: " + value);

                ReflectionHelper.setPrimitiveField(resultObject, field, value);
            }
        }

        public Object getObject() {
            return resultObject;
        }
    }

    private static ResourceServiceImpl instance;

    public static ResourceServiceImpl getInstance() {
        if (instance == null) {
            instance = new ResourceServiceImpl();
        }

        return instance;
    }


    private String link;

    private ResourceServiceImpl() { }

    @Override
    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public Object getResource() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            SAXParseHandler handler = new SAXParseHandler();

            parser.parse(link, handler);

            return handler.getObject();
        } catch (SAXException e) {
            logger.error("XML processing error '" + link + "'", e);

            return null;
        } catch (IOException e) {
            logger.error("Can't get access to file '" + link + "'", e);

            return null;
        } catch (ParserConfigurationException e) {
            logger.error("Wrong parser configuration", e);

            return null;
        }
    }
}
