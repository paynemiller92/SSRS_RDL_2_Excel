package util;

import model.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    private static Integer currentCellIndex = 0;
    private static Integer sheetCounter = 0;

    public static List<Workbook> createWorkbook(List<Report> reports) {
        List<Workbook> workbooks = new ArrayList<Workbook>();
        sheetCounter = 0;
        for (Report report : reports) {
            Workbook workbook =  new XSSFWorkbook();
            currentCellIndex = 0;
            for(DataSet dataSet : report.getDataSets()) {
                Sheet sheet = createSheet(report, dataSet, workbook);
                createTitleRow(report, sheet);
                for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
                    sheet.autoSizeColumn(i);
                }
            }
            workbooks.add(workbook);
        }
        return workbooks;
    }

    private static Sheet createSheet(Report report, DataSet dataSet, Workbook workbook) {
        Sheet sheet = workbook.createSheet("DataSet" + sheetCounter);
        sheetCounter++;
        createHeaderRows(report, dataSet, sheet);
            createValueRow(sheet, 2, report.getDataSources(), dataSet);
        return sheet;
    }

    private static void createHeaderRows(Report report, DataSet dataSet, Sheet sheet) {
        createColumnHeadings(sheet, dataSet, report);
    }

    private static void createTitleRow(Report report, Sheet sheet) {
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue(report.getName());
        boldCell(cell);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0, currentCellIndex));
    }

    private static void createColumnHeadings(Sheet sheet, DataSet dataSet, Report report) {
        Row row = sheet.createRow(1);
        String[] knownHeaders = {"Data Source Name", "Data Source Reference", "Data Set Name"};
        List<String> headers = new ArrayList<String>();
        headers.add(knownHeaders[0]);
        headers.add(knownHeaders[1]);
        headers.add(knownHeaders[2]);


            headers.add("Query Command Text");

            if (dataSet.getQuery().getQueryParameters() != null) { //FIXME: Potential Bug!

                for (QueryParameter parameter : dataSet.getQuery().getQueryParameters()) {
                    headers.add("Query Name");
                    headers.add("Query Value");
                }
            }

            for (Field field : dataSet.getFields()) {
                headers.add("Field Name");
                headers.add("Field Data Field");
                headers.add("Field Type");
            }

        Integer index = 0;
        for(String header : headers) {
            row.createCell(index).setCellValue(header);
            index++;
        }
    }

    private static void createValueRow(Sheet sheet, Integer rowIndex, List<DataSource> dataSources, DataSet dataSet) {
        currentCellIndex = 0;
        Row row = sheet.createRow(rowIndex);
        for(DataSource dataSource : dataSources) {
            createDataSourceCells(dataSource, row);
        }
        createDataSetCells(dataSet, row);
    }

    private static void createDataSourceCells(DataSource dataSource, Row row) {
        createDataSourceNameCell(dataSource, row);
        createDataSourceReferenceCell(dataSource, row);
    }

    private static void createDataSourceNameCell(DataSource dataSource, Row row) {
        Cell cell = row.createCell(currentCellIndex);
        cell.setCellValue(dataSource.getName());
        applyDataSourceStyle(cell);
        currentCellIndex++;
    }

    private static void createDataSourceReferenceCell(DataSource dataSource, Row row) {
        Cell cell = row.createCell(currentCellIndex);
        cell.setCellValue(dataSource.getDataSourceReference());
        applyDataSourceStyle(cell);
        currentCellIndex++;
    }

    private static void createDataSetCells(DataSet dataSet, Row row) {
        createDataSetNameCell(dataSet, row);
        createDataSetQueryCells(dataSet.getQuery(), row);
        createDataSetFieldCells(dataSet.getFields(), row);
    }

    private static void createDataSetNameCell(DataSet dataSet, Row row) {
        Cell cell = row.createCell(currentCellIndex);
        cell.setCellValue(dataSet.getName());
        applyDataSetStyle(cell);
        currentCellIndex++;
    }

    private static void createDataSetQueryCells(Query query, Row row) {
        createQueryCommandTextCell(query, row);
        createQueryParameterCells(query, row);
    }

    private static void createQueryCommandTextCell(Query query, Row row) {
        Cell cell = row.createCell(currentCellIndex);
        cell.setCellValue(query.getCommandText().trim());
        applyQueryStyle(cell);
        currentCellIndex++;
    }

    private static void createQueryParameterCells(Query query, Row row) {

        if(query.getQueryParameters() != null) { //FIXME: Potential Bug

            for (QueryParameter parameter : query.getQueryParameters()) {
                Cell cell = row.createCell(currentCellIndex);
                cell.setCellValue(parameter.getName());
                applyQueryStyle(cell);
                currentCellIndex++;

                cell = row.createCell(currentCellIndex);
                cell.setCellValue(parameter.getValue());
                applyQueryStyle(cell);
                currentCellIndex++;
            }
        }
    }

    private static void createDataSetFieldCells(List<Field> fields, Row row) {
        for (Field field : fields) {
            createFieldNameCell(field, row);
            createDataFieldCell(field, row);
            createFieldTypeCell(field, row);
        }
    }

    private static void createFieldNameCell(Field field, Row row) {
        Cell cell = row.createCell(currentCellIndex);
        cell.setCellValue(field.getName());
        applyDataSetStyle(cell);
        currentCellIndex++;
    }

    private static void createDataFieldCell(Field field, Row row) {
        Cell cell = row.createCell(currentCellIndex);
        cell.setCellValue(field.getDataField());
        applyDataSetStyle(cell);
        currentCellIndex++;
    }

    private static void createFieldTypeCell(Field field, Row row) {
        Cell cell = row.createCell(currentCellIndex);
        cell.setCellValue(field.getType());
        applyDataSetStyle(cell);
        currentCellIndex++;
    }

    public static Boolean saveWorkbook(String filename, List<Workbook> workbooks) {

        Integer index = 0;
        for (Workbook workbook : workbooks) {
            try {
                workbook.write(new FileOutputStream("excel/" + filename.replace(".rdl", "") + ".xlsx"));
                index++;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private static void boldCell(Cell cell) {
        CellStyle style = cell.getRow().getSheet().getWorkbook().createCellStyle();
        Font boldFont = cell.getRow().getSheet().getWorkbook().createFont();
        boldFont.setBold(true);
        style.setFont(boldFont);
        style.setAlignment(HorizontalAlignment.CENTER);
        cell.setCellStyle(style);
    }

    private static void applyDataSourceStyle(Cell cell) {
        XSSFCellStyle style = (XSSFCellStyle) cell.getRow().getSheet().getWorkbook().createCellStyle();
        XSSFFont font = (XSSFFont) cell.getRow().getSheet().getWorkbook().createFont();
        font.setColor(new XSSFColor(new java.awt.Color(255,255,255)));
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(23, 136, 193)));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(font);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        cell.setCellStyle(style);
    }

    private static void applyDataSetStyle(Cell cell) {
        XSSFCellStyle style = (XSSFCellStyle) cell.getRow().getSheet().getWorkbook().createCellStyle();
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(211, 192, 44)));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setWrapText(true);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        cell.setCellStyle(style);
    }

    private static void applyQueryStyle(Cell cell) {
        XSSFCellStyle style = (XSSFCellStyle) cell.getRow().getSheet().getWorkbook().createCellStyle();
        XSSFFont font = (XSSFFont) cell.getRow().getSheet().getWorkbook().createFont();
        font.setColor(new XSSFColor(new java.awt.Color(255,255,255)));
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(139, 52, 175)));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setWrapText(true);
        style.setFont(font);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        cell.setCellStyle(style);
    }
}
