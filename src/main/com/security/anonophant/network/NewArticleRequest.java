/**
 * NewArticleRequest
 *
 * @author Aaron Weaver  (waaronl@okstate.edu)
 * @version 1.0
 * @since 4/17/15
 */

package main.com.security.anonophant.network;



import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by weava on 4/17/15.
 *
 *
 */
public class NewArticleRequest extends BaseRequest
{

    private int articleId;
    private String token;

    public NewArticleRequest(String URI, int port, String newToken, int articleId) throws IOException {
        super(URI, port, null);
        this.articleId = articleId;
        this.token = newToken;

        this.setOnFailed(new FailHandler("Could not connect to Gateway."));
    }

    @Override
    public void write() throws IOException
    {


    }

    @Override
    protected ArrayList<String> call() throws Exception
    {
        Socket soc = getRequestSocket();
        BufferedWriter write = new BufferedWriter(new OutputStreamWriter(soc.getOutputStream()));
        write.write("NEW_CONTENT\n");
        write.write(token+"\n");
        write.write(articleId + "\n");
        write.flush();


        ArrayList<String> articleLines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));

        String line = "";
        while ((line = reader.readLine())!=null)
        {
            articleLines.add(line);
        }
        close();
        File file = new File(System.getProperty("user.dir") + "/tokens/oldtoken.txt");
        if (!file.exists())
        {
            file.getParentFile().mkdirs();
        }
        PrintWriter out = new PrintWriter(file);
        out.write(token);
        out.flush();
        out.close();
        return articleLines;
    }
}
