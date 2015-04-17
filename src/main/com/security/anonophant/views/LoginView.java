/**
 * Login
 *
 * @author Aaron Weaver  (waaronl@okstate.edu)
 * @version 1.0
 * @since 3/30/15
 */

package main.com.security.anonophant.views;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.com.security.anonophant.network.TestRequest;
import main.com.security.anonophant.utils.LayoutConstants;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by weava on 3/30/15.
 */
public class LoginView extends Stage
{
    private final String STAGE_TITLE = "Login";

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
        TestRequest request = new TestRequest("www.test.com", 22, null);
        request.setOnSucceeded(new TaskSuccessHandler());
        new Thread(request).start();
    }

    private class LoginController implements Initializable
    {
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

        }
    }

    /**
     * Each request will probably have a unique success handler.
     * This will decide what to do when a task is complete.
     *
     * This should be created in the class that is threading the request,
     * as this will make it easier to update UI and other elements.
     */
    private class TaskSuccessHandler implements EventHandler<WorkerStateEvent>
    {
        @Override
        public void handle(WorkerStateEvent event) {

        }
    }
}
