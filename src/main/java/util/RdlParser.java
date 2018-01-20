package util;

import model.Report;
import org.apache.commons.io.FileUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RdlParser {

    public static Report getRdl(File file) {
        try {
             FileUtils.readFileToString(file);
             Report report;
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(Report.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                report = (Report) jaxbUnmarshaller.unmarshal(file);
                RdlSanitizer.sanitizeFields(report);
                String fileName = file.getName();
                report.setName(fileName.replace("_", ""));
                report.setName(fileName.replace(".rdl", ""));
                return report;
            } catch (JAXBException e) {
                System.out.println("File: " + file.getName() + "is corrupt.");
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
