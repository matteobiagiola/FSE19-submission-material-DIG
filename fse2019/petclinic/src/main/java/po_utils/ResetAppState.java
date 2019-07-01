package po_utils;

import org.openqa.selenium.WebDriver;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ResetAppState {

    public static void reset(WebDriver driver){
        resetDB("root","root","petclinic");
        driver.get("http://localhost:" + MyProperties.getInstance().getProperty("appPort"));
    }

    public static void reset(){
        resetDB("root","root","petclinic");
    }

    // needed for code-coverage
    public static void quitDriver(WebDriver driver){
        driver.quit();
    }

    private static void resetDB(String username, String password, String dbName){
        int port = Integer.valueOf(MyProperties.getInstance().getProperty("dbPort"));
        MySqlConnection mySqlConnection = new MySqlConnection();
        Optional<Connection> optionalConnection = mySqlConnection.establishDBConnection(username, password, port, dbName);
        if(optionalConnection.isPresent()){
            Connection connection = optionalConnection.get();
            List<String> tablesToReset = new ArrayList<String>();
            tablesToReset.add("pets");
            tablesToReset.add("owners");
//            tablesToReset.add("specialties");
//            tablesToReset.add("types");
//            tablesToReset.add("vet_specialties");
//            tablesToReset.add("vets");
            tablesToReset.add("visits");
            mySqlConnection.resetTables(connection,"petclinic",tablesToReset);
            /*String sqlInsertScript = "INSERT INTO `users` (`id`, `email`, `type`, `password`, `login`, `is_demo`, `is_admin`, `registration_date`, `activity_date`, `registration_ip`, `activity_ip`, `confirmation_code`, `remove_account_code`, `password_restore_code`, `is_banned`)\n" +
                    "VALUES\n" +
                    "\t(1, 'asd@asd.com', 'default', '729da45a6f270a749cb4e76548f7b624', 'asd', 0, 0, 1518350216, 1518350216, '::ffff:172.17.0.1', '::ffff:172.17.0.1', NULL, NULL, NULL, 0);\n";
            mySqlConnection.insertStatement(connection,sqlInsertScript);*/
            mySqlConnection.closeConnection(connection);
        }else{
            throw new IllegalStateException(ResetAppState.class.getName() + ": connection to remote mysql server failed.");
        }
    }


}
