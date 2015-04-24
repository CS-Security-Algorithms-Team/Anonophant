package main.com.security.anonophant; /**
 * Main
 *
 * @author Aaron Weaver  (waaronl@okstate.edu)
 * @version 1.0
 * @since 3/30/15
 */

import java.io.File;

import main.com.security.anonophant.utils.LayoutConstants;
import main.com.security.anonophant.utils.LoggingUtil;
import main.com.security.anonophant.views.ArticleView;
import main.com.security.anonophant.views.LoginView;
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
        LoginView loginView = new LoginView();
        loginView.show();
        //ArticleView articleView = new ArticleView();
        //articleView.show();
    }

    public static void main(String[] args)
    {
    	File file = new File(LayoutConstants.LAYOUT_LOGIN);
    	LoggingUtil.log(LOG_TAG, "Login layout exists? " + file.exists(), LoggingUtil.LEVEL_DEBUG);
        LoggingUtil.log(LOG_TAG, LayoutConstants.LAYOUT_LOGIN, LoggingUtil.LEVEL_DEBUG);
        launch(args);
    }
}
