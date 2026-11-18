package sldhoo;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class OrarendModifyNeptunkod {

    public static void main(String[] args) {

        try {
            File xmlFile = new File("orarendSLDHOO.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();

            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            // Módosítja az ora elem példányban (05, 06) a helyszin és oktato elemek tartalmát.
            NodeList nList = doc.getElementsByTagName("ora");

            for (int i = 0; i < nList.getLength(); i++) {
                Node ora = doc.getElementsByTagName("ora").item(i);

                NodeList list = ora.getChildNodes();

                for (int temp = 0; temp < list.getLength(); temp++) {
                    Node node = list.item(temp);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) node;
                        if ("helyszin".equals(eElement.getNodeName())) {
                            if ("In I. em. 101".equals(eElement.getTextContent())) {
                                eElement.setTextContent("In I. em. 103");
                            }
                        }
                        if ("oktato".equals(eElement.getNodeName())) {
                            if ("Agárdi Anita".equals(eElement.getTextContent())) {
                                eElement.setTextContent("Példa Péter");
                            }
                        }
                    }
                }
            }

            // Módosítja az ora tipus attribútum értékét gyakorlat-ra értékre!
            Node ora = doc.getElementsByTagName("ora").item(2);

            NamedNodeMap attr = ora.getAttributes();
            Node nodeAttr = attr.getNamedItem("tipus");
            nodeAttr.setTextContent("gyakorlat");

            //  Törli az összes szerdai ora elemet az xml fájlból!
            Node orarend = doc.getFirstChild();
            NodeList childNodes = orarend.getChildNodes();

            for (int count = childNodes.getLength() - 1; count > 0; count--) {
                Node node =childNodes.item(count);

                if (node.getNodeType() == Node.ELEMENT_NODE && "ora".equals(node.getNodeName())) {
                    Element oraElem = (Element)node;
                    String nap = oraElem.getElementsByTagName("nap").item(0).getTextContent();
                    if ("szerda".equals(nap)) {
                        orarend.removeChild(node);
                    }
                }
            }

            // A módosított XML fájlt írja ki a konzolra.
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            System.out.println("---------Módosított XML fájl---------");
            StreamResult consolResult = new StreamResult(System.out);
            transformer.transform(source,consolResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
