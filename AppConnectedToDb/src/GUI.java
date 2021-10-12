import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;


public class GUI extends JFrame {
    public static int JTable;
    private JPanel mainPanel;
    private JTable table;
    private JButton addButton;
    private JButton exitButton;
    private JButton infoButton;
    private JButton deleteRecords;
    private JButton editButton;
    private JButton deleteRowButton;
    private JTextField searchTextField;
    private JLabel searchLabel;
    static AddDialog dialog;
    static DataBase db = new DataBase();

    public static DefaultTableModel model;

    public GUI(String title) throws SQLException {

        super(title);

        model = new DefaultTableModel();


        table.setFillsViewportHeight(true);

        db.addColumns(model);

        //Scroll Panel
        JScrollPane scrollPane = new JScrollPane(table);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);



        //Dashboard
        JPanel dashboard = new JPanel();
        dashboard.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Adding buttons
        dashboard.add(addButton, gbc);
        dashboard.add(deleteRecords, gbc);
        dashboard.add(editButton, gbc);
        dashboard.add(deleteRowButton, gbc);

        //Centering buttons
        dashboard.setAlignmentY(Component.CENTER_ALIGNMENT);

        //Top panel
        JPanel topPanel = new JPanel();
        topPanel.add(searchLabel);
        searchTextField.setColumns(10);
        topPanel.add(searchTextField);

        //Bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(exitButton);
        bottomPanel.add(infoButton);

        table.setModel(model);

        //Main panel
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(dashboard, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0,0));

        //Loading records from the database
        db.loadRecords();

        this.pack();

        // ------------------------------------------ METHODS --------------------------------------------------------
        //Listeners
        addButton.addActionListener(new ActionListener() {
            /**
             * Provides functionality for add button
             *
             * @param actionEvent the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dialog = new AddDialog();
                dialog.pack();
                dialog.setVisible(true);

            }
        });
        exitButton.addActionListener(new ActionListener() {
            /**
             * Provides functionality for exit button
             *
             * @param actionEvent the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        infoButton.addActionListener(new ActionListener() {
            /**
             * Provides functionality for info button
             *
             * @param actionEvent the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!table.getSelectionModel().isSelectionEmpty()) {

                    StringBuilder message = new StringBuilder();

                    for (int i = 0; i< DataBase.columnCount; i++) {
                        message.append(table.getColumnName(i)).append(" ").append(table.getValueAt(table.getSelectedRow(), i)).append("\n");
                    }

                    // Message pops up
                    JOptionPane.showMessageDialog(null, message.toString(), "Client details", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        deleteRecords.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param actionEvent the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    db.deleteRecords();
                    model.setRowCount(0);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        searchTextField.addKeyListener(new KeyAdapter() {
            /**
             * Invoked when a key has been released.
             *
             * @param e
             */
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                filer(searchTextField.getText());
            }
        });
        deleteRowButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param actionEvent the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int row = table.getSelectedRow();
                String selected = model.getValueAt(row, 0).toString();
                try {
                    db.deleteRow(selected);
                    model.removeRow(row);
                    model.fireTableDataChanged();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * Filtering records
     * */
    public void filer(String query)
    {
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
        table.setRowSorter(tr);

        tr.setRowFilter(RowFilter.regexFilter(query));
    }

    /**
     * Adding person to database
     * */
    public static void addPerson(ArrayList<String> person) throws SQLException {

        db.insert(person);
        model.addRow(person.toArray());

        model.fireTableDataChanged();

    }

    public static void main(String[] args) throws SQLException {
        GUI frame = new GUI("My First Program");
        frame.setVisible(true);

    }
}
