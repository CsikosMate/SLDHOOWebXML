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

public class xPathQuerySldhoo {

    public static void main(String[] args) {

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.parse("studentSLDHOO.xml");
            document.getDocumentElement().normalize();

            // Az XPath készítése
            XPath xPath = XPathFactory.newInstance().newXPath();

            // Meg kell adni az elérési út kifejezést és a csomópont listát:
            //String sldhoo = "/class/hallgato";
            //String sldhoo = "/class/hallgato[@id=2]";
            //String sldhoo = "//hallgato";
            //String sldhoo = "/class/hallgato[2]";
            //String sldhoo = "/class/hallgato[last()]";
            //String sldhoo = "/class/hallgato[last()-1]";
            String sldhoo = "/class/hallgato[position() <=2]";



            System.out.println("\nA futás eredménye:");

            NodeList sLdhoo = (NodeList) xPath.compile(sldhoo).evaluate(document, XPathConstants.NODESET);

            BufferedWriter writer = new BufferedWriter(new FileWriter("hallgato1.xml"));

            writer.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
            writer.write("<hallgatok>");

            // A for ciklus segítségével a NodeList csomópontjain végig kell iterálni.
            for (int i = 0; i < sLdhoo.getLength(); i++) {
                // Kivesszük a NodeList-ből az aktuális Node elemet.
                Node node = sLdhoo.item(i);

                System.out.println("\nAktuális elem: " + node.getNodeName());

                // Meg kell vizsgálni a csomópontot, tesztelni kell a subelement
                if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("hallgato")) {

                    // Node csomópont elemmé konvertálja
                    Element element = (Element) node;

                    String id = element.getAttribute("id");
                    String keresztnev = element.getElementsByTagName("keresztnev").item(0).getTextContent();
                    String vezeteknev = element.getElementsByTagName("vezeteknev").item(0).getTextContent();
                    String becenev = element.getElementsByTagName("becenev").item(0).getTextContent();
                    String kor = element.getElementsByTagName("kor").item(0).getTextContent();


                    System.out.println("Hallgató ID: " + id);
                    System.out.println("Keresztnév: " + keresztnev);
                    System.out.println("Vezetéknév: " + vezeteknev);
                    System.out.println("Becenév: " + becenev);
                    System.out.println("Kor: " + kor);

                    writer.write("<hallgato id=\"" + id + "\">\n");
                    writer.write("        <kersztnev>" + keresztnev + "</kersztnev>\n");
                    writer.write("        <vezeteknev>" + vezeteknev + "</vezeteknev>\n");
                    writer.write("        <becenev>" + becenev + "</becenev>\n");
                    writer.write("        <kor>" + kor + "</kor>\n");
                    writer.write("</hallgato>\n");
                }
            }

            writer.write("</hallgatok>");
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
