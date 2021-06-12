package com.company.windows;

import com.company.DBWorker;
import com.company.object.Bar;
import com.company.object.Order;
import com.company.object.StructureOfOrder;
import com.company.object.Waiter;
import com.company.tablemodel.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class WaiterFrame extends JFrame {
    private static TableWaiter tableWaiter = new TableWaiter();
    private static JTable tabWaiter = new JTable(tableWaiter);
    private static TableOrderEnd tableOrderEnd;
    private static JTable tabOrderEnd = new JTable(tableOrderEnd);
    public WaiterFrame(String s, TableOrderEnd tb){
        super(s);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(2);
        tableOrderEnd = tb;
        ChooseSalary();
        JPanel centerTab = new JPanel(new GridLayout(1,1,1,3));
        JPanel northButtons = new JPanel();
        JButton addWaiter = new JButton("Добавить официанта");
        JButton changeWaiter = new JButton("Изменить официанта");
        JButton deleteWaiter = new JButton("Удалить официанта");
        JScrollPane scrollPaneTest = new JScrollPane(tabWaiter);
        northButtons.add(addWaiter);
        northButtons.add(changeWaiter);
        northButtons.add(deleteWaiter);
        centerTab.add(scrollPaneTest);


        addWaiter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateDialogAddWaiter();
            }
        });
        changeWaiter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tabWaiter.getSelectedRow();
                if (row == -1){
                    JOptionPane.showMessageDialog(centerTab,
                            "Вы ничего не выбрали",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                CreateDialogChangeWaiter();
            }
        });
        deleteWaiter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tabWaiter.getSelectedRow();
                if (row == -1){
                    JOptionPane.showMessageDialog(centerTab,
                            "Вы ничего не выбрали",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                tableWaiter.deleteWaiter(row);
            }
        });
        add(northButtons, BorderLayout.NORTH);
        add(centerTab, BorderLayout.CENTER);
        setVisible(true);

    }
    public static void CreateDialogAddWaiter(){
        JDialog dialog=new JDialog();
        dialog.setModal(true);
        dialog.setTitle("Добавление официанта");
        dialog.setSize(350, 250);
        GridLayout Grid = new GridLayout(3,3,1,3);
        JPanel panelInput = new JPanel();
        JPanel panel2But = new JPanel();
        panelInput.setLayout(Grid);
        JLabel labelNameWaiter = new JLabel("Имя");
        JTextField fieldNameWaiter = new JTextField(10);
        JLabel labelSecondWaiter = new JLabel("Фамилия");
        JTextField fieldSecondWaiter = new JTextField(10);
        JLabel labelSalaryWaiter = new JLabel("Оклад, руб");
        JTextField fieldSalaryWaiter= new JTextField(10);
        JButton buttonAddWaiter = new JButton("Добавить");
        panelInput.add(labelNameWaiter);
        panelInput.add(fieldNameWaiter);
        panelInput.add(labelSecondWaiter);
        panelInput.add(fieldSecondWaiter);
        panelInput.add(labelSalaryWaiter);
        panelInput.add(fieldSalaryWaiter);

        panel2But.add(buttonAddWaiter);
        buttonAddWaiter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fieldNameWaiter.getText().equals("") || fieldSecondWaiter.getText().equals("") || fieldSalaryWaiter.getText().equals("")){
                    JOptionPane.showMessageDialog(fieldNameWaiter,
                            "Не все поля заполнены",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                else if(!isNumeric(fieldSalaryWaiter.getText()) ){
                    JOptionPane.showMessageDialog(fieldNameWaiter,
                            "Цена должна быть числом",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;

                }
                else if(Integer.parseInt(fieldSalaryWaiter.getText()) < 0){
                    JOptionPane.showMessageDialog(fieldNameWaiter,
                            "Цена должна быть больше нуля",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                else {
                    try {
                        int id = 0;
                        tableWaiter.addWaiter(new com.company.object.Waiter(id,fieldNameWaiter.getText(),fieldSecondWaiter.getText(),Integer.parseInt(fieldSalaryWaiter.getText())));
                        fieldNameWaiter.setText(null);
                        fieldSecondWaiter.setText(null);
                        fieldSalaryWaiter.setText(null);


                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(fieldNameWaiter,
                                "Цена должна быть целым числом",
                                "Ошибка",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                }

            }
        });
        dialog.add(panelInput, BorderLayout.CENTER);
        dialog.add(panel2But, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    public static void CreateDialogChangeWaiter(){
        JDialog dialog=new JDialog();
        dialog.setModal(true);
        com.company.object.Waiter waiter = tableWaiter.getValueAt(tabWaiter.getSelectedRow());
        dialog.setTitle("Изменение официанта");
        dialog.setSize(350, 250);
        GridLayout Grid = new GridLayout(3,3,1,3);
        JPanel panelInput = new JPanel();
        JPanel panel2But = new JPanel();
        panelInput.setLayout(Grid);
        JLabel labelNameWaiter = new JLabel("Имя");
        JTextField fieldNameWaiter = new JTextField(10);
        fieldNameWaiter.setText(waiter.getName());
        JLabel labelSecondWaiter = new JLabel("Фамилия");
        JTextField fieldSecondWaiter = new JTextField(10);
        fieldSecondWaiter.setText(waiter.getSecondName());
        JLabel labelSalaryWaiter = new JLabel("Оклад, руб");
        JTextField fieldSalaryWaiter= new JTextField(10);
        fieldSalaryWaiter.setText(String.valueOf(waiter.getSalary()));
        JButton buttonChangeWaiter = new JButton("Изменить");
        panelInput.add(labelNameWaiter);
        panelInput.add(fieldNameWaiter);
        panelInput.add(labelSecondWaiter);
        panelInput.add(fieldSecondWaiter);
        panelInput.add(labelSalaryWaiter);
        panelInput.add(fieldSalaryWaiter);
        panel2But.add(buttonChangeWaiter);

        buttonChangeWaiter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fieldNameWaiter.getText().equals("") || fieldSecondWaiter.getText().equals("") || fieldSalaryWaiter.getText().equals("") ){
                    JOptionPane.showMessageDialog(fieldNameWaiter,
                            "Одно из полей пусто, проверьте данные",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                else {
                    try {
                        Integer.parseInt(fieldSalaryWaiter.getText());
                        int id_w = (int) tableWaiter.getValueAt(tabWaiter.getSelectedRow(),0);
                        tableWaiter.updateWaiter(new Waiter(0, fieldNameWaiter.getText(), fieldSecondWaiter.getText(), Integer.parseInt(fieldSalaryWaiter.getText())), id_w);
                        ChooseSalary();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(fieldNameWaiter,
                                "Оклад должен быть целым числом",
                                "Ошибка",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                }
            }
        });
        dialog.add(panelInput, BorderLayout.CENTER);
        dialog.add(panel2But, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static void ChooseSalary(){

        List<Order> listCopy = tableOrderEnd.getList();
        int id=0;
        int priceStruc=0;
        double multi = 0;
        for(int k=0; k<tableWaiter.getRowCount();k++){
            System.out.println(listCopy);
            priceStruc = 0;
            System.out.println(tableOrderEnd.getRowCount());
            id = Integer.parseInt(tableWaiter.getValueAt(k,0).toString());
            for (int j = 0; j < tableOrderEnd.getRowCount(); j++) {
                if((listCopy.get(j).getWaiter().equals(tableWaiter.getValueAt(k,2).toString()))){
                    multi = Integer.parseInt(tableOrderEnd.getValueAt(j,3).toString()) * 0.1;
                    priceStruc += (int) multi;
                    System.out.println(priceStruc);

                    System.out.println(multi);
                }
            }
            priceStruc += 10000;
            tableWaiter.ReSalary(priceStruc,id);
        }
    }
}
