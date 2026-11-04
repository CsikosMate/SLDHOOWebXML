package sldhoo;


import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMModifySLDHOO {
    public static void main(String[] args) {

        try {
            File xmlFile = new File("SLDHOO_vendeglatas.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();

            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            // Módosítsa a vendég elem példányban (v1) a nev és kor elemek tartalmát!
            NodeList nList = doc.getElementsByTagName("vendeg");

            for (int i = 0; i < nList.getLength(); i++) {
                Node vendeg = doc.getElementsByTagName("vendeg").item(i);

                NodeList list = vendeg.getChildNodes();

                for (int temp = 0; temp < list.getLength(); temp++) {
                    Node node = list.item(temp);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) node;
                        if ("eletkor".equals(eElement.getNodeName())) {
                            if ("26".equals(eElement.getTextContent())) {
                                eElement.setTextContent("22");
                            }
                        }
                        if ("nev".equals(eElement.getNodeName())) {
                            if ("Kiss Hajnalka".equals(eElement.getTextContent())) {
                                eElement.setTextContent("Kiss Piroska");
                            }
                        }
                    }
                }
            }

            // Módosítsa a gyakornok e_gy attribútum értékét e3 értékre!
            Node gyakornok = doc.getElementsByTagName("gyakornok").item(2);

            NamedNodeMap attr = gyakornok.getAttributes();
            Node nodeAttr = attr.getNamedItem("e_gy");
            nodeAttr.setTextContent("e3");

            //  Törölje az összes rendelés elemet az xml fájlból!
            Node vendeglatas = doc.getFirstChild();
            NodeList childNodes = vendeglatas.getChildNodes();

            for (int count = 0; count < childNodes.getLength(); count++) {
                Node node =childNodes.item(count);

                if ("rendeles".equals(node.getNodeName())) {
                    vendeglatas.removeChild(node);
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
