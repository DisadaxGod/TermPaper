package com.company.tablemodel;

import com.company.DBWorker;
import com.company.object.Bar;
import com.company.object.Menu;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableBar extends AbstractTableModel {
    private List<Bar> data;
    String[] columnNames = {"Id", "Название", "Объём", "Состав", "Цена"};

    public Bar getValueAt(int rowIndex){
        return data.get(rowIndex);
    }
    public void deleteBar(int rowIndex){
        DBWorker.initDB();
        DBWorker.newTable();
        DBWorker.deleteBar(getValueAt(rowIndex));
        this.fireTableDataChanged();
        updateTable();
        DBWorker.closeBD();
    }
    public void updateBar(Bar bar,int id){
        DBWorker.initDB();
        DBWorker.updateBar(bar,id);
        this.fireTableDataChanged();
        updateTable();
        DBWorker.closeBD();
    }
    public void addBar(Bar bar){
        DBWorker.initDB();
        DBWorker.addBar(bar);
        updateTable();
        this.fireTableDataChanged();
        DBWorker.closeBD();
    }
    public TableBar() {
        DBWorker.initDB();
        this.data = DBWorker.getBar();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Bar bar = data.get(rowIndex);
        switch (columnIndex){
            case 0: return bar.getId();
            case 1: return bar.getName();
            case 2: return bar.getVolume();
            case 3: return bar.getStructure();
            case 4: return bar.getPrice();
        }
        return null;
    }
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    public void updateTable(){
        DBWorker.initDB();
        data = DBWorker.getBar();
        DBWorker.closeBD();
        this.fireTableDataChanged();
    }
}
