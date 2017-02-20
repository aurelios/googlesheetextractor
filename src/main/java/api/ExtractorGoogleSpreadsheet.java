package api;

import api.exception.GoogleSheetException;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author aurelio.gimenes
 *
 */
public abstract class ExtractorGoogleSpreadsheet<T> implements ExtractorSpreadsheet<T> {

    public List<T> extract(ListFeed listFeed) throws GoogleSheetException {
        List<T> objects = new ArrayList<T>();
        int index = 1;
        if(listFeed != null){
            for (ListEntry row : listFeed.getEntries()) {
                try {
                    T t = (T) factory().forName(factory().getName()).newInstance();            
                    for (String tag : row.getCustomElements().getTags()) {
                        if (this.columns() != null && this.columns().containsKey(tag)) {
                            this.set(t, this.columns().get(tag), row.getCustomElements().getValue(tag));
                        }
                        index++;
                    }
                    objects.add(t);
                } catch (Exception e) {
                    throw new GoogleSheetException("Erro ao realizar a extração de dados ao processar a linha " + index + " da planilha " + listFeed
                                                                                                                    .getTitle()
                                                                                                                    .getPlainText(), e.getCause());
                }
            }
        }
        return objects;
    }

    public boolean set(Object object, String fieldName, String fieldValue) throws NoSuchFieldException,
            SecurityException, IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            Field field = null;

            field = clazz.getDeclaredField(fieldName);

            field.setAccessible(true);

            Object value;
            value = fieldValue;


            field.set(object, value);

            return true;
        }
        return false;
    }
}