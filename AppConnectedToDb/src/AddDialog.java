import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel firstNameLabel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JCheckBox maleCheckBox;
    private JCheckBox femaleCheckBox;
    private JTextField FieldTextField;
    private JTextField yearTextField;
    private JLabel sexLabel;
    private JLabel yearLabel;
    private JLabel fieldLabel;
    private JLabel lastNameLabel;
    private JPanel PanelWithComponents;
    private JPanel MainPanel;

    public AddDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() throws SQLException {

        ArrayList<String> personToAdd = new ArrayList<>();
        personToAdd.add(firstNameTextField.getText());
        personToAdd.add(lastNameTextField.getText());
        personToAdd.add(FieldTextField.getText());
        personToAdd.add(String.valueOf(Integer.parseInt(yearTextField.getText())));

        if (maleCheckBox.isSelected()) {

            personToAdd.add("Male");

        } else {
            personToAdd.add("Female");
        }
        GUI.addPerson(personToAdd);
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {

        AddDialog dialog = new AddDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
