import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

class DataBase {

    private static String password = "";
    private static String username = "";
    private static boolean initialized = true;
    static Connection con;
    static int columnCount;


    public Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sonoo", "root", "patafian73");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from users");
            stmt.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }

    public void loadRecords() throws SQLException {

        con = connect();

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from users");
        while (rs.next()) {

//                JOptionPane.showMessageDialog(null, "You are now logon!");
            String firstName = rs.getString("FirstName");
            String lastName = rs.getString("LastName");
            String fieldOfStudy = rs.getString("FieldOfStudy");
            String year = String.valueOf(rs.getInt("Year"));
            String sex = rs.getString("Sex");
            String[] tbData = {firstName, lastName, fieldOfStudy, year, sex};

            GUI.model.addRow(tbData);
            GUI.model.fireTableDataChanged();
        }

        con.close();
    }

    //1) public Statement createStatement(): creates a statement object that can be used to execute SQL queries.
    //2) public Statement createStatement(int resultSetType,int resultSetConcurrency): Creates a Statement object that will generate ResultSet objects with the given type and concurrency.
    //3) public void setAutoCommit(boolean status): is used to set the commit status.By default it is true.
    //4) public void commit(): saves the changes made since the previous commit/rollback permanent.
    //5) public void rollback(): Drops all changes made since the previous commit/rollback.
    //6) public void close(): closes the connection and Releases a JDBC resources immediately.
    public void insert(ArrayList<String> person ) throws SQLException {
        con = connect();

        StringBuilder str = new StringBuilder("INSERT INTO users VALUES ('");

        int count=0;
        while (person.size()-1 > count) {
            str.append(person.get(count)).append("','");
            count++;
        }
        str.append(person.get(count)).append("');");
        PreparedStatement prep = con.prepareStatement(str.toString());
        prep.executeUpdate();
        con.close();
    }

    public void deleteRecords() throws SQLException {
        con = connect();
        PreparedStatement prep = con.prepareStatement("DELETE FROM users;");
        prep.executeUpdate();
        con.close();
    }

    public void deleteRow(String selected) throws SQLException{
        con = connect();
        PreparedStatement prep = con.prepareStatement("DELETE FROM users WHERE FirstName='"+selected+"' ");
        prep.executeUpdate();
        con.close();
    }

    public void addColumns(DefaultTableModel model) throws SQLException {

        con = connect();


        ArrayList<String> cols = new ArrayList<>();

        Statement statement = con.createStatement();

        ResultSet results = statement.executeQuery("SELECT * FROM users");

        ResultSetMetaData metadata = results.getMetaData();

        columnCount = metadata.getColumnCount();

        for (int i=1; i<=columnCount; i++) {

            String columnName = metadata.getColumnName(i);

            cols.add(columnName);

        }
        model.setColumnIdentifiers(cols.toArray());
        model.fireTableDataChanged();
        con.close();
    }

}