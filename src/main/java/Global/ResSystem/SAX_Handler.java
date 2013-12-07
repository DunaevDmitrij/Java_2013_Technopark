package Global.ResSystem;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 07.12.13
 * Time: 9:14
 */

public class SAX_Handler <ReadType extends XML_Convertable> extends DefaultHandler {
    private String className;
    private String element = null;
    private ReadType object = null;

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start document");

    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("End document ");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName != this.className) {
            this.element = qName;
            ReflectionHelper.setFieldValue(this.object, this.element, qName);
        } else {
            String className = attributes.getValue(0);
            System.out.println("Class name: " + className);

            try {
                this.object = (ReadType) ReflectionHelper.createIntance(className);
            } catch (Exception e) {
                System.out.println("Ошибка при создании объекта записи: " + className);
                e.printStackTrace();
                this.object = null;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        this.element = null;
    }

    public ReadType getObject() {
        return this.object;
    }
}
