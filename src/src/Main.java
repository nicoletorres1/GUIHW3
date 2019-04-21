package src;

import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URI;
import java.util.ListIterator;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeListener;

/**
 * A dialog for tracking the price of an item.
 *
 * @author Yoonsik Cheon
 */
@SuppressWarnings("serial")
public class Main extends JFrame {

    private WatchList watchList = new WatchList();
    private String itemName = "LED Monitor";
    private String URL = "http://www.bestbuy.com/site/samsun-ue90-series-28-led-4k-uhd-moniotr-black/5484022.p?skuId=5484022";
    private double maxPrice = 369.99;
    private double minPrice = 61.67;
    private double itemChange;
    private String itemDate = "08/25/2018";
    private JList createJList;


    /** Default dimension of the dialog. */
    private final static Dimension DEFAULT_SIZE = new Dimension(400, 300);

    /** Special panel to display the watched item. */
    private ItemView itemView;

    private DefaultListModel defaultListModel;

    /** Message bar to display various messages. */
    private JLabel msgBar = new JLabel(" ");

    /** Create a new dialog. */
    public Main() {
        this(DEFAULT_SIZE);
    }

    /** Create a new dialog of the given screen dimension. */
    public Main(Dimension dim) {
        super("Price Watcher");
        setSize(dim);
        configureUI();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        //setResizable(false);
        showMessage("Welcome!");
        pack();
    }

    /** Callback to be invoked when the refresh button is clicked.
     * Find the current price of the watched item and display it
     * along with a percentage price change. */
    private void refreshButtonClicked(ActionEvent event) {
        //--
        //-- WRITE YOUR CODE HERE!
        //--
        itemView.getItem().setPreviousPrice(itemView.getItem().getItemPrice());
        itemView.getItem().setItemPrice(itemView.getItem().getRandomPrice());
        itemView.getItem().setItemChange();
        super.repaint();
        showMessage("Refresh clicked!");
    }

    /** Callback to be invoked when the view-page icon is clicked.
     * Launch a (default) web browser by supplying the URL of
     * the item. */
    private void viewPageClicked() {
        //--
        //-- WRITE YOUR CODE HERE!
        //--
        try{
            Desktop d = Desktop.getDesktop();
            d.browse(new URI(itemView.getItem().getURL()));
        }catch(Exception e){
            e.printStackTrace();
        }

        showMessage("View clicked!");
    }

