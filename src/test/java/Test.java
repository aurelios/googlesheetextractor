import api.ExtractorSpreadsheet;
import api.GoogleSheetsApi;
import api.Sheet;
import api.exception.GoogleSheetException;
import api.objects.Sheet1;
import com.google.gdata.data.spreadsheet.ListFeed;

import java.util.List;
import java.util.Map;

/**
 * Created by aurelio.gimenes on 20/02/2017.
 */
public class Test {


    public static void main(String[] args) {

        try {
            Map<String, ListFeed> mapFeeds = extractSheet("");
            for (Sheet bs : Sheet.values()) {
                ListFeed feed = mapFeeds.get(bs.getSheetName());
                ExtractorSpreadsheet extractor = bs.getExtractorInstance();
                if(Sheet.SHEET1.equals(bs)){
                    List<Sheet1> list = extractor.extract(mapFeeds.get(bs.getSheetName()));
                    System.out.println(list);
                }
            }
        } catch (GoogleSheetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    public static Map<String, ListFeed> extractSheet(String key) throws GoogleSheetException {
        GoogleSheetsApi gsa = new GoogleSheetsApi();
        Map<String, ListFeed> mapFeeds = null;
        try {
            mapFeeds = gsa.loadWorksheetsFromSpreadsheet(key);
        } catch (Exception e) {
            throw new GoogleSheetException("Erro ao realizar importação: " + e.getCause());
        }

        return mapFeeds;
    }
}
