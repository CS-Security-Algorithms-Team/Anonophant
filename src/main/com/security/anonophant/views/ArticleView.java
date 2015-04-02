/**
 * ArticleView
 *
 * @author Aaron Weaver  (waaronl@okstate.edu)
 * @version 1.0
 * @since 4/1/15
 */

package main.com.security.anonophant.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import main.com.security.anonophant.utils.LayoutConstants;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by weava on 4/1/15.
 */
public class ArticleView extends Stage
{
    private final String STAGE_TITLE = "Anonophant";

    public ArticleView()
    {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(LayoutConstants.LAYOUT_ARTICLES));
        fxmlLoader.setController(new ArticleController());
        Parent root = fxmlLoader.load();
        Scene loginScene = new Scene(root);

        this.setTitle(STAGE_TITLE);
        this.setScene(loginScene);
    }

    private class ArticleController implements Initializable
    {
        @FXML
        WebView web_view_article;

        @FXML
        ListView list_view_articles;

        @Override
        public void initialize(URL location, ResourceBundle resources)
        {

        }
    }
}
