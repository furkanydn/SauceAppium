package com.appium.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestUtilities {
    public static final long WAITTIME = 10;

    public HashMap<String, String> parseStringXML(InputStream file) throws IOException, ParserConfigurationException, SAXException {
        HashMap<String,String> stringHashMap;

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);
        document.getDocumentElement().normalize();

        Element root = document.getDocumentElement();
        NodeList nodeList = document.getElementsByTagName("string");

        stringHashMap = IntStream.range(0, nodeList.getLength()).mapToObj(nodeList::item).filter(node -> node.getNodeType() == Node.ELEMENT_NODE).map(node -> (Element) node).collect(Collectors.toMap(element -> element.getAttribute("name"), Node::getTextContent, (a, b) -> b, HashMap::new));
        return stringHashMap;
    }

    public Logger logger(){
        return LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
    }

    public String dateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
