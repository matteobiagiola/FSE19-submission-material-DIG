package org.evosuite.selenium.state;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.evosuite.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResetAppState {

	private static final Logger logger = LoggerFactory.getLogger(ResetAppState.class);
	private static Exception exception;

	public static void resetViaReflection(){
		//String parameter
		//Class[] paramString = new Class[1];
		//paramString[0] = String.class;

        if(!reset()){
            throw new IllegalStateException("resetViaReflection: stop execution failed to reset state. Runtime exception: " + exception.getCause());
        }
	}

	private static boolean reset(){
		try{
			//load the ResetAppState at runtime
			if(Properties.RESET_CLASS_NAME.isEmpty())
				throw new IllegalStateException("Specify a proper value for the property RESET_CLASS_NAME.");

			String qualifiedNameOfResetClass = Properties.RESET_CLASS_NAME;
			Class cls = Class.forName(qualifiedNameOfResetClass);
			Object obj = cls.newInstance();
			//call the reset method with a parameter of type string
			//Method method = cls.getDeclaredMethod("reset", paramString);
			Method method = cls.getDeclaredMethod("reset");
			//invoke method with a parameter
			//method.invoke(obj, "http://localhost:4000");
			Object o = method.invoke(obj);
			return true;
		}catch(ClassNotFoundException|
                IllegalStateException|
                InvocationTargetException|
                NoSuchMethodException|
                IllegalAccessException|
                InstantiationException ex){
			exception = ex;
			return false;
		}
    }

	/*public static void reset(){
		//List<String> tables = Arrays.asList("wallets","wallet_accesses","users","transactions","plans","plan_wallets","authentications");
		if(Properties.RESET_MYSQL_DB){
			List<String> tablesToReset = new ArrayList<String>();
			for(int i = 0; i < Properties.MYSQL_DB_TABLES.length; i++){
				//System.out.println("@@@table: " + Properties.MYSQL_DB_TABLES[i]);
				tablesToReset.add(Properties.MYSQL_DB_TABLES[i]);
			}
			logger.debug("Tables to reset: " + tablesToReset);
			resetDB(Properties.MYSQL_DB_USERNAME, Properties.MYSQL_DB_PASSWORD, Properties.MYSQL_DB_NAME, tablesToReset);
		}
		WebDriver driver = Driver.getDriverTesting();
		clearCookies(driver);
		driver.get(Properties.APP_URL);
	}*/

	/*private static void resetDB(String username, String password, String dbName, List<String> tables){
		MySqlConnection mySqlConnection = new MySqlConnection();
		mySqlConnection.resetDBState(username,password,dbName,tables);
	}*/

	/*private static void clearCookies(WebDriver driver){
		if(Properties.LOGOUT){
			if(Properties.RESET_COOKIES){
				for (int i = 0; i < Properties.COOKIE_NAMES.length; i++) {
					String cookieName = Properties.COOKIE_NAMES[i];
					if(driver.manage().getCookieNamed(cookieName) != null){
						driver.manage().deleteCookieNamed(cookieName);
					}
				}
			}
		}
	}*/
}
