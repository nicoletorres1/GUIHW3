package hw4;

import hw3.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends hw3.Main {

    WebPriceFinder randPrice = new WebPriceFinder();

    /**
     * Runs the subclass of hw3 main
     * @param args
     */
    public static void main(String[] args){
        new Main();
    }

    /**
     * Overides the hw3 createAddButton method and allows it to use the webpricefinder subclass
     * @return
     */
    @Override
    protected JButton createAddButton() {
        JButton butAdd = new JButton();
        butAdd.setIcon(new ImageIcon(getClass().getClassLoader().getResource("image/add.jpg")));
        butAdd.setToolTipText("Add a new item");
        butAdd.addActionListener(new AddItemListener());
        return butAdd;
    }

    /**
     *Overides the hw3 createRefreshButton method and allows it to use the webpricefinder subclass
     * @return
     */
    @Override
    protected JButton createRefreshButton() {
        JButton butRefresh = new JButton();
        butRefresh.setIcon(new ImageIcon(getClass().getClassLoader().getResource("image/refresh3.png")));
        butRefresh.setToolTipText("Refresh All");
        butRefresh.addActionListener(new refreshListener());
        return butRefresh;
    }

    /**
     *Checks the new refreshbutton and allows for the webpricefinder subclass to be used
     */
    private class refreshListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if(defaultListModel.getSize() > -1) {
                for(int i = 0; i < defaultListModel.getSize(); i++){
                    Item refreshItem;
                    createJList.setSelectedIndex(i);
                    refreshItem = (Item) defaultListModel.get(createJList.getSelectedIndex());
                    refreshItem.setPreviousPrice(refreshItem.getItemPrice());
                    refreshItem.setItemPrice(randPrice.getRandomPrice(refreshItem));
                    refreshItem.setItemChange();
                    repaint();
                }
            }
        }
    }

    /**
     * checks the new additemlistener and allows for the webpricefinder subclass to be used
     */
    protected class AddItemListener extends hw3.Main.AddItemListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            WebPriceFinder webPrice = new WebPriceFinder();
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

            priceField.setEditable(false);
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
            String date = dateField.getText();
            if(button == 0) {
                Item addItem = new Item(name, URL, 0, 0, date);
                double price = webPrice.getRandomPrice(addItem);
                addItem.setItemPrice(price);

                defaultListModel.addElement(addItem);
            }
        }
    }


}
