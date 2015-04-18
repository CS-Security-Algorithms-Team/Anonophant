/**
 * ArticleView
 *
 * @author Aaron Weaver  (waaronl@okstate.edu)
 * @version 1.0
 * @since 4/1/15
 */

package main.com.security.anonophant.views;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import main.com.security.anonophant.utils.LayoutConstants;

import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by weava on 4/1/15.
 */
public class ArticleView extends Stage
{
    private final String STAGE_TITLE = "Anonophant";
    private Scene artScene;

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
        Scene articleScene = new Scene(root);
        artScene = articleScene;

        this.setTitle(STAGE_TITLE);
        this.setScene(articleScene);
    }

    private class ArticleController implements Initializable
    {
        @FXML
        WebView web_view_article;

        @FXML
        ListView list_view_articles;

        @FXML
        AnchorPane anchor_pane_articles;

        @Override
        public void initialize(URL location, ResourceBundle resources)
        {
            //DoubleProperty.doubleProperty(  )
            //anchor_pane_articles.layoutXProperty().bind();
        }
    }
}
