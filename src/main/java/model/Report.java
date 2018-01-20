package model;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "Report")
public class Report {

    private String name;
    private List<DataSource> dataSources;
    private List<DataSet> dataSets;

    @XmlElementWrapper(name = "DataSources")
    @XmlElement(name = "DataSource")
    public List<DataSource> getDataSources() {
        return dataSources;
    }

    @XmlElementWrapper(name = "DataSets")
    @XmlElement(name = "DataSet")
    public List<DataSet> getDataSets() {
        return this.dataSets;
    }

    public void setDataSources(List<DataSource> dataSources) {
        this.dataSources = dataSources;
    }

    public void setDataSets(List<DataSet> dataSets) {
        this.dataSets = dataSets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
