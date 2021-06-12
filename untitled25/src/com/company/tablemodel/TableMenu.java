package com.company.tablemodel;

import com.company.DBWorker;
import com.company.object.Menu;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableMenu extends AbstractTableModel {

    private List<Menu> data;
    String[] columnNames = {"Id", "Название", "Состав", "Калории", "Цена", "Время"};

    public Menu getValueAt(int rowIndex){
        return data.get(rowIndex);
    }
    public void deleteMenu(int rowIndex){
        DBWorker.initDB();
        DBWorker.newTable();
        DBWorker.deleteMenu(getValueAt(rowIndex));
        this.fireTableDataChanged();
        updateTable();
        DBWorker.closeBD();
    }
    public void updateMenu(Menu menu,int id){
        DBWorker.initDB();
        DBWorker.updateMenu(menu,id);
        this.fireTableDataChanged();
        updateTable();
        DBWorker.closeBD();
    }
    public void addMenu(Menu menu){
        DBWorker.initDB();
        DBWorker.addMenu(menu);
        updateTable();
        this.fireTableDataChanged();
        DBWorker.closeBD();
    }
    public TableMenu() {
        DBWorker.initDB();
        this.data = DBWorker.getMenu();
        DBWorker.closeBD();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Menu menu = data.get(rowIndex);
        switch (columnIndex){
            case 0: return menu.getId();
            case 1: return menu.getName();
            case 2: return menu.getStructure();
            case 3: return menu.getCalories();
            case 4: return menu.getPrice();
            case 5: return menu.getTime();
        }
        return null;
    }
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    public void updateTable(){
        DBWorker.initDB();
        data = DBWorker.getMenu();
        DBWorker.closeBD();
        this.fireTableDataChanged();
    }
}
