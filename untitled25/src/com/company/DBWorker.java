package com.company;

import com.company.object.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBWorker {
    public static final String Path_To_BD_File = "coffee.bd";
    public static final String URL = "jdbc:sqlite:" + Path_To_BD_File;
    public static Connection connection; //Для соединения с БД необходимо использовать класс Connection пакета java.sql.
    public static Statement statement;
    public static void initDB(){
        try{
            connection = DriverManager.getConnection(URL);
            if (connection != null){
                DatabaseMetaData meta = connection.getMetaData();
                //System.out.println("Драйвер" + meta.getDriverName());
                newTable();
            }

        }
        catch (SQLException ex){
            System.out.println("Ошибка подключения " + ex);
        }
    }
    public static void newTable(){
        try {
            statement = connection.createStatement();//создание экземпляра класса Statement
            statement.execute("CREATE TABLE if not exists 'menu' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'structure' text, 'calories' Integer, 'price' Integer, 'time' text);");// позволяет выполнять различные статичные SQL запросы, используется, когда операторы SQL возвращают более одного набора данных, более одного счетчика обновлений или и то, и другое
            //System.out.println("Таблица меню существует");
            statement.execute("CREATE TABLE if not exists 'bar' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'structure' text, 'volume' Integer, 'price' Integer);");
            //System.out.println("Таблица бар существует");
            statement.execute("CREATE TABLE if not exists 'waiter' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'secondName' text, 'salary' Integer);");
            //System.out.println("Таблица официанты существует");
            statement.execute("CREATE TABLE if not exists 'ordersC' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'waiter' text, 'comment' text, 'price' Integer, 'time' text, 'table' Integer, 'ready' Integer);");
            //System.out.println("Таблица заказы существует");
            statement.execute("CREATE TABLE if not exists 'structureOrder' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'numberOrder' Integer , 'structure' text);");
            //System.out.println("Таблица подробнее о заказе существует");
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void addMenu(Menu menu){
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO menu('name','structure','calories','price','time')" + "VALUES(?, ?, ?, ?, ?);");
            statement.setObject(1,menu.getName());
            statement.setObject(2,menu.getStructure());
            statement.setObject(3,menu.getCalories());
            statement.setObject(4,menu.getPrice());
            statement.setObject(5,menu.getTime());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<Menu> getMenu() {
        List<Menu> listMenu = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM menu");
            while ((resultSet.next())){
                listMenu.add(new Menu(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3), resultSet.getInt(4), resultSet.getInt(5),resultSet.getString(6)));
            }
            resultSet.close();
            statement.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listMenu;
    }
    public static List<String> getMenuName() {
        initDB();
        List<String> listMenuName = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name FROM menu");
            while ((resultSet.next())){
                listMenuName.add(resultSet.getString("name"));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeBD();
        return listMenuName;
    }
    public static void deleteMenu(Menu menu){
        try {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM menu WHERE id = " + menu.getId());
            System.out.println("ydaleno");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateMenu(Menu menu, int id){
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE menu SET name = ?, structure = ?, calories = ?, price = ?, time = ? WHERE id = ?;" );
            statement.setObject(1,menu.getName());
            statement.setObject(2,menu.getStructure());
            statement.setObject(3,menu.getCalories());
            statement.setObject(4,menu.getPrice());
            statement.setObject(5,menu.getTime());
            statement.setObject(6,id);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void addWaiter(Waiter waiter){
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO waiter('name','secondName','salary')" + "VALUES(?, ?, ?);");
            statement.setObject(1,waiter.getName());
            statement.setObject(2,waiter.getSecondName());
            statement.setObject(3,waiter.getSalary());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<Waiter> getWaiter() {
        List<Waiter> listWaiter = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM waiter");
            while ((resultSet.next())){
                listWaiter.add(new Waiter(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3), resultSet.getInt(4)));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listWaiter;
    }
    public static void deleteWaiter(Waiter waiter){
        try {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM waiter WHERE id = " + waiter.getId());
            System.out.println("ydaleno");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateWaiter(Waiter waiter, int id){
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE waiter SET name = ?, secondName = ?, salary = ? WHERE id = ?;" );
            statement.setObject(1,waiter.getName());
            statement.setObject(2,waiter.getSecondName());
            statement.setObject(3,waiter.getSalary());
            statement.setObject(4,id);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String[] getWaiterSecondName(){
        initDB();
        List<String> name = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT secondName FROM waiter");
            while (resultSet.next()){
                name.add(resultSet.getString("secondName"));
            }
            String[] name1 = new String[name.size()];
            name.toArray(name1);
            resultSet.close();
            statement.close();
            return name1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeBD();
        return new String[0];
    }
    public static String[] getNumOrders(){
        initDB();
        List<String> listNumOrders = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id FROM ordersC");
            while (resultSet.next()){
                listNumOrders.add(resultSet.getString("id"));
            }
            String[] Nums = new String[listNumOrders.size()];
            listNumOrders.toArray(Nums);
            resultSet.close();
            statement.close();
            return Nums;

        } catch (Exception e) {
            e.printStackTrace();
        }
        closeBD();
        return new String[0];
    }
    public static void addBar(Bar bar){
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO bar('name','structure','volume','price')" + "VALUES(?, ?, ?, ?);");
            statement.setObject(1,bar.getName());
            statement.setObject(2,bar.getStructure());
            statement.setObject(3,bar.getVolume());
            statement.setObject(4,bar.getPrice());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<Bar> getBar() {
        List<Bar> listBar = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM bar");
            while ((resultSet.next())){
                listBar.add(new Bar(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3), resultSet.getInt(4), resultSet.getInt(5)));
            }
            resultSet.close();
            statement.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listBar;
    }
    public static void deleteBar(Bar bar){
        try {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM bar WHERE id = " + bar.getId());
            System.out.println("ydaleno");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateBar(Bar bar, int id){
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE bar SET name = ?, structure = ?, volume = ?, price = ? WHERE id = ?;" );
            statement.setObject(1,bar.getName());
            statement.setObject(2,bar.getStructure());
            statement.setObject(3,bar.getVolume());
            statement.setObject(4,bar.getPrice());
            statement.setObject(5,id);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<String> getBarName() {
        initDB();
        List<String> listBarName = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name FROM bar");
            while ((resultSet.next())){
                listBarName.add(resultSet.getString("name"));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeBD();
        return listBarName;
    }
    public static void addOrder(Order order){
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ordersC('waiter','comment','price','time','table','ready')" + "VALUES(?, ?, ?, ?, ?, ?);");
            statement.setObject(1,order.getWaiter());
            statement.setObject(2,order.getComment());
            statement.setObject(3,order.getPrice());
            statement.setObject(4,order.getTime());
            statement.setObject(5,order.getTable());
            statement.setObject(6,order.getReady());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<Order> getOrderCurrent() {
        List<Order> listOrder = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ordersC WHERE ready=1");
            while ((resultSet.next())){
                listOrder.add(new Order(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7)));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listOrder;
    }
    public static List<Order> getOrderEnd() {
        List<Order> listOrder = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ordersC WHERE ready=0");
            while ((resultSet.next())){
                listOrder.add(new Order(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7)));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOrder;
    }
    public static void deleteOrder(Order order){
        try {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM ordersC WHERE id = " + order.getId());
            System.out.println("ydaleno");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateOrder(Order order, int id){
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE ordersC SET waiter = ?, comment = ?, price = ?, time = ?, table = ? ready = ? WHERE id = ?;" );
            statement.setObject(1,order.getWaiter());
            statement.setObject(2,order.getComment());
            statement.setObject(3,order.getPrice());
            statement.setObject(4,order.getTime());
            statement.setObject(5,order.getTable());
            statement.setObject(6,order.getReady());
            statement.setObject(7,id);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateReady(int id){
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE ordersC SET ready = 0 WHERE id = ?;" );
            statement.setObject(1,id);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addStructureOfOrder(StructureOfOrder structureOfOrder){
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO structureOrder('numberOrder','structure')" + "VALUES(?, ?);");
            statement.setObject(1,structureOfOrder.getNumberOfOrder());
            statement.setObject(2,structureOfOrder.getList());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<StructureOfOrder> getStructureOfOrder() {
        List<StructureOfOrder> listStructureOfOrder = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM structureOrder");
            while ((resultSet.next())){
                listStructureOfOrder.add(new StructureOfOrder(resultSet.getInt(1),resultSet.getInt(2),resultSet.getString(3)));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listStructureOfOrder;
    }
    public static List<StructureOfOrder> getStructureOfOrderId(int id) {
        List<StructureOfOrder> listStructureOfOrder = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM structureOrder WHERE numberOrder =" + id);
            while ((resultSet.next())){
                listStructureOfOrder.add(new StructureOfOrder(resultSet.getInt(1),resultSet.getInt(2),resultSet.getString(3)));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listStructureOfOrder;
    }

    public static void deleteStructureOfOrder(int[] structureOfOrders){
        try {
            for (int i = 0; i < structureOfOrders.length; i++) {
                Statement statement = connection.createStatement();
                statement.execute("DELETE FROM structureOrder WHERE id = " + structureOfOrders[i]);
                System.out.println("ydaleno");
                statement.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateStructureOfOrder(StructureOfOrder structureOfOrder, int id){
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE structureOrder SET numberOrder = ?, structure = ? WHERE id = ?;" );
            statement.setObject(1,structureOfOrder.getNumberOfOrder());
            statement.setObject(2,structureOfOrder.getList());
            statement.setObject(3,id);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void priceCount(int price,int id){
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE ordersC SET price = ? WHERE id = ?");
            statement.setObject(1,price);
            statement.setObject(2,id);
            statement.execute();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void salaryCount(int salary,int id){
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE waiter SET salary = ? WHERE id = ?");
            statement.setObject(1,salary);
            statement.setObject(2,id);
            statement.execute();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void closeBD()
    {
        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Соединения закрыты");
    }
}
