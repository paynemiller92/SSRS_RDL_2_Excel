package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Field {
    private String name;
    private String dataField;
    private String type;

    @XmlAttribute(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "DataField")
    public String getDataField() {
        return dataField;
    }

    public void setDataField(String dataField) {
        this.dataField = dataField;
    }

    @XmlElement(name = "TypeName")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
