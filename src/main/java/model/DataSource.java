package model;

import javax.xml.bind.annotation.*;

public class DataSource {

    private String name;
    private String dataSourceReference;

    @XmlAttribute(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "DataSourceReference")
    public String getDataSourceReference() {
        return dataSourceReference;
    }

    public void setDataSourceReference(String dataSourceReference) {
        this.dataSourceReference = dataSourceReference;
    }

    public DataSource(String name) {
        this();
        this.name = name;
    }

    public DataSource() {

    }
}
