package api.spreadsheet.objects;

import api.ExtractorGoogleSpreadsheet;
import api.objects.Sheet1;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author aurelio.gimenes
 *
 */
public class Sheet1Spreadsheet extends ExtractorGoogleSpreadsheet<Sheet1> {

    public Map<String,String> columns() {
        Map<String,String> columns = new HashMap<String,String>();
        columns.put("column1","column1");
        columns.put("column2","column2");
        return columns;
    }

    public Class<Sheet1> factory() {
        return Sheet1.class;
    }

}
