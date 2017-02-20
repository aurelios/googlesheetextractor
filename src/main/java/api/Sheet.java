package api;

import api.spreadsheet.objects.Sheet1Spreadsheet;

/**
 * 
 * @author aurelio.gimenes
 *
 */
public enum Sheet {

    SHEET1("Sheet1", Sheet1Spreadsheet.class);
    
    private String sheetName;
    private Class<? extends ExtractorSpreadsheet<?>> extractorClass;
    
    Sheet(String sheetName, Class<? extends ExtractorSpreadsheet<?>> extractorClass) {
        this.sheetName = sheetName;
        this.extractorClass = extractorClass;
    }
    
    public ExtractorSpreadsheet<?> getExtractorInstance() throws InstantiationException, IllegalAccessException {
        return extractorClass.newInstance();
    }
    
    public String getSheetName() {
        return sheetName;
    }
    
    public static Sheet getEnum(String sheetName){
        for(Sheet bs : Sheet.values()){
            if(bs.getSheetName().equals(sheetName)){
                return bs;
            }
        }
        return null;
    }
}
