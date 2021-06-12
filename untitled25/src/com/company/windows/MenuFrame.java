package com.company.windows;

import com.company.object.Menu;
import com.company.tablemodel.TableMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFrame extends JFrame {
    private static TableMenu tableMenu = new TableMenu();
    private static JTable tabMenu = new JTable(tableMenu);

    MenuFrame(String s){
        super(s);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(2);

        JPanel centerTab = new JPanel(new GridLayout(1,1,1,3));
        JPanel northButtons = new JPanel();
        JButton addMenu = new JButton("Добавить блюдо");
        JButton changeMenu = new JButton("Изменить блюдо");
        JButton deleteMenu = new JButton("Удалить блюдо");
        JScrollPane scrollPaneTest = new JScrollPane(tabMenu);
        northButtons.add(addMenu);
        northButtons.add(changeMenu);
        northButtons.add(deleteMenu);
        centerTab.add(scrollPaneTest);
        add(northButtons, BorderLayout.NORTH);
        add(centerTab, BorderLayout.CENTER);
        setVisible(true);
        addMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateDialogAddMenu();
            }
        });
        changeMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tabMenu.getSelectedRow();
                if (row == -1){
                    JOptionPane.showMessageDialog(centerTab,
                            "Вы ничего не выбрали",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                CreateDialogChangeMenu();
            }
        });
        deleteMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tabMenu.getSelectedRow();
                if (row == -1){
                    JOptionPane.showMessageDialog(centerTab,
                            "Вы ничего не выбрали",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                tableMenu.deleteMenu(row);
            }
        });

    }
    public static void CreateDialogAddMenu(){
        JDialog dialog=new JDialog();
        dialog.setModal(true);
        dialog.setTitle("Добавление блюда");
        dialog.setSize(350, 250);
        GridLayout Grid = new GridLayout(5,5,1,3);
        JPanel panelInput = new JPanel();
        JPanel panel2But = new JPanel();
        panelInput.setLayout(Grid);
        JLabel labelNameMenu = new JLabel("Название блюда");
        JTextField fieldNameMenu = new JTextField(10);
        JLabel labelStructureMenu = new JLabel("Состав блюда");
        JTextField fieldStructureMenu = new JTextField(10);
        JLabel labelCaloriesMenu = new JLabel("Калории блюда");
        JTextField fieldCaloriesMenu = new JTextField(10);
        JLabel labelPriceMenu = new JLabel("Стоимость блюда, руб");
        JTextField fieldPriceMenu = new JTextField(10);
        JLabel labelTimeMenu = new JLabel("Время готовки блюда, мин");
        JTextField fieldTimeMenu = new JTextField(10);
        JButton buttonAddMenu = new JButton("Добавить");
        panelInput.add(labelNameMenu);
        panelInput.add(fieldNameMenu);
        panelInput.add(labelStructureMenu);
        panelInput.add(fieldStructureMenu);
        panelInput.add(labelCaloriesMenu);
        panelInput.add(fieldCaloriesMenu);
        panelInput.add(labelPriceMenu);
        panelInput.add(fieldPriceMenu);
        panelInput.add(labelTimeMenu);
        panelInput.add(fieldTimeMenu);
        panel2But.add(buttonAddMenu);
        buttonAddMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fieldNameMenu.getText().equals("") || fieldStructureMenu.getText().equals("") || fieldCaloriesMenu.getText().equals("") || fieldPriceMenu.getText().equals("") || fieldTimeMenu.getText().equals("")){
                    JOptionPane.showMessageDialog(fieldNameMenu,
                            "Не все поля заполнены",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                else if(!isNumeric(fieldPriceMenu.getText()) || !isNumeric(fieldCaloriesMenu.getText())){
                    JOptionPane.showMessageDialog(fieldNameMenu,
                            "Каллории, цены и время должны быть числами",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;

                }
                else if(Integer.parseInt(fieldCaloriesMenu.getText()) < 0 || Integer.parseInt(fieldPriceMenu.getText()) < 0 || Integer.parseInt(fieldTimeMenu.getText()) <0 ){
                    JOptionPane.showMessageDialog(fieldNameMenu,
                            "Каллории, цены и время должны быть больше нуля",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                else {
                    try {
                            int id = 0;
                            tableMenu.addMenu(new Menu(id,(String)fieldNameMenu.getText(),(String)fieldStructureMenu.getText(),Integer.parseInt(fieldCaloriesMenu.getText()),Integer.parseInt(fieldPriceMenu.getText()),(String)fieldTimeMenu.getText()));
                            fieldNameMenu.setText(null);
                            fieldStructureMenu.setText(null);
                            fieldCaloriesMenu.setText(null);
                            fieldPriceMenu.setText(null);
                            fieldTimeMenu.setText(null);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(fieldNameMenu,
                                "Каллории, цена и время должны быть целыми числами",
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
    public static void CreateDialogChangeMenu(){
        JDialog dialog=new JDialog();
        dialog.setModal(true);
        Menu menu = tableMenu.getValueAt(tabMenu.getSelectedRow());
        dialog.setTitle("Изменение блюда");
        dialog.setSize(350, 250);
        GridLayout Grid = new GridLayout(5,5,1,3);
        JPanel panelInput = new JPanel();
        JPanel panel2But = new JPanel();
        panelInput.setLayout(Grid);
        JLabel labelNameMenu = new JLabel("Название блюда");
        JTextField fieldNameMenu = new JTextField(10);
        fieldNameMenu.setText(menu.getName());
        JLabel labelStructureMenu = new JLabel("Состав блюда");
        JTextField fieldStructureMenu = new JTextField(10);
        fieldStructureMenu.setText(menu.getStructure());
        JLabel labelCaloriesMenu = new JLabel("Каллории блюда");
        JTextField fieldCaloriesMenu = new JTextField(10);
        fieldCaloriesMenu.setText(String.valueOf(menu.getCalories()));
        JLabel labelPriceMenu = new JLabel("Стоимость блюда, руб");
        JTextField fieldPriceMenu = new JTextField(10);
        fieldPriceMenu.setText(String.valueOf(menu.getPrice()));
        JLabel labelTimeMenu = new JLabel("Время готовки блюда, мин");
        JTextField fieldTimeMenu = new JTextField(10);
        fieldTimeMenu.setText(String.valueOf(menu.getTime()));
        JButton buttonChangeMenu = new JButton("Изменить");
        panelInput.add(labelNameMenu);
        panelInput.add(fieldNameMenu);
        panelInput.add(labelStructureMenu);
        panelInput.add(fieldStructureMenu);
        panelInput.add(labelCaloriesMenu);
        panelInput.add(fieldCaloriesMenu);
        panelInput.add(labelPriceMenu);
        panelInput.add(fieldPriceMenu);
        panelInput.add(labelTimeMenu);
        panelInput.add(fieldTimeMenu);
        panel2But.add(buttonChangeMenu);

        buttonChangeMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fieldNameMenu.getText().equals("") || fieldStructureMenu.getText().equals("") || fieldCaloriesMenu.getText().equals("") || fieldPriceMenu.getText().equals("") || fieldTimeMenu.getText().equals("")){
                    JOptionPane.showMessageDialog(fieldNameMenu,
                            "Одно из полей пусто, проверьте данные",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                else {
                    try {
                        Integer.parseInt(fieldCaloriesMenu.getText());
                        Integer.parseInt(fieldPriceMenu.getText());
                        int id_m = (int) tableMenu.getValueAt(tabMenu.getSelectedRow(),0);
                        tableMenu.updateMenu(new Menu(0, fieldNameMenu.getText(), fieldStructureMenu.getText(), Integer.parseInt(fieldCaloriesMenu.getText()),Integer.parseInt(fieldPriceMenu.getText()),fieldTimeMenu.getText()), id_m);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(fieldNameMenu,
                                "Каллории, цена и время должны быть целыми числами",
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
}
