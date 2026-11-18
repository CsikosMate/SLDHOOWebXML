package sldhoo;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class OrarendQuerySldhoo {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        File xmlFile = new File("orarendSLDHOO.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();

        Document doc = dBuilder.parse(xmlFile);

        doc.getDocumentElement().normalize();

        System.out.println("Gyökér elem: " + doc.getDocumentElement().getNodeName() + "\n");

        // Lekérdezi azokat az órákat, amik a 101 teremben vannak
        System.out.println("Azok az órák, amik a 101 teremben vannak:\n");

        NodeList nodeList = doc.getElementsByTagName("ora");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;

                Node node3;

                //Lekérdezi azokat az órákat, amelyek helyszíne: In I. em. 101
                for (int j = 0; j < elem.getElementsByTagName("helyszin").getLength(); j++) {
                    node3 = elem.getElementsByTagName("helyszin").item(j);
                    String helyszin = node3.getTextContent();
                    // Ha ott van, akkor kiíratom az adatait
                    if ("In I. em. 101".equals(helyszin)) {

                        String id = elem.getAttribute("id");
                        String type = elem.getAttribute("tipus");
                        Node node1 = elem.getElementsByTagName("targy").item(0);
                        String subject = node1.getTextContent();

                        Node node2 = elem.getElementsByTagName("nap").item(0);
                        String day = node2.getTextContent();

                        Node node8 = elem.getElementsByTagName("tol").item(0);
                        String from = node8.getTextContent();

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
                        System.out.println("Szak: " + degreeProgram + "\n");

                    }
                }
            }
        }

        System.out.println("----------------------");

        //  Lekérdezi a délelőtti órák nevét és idejét
        System.out.println("A délelőtti órák neve és ideje:\n");

        nodeList = doc.getElementsByTagName("ora");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;

                Node node1 = elem.getElementsByTagName("targy").item(0);
                String subject = node1.getTextContent();

                Node node2 = elem.getElementsByTagName("nap").item(0);
                String day = node2.getTextContent();

                Node node8 = elem.getElementsByTagName("tol").item(0);
                String from = node8.getTextContent();

                Node node4 = elem.getElementsByTagName("ig").item(0);
                String to = node4.getTextContent();

                String idopont = day + " " + from + " órától " + to + " óráig";

                System.out.println("Tárgy: " + subject);
                System.out.println("Időpont: " + idopont + "\n");

            }
        }

        System.out.println("----------------------");

        //  Lekérdezi azokat az oktatókat, akik előadást tartanak

        System.out.println("Azok az oktatók, akik előadást tartanak:\n");

        nodeList = doc.getElementsByTagName("ora");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;

                if ("előadás".equals(elem.getAttribute("tipus"))) {

                    Node node6 = elem.getElementsByTagName("oktato").item(0);
                    String lecturer = node6.getTextContent();

                    System.out.println("Oktató: " + lecturer);

                }
            }
        }
    }
}
