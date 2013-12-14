package Global.ResSystem;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 07.12.13
 * Time: 9:14
 */

public class SAX_Handler <ReadType extends XML_Convertable> extends DefaultHandler {
    private String classFullName;
    private String className;
    private Map<String, ReadType> records;

    private ReadType record = null;
    private StringBuilder fieldValue;
    private String vtype;

    public SAX_Handler(String classFullName) {
        super();
        this.records = new HashMap<>();
        this.classFullName = classFullName;
        this.fieldValue = new StringBuilder();

        int beginIndex = this.classFullName.lastIndexOf(".") + 1;
        if (this.classFullName.lastIndexOf("$") + 1 > beginIndex) {
            beginIndex = this.classFullName.lastIndexOf("$") + 1;
        }
        this.className = this.classFullName.substring(beginIndex, this.classFullName.length());
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals(this.className)) {
            this.vtype = attributes.getValue("vtype");

            try {
                this.record = (ReadType) ReflectionHelper.createInstance(this.classFullName);
            } catch (Exception e) {
                System.out.println("Ошибка при создании объекта записи: " + this.classFullName);
                e.printStackTrace();
                this.record = null;
            }
        } else if (this.record != null) {
            this.fieldValue.setLength(0);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        this.fieldValue.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (this.record != null) {
            if (qName.equals(this.className)) {
                this.records.put(this.record.getUnique(), this.record);
                this.record = null;
                this.vtype = null;
            } else if (this.record.getUniqueFields().contains(qName)) {
                ReflectionHelper.setFieldValue(this.record,
                        "java.lang.String", qName, this.fieldValue.toString());
            } else {
                ReflectionHelper.setFieldValue(this.record,
                        this.vtype, qName, this.fieldValue.toString());
            }
        }
    }

    public Map<String, ReadType> getData() {
        return this.records;
    }
}
