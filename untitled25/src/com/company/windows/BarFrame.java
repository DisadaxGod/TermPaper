package com.company.windows;

import com.company.object.Bar;
import com.company.object.Menu;
import com.company.tablemodel.TableBar;
import com.company.tablemodel.TableMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BarFrame extends JFrame {


    private static TableBar tableBar = new TableBar();
    private static JTable tabBar = new JTable(tableBar);

    BarFrame(String s){
        super(s);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(2);
        JPanel centerTab = new JPanel(new GridLayout(1,1,1,3));
        JPanel northButtons = new JPanel();
        JButton addBar = new JButton("Добавить напиток");
        JButton changeBar = new JButton("Изменить напиток");
        JButton deleteBar = new JButton("Удалить напиток");
        JScrollPane scrollPaneTest = new JScrollPane(tabBar);
        northButtons.add(addBar);
        northButtons.add(changeBar);
        northButtons.add(deleteBar);
        centerTab.add(scrollPaneTest);
        add(northButtons, BorderLayout.NORTH);
        add(centerTab, BorderLayout.CENTER);
        setVisible(true);
        addBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateDialogAddBar();
            }
        });
        changeBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tabBar.getSelectedRow();
                if (row == -1){
                    JOptionPane.showMessageDialog(centerTab,
                            "Вы ничего не выбрали",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                CreateDialogChangeBar();
            }
        });
        deleteBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tabBar.getSelectedRow();
                if (row == -1){
                    JOptionPane.showMessageDialog(centerTab,
                            "Вы ничего не выбрали",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                tableBar.deleteBar(row);
            }
        });

    }
    public static void CreateDialogAddBar(){
        JDialog dialog=new JDialog();
        dialog.setModal(true);
        dialog.setTitle("Добавление напитка");
        dialog.setSize(350, 250);
        GridLayout Grid = new GridLayout(4,4,1,3);
        JPanel panelInput = new JPanel();
        JPanel panel2But = new JPanel();
        panelInput.setLayout(Grid);
        JLabel labelNameBar = new JLabel("Название напитка");
        JTextField fieldNameBar = new JTextField(10);
        JLabel labelStructureBar = new JLabel("Состав напитка");
        JTextField fieldStructureBar = new JTextField(10);
        JLabel labelVolumeBar = new JLabel("Объем, мл");
        JTextField fieldVolumeBar = new JTextField(10);
        JLabel labelPriceBar = new JLabel("Стоимость напитка, руб");
        JTextField fieldPriceBar = new JTextField(10);
        JButton buttonAddBar = new JButton("Добавить");
        panelInput.add(labelNameBar);
        panelInput.add(fieldNameBar);
        panelInput.add(labelStructureBar);
        panelInput.add(fieldStructureBar);
        panelInput.add(labelVolumeBar);
        panelInput.add(fieldVolumeBar);
        panelInput.add(labelPriceBar);
        panelInput.add(fieldPriceBar);

        panel2But.add(buttonAddBar);
        buttonAddBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fieldNameBar.getText().equals("") || fieldVolumeBar.getText().equals("") || fieldStructureBar.getText().equals("") || fieldPriceBar.getText().equals("")){
                    JOptionPane.showMessageDialog(fieldNameBar,
                            "Не все поля заполнены",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                else if(!isNumeric(fieldVolumeBar.getText()) || !isNumeric(fieldPriceBar.getText())){
                    JOptionPane.showMessageDialog(fieldNameBar,
                            "Цена и объем должны быть числами",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;

                }
                else if(Integer.parseInt(fieldVolumeBar.getText()) < 0 || Integer.parseInt(fieldPriceBar.getText()) < 0){
                    JOptionPane.showMessageDialog(fieldNameBar,
                            "Каллории, цены и время должны быть больше нуля",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                else {
                    try {
                        int id = 0;
                        tableBar.addBar(new com.company.object.Bar(id,(String)fieldNameBar.getText(),(String)fieldStructureBar.getText(),Integer.parseInt(fieldVolumeBar.getText()),Integer.parseInt(fieldPriceBar.getText())));
                        fieldNameBar.setText(null);
                        fieldStructureBar.setText(null);
                        fieldVolumeBar.setText(null);
                        fieldPriceBar.setText(null);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(fieldNameBar,
                                "Цена и объем должны быть целыми числами",
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
    public static void CreateDialogChangeBar(){
        JDialog dialog=new JDialog();
        dialog.setModal(true);
        JScrollPane scrollPaneTest = new JScrollPane(tabBar);
        com.company.object.Bar bar = tableBar.getValueAt(tabBar.getSelectedRow());
        dialog.setTitle("Изменение напитка");
        dialog.setSize(350, 250);
        GridLayout Grid = new GridLayout(4,4,1,3);
        JPanel panelInput = new JPanel();
        JPanel panel2But = new JPanel();
        panelInput.setLayout(Grid);
        JLabel labelNameBar = new JLabel("Название напитка");
        JTextField fieldNameBar = new JTextField(10);
        fieldNameBar.setText(bar.getName());
        JLabel labelStructureBar = new JLabel("Состав напитка");
        JTextField fieldStructureBar = new JTextField(10);
        fieldStructureBar.setText(bar.getStructure());
        JLabel labelVolumeBar = new JLabel("Объем, мл");
        JTextField fieldVolumeBar = new JTextField(10);
        fieldVolumeBar.setText(String.valueOf(bar.getVolume()));
        JLabel labelPriceBar = new JLabel("Стоимость напитка, руб");
        JTextField fieldPriceBar = new JTextField(10);
        fieldPriceBar.setText(String.valueOf(bar.getPrice()));
        JButton buttonChangeBar = new JButton("Изменить");
        panelInput.add(labelNameBar);
        panelInput.add(fieldNameBar);
        panelInput.add(labelStructureBar);
        panelInput.add(fieldStructureBar);
        panelInput.add(labelVolumeBar);
        panelInput.add(fieldVolumeBar);
        panelInput.add(labelPriceBar);
        panelInput.add(fieldPriceBar);
        panel2But.add(buttonChangeBar);

        buttonChangeBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fieldNameBar.getText().equals("") || fieldStructureBar.getText().equals("") || fieldVolumeBar.getText().equals("") || fieldPriceBar.getText().equals("") ){
                    JOptionPane.showMessageDialog(fieldNameBar,
                            "Одно из полей пусто, проверьте данные",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                else {
                    try {
                        Integer.parseInt(fieldVolumeBar.getText());
                        Integer.parseInt(fieldPriceBar.getText());
                        int id_b = (int) tableBar.getValueAt(tabBar.getSelectedRow(),0);
                        tableBar.updateBar(new Bar(0, fieldNameBar.getText(), fieldStructureBar.getText(), Integer.parseInt(fieldVolumeBar.getText()),Integer.parseInt(fieldPriceBar.getText())), id_b);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(fieldNameBar,
                                "Цена и объем должны быть целыми числами",
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
