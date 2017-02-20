package api;

import api.exception.GoogleSheetException;
import com.google.gdata.data.spreadsheet.ListFeed;

import java.util.List;
import java.util.Map;
/**
 * 
 * @author aurelio.gimenes
 *
 */
public interface ExtractorSpreadsheet<T> {

    Map<String, String> columns();

    Class<T> factory();

    List<T> extract(ListFeed listFeed) throws GoogleSheetException;
}
