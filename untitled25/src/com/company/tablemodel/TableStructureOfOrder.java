package com.company.tablemodel;

import com.company.DBWorker;
import com.company.object.Menu;
import com.company.object.StructureOfOrder;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableStructureOfOrder extends AbstractTableModel {
    private List<StructureOfOrder> data;
    String[] columnNames = {"Id", "Номер заказа", "Продукция"};
    public List<StructureOfOrder> getList(){
        return data;
    }

    public StructureOfOrder getValueAt(int rowIndex){
        return data.get(rowIndex);
    }
    public void deleteStructureOfOrder(int[] rowIndexs, int id){
        DBWorker.initDB();
        DBWorker.newTable();
        DBWorker.deleteStructureOfOrder(rowIndexs);
        updateTable(id);
        DBWorker.closeBD();
    }
    public void updateStructureOfOrder(StructureOfOrder structureOfOrder,int id){
        DBWorker.initDB();
        DBWorker.updateStructureOfOrder(structureOfOrder,id);
        updateTable();
        DBWorker.closeBD();
    }
    public void addStructureOfOrder(StructureOfOrder structureOfOrder){
        DBWorker.initDB();
        DBWorker.addStructureOfOrder(structureOfOrder);
        DBWorker.closeBD();
        updateTable();
    }
    public void addStructureOfOrderId(StructureOfOrder structureOfOrder, int id){
        DBWorker.initDB();
        DBWorker.addStructureOfOrder(structureOfOrder);
        DBWorker.closeBD();
        updateTable(id);
    }
    public TableStructureOfOrder(int id) {
        DBWorker.initDB();
        this.data = DBWorker.getStructureOfOrderId(id);
        DBWorker.closeBD();
    }
    public TableStructureOfOrder() {
        DBWorker.initDB();
        this.data = DBWorker.getStructureOfOrder();
        DBWorker.closeBD();
    }
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StructureOfOrder structureOfOrder = data.get(rowIndex);
        switch (columnIndex){
            case 0: return structureOfOrder.getId();
            case 1: return structureOfOrder.getNumberOfOrder();
            case 2: return structureOfOrder.getList();

        }
        return null;
    }
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    public void updateTable(){
        DBWorker.initDB();
        data = DBWorker.getStructureOfOrder();
        DBWorker.closeBD();
        this.fireTableDataChanged();
    }
    public void updateTable(int id){
        DBWorker.initDB();
        data = DBWorker.getStructureOfOrderId(id);
        DBWorker.closeBD();
        this.fireTableDataChanged();
    }
}
