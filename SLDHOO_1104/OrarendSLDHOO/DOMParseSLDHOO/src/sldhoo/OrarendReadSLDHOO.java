package sldhoo;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class OrarendReadSLDHOO {
    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {

        // Ebből a fájlból olvas
        File xmlFile = new File("orarendSLDHOO.xml");

        //A DocumentBuilderFactory-ból megkaojuk a DocumentBuildert
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder dBuilder = factory.newDocumentBuilder();

        Document doc = dBuilder.parse(xmlFile);

        doc.getDocumentElement().normalize();

        System.out.println("Gyökér elem: " + doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("ora");

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            System.out.println("\nAkktuális elem: " + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;

                String id = elem.getAttribute("id");
                String type = elem.getAttribute("tipus");
                Node node1 = elem.getElementsByTagName("targy").item(0);
                String subject = node1.getTextContent();

                Node node2 = elem.getElementsByTagName("nap").item(0);
                String day = node2.getTextContent();

                Node node3 = elem.getElementsByTagName("tol").item(0);
                String from = node3.getTextContent();

                Node node4 = elem.getElementsByTagName("ig").item(0);
                String to = node4.getTextContent();

                Node node5 = elem.getElementsByTagName("helyszin").item(0);
                String place = node5.getTextContent();

                Node node6 = elem.getElementsByTagName("oktato").item(0);
                String lecturer = node6.getTextContent();

                Node node7 = elem.getElementsByTagName("szak").item(0);
                String degreeProgram = node7.getTextContent();

                System.out.println("Óra ID: " + id);
                System.out.println("Típus: " + type);
                System.out.println("Tárgy: " + subject);
                System.out.println("Időpont:");
                System.out.println("\tNap: " + day);
                System.out.println("\tÓraTól: " + from);
                System.out.println("\tÓraIg: " + to);
                System.out.println("Helyszín: " + place);
                System.out.println("Oktató: " + lecturer);
                System.out.println("Szak: " + degreeProgram);
            }
        }
    }
}
