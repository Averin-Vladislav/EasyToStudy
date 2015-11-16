package sample;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Владислав on 13.11.2015.
 */
public class StatisticsInfo {
    private final SimpleStringProperty firstColumn;
    private final SimpleStringProperty secondColumn;
    private final SimpleStringProperty thirdColumn;

    StatisticsInfo(String firstColumnName, String secondColumnName) {
        this.firstColumn = new SimpleStringProperty(firstColumnName);
        this.secondColumn = new SimpleStringProperty(secondColumnName);
        this.thirdColumn = null;
    }

    StatisticsInfo(String firstColumnName, String secondColumnName, String thirdColumnName) {
        this.firstColumn = new SimpleStringProperty(firstColumnName);
        this.secondColumn = new SimpleStringProperty(secondColumnName);
        this.thirdColumn = new SimpleStringProperty(thirdColumnName);
    }

    public String getFirstColumn() {
        return firstColumn.get();
    }

    public String getSecondColumn() {
        return secondColumn.get();
    }

    public String getThirdColumn() {
        return thirdColumn.get();
    }

    public void setFirstColumn(String firstColumnName) {
        firstColumn.set(firstColumnName);
    }

    public void setSecondColumn(String secondColumnName) {
        secondColumn.set(secondColumnName);
    }

    public void setThirdColumn(String thirdColumnName) {
        thirdColumn.set(thirdColumnName);
    }
}
