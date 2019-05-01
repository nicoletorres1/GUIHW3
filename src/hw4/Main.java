package hw4;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Main extends hw3.Main {
    protected AddItemListener addItemListener = new AddItemListener();

    public static void main(String[] args){
        new Main();
    }

//    @Override
//    public void addItem(ActionEvent e) {
//        System.out.println("override testing");
//        super.addItem(e);
//    }

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
            System.out.println("override testing");
//            super.actionPerformed(e);
        }
    }
}
