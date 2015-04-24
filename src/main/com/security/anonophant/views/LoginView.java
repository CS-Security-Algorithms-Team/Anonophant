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
import main.com.security.anonophant.network.AuthenticationRequest;
import main.com.security.anonophant.network.TTPRequest;
import main.com.security.anonophant.network.TestRequest;
import main.com.security.anonophant.network.TokenArticleRequest;
import main.com.security.anonophant.utils.LayoutConstants;

import javafx.scene.input.MouseEvent;
import main.com.security.anonophant.utils.LoggingUtil;
import main.com.security.anonophant.utils.NetworkConstants;
import main.com.security.anonophant.utils.UserSettings;

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
    private boolean lastActivityChecked;
    private final String STAGE_TITLE = "Login";
    public Stage currentStage = this;
    public TTPRequest request;
    public AuthenticationRequest authRequest;
    private LoginController controller;

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
        controller = new LoginController();
        fxmlLoader.setController(controller);
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
        private int appNum;

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
            appNum = combo_app_name.getSelectionModel().getSelectedIndex() + 1;

            String[] loginInfo = new String[3];
            loginInfo[0] = username;
            loginInfo[1] = password;
            loginInfo[2] = appNum + "";

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
            request = null;
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
            System.out.println("THIS IS WORKING");
            String cypheredKey = "";
            ArrayList<String> receivedStrings = request.getFromServer();
            for(int i = 0; i < receivedStrings.size(); i++)
            {
                System.out.println(receivedStrings.get(i));
            }

            if(receivedStrings.size() > 0 && receivedStrings.size() < 2)
            {
                cypheredKey = cypherKey(receivedStrings.get(0));
                System.out.println(cypheredKey);
            }



            cypheredKey = cypheredKey + "\n";

            UserSettings.providerPasswords.put("Tech News",cypheredKey);

            ArticleView articleView = null;

            if(lastActivityChecked)
            {
                System.out.println("You selected last activity");
                articleView = new ArticleView(lastActivityChecked);
            }
            else
            {
                articleView = new ArticleView();
            }
            articleView.show();
            currentStage.hide();
        }



        private String cypherKey(String message)
        {
            int k = 3;
            String cyphered = "";

            for(int i = 0; i < message.length(); i++)
            {
                char x = message.charAt(i);
                x = (char) (x + k);
                cyphered = cyphered + x;
            }

            return cyphered;
        }
    }

    private class AuthenticationSuccessHandler implements EventHandler<WorkerStateEvent>
    {
        String token;

        @Override
        public void handle(WorkerStateEvent event)
        {
            System.out.println("Authentication successful :)");

            ArrayList<String> receivedFromServer = authRequest.getFromServer();

            if(receivedFromServer.size() > 0 && receivedFromServer.size() < 2)
            {
                token = receivedFromServer.get(0);
            }
        }

        public String getToken()
        {
            return token;
        }
    }
}
