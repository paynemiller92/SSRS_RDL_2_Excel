package util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FilePreparer {
    public static void cleanRdlFiles() {
        File dir = new File("/Users/pmiller/Documents/Projects/RLD-2-XLSX");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                try {
                    if(file.getName().contains(".rdl")) {
                        String contents = FileUtils.readFileToString(file);
                        contents = contents.replaceAll("rd:", "");
                        contents = contents.replaceAll("cl:", "");
                        contents = contents.replaceAll("xmlns:rd=\"http://schemas.microsoft.com/SQLServer/reporting/reportdesigner\"", "");
                        contents = contents.replaceAll(" xmlns=\"http://schemas.microsoft.com/sqlserver/reporting/2005/01/reportdefinition\"", "");
                        contents = contents.replaceAll(" xmlns=\"http://schemas.microsoft.com/sqlserver/reporting/2008/01/reportdefinition\"", "");
                        contents = contents.replaceAll(" xmlns=\"http://schemas.microsoft.com/sqlserver/reporting/2010/01/reportdefinition\"", "");
                        contents = contents.replaceAll(" xmlns:cl=\"http://schemas.microsoft.com/sqlserver/reporting/2010/01/componentdefinition\"", "");
                        contents = contents.replaceAll(" <?xml version=\"1.0\" encoding=\"utf-8\"?>\n", "");
                        contents = contents.replaceAll("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n", "");
                        FileUtils.writeStringToFile(file, contents);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
