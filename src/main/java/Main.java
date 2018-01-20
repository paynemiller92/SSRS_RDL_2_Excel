import model.Report;
import org.apache.poi.ss.usermodel.Workbook;
import util.ExcelUtil;
import util.FilePreparer;
import util.RdlParser;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Main {

    private static final String[] filenames = {"Agent_Activity_Report.rdl"};

    public static void main(String [] args) throws IOException {
        FilePreparer.cleanRdlFiles();
        Report report;
        File dir = new File("/Users/pmiller/Documents/Projects/RLD-2-XLSX");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                if (file.getName().contains(".rdl")) {
                    report = RdlParser.getRdl(file);
                    List<Report> reports = Collections.singletonList(report);
                    List<Workbook> workbooks = ExcelUtil.createWorkbook(reports);
                    Boolean wasSuccessful = ExcelUtil.saveWorkbook(file.getName(), workbooks);

                    if (wasSuccessful) {
                        System.out.println("Success!");
                    } else {
                        System.out.println("Failure!");
                    }
                }
            }
        }
    }
}

