package hw4;

import hw3.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main extends hw3.Main {

    public static void main(String[] args){
        new Main();
    }

    @Override
    protected JButton createAddButton() {
        JButton butAdd = new JButton();
        butAdd.setIcon(new ImageIcon(getClass().getClassLoader().getResource("image/add.jpg")));
        butAdd.setToolTipText("Add a new item");
        butAdd.addActionListener(new AddItemListener());
        return butAdd;
    }

    protected class AddItemListener extends hw3.Main.AddItemListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane testOption = new JOptionPane();

            JTextField nameField = new JTextField(10);
            JTextField urlField = new JTextField(10);
            JTextField priceField = new JTextField(10);
            JTextField dateField = new JTextField(10);

            JPanel namePanel = new JPanel();
            JPanel URLPanel = new JPanel();
            JPanel pricePanel = new JPanel();
            JPanel datePanel = new JPanel();
            JPanel outer = new JPanel();

            outer.setLayout(new BoxLayout(outer, BoxLayout.Y_AXIS));

            namePanel.add(new JLabel("Name:\t"));
            namePanel.add(nameField);
            namePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            URLPanel.add(new JLabel("URL:\t"), urlField);
            URLPanel.add(urlField);
            URLPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            pricePanel.add(new JLabel("Price(Numbers only):\t"));
            pricePanel.add(priceField);
            pricePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            datePanel.add(new JLabel("Date Added:\t"));
            datePanel.add(dateField);
            datePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            outer.add(namePanel);
            outer.add(URLPanel);
            outer.add(pricePanel);
            outer.add(datePanel);

            int button = testOption.showConfirmDialog(null, outer, "Add Item", JOptionPane.OK_CANCEL_OPTION);

            String name = nameField.getText();
            String URL = urlField.getText();
            double price =  Double.parseDouble(priceField.getText());
            String date = dateField.getText();
            if(button == 0) {
                Item addItem = new Item(name, URL, price, 0, date);

                defaultListModel.addElement(addItem);
            }
        }
    }


}
