import java.sql.*;
import java.util.Arrays;

class DataBase {

    private static String password = "";
    private static String username = "";
    private static boolean initialized = true;
    static Connection con;


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
        System.out.println(con);

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

            System.out.println("Records Exist " + Arrays.toString(tbData));

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
    public void insert(String f1, String f2, String f3, int f4, String f5) throws SQLException {
        con = connect();
        PreparedStatement prep = con.prepareStatement("INSERT INTO users VALUES('" + f1 + "','" + f2 + "', '" + f3 + "', '" + f4 + "', '" + f5 + "')");
        prep.executeUpdate();
        con.close();
    }

    public void deleteRecords() throws SQLException {
        con = connect();
        PreparedStatement prep = con.prepareStatement("DELETE FROM users;");
        prep.executeUpdate();
        con.close();
    }

}