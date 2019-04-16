package guihw3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WatchList extends JFrame {

    public WatchList(){
        //create array of String data for JList
        String[] list = {"TV", "\nComputer", "\nLaptop"};
        //create JList to pass to JPanel
        JList<String> watchList = new JList<String>(list);

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        p1.add(watchList);
        p1.setBackground(Color.white);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 2, 2));


        //add "add item" button and attach action listener
        JButton addItem = new JButton("Add Item");
        addItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String input = (JOptionPane.showInputDialog("Please enter another item: "));

            }// end action performed
        }); // end add action listener

        //add close button and attach action listener
        JButton close = new JButton("Close");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);

            } // end action performed
        }); // end action listeneer

        p2.add(addItem);
        p2.add(close);

        add(p1, BorderLayout.CENTER);
        add(p2, BorderLayout.SOUTH);

    } // end WatchList constructor

}
