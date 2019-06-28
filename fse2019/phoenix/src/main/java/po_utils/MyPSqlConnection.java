package po_utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class MyPSqlConnection {

    public MyPSqlConnection(){

    }

    private Optional<Connection> connect(String user, String password, String dbName, int port, String ssl){
        String url = "jdbc:postgresql://localhost:" + port + "/" + dbName + "?user=" + user + "&password=" + password + "&ssl=" + ssl;
        try {
            Connection conn = DriverManager.getConnection(url);
            return Optional.of(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public void reset(String username, String password, String dbName, int port, String ssl){
        Optional<Connection> optionalConnection = this.connect(username, password, dbName, port, ssl);
        if(optionalConnection.isPresent()){
            Connection connection = optionalConnection.get();
            try {
                Statement st = connection.createStatement();
                st.executeUpdate("truncate table boards cascade;");
                st.executeUpdate("truncate table card_members cascade;");
                st.executeUpdate("truncate table cards cascade;");
                st.executeUpdate("truncate table comments cascade;");
                st.executeUpdate("truncate table lists cascade;");
                st.executeUpdate("truncate table schema_migrations cascade;");
                st.executeUpdate("truncate table user_boards cascade;");
                st.executeUpdate("truncate table users cascade;");
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                st.executeUpdate("insert into users (first_name,last_name,email,encrypted_password,inserted_at,updated_at) VALUES ('John', 'Doe', 'john@phoenix-trello.com', '$2b$12$Ptnoc/IyfWMIOFWrworbu.f6UJ1AAVHwGgQwLnk5AS8NnJ5XVyvU2','" + dateFormat.format(date) + "'," + "'" + dateFormat.format(date) + "')");
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            throw new IllegalStateException(this.getClass().getName() + ": connection failed");
        }
    }
}
