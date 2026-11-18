package xpathsldhoo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.xpath.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import org.xml.sax.SAXException;


public class XPathModifySldhoo {


    public static void main(String[] args) {

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.parse("orarendSLDHOO.xml");
            document.getDocumentElement().normalize();

            // Az XPath készítése
            XPath xPath = XPathFactory.newInstance().newXPath();

            // A 101 termet átírjuk 103-ra
            String sldhoo = "/orarend/ora/helyszin[.='In I. em. 101']";

            NodeList Sldhoo = (NodeList) xPath.compile(sldhoo).evaluate(document, XPathConstants.NODESET);

            for (int i = 0; i < Sldhoo.getLength(); i++) {
                Sldhoo.item(i).setTextContent("In I. em. 103");
            }

            // Az órát, aminek az ID 1, áttesszük csütörtökre
            sldhoo = "/orarend/ora[@id = 1]/idopont/nap";

            Sldhoo = (NodeList) xPath.compile(sldhoo).evaluate(document, XPathConstants.NODESET);

            for (int i = 0; i < Sldhoo.getLength(); i++) {
                Sldhoo.item(i).setTextContent("csütörtök");
            }


            // Az óra típusát, aminek az ID 2, előadásról gyakorlatra írjuk át
            sldhoo = "/orarend/ora[@id = 1]/@tipus";

            Sldhoo = (NodeList) xPath.compile(sldhoo).evaluate(document, XPathConstants.NODESET);

            for (int i = 0; i < Sldhoo.getLength(); i++) {
                Sldhoo.item(i).setTextContent("gyakorlat");
            }

            sldhoo = "/orarend/ora";
            Sldhoo = (NodeList) xPath.compile(sldhoo).evaluate(document, XPathConstants.NODESET);

            System.out.println("\nA futás eredménye:");

            BufferedWriter writer = new BufferedWriter(new FileWriter("orarend1.xml"));

            writer.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
            writer.write("<orarend>");

            // A for ciklus segítségével a NodeList csomópontjain végig kell iterálni.
            for (int i = 0; i < Sldhoo.getLength(); i++) {
                // Kivesszük a NodeList-ből az aktuális Node elemet.
                Node node = Sldhoo.item(i);

                System.out.println("\nAktuális elem: " + node.getNodeName());

                // Meg kell vizsgálni a csomópontot, tesztelni kell a subelement
                if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("ora")) {

                    // Node csomópont elemmé konvertálja
                    Element element = (Element) node;

                    String id = element.getAttribute("id");
                    String type = element.getAttribute("tipus");
                    String subject = element.getElementsByTagName("targy").item(0).getTextContent();
                    String day = element.getElementsByTagName("nap").item(0).getTextContent();
                    String from = element.getElementsByTagName("tol").item(0).getTextContent();
                    String to = element.getElementsByTagName("ig").item(0).getTextContent();
                    String place = element.getElementsByTagName("helyszin").item(0).getTextContent();
                    String lecturer = element.getElementsByTagName("oktato").item(0).getTextContent();
                    String degreeProgram = element.getElementsByTagName("szak").item(0).getTextContent();

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

                    writer.write("<ora id=\"" + id + "\" tipus=\"" + type + "\">\n");
                    writer.write("    <targy>" + subject + "</targy>\n");
                    writer.write("    <idopont>\n");
                    writer.write("        <nap>" + day + "</nap>\n");
                    writer.write("        <tol>" + from + "</tol>\n");
                    writer.write("        <ig>" + to + "</ig>\n");
                    writer.write("    </idopont>\n");
                    writer.write("    <helyszin>" + place + "</helyszin>\n");
                    writer.write("    <oktato>" + lecturer + "</oktato>\n");
                    writer.write("    <szak>" + degreeProgram + "</szak>\n");
                    writer.write("</ora>\n");
                }
            }

            writer.write("</orarend>");
            writer.close();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathException e) {
            e.printStackTrace();
        }
    }
}
