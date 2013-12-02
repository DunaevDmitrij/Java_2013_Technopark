package Global.ResSystem;

/**
 * Created with IntelliJ IDEA.
 * User: max
 * Date: 02.12.13
 * Time: 13:12
 */

public class SysParam<ValueType> implements XML_Convertable {
    private String name;         // unique
    private ValueType value;

    public SysParam(String name, ValueType value) {
        super();
        this.name = name;
        this.value = value;
    }

    @Override
    public String getUnique() {
        return this.name;
    }

    public ValueType getValue() {
        return this.value;
    }

    @Override
    public String toXML() {
        return "";
    }

    @Override
    public XML_Convertable fromXML() {
        return this;
    }
}
