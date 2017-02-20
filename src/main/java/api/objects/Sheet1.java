package api.objects;

public class Sheet1 {
    
    private String columnOne;
    private String columnTwo;
    
    public Sheet1() {
     
    }

    public String getColumnOne() {
        return columnOne;
    }

    public void setColumnOne(String columnOne) {
        this.columnOne = columnOne;
    }

    public String getColumnTwo() {
        return columnTwo;
    }

    public void setColumnTwo(String columnTwo) {
        this.columnTwo = columnTwo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sheet1 that = (Sheet1) o;

        if (columnOne != null ? !columnOne.equals(that.columnOne) : that.columnOne != null) return false;
        return columnTwo != null ? columnTwo.equals(that.columnTwo) : that.columnTwo == null;
    }

    @Override
    public int hashCode() {
        int result = columnOne != null ? columnOne.hashCode() : 0;
        result = 31 * result + (columnTwo != null ? columnTwo.hashCode() : 0);
        return result;
    }
}
