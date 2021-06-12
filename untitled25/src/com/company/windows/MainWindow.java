package com.company.windows;

import com.company.DBWorker;
import com.company.object.Menu;
import com.company.object.Order;
import com.company.object.StructureOfOrder;
import com.company.tablemodel.*;
import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainWindow extends JFrame {
    private static TableMenu tableMenu = new TableMenu();
    private static TableOrder tableOrder = new TableOrder();
    private static TableBar tableBar = new TableBar();
    private static TableOrderEnd tableOrderEnd = new TableOrderEnd();
    private static TableStructureOfOrder tableStructureOfOrder;
    private static JTable tabStructureOfOrder;
    private static JTable tabOrder = new JTable(tableOrder);
    private static JTable tabOrderEnd = new JTable(tableOrderEnd);


    public MainWindow(String s){
        super(s);
        DBWorker.initDB();
        this.setSize(800, 600);
        this.setDefaultCloseOperation(3);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;
        this.setLocation(x,y);
        JPanel CenterTables = new JPanel(new GridLayout(1,1,1,3));
        JPanel buttonsSearch = new JPanel(new GridLayout(3,3,1,3));
        JScrollPane scrollPaneTest = new JScrollPane(tabOrder);
        JPanel northButton = new JPanel(new GridLayout(1,3,1,1));
        JPanel eastButton = new JPanel(new GridLayout(5,1,1,3));
        JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton menuButton = new JButton("Меню");
        JButton barButton = new JButton("Бар");
        JButton waiterButton = new JButton("Официанты");
        northButton.add(menuButton);
        northButton.add(barButton);
        northButton.add(waiterButton);
        JButton addOrder = new JButton("Добавить заказ");
        JButton orderMoreButton = new JButton("Изменить заказ");
        JButton deleteOrder = new JButton("Удалить заказ");
        JButton readyOrder = new JButton("Оплачен");
        JButton historyOrders = new JButton("История заказов");
        eastButton.add(addOrder);
        eastButton.add(orderMoreButton);
        eastButton.add(deleteOrder);
        eastButton.add(readyOrder);
        eastButton.add(historyOrders);
        CenterTables.add(scrollPaneTest);
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MenuFrame("Меню");
            }
        });
        barButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BarFrame("Бар");
            }
        });
        waiterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WaiterFrame("Официанты",tableOrderEnd);

            }
        });
        north.add(northButton);
        add(eastButton, BorderLayout.EAST);
        add(buttonsSearch, BorderLayout.SOUTH);
        add(north, BorderLayout.NORTH);
        add(CenterTables, BorderLayout.CENTER);
        setVisible(true);

        addOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateDialogAddOrder();
            }
        });
        deleteOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tabOrder.getSelectedRow();
                if (row == -1){
                    JOptionPane.showMessageDialog(deleteOrder,
                            "Вы ничего не выбрали",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                tableOrder.deleteOrder(row);
            }
        });
        readyOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = Integer.parseInt(tableOrder.getValueAt(tabOrder.getSelectedRow(),0).toString());
                if (row == -1){
                    JOptionPane.showMessageDialog(deleteOrder,
                            "Вы ничего не выбрали",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                tableOrder.readyOrder(row);
                tableOrderEnd.updateTable();
            }
        });
        orderMoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = Integer.parseInt(tableOrder.getValueAt(tabOrder.getSelectedRow(),0).toString());
                    System.out.println(row);
                    CreateDialogSelectStruc(row);
                } catch (NumberFormatException | IndexOutOfBoundsException numberFormatException) {
                    JOptionPane.showMessageDialog(null,
                            "Вы ничего не выбрали",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        historyOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableOrderEnd.updateTable();
                CreateDialogSelectHistory();
            }
        });
    }
    public static void CreateDialogAddOrder(){
        tableStructureOfOrder = new TableStructureOfOrder();
        tabStructureOfOrder = new JTable(tableStructureOfOrder);
        JDialog dialog=new JDialog();
        dialog.setModal(true);
        dialog.setTitle("Добавление заказа");
        dialog.setSize(350, 250);
        String[] tables = {
                "Стол 1",
                "Стол 2",
                "Стол 3",
                "Стол 4",
                "С собой"
        };
        GridLayout Grid = new GridLayout(4,3,1,3);
        GridLayout GridMenu = new GridLayout(((DBWorker.getMenuName().size()+DBWorker.getBarName().size())/3)+1,3,1,3);
        JPanel panelInput = new JPanel();
        JPanel panel2But = new JPanel();
        JPanel panelName = new JPanel(GridMenu);
        panelInput.setLayout(Grid);
        JLabel labelNameWaiter = new JLabel("Официант");
        JComboBox comboBoxNameWaiter = new JComboBox(DBWorker.getWaiterSecondName());
        JLabel labelStructureMenu = new JLabel("Комментарий");
        JTextField fieldStructureMenu = new JTextField(10);
        JLabel labelTable = new JLabel("Способ забрать");
        JComboBox comboBoxTable = new JComboBox(tables);
        comboBoxTable.setEditable(true);
        JButton buttonAddMenu = new JButton("Добавить");
        JLabel labelMenu = new JLabel("Меню");
        JLabel labelPrice = new JLabel("25");
        JTextField textFieldPrice = new JTextField();
        JLabel labelTime = new JLabel("00:00");
        panelInput.add(labelNameWaiter);
        panelInput.add(comboBoxNameWaiter);
        panelInput.add(labelStructureMenu);
        panelInput.add(fieldStructureMenu);
        panelInput.add(labelTable);
        panelInput.add(comboBoxTable);
        panelInput.add(labelMenu);
        ArrayList<JCheckBox> checkBoxList = new ArrayList<>();
        for (String name:
                DBWorker.getMenuName()) {
            JCheckBox checkBox = new JCheckBox("" + name);
            panelName.add(checkBox);
            checkBoxList.add(checkBox);
        }
        for (String name:
                DBWorker.getBarName()) {
            JCheckBox checkBox = new JCheckBox("" + name);
            panelName.add(checkBox);
            checkBoxList.add(checkBox);
        }
        panel2But.add(buttonAddMenu);

        buttonAddMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(labelNameWaiter.getText().equals("")){
                    JOptionPane.showMessageDialog(labelNameWaiter,
                            "Не все поля заполнены",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                else {


                    try {
                        Date dateNow = new Date();
                        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
                        labelTime.setText(time.format(dateNow));
                        int id = 0;

                        int counts = tableMenu.getRowCount()+tableBar.getRowCount();
                        tableOrder.addOrder(new Order(id,String.valueOf(comboBoxNameWaiter.getSelectedItem()) ,fieldStructureMenu.getText(),0,labelTime.getText(),String.valueOf(comboBoxTable.getSelectedItem()),1));
                        int count = tabOrder.getRowCount();
                        System.out.println(count);
                        for (JCheckBox box:
                             checkBoxList) {
                            if(box.isSelected()){
                                int i = Integer.parseInt(tabOrder.getValueAt(count-1,0).toString());
                                tableStructureOfOrder.addStructureOfOrder(new StructureOfOrder(id,i,box.getText()));
                            }
                            else{
                                counts= counts-1;
                            }
                        }
                        if (counts == 0 ){
                            JOptionPane.showMessageDialog(labelNameWaiter,
                                    "nul",
                                    "Ошибка",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            tableOrder.deleteOrder(tabOrder.getRowCount()-1);
                        }
                        else {
                            ChoosePrice();
                            WaiterFrame.ChooseSalary();
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();

                    }
                }
            }

        });
        dialog.add(panelInput, BorderLayout.NORTH);
        dialog.add(panel2But, BorderLayout.SOUTH);
        dialog.add(panelName, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    public static void ChoosePrice(){
        List<StructureOfOrder> listCopy = tableStructureOfOrder.getList();
        int id =0;
        for (StructureOfOrder r:
             listCopy) {
            if(r.getNumberOfOrder()!=id){
                List<StructureOfOrder> listWithId = new ArrayList<>();
                int priceStruc=0;
                for (StructureOfOrder r2:
                     listCopy) {

                    if(r2.getNumberOfOrder()==r.getNumberOfOrder()){
                        listWithId.add(r2);
                    }

                }
                id =r.getNumberOfOrder();
                for(int k=0; k<listWithId.size();k++){
                    System.out.println(tableMenu.getRowCount()+tableBar.getRowCount());
                    for (int j = 0; j < tableMenu.getRowCount(); j++) {
                        if(listWithId.get(k).getList().equals(tableMenu.getValueAt(j,1).toString())){
                            priceStruc += Integer.parseInt(tableMenu.getValueAt(j,4).toString());
                        }
                        System.out.println(priceStruc);
                    }
                    for (int j = 0; j < tableBar.getRowCount(); j++) {
                        if ( listWithId.get(k).getList().equals(tableBar.getValueAt(j,1).toString())){
                            priceStruc += Integer.parseInt(tableBar.getValueAt(j,4).toString());
                        }

                        System.out.println(priceStruc);
                    }
                }
                tableOrder.Recount(priceStruc,id);
            }
        }
    }
    public static void CreateDialogSelectStruc(int row){
        JDialog dialog=new JDialog();
        dialog.setModal(true);
        try {
            tableStructureOfOrder = new TableStructureOfOrder(row);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tabStructureOfOrder = new JTable(tableStructureOfOrder);
        JScrollPane scrollPaneTest = new JScrollPane(tabStructureOfOrder);
        dialog.setTitle("Подробнее о заказе № "+ row);
        dialog.setSize(550, 450);
        GridLayout Grid = new GridLayout(5,5,1,3);
        JPanel panelInput = new JPanel();
        JPanel panel2But = new JPanel();
        JButton buttonAdd = new JButton("Добавить позиции");
        JButton buttonDelete = new JButton("Удалить позиции");
        panelInput.add(scrollPaneTest);
        panel2But.add(buttonAdd);
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateDialogAddStruc(row);
            }
        });
        panel2But.add(buttonDelete);
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int[] count = tabStructureOfOrder.getSelectedRows();
                    int[] id = new int[count.length];
                    for (int i = 0; i < count.length; i++) {
                        id[i] = (int) tableStructureOfOrder.getValueAt(count[i],0);
                    }
                    tableStructureOfOrder.deleteStructureOfOrder(id, row);
                    ChoosePrice();

                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null,
                            "Вы ничего не выбрали",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );

                }
            }
        });
        dialog.add(panelInput, BorderLayout.CENTER);
        dialog.add(panel2But, BorderLayout.NORTH);
        dialog.setVisible(true);
    }
    public static void CreateDialogAddStruc(int row){
        JDialog dialog=new JDialog();
        dialog.setModal(true);
        System.out.println("open");
        dialog.setTitle("Добавление позиции");
        dialog.setSize(350, 250);
        GridLayout Grid = new GridLayout(2,2,1,3);
        GridLayout GridMenu = new GridLayout(((DBWorker.getMenuName().size()+DBWorker.getBarName().size())/3)+1,3,1,3);
        JPanel panelInput = new JPanel();
        JPanel panel2But = new JPanel();
        JPanel panelBox = new JPanel();
        panelInput.setLayout(Grid);
        panelBox.setLayout(GridMenu);
        JLabel labelNameStruc = new JLabel("Номер заказа: " + row);
        JLabel labelStructure = new JLabel("Меню:");
        JCheckBox checkBoxStructure = new JCheckBox();
        JButton buttonAddStruc = new JButton("Добавить");
        panelInput.add(labelNameStruc);
        panelInput.add(labelStructure);
        ArrayList<JCheckBox> checkBoxList = new ArrayList<>();
        for (String name:
                DBWorker.getMenuName()) {
            JCheckBox checkBox = new JCheckBox("" + name);
            System.out.println("" + name);
            panelBox.add(checkBox);
            checkBoxList.add(checkBox);
        }
        for (String name:
                DBWorker.getBarName()) {
            JCheckBox checkBox = new JCheckBox("" + name);
            System.out.println("" + name);
            panelBox.add(checkBox);
            checkBoxList.add(checkBox);
        }
        panel2But.add(buttonAddStruc);
        buttonAddStruc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = 0;

                    for (JCheckBox box:
                            checkBoxList) {
                        if(box.isSelected()){
                            tableStructureOfOrder.addStructureOfOrderId(new StructureOfOrder(id, row,box.getText()),row);
                        }
                    }
                    ChoosePrice();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(checkBoxStructure,
                            "Цена и объем должны быть целыми числами",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                }


            }
        });
        dialog.add(panelBox, BorderLayout.CENTER);
        dialog.add(panel2But, BorderLayout.SOUTH);
        dialog.add(panelInput, BorderLayout.NORTH);
        dialog.setVisible(true);
    }
    public static void CreateDialogSelectHistory(){
        JDialog dialog=new JDialog();
        dialog.setModal(true);

        JScrollPane scrollPaneTest = new JScrollPane(tabOrderEnd);
        dialog.setTitle("История заказов");
        dialog.setSize(550, 450);
        JPanel panelInput = new JPanel();
        panelInput.add(scrollPaneTest);
        dialog.add(panelInput, BorderLayout.CENTER);
        dialog.setVisible(true);
    }
}
