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
import main.com.security.anonophant.network.ArticleListRequest;
import main.com.security.anonophant.network.NewArticleRequest;
import main.com.security.anonophant.utils.LayoutConstants;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

/**
 * Created by weava on 4/1/15.
 */
public class ArticleView extends Stage
{
    private final String STAGE_TITLE = "Anonophant";
    private ArticleController articleController;
    private HashMap<String, Integer> mediaIds = new HashMap<>();

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
        articleController = new ArticleController();
        fxmlLoader.setController(articleController);
        Parent root = fxmlLoader.load();
        Scene loginScene = new Scene(root);



        this.setTitle(STAGE_TITLE);
        this.setScene(loginScene);

        ArticleListRequest alr = new ArticleListRequest("localhost",12345,null);
        alr.setOnSucceeded(event ->
        {
            System.out.println("alrSuccess!");
            try {
                ArrayList<String> articles = alr.get();
                ArrayList<String> articleTitles = new ArrayList<String>();
                articles.stream()
                        .forEach(str ->
                        {
                            String[] pieces = str.split(":");
                            mediaIds.put(pieces[1], Integer.parseInt(pieces[0]));
                            articleTitles.add(pieces[1]);
                        });
                articleController.list_view_articles.getItems().addAll(articleTitles);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        alr.setOnFailed(event -> System.out.println("alrFail"));
        Thread t = new Thread(alr);
        t.start();
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
            list_view_articles.setOnMouseClicked(
                    event->{
                        System.out.println("CLICKED");
                        String title = (String)list_view_articles.getSelectionModel().getSelectedItem();
                        int mediaId = mediaIds.get(title);
                        Date aLittleAfterNow = new Date(new Date().getTime()+3000); //THIS IS OBVIOUSLY INCORRECT
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

                        try {
                            NewArticleRequest nar = new NewArticleRequest("localhost",12345,"TOKEN|florida|"+sdf.format(aLittleAfterNow) , mediaId);
                            nar.setOnSucceeded(narEvent -> {
                                try {
                                    String allOne = String.join("",nar.get());
                                    web_view_article.getEngine().loadContent(allOne);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }
                            });
                            nar.setOnFailed(narEvent -> System.out.println("nar failed"));
                            Thread t1 = new Thread(nar);
                            t1.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
            );
        }

    }
}
