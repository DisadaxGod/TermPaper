package com.company.tablemodel;

import com.company.DBWorker;
import com.company.object.Waiter;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableWaiter extends AbstractTableModel {
    private List<Waiter> data;
    String[] columnNames = {"Id", "Имя", "Фамилия", "Зарплата"};

    public Waiter getValueAt(int rowIndex){
        return data.get(rowIndex);
    }


    public void deleteWaiter(int rowIndex){
        DBWorker.initDB();
        DBWorker.newTable();
        DBWorker.deleteWaiter(getValueAt(rowIndex));
        updateTable();
        DBWorker.closeBD();
    }
    public void updateWaiter(Waiter waiter,int id){
        DBWorker.initDB();
        DBWorker.updateWaiter(waiter,id);
        updateTable();
        DBWorker.closeBD();
    }
    public void addWaiter(Waiter waiter){
        DBWorker.initDB();
        DBWorker.addWaiter(waiter);
        updateTable();
        DBWorker.closeBD();
    }
    public TableWaiter() {
        DBWorker.initDB();
        this.data = DBWorker.getWaiter();

        DBWorker.closeBD();
    }
    public void ReSalary(int salary, int id){
        DBWorker.initDB();
        DBWorker.salaryCount(salary,id);
        updateTable();
        DBWorker.closeBD();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Waiter waiter = data.get(rowIndex);
        switch (columnIndex){
            case 0: return waiter.getId();
            case 1: return waiter.getName();
            case 2: return waiter.getSecondName();
            case 3: return waiter.getSalary();

        }
        return null;
    }
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    public void updateTable(){
        DBWorker.initDB();
        data = DBWorker.getWaiter();
        DBWorker.closeBD();
        this.fireTableDataChanged();
    }

}
