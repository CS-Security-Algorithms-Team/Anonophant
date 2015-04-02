/**
 * Login
 *
 * @author Aaron Weaver  (waaronl@okstate.edu)
 * @version 1.0
 * @since 3/30/15
 */

package main.com.security.anonophant.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.com.security.anonophant.utils.LayoutConstants;

import java.io.IOException;

/**
 * Created by weava on 3/30/15.
 */
public class Login extends Stage
{
    private final String STAGE_TITLE = "Login";

    public Login()
    {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(LayoutConstants.LAYOUT_LOGIN));
        Scene loginScene = new Scene(root);

        this.setTitle(STAGE_TITLE);
        this.setScene(loginScene);
        this.setResizable(false);
    }
}
