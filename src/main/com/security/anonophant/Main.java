package main.com.security.anonophant; /**
 * Main
 *
 * @author Aaron Weaver  (waaronl@okstate.edu)
 * @version 1.0
 * @since 3/30/15
 */

import main.com.security.anonophant.utils.LayoutConstants;
import main.com.security.anonophant.utils.LoggingUtil;
import main.com.security.anonophant.views.Login;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by weava on 3/30/15.
 */
public class Main extends Application {

    private static final String LOG_TAG = "MAIN ACTIVITY";

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Login login = new Login();
        login.show();
    }

    public static void main(String[] args)
    {
        System.out.println("LOL");
        LoggingUtil.log(LOG_TAG, LayoutConstants.LAYOUT_LOGIN, LoggingUtil.LEVEL_DEBUG);
        launch(args);
    }
}
