/**
 * Login
 *
 * @author Aaron Weaver  (waaronl@okstate.edu)
 * @version 1.0
 * @since 3/30/15
 */

package main.com.security.anonophant.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.com.security.anonophant.network.TTPRequest;
import main.com.security.anonophant.network.TestRequest;
import main.com.security.anonophant.utils.LayoutConstants;

import javafx.scene.input.MouseEvent;
import main.com.security.anonophant.utils.LoggingUtil;
import main.com.security.anonophant.utils.NetworkConstants;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by weava on 3/30/15.
 */
public class LoginView extends Stage
{
    private final String STAGE_TITLE = "Login";
    public Stage currentStage = this;

    public LoginView()
    {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(LayoutConstants.LAYOUT_LOGIN));
        fxmlLoader.setController(new LoginController());
        Parent root = fxmlLoader.load();
        Scene loginScene = new Scene(root);

        this.setTitle(STAGE_TITLE);
        this.setScene(loginScene);
        this.setResizable(false);
    }

    private class LoginController implements Initializable, EventHandler<MouseEvent> {
        private final String LOG_TAG = "LOGIN CONTROLLER";

        private String username;
        private String password;
        private String appName;
        private boolean lastActivityChecked;

        @FXML
        ComboBox combo_app_name;

        @FXML
        TextField text_field_login;

        @FXML
        PasswordField text_field_password;

        @FXML
        CheckBox check_box_last_activity;

        @FXML
        Button button_login;

        @Override
        public void initialize(URL location, ResourceBundle resources)
        {
            ArrayList<String> applicationsList = new ArrayList<>();
            applicationsList.add("Tech News");
            ObservableList<String> applicationsObservable = FXCollections.observableArrayList(applicationsList);
            combo_app_name.setItems(applicationsObservable);

            button_login.setOnMouseClicked(this);
        }

        @Override
        public void handle(MouseEvent event)
        {
            LoggingUtil.log(LOG_TAG, "The login button has been clicked", LoggingUtil.LEVEL_DEBUG);

            /*
             * Finalize all items on the login screen to be verified.
             */
            username = text_field_login.getText().trim();
            password = text_field_password.getText();
            lastActivityChecked = check_box_last_activity.isSelected();
            appName = combo_app_name.getSelectionModel().getSelectedItem().toString();

            String[] loginInfo = new String[3];
            loginInfo[0] = username;
            loginInfo[1] = password;
            loginInfo[2] = appName;

            LoggingUtil.log(LOG_TAG, "\nApp Name: " + appName + "\nUsername: " + username +
                    "\nPassword: " + password + "\nLast Activity: " + lastActivityChecked, LoggingUtil.LEVEL_DEBUG);

            handleLogin(loginInfo);
        }

        public void handleLogin(String[] loginInfo)
        {
                    /*
         * Sample request for sending to test.com over port 22
         * Using task, you have the ability to bind data and views to update
         * loading screens and other data types
         *
         * To see how to bind data types and views, look at JavaFX docs
         *
         * this website (http://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm)
         * was particularly helpful.
         */

            TTPRequest request = null;
            try {
                request = new TTPRequest(NetworkConstants.TTP_URL, NetworkConstants.TTP_PORT, loginInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
            request.setOnSucceeded(new TTPSuccessHandler());
        new Thread(request).start();
        }
    }

    /**
     * Each request will probably have a unique success handler.
     * This will decide what to do when a task is complete.
     *
     * This should be created in the class that is threading the request,
     * as this will make it easier to update UI and other elements.
     */
    private class TTPSuccessHandler implements EventHandler<WorkerStateEvent>
    {
        @Override
        public void handle(WorkerStateEvent event)
        {
            currentStage.hide();
            ArticleView articleView = new ArticleView();
            articleView.show();
        }
    }
}