    /** Configure UI. */
    private void configureUI() {
        setLayout(new BorderLayout());
        watchList.add(createItem(itemName,URL,maxPrice,minPrice,itemDate));
        watchList.add(createItem(itemName,URL,maxPrice,minPrice,itemDate));
        watchList.add(createItem(itemName,URL,maxPrice,minPrice,itemDate));
        watchList.add(createItem(itemName,URL,maxPrice,minPrice,itemDate));
        watchList.add(createItem(itemName,URL,maxPrice,minPrice,itemDate));
        watchList.add(createItem(itemName,URL,maxPrice,minPrice,itemDate));
        watchList.add(createItem(itemName,URL,maxPrice,minPrice,itemDate));

        defaultListModel = createListModel();
        createJList = new JList<>(defaultListModel);
        createJList.setCellRenderer(new ItemView());
        JScrollPane creatScroll = new JScrollPane(createJList);
        JPanel control = makeControlPanel();
        control.setBorder(BorderFactory.createEmptyBorder(10,16,0,16));
        add(control, BorderLayout.NORTH);
        JPanel board = new JPanel();
        board.add(creatScroll);
        board.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10,16,0,16),
                BorderFactory.createLineBorder(Color.GRAY)));
        board.setLayout(new GridLayout(1,1));
        itemView = new ItemView();
        itemView.setClickListener(this::viewPageClicked);
        //board.add(itemView);
        add(board, BorderLayout.CENTER);
        msgBar.setBorder(BorderFactory.createEmptyBorder(10,16,10,0));
        add(msgBar, BorderLayout.SOUTH);
    }

    /**
     *
      * @return
     */
    private JPanel makeControlPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JToolBar buttonBar = new JToolBar();

        JButton butRefresh = new JButton();
        butRefresh.setIcon(new ImageIcon("src/image/refresh3.png"));
        buttonBar.add(butRefresh);
        butRefresh.addActionListener(new refreshListener());

        JButton butAdd = new JButton();
        butAdd.setIcon(new ImageIcon("src/image/add.jpg"));
        buttonBar.add(butAdd);
        butAdd.addActionListener(new AddItemListener());

        JButton butSearch = new JButton();
        butSearch.setIcon(new ImageIcon("src/image/search-icon1.png"));
        buttonBar.add(butSearch);

        JButton butSelFirst = new JButton();
        butSelFirst.setIcon(new ImageIcon("src/image/firstItem.png"));
        butSelFirst.addActionListener(new butSelFirstListener());
        buttonBar.add(butSelFirst);

        JButton butSelLast = new JButton();
        butSelLast.setIcon(new ImageIcon("src/image/lastItem.png"));
        buttonBar.add(butSelLast);
        butSelLast.addActionListener(new ButSelLastListener());
        buttonBar.addSeparator();

        JButton butCheckSingle = new JButton();
        butCheckSingle.setIcon(new ImageIcon("src/image/blueRefresh.png"));
        buttonBar.add(butCheckSingle);
        butCheckSingle.addActionListener(new CheckSingleListener());

        JButton butViewPage = new JButton();
        butViewPage.setIcon(new ImageIcon("src/image/URL.png"));
        buttonBar.add(butViewPage);
        butViewPage.addActionListener(new viewPageListener());

        JButton butEdit = new JButton();
        butEdit.setIcon(new ImageIcon("src/image/edit.png"));
        buttonBar.add(butEdit);

        JButton butDeleteSel = new JButton();
        butDeleteSel.setIcon(new ImageIcon("src/image/delete.png"));
        buttonBar.add(butDeleteSel);
        butDeleteSel.addActionListener(new deleteSelListener());
        buttonBar.addSeparator();

        JButton butInfo = new JButton();
        butInfo.setIcon(new ImageIcon("src/image/questionMark.png"));
        buttonBar.add(butInfo);
        butInfo.addActionListener(new AboutListener());
        panel.add(buttonBar, BorderLayout.CENTER);

        JMenuBar dropBar = new JMenuBar();

        JMenu app = new JMenu("App");
        app.setMnemonic('A');
        JMenu item = new JMenu("Item");
        item.setMnemonic('I');
        JMenu sort = new JMenu("Sort");
        sort.setMnemonic('S');
        JMenu select = new JMenu("Selected");
        select.setMnemonic('s');

        dropBar.add(app);
        dropBar.add(item);
        dropBar.add(sort);
        //app dropdown
        JMenuItem about = new JMenuItem("About", new ImageIcon("src/image/questionMarkDrop.png"));
        about.setMnemonic('a');
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));
        app.add(about);
        about.addActionListener(new AboutListener());
        app.addSeparator();
        JMenuItem dropExit = new JMenuItem("Exit");
        dropExit.setMnemonic('e');
        dropExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, Event.CTRL_MASK));
        app.add(dropExit);
        //item drop down
        JMenuItem dropCheckPrice = new JMenuItem("Check Price", new ImageIcon("src/image/refresh1.png"));
        dropCheckPrice.setMnemonic('c');
        dropCheckPrice.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
        item.add(dropCheckPrice);
        dropCheckPrice.addActionListener(new refreshListener());
        JMenuItem dropAddItem = new JMenuItem("Add", new ImageIcon("src/image/add1.png"));
        dropAddItem.setMnemonic('i');
        dropAddItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.CTRL_MASK));
        item.add(dropAddItem);
        dropAddItem.addActionListener(new AddItemListener());
        item.addSeparator();
        JMenuItem dropSearch = new JMenuItem("Search", new ImageIcon("src/image/Search-icon2.png"));
        dropSearch.setMnemonic('f');
        dropSearch.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK));
        item.add(dropSearch);
        JMenuItem dropSearchFirst = new JMenuItem("First Item", new ImageIcon("src/image/firstItem1.png"));
        dropSearchFirst.setMnemonic('1');
        dropSearchFirst.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, Event.CTRL_MASK));
        item.add(dropSearchFirst);
        dropSearchFirst.addActionListener(new butSelFirstListener());
        JMenuItem dropSearchLast = new JMenuItem("Last Item", new ImageIcon("src/image/lastItem1.png"));
        dropSearchLast.setMnemonic('l');
        dropSearchLast.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.CTRL_MASK));
        item.add(dropSearchLast);
        butSelLast.addActionListener(new ButSelLastListener());
        item.addSeparator();
        //select drop down
        item.add(select);
        JMenuItem dropSelPrice = new JMenuItem("Price", new ImageIcon("src/image/blueRefresh1.png"));
        dropSelPrice.setMnemonic('p');
        dropSelPrice.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK));
        select.add(dropSelPrice);
        dropSelPrice.addActionListener(new CheckSingleListener());
        JMenuItem dropSelView = new JMenuItem("View Webpage", new ImageIcon("src/image/URL1.png"));
        dropSelView.setMnemonic('w');
        dropSelView.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Event.CTRL_MASK));
        select.add(dropSelView);
        dropSelView.addActionListener(new viewPageListener());
        JMenuItem dropSelEdit = new JMenuItem("Edit", new ImageIcon("src/image/edit1.png"));
        dropSelEdit.setMnemonic('x');
        dropSelEdit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));
        select.add(dropSelEdit);
        JMenuItem dropSelRemove = new JMenuItem("Remove", new ImageIcon("src/image/delete1.png"));
        dropSelRemove.setMnemonic('d');
        dropSelRemove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK));
        select.add(dropSelRemove);
        dropSelRemove.addActionListener(new deleteSelListener());
        select.addSeparator();
        JMenuItem dropSelCopyName = new JMenuItem("Copy Name");
        dropSelCopyName.setMnemonic('n');
        dropSelCopyName.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
        select.add(dropSelCopyName);
        JMenuItem dropSelCopyUrl = new JMenuItem("Copy URL");
        dropSelCopyUrl.setMnemonic('R');
        dropSelCopyUrl.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.CTRL_MASK));
        select.add(dropSelCopyUrl);
        JMenuItem dropSelCopyItem = new JMenuItem("Copy Item");
        dropSelCopyItem.setMnemonic('m');
        dropSelCopyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, Event.CTRL_MASK));
        select.add(dropSelCopyItem);
        //radio buttons
        ButtonGroup radioGroup = new ButtonGroup();
        JRadioButtonMenuItem dropOldestAdded = new JRadioButtonMenuItem("Oldest Added");
        dropOldestAdded.setMnemonic('o');
        dropOldestAdded.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, Event.CTRL_MASK));
        radioGroup.add(dropOldestAdded);
        sort.add(dropOldestAdded);
        JRadioButtonMenuItem dropNewestAdded = new JRadioButtonMenuItem("Newest Added");
        dropNewestAdded.setMnemonic('n');
        dropNewestAdded.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, Event.CTRL_MASK));
        radioGroup.add(dropNewestAdded);
        sort.add(dropNewestAdded);
        sort.addSeparator();
        JRadioButtonMenuItem dropNameAsc = new JRadioButtonMenuItem("Name Ascending");
        dropNameAsc.setMnemonic('a');
        dropNameAsc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, Event.CTRL_MASK));
        radioGroup.add(dropNameAsc);
        sort.add(dropNameAsc);
        JRadioButtonMenuItem dropNameDesc = new JRadioButtonMenuItem("Name Descending");
        dropNameDesc.setMnemonic('d');
        dropNameDesc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, Event.CTRL_MASK));
        radioGroup.add(dropNameDesc);
        sort.add(dropNameDesc);
        sort.addSeparator();
        JRadioButtonMenuItem dropPriceChange = new JRadioButtonMenuItem("Price Change (%)");
        dropPriceChange.setMnemonic('%');
        dropPriceChange.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, Event.CTRL_MASK));
        radioGroup.add(dropPriceChange);
        sort.add(dropPriceChange);
        JRadioButtonMenuItem dropPriceHigh = new JRadioButtonMenuItem("Price High ($)");
        dropPriceHigh.setMnemonic('h');
        dropPriceHigh.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, Event.CTRL_MASK));
        radioGroup.add(dropPriceHigh);
        sort.add(dropPriceHigh);
        JRadioButtonMenuItem dropPriceLow = new JRadioButtonMenuItem("Price Low ($)");
        dropPriceLow.setMnemonic('l');
        dropPriceLow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7, Event.CTRL_MASK));
        radioGroup.add(dropPriceLow);
        sort.add(dropPriceLow);

        panel.add(dropBar, BorderLayout.NORTH);
        return panel;
    }


    /**
     * Show briefly the given string in the message bar.
     * @param msg
     */
    private void showMessage(String msg) {
        msgBar.setText(msg);
        new Thread(() -> {
            try {
                Thread.sleep(3 * 1000); // 3 seconds
            } catch (InterruptedException e) {
            }
            if (msg.equals(msgBar.getText())) {
                SwingUtilities.invokeLater(() -> msgBar.setText(" "));
            }
        }).start();
    }


    //method to populate watchList
    public Item createItem(String itemName, String URL, Double maxPrice, Double minPrice, String itemDate){
        Item testItem = new Item(itemName, URL, maxPrice,
        minPrice, itemDate);

        return testItem;
    }

    /**
     * @return list
     */
    public DefaultListModel createListModel(){
        DefaultListModel list = new DefaultListModel<>();
        watchList.getHolder().forEach((iter) -> {
            list.addElement(iter);
        });
        return list;
    }

    public static void main(String[] args) {
        new Main();

    }
    //Get program info
    private class AboutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "      " +
                    "          Authors\n           Nicole Torres\n          Scott Honaker\n", "About",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //add new item
    private class AddItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
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
    //update price on selected item
    private class CheckSingleListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if(createJList.getSelectedIndex() > -1) {
                Item refreshItem;
                refreshItem = (Item) defaultListModel.get(createJList.getSelectedIndex());
                refreshItem.setPreviousPrice(refreshItem.getItemPrice());
                refreshItem.setItemPrice(refreshItem.getRandomPrice());
                refreshItem.setItemChange();
                repaint();
            }
        }
    }


    private class butSelFirstListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (defaultListModel.getSize() > -1) {
                createJList.setSelectedIndex(0);
            }
        }
    }

    private class ButSelLastListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (defaultListModel.getSize() > -1) {
                createJList.setSelectedIndex(defaultListModel.getSize() - 1);
            }
        }
    }

    private class refreshListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if(defaultListModel.getSize() > -1) {
                for(int i = 0; i < defaultListModel.getSize(); i++){
                    Item refreshItem;
                    createJList.setSelectedIndex(i);
                    refreshItem = (Item) defaultListModel.get(createJList.getSelectedIndex());
                    refreshItem.setPreviousPrice(refreshItem.getItemPrice());
                    refreshItem.setItemPrice(refreshItem.getRandomPrice());
                    refreshItem.setItemChange();
                    repaint();
                }
            }
        }
    }

    private class viewPageListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if(createJList.getSelectedIndex() > -1) {
                Item refreshItem;
                refreshItem = (Item) defaultListModel.get(createJList.getSelectedIndex());
                try{
                    Desktop d = Desktop.getDesktop();
                    d.browse(new URI(refreshItem.getURL()));
                }catch(Exception E){
                    E.printStackTrace();
                }

                showMessage("View clicked!");
            }
        }
    }

    private class deleteSelListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (createJList.getSelectedIndex() > -1) {
                defaultListModel.remove(createJList.getSelectedIndex());
                repaint();
            }
        }
    }
}

