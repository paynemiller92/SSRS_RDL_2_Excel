package util;

import model.DataSet;
import model.Field;
import model.Report;

public class RdlSanitizer {
    public static void sanitizeFields(Report report) {
        for (DataSet dataSet : report.getDataSets()) {
            for (Field field : dataSet.getFields()) {
                if (field != null && field.getDataField() != null) {
                    field.setDataField(field.getDataField().replace("<?xml version=\"1.0\" encoding=\"utf-8\"?><Field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xsi:type=\"Level\" UniqueName=\"", ""));
                    field.setDataField(field.getDataField().replace("<?xml version=\"1.0\" encoding=\"utf-8\"?><Field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xsi:type=\"Label\" UniqueName=\"", ""));
                    field.setDataField(field.getDataField().replace("<?xml version=\"1.0\" encoding=\"utf-8\"?><Field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xsi:type=\"Measure\" UniqueName=\"", ""));
                    field.setDataField(field.getDataField().replace("/>", ""));
                    field.setDataField(field.getDataField().replace("\"", ""));
                    field.setDataField(field.getDataField().trim());
                }
            }
        }
    }
}