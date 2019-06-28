package po_utils;

import org.openqa.selenium.WebDriver;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ResetAppState {

    public static void main(String[] args) {
        reset();
    }

    public static void reset(){
        List<String> tables = Arrays.asList("wallets","wallet_accesses","users","transactions","plans","plan_wallets","authentications");
        resetDB("root","root","walletjs",tables);
    }

    public static void resetClient(WebDriver driver) {
        clearCookies(driver);
    }

    private static void resetDB(String username, String password, String dbName, List<String> tables){
        int port = Integer.valueOf(MyProperties.getInstance().getProperty("dbPort"));
        MySqlConnection mySqlConnection = new MySqlConnection();
        Optional<Connection> optionalConneciton = mySqlConnection.establishDBConnection("root","root",port,"walletjs");
        if(optionalConneciton.isPresent()){
            Connection connection = optionalConneciton.get();
            List<String> tablesToReset = new ArrayList<String>();
            tablesToReset.add("authentications");
            tablesToReset.add("plan_wallets");
            tablesToReset.add("plans");
            tablesToReset.add("transactions");
            tablesToReset.add("users");
            tablesToReset.add("wallet_accesses");
            tablesToReset.add("wallets");
            mySqlConnection.resetTables(connection,"walletjs",tablesToReset);
            /*String sqlInsertScript = "INSERT INTO `users` (`id`, `email`, `type`, `password`, `login`, `is_demo`, `is_admin`, `registration_date`, `activity_date`, `registration_ip`, `activity_ip`, `confirmation_code`, `remove_account_code`, `password_restore_code`, `is_banned`)\n" +
                    "VALUES\n" +
                    "\t(1, 'asd@asd.com', 'default', '729da45a6f270a749cb4e76548f7b624', 'asd', 0, 0, 1518350216, 1518350216, '::ffff:172.17.0.1', '::ffff:172.17.0.1', NULL, NULL, NULL, 0);\n";
            mySqlConnection.insertStatement(connection,sqlInsertScript);*/
            mySqlConnection.closeConnection(connection);
        }else{
            throw new IllegalStateException(ResetAppState.class.getName() + ": connection to remote mysql server failed.");
        }
    }

    private static void clearCookies(WebDriver driver){
        if(driver.manage().getCookieNamed("is_logged_in_user") != null){
            driver.manage().deleteCookieNamed("is_logged_in_user");
        }
        if(driver.manage().getCookieNamed("logged_in_user") != null){
            driver.manage().deleteCookieNamed("logged_in_user");
        }
    }


}
