package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

public class Query {
    private String commandText;
    private List<QueryParameter> queryParameters;

    @XmlElement(name = "CommandText")
    public String getCommandText() {
        return this.commandText;
    }

    public void setCommandText(String commandText) {
        this.commandText = commandText;
    }

    @XmlElementWrapper(name = "QueryParameters")
    @XmlElement(name = "QueryParameter")
    public List<QueryParameter> getQueryParameters() {
        return queryParameters;
    }

    public void setQueryParameters(List<QueryParameter> queryParameters) {
        this.queryParameters = queryParameters;
    }
}
