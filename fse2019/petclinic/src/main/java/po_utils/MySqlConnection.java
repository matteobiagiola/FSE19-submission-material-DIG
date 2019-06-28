package po_utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class MySqlConnection {

    public MySqlConnection(){
    }

    public void insertStatement(Connection connection, String sqlInsertScript){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlInsertScript);
            this.closeStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Connection> establishDBConnection(String username, String password, int port, String dbName){
        // This will load the MySQL driver, each DB has its own driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            // Setup the connection with the DB
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:" + port + "/" + dbName, username, password);
            return Optional.of(connection);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void resetTables(Connection connection, String dbName, List<String> tables){
        try {
            Statement statement = connection.createStatement();
            tables.stream().forEach(new Consumer<String>() {
                @Override
                public void accept(String table) {
                    try {
//                        statement.executeUpdate("truncate " + table);
                        statement.executeUpdate("SET FOREIGN_KEY_CHECKS=0;");
                        statement.executeUpdate("delete from " + dbName + "." + table);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            this.closeStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeStatement(Statement statement){
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    /*public void resetDBState(String username, String password, String dbname, List<String> tables){
        try {
            this.tables = tables;
            this.establishDBConnection(username, password, 3306, dbname);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }*/

    /*public void resetDBStateSSH(String username, String password, String dbname, List<String> tables){
        int assigned_port = this.establishSSHConnection();
        if(assigned_port == 0){
            System.out.println("Connection failed port = 0! Exit.");
            this.session.disconnect();
            return;
        }
        try {
            this.tables = tables;
            this.establishDBConnection(username, password, assigned_port, dbname);
            this.closeSSHConnection();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.session.disconnect();
        }

    }*/

    /*private int establishSSHConnection(){
        JSch jsch = new JSch();
        int assigned_port = 0;
        // Create SSH session.  Port 2222 is your SSH port which
        // is open in your firewall setup.
        try {
            this.session = jsch.getSession("vagrant","127.0.0.1", 2222);
            this.session.setPassword("vagrant");

            // Additional SSH options.  See your ssh_config manual for
            // more options.  Set options according to your requirements.
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            config.put("Compression", "yes");
            config.put("ConnectionAttempts","2");

            this.session.setConfig(config);

            this.session.connect();
            final int local_port = 5000;
            assigned_port = session.setPortForwardingL(local_port,
                    "127.0.0.1", 3306);
            return assigned_port;
        } catch (JSchException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            this.session.disconnect();
            return 0;
        }
    }*/

    /*private void closeSSHConnection(){
        if(this.session != null){
            this.session.disconnect();
        }
    }*/

    /*private void establishDBConnection(String username, String password, int port, String dbname) throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            // Setup the connection with the DB
            this.connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:" + port + "/" + dbname, username, password);
            // Statements allow to issue SQL queries to the database
            this.statement = connect.createStatement();
            // Result set get the result of the SQL query
            this.resetTables(dbname);
                    for(String table: tables){
                    	this.statement.executeUpdate("delete from " + dbname + "." + table);
                    }
            //writeResultSet(resultSet);
        } catch (Exception e) {
            throw e;
        } finally {
            closeOperation();
        }
    }*/

    // You need to closeOperation the resultSet
    /*private void closeOperation() {
        try {
            if (resultSet != null) {
                resultSet.closeOperation();
            }

            if (statement != null) {
                statement.closeOperation();
            }

            if (connect != null) {
                connect.closeOperation();
            }
            if(session != null){
                this.session.disconnect();
            }
        } catch (Exception e) {

        }
    }*/

    /*public void resetTables(String dbname){
        // Statements allow to issue SQL queries to the database
        Statement statement;
        try {
            statement = this.connect.createStatement();
            // Result set get the result of the SQL query
            for(String table: tables){
                this.statement.executeUpdate("delete from " + dbname + "." + table);
            }
	        statement.executeUpdate("delete from addressbook.group_list");
	        statement.executeUpdate("delete from addressbook.addressbook");
	        statement.executeUpdate("delete from addressbook.address_in_groups");

            //insertIntoDB();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/

    /*public void insertIntoDB(){
        Statement statement;
        try {
            statement = this.connect.createStatement();
            insertGroups(statement);
            insertBooks(statement);
            bindBooksToGroups(statement);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }*/

    /*public void insertGroups(Statement statement) throws SQLException{
        statement.executeUpdate("INSERT INTO addressbook.group_list (group_id, group_name, group_header, group_footer)" +
                "VALUES (1, 'Futsal','Falcao','Marcos')");
        statement.executeUpdate("INSERT INTO addressbook.group_list (group_id, group_name, group_header, group_footer)" +
                "VALUES (2, 'Tennis','Federer','Nadal')");
    }*/

    /*public void insertBooks(Statement statement) throws SQLException{
        statement.executeUpdate("INSERT INTO addressbook.addressbook (id, firstname, lastname, email, bday, bmonth, byear, aday, amonth, ayear)" +
                "VALUES (1,'Foo','Bar','foo@bar.com',12,'March',1991,19,'June',2012)");
        statement.executeUpdate("INSERT INTO addressbook.addressbook (id, firstname, lastname, email, bday, bmonth, byear, aday, amonth, ayear)" +
                "VALUES (2,'Matteo','Biagiola','asd@asd.com',13,'May',1991,1,'September',2016)");
    }*/

    /*public void bindBooksToGroups(Statement statement) throws SQLException{
        //book id=1 binded with group group_id=2
        statement.executeUpdate("INSERT INTO addressbook.address_in_groups (id, group_id)" +
                "VALUES (1, 1)");
        //book id=1 binded with group group_id=2
        statement.executeUpdate("INSERT INTO addressbook.address_in_groups (id, group_id)" +
                "VALUES (2, 2)");
    }*/
}
