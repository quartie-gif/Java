import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;


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
        String[] header = new String[]{"First Name", "Last Name", "Field of Study",
                "Year", "Sex"};

        model.setColumnIdentifiers(header);
        model.fireTableDataChanged();

        //Scroll Panel
        JScrollPane scrollPane = new JScrollPane(table);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);

        JPanel dashboard = new JPanel();

        //Dashboard's layout
        dashboard.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Adding buttons
        dashboard.add(addButton, gbc);
        dashboard.add(deleteRecords, gbc);
        dashboard.add(deleteRowButton, gbc);

        //Centering buttons
        dashboard.setAlignmentY(Component.CENTER_ALIGNMENT);

        JPanel topPanel = new JPanel();
        topPanel.add(searchLabel);
        searchTextField.setColumns(10);
        topPanel.add(searchTextField);





        JPanel bottomPanel = new JPanel();
        bottomPanel.add(exitButton);
        bottomPanel.add(infoButton);

        table.setModel(model);


        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(dashboard, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0,0));
        //Loading records from Database
        db.loadRecords();

        this.pack();

        //Listeners
        addButton.addActionListener(new ActionListener() {
            /**
             * Provides functionality for add button
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog = new AddDialog();
                dialog.pack();
                dialog.setVisible(true);

            }
        });
        exitButton.addActionListener(new ActionListener() {
            /**
             * Provides functionality for exit button
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        infoButton.addActionListener(new ActionListener() {
            /**
             * Provides functionality for info button
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!table.getSelectionModel().isSelectionEmpty()) {

                    String data = "First Name: " + table.getValueAt(table.getSelectedRow(), 0) +
                            "\nSecond name: " + table.getValueAt(table.getSelectedRow(), 1) +
                            "\nField of Study: " + table.getValueAt(table.getSelectedRow(), 2) +
                            "\nYear: " + table.getValueAt(table.getSelectedRow(), 3) +
                            "\nSex: " + table.getValueAt(table.getSelectedRow(), 4);

                    // Message pops up
                    JOptionPane.showMessageDialog(null, data, "Client details", JOptionPane.PLAIN_MESSAGE);
                }


            }
        });

        deleteRecords.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
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
    }

    public void filer(String query)
    {
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        table.setRowSorter(tr);

        tr.setRowFilter(RowFilter.regexFilter(query));
    }

    public static void addPerson(Person person) throws SQLException {
        model.addRow(new Object[]{person.firstName, person.lastName, person.fieldOfStudy, person.year, person.sex});

        db.insert(person.firstName, person.lastName, person.fieldOfStudy, person.year, person.sex);
        model.fireTableDataChanged();

    }

    public static void main(String[] args) throws SQLException {
        GUI frame = new GUI("My First Program");
        frame.setVisible(true);

    }
}
