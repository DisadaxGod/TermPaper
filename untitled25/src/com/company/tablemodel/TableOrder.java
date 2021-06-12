package com.company.tablemodel;

import com.company.DBWorker;
import com.company.object.Menu;
import com.company.object.Order;
import com.company.object.StructureOfOrder;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableOrder extends AbstractTableModel {
    private List<Order> data;
    String[] columnNames = {"Id", "Официант", "Комментарий", "Цена", "Время", "Столик"};

    public Order getValueAt(int rowIndex){
        return data.get(rowIndex);
    }
    public void deleteOrder(int rowIndex){
        DBWorker.initDB();
        DBWorker.deleteOrder(getValueAt(rowIndex));
        updateTable();
        DBWorker.closeBD();
    }
    public void readyOrder(int id){
        DBWorker.initDB();
        DBWorker.updateReady(id);
        updateTable();
        DBWorker.closeBD();

    }
    public void updateOrder(Order order,int id){
        DBWorker.initDB();
        DBWorker.updateOrder(order,id);
        updateTable();
        DBWorker.closeBD();
    }
    public void addOrder(Order order){
        DBWorker.initDB();
        DBWorker.addOrder(order);
        updateTable();
        DBWorker.closeBD();
    }
    public TableOrder() {
        DBWorker.initDB();
        this.data = DBWorker.getOrderCurrent();
        DBWorker.closeBD();
    }
    public void Recount(int price, int id){
        DBWorker.initDB();
        DBWorker.priceCount(price,id);
        updateTable();
        DBWorker.closeBD();
    }

    public List<Order> getList(){
        return data;
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
        Order order = data.get(rowIndex);
        switch (columnIndex){
            case 0: return order.getId();
            case 1: return order.getWaiter();
            case 2: return order.getComment();
            case 3: return order.getPrice();
            case 4: return order.getTime();
            case 5: return order.getTable();
            case 6: return order.getReady();
        }
        return "";
    }
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    public void updateTable(){
        DBWorker.initDB();
        data = DBWorker.getOrderCurrent();
        DBWorker.closeBD();
        this.fireTableDataChanged();
    }

}
