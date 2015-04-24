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
import main.com.security.anonophant.network.*;
import main.com.security.anonophant.utils.LayoutConstants;
import main.com.security.anonophant.utils.UserSettings;

import java.io.*;
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
public class ArticleView extends Stage {
    private final String STAGE_TITLE = "Anonophant";
    private ArticleController articleController;
    private HashMap<String, Integer> mediaIds = new HashMap<>();

    private boolean isRequestingOldToken = false;
    public String oldToken;
    public String articleContents;

    public ArticleView() {
        try {
            isRequestingOldToken = false;
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArticleView(boolean lastActivity) {
        isRequestingOldToken = true;
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(LayoutConstants.LAYOUT_ARTICLES));
        articleController = new ArticleController();
        fxmlLoader.setController(articleController);
        Parent root = fxmlLoader.load();
        Scene articleScene = new Scene(root);


        this.setTitle(STAGE_TITLE);
        this.setScene(articleScene);

        ArticleListRequest alr = new ArticleListRequest("localhost", 12345, null);
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

    private class ArticleController implements Initializable {
        @FXML
        WebView web_view_article;

        @FXML
        ListView list_view_articles;

        @Override
        public void initialize(URL location, ResourceBundle resources) {

            String[] authSettings = {UserSettings.providerPasswords.get("Tech News")};
            if (isRequestingOldToken) {
                System.out.println("REQUESTING OLD TOKEN?      " + isRequestingOldToken);
                try {
                    AuthenticationRequest authRequest = new AuthenticationRequest("localhost", 8888, authSettings);
                    authRequest.setOnSucceeded(ev -> {
                        try {
                            String token = authRequest.get().get(0);
                            File file = new File(System.getProperty("user.dir") + "/tokens/oldtoken.txt");

                            BufferedReader reader = new BufferedReader(new FileReader(file));
                            oldToken = reader.readLine();
                            TokenArticleRequest tar = new TokenArticleRequest("localhost", 12345,  token, oldToken);
                            tar.setOnSucceeded(tarEvent -> {
                                try {
                                    articleContents = String.join("", tar.get());
                                    System.out.println("ARTICLE CONTENTS:  " + articleContents);
                                    web_view_article.getEngine().loadContent(articleContents);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }
                            });
                            new Thread(tar).start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    });
                    new Thread(authRequest).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

                list_view_articles.setOnMouseClicked(
                        event -> {
                            System.out.println("CLICKED");


                            String title = (String) list_view_articles.getSelectionModel().getSelectedItem();
                            if (title == null) {
                                return;
                            }
                            int mediaId = mediaIds.get(title);
                            Date aLittleAfterNow = new Date(new Date().getTime() + 3000); //THIS IS OBVIOUSLY INCORRECT
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");


                            try {
                                AuthenticationRequest authenticationRequest = new AuthenticationRequest("localhost", 8888, authSettings);
                                authenticationRequest.setOnFailed(new FailHandler("AUTHENTICATION ISNT WORKING"));
                                authenticationRequest.setOnSucceeded(ev -> {
                                            try {
                                                System.out.println("AUTH HAS SUCCEEDED");
                                                String token = authenticationRequest.get().get(0);
                                                System.out.println("REQUESTING OLD TOKEN?      " + isRequestingOldToken);
                                                try {
                                                    NewArticleRequest nar = null;
                                                        nar = new NewArticleRequest("localhost", 12345, token, mediaId);

                                                        final NewArticleRequest finalNar = nar;
                                                        nar.setOnSucceeded(narEvent -> {
                                                            try {
                                                                String allOne = String.join("", finalNar.get());
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

                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            } catch (ExecutionException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                );

                                Thread t = new Thread(authenticationRequest);
                                t.start();
                            } catch (IOException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                );
            }
        }
    }
