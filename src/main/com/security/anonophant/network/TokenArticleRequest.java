package main.com.security.anonophant.network;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Max on 4/17/2015.
 */
public class TokenArticleRequest extends BaseRequest
{
    private String newToken;
    private String oldToken;

    public TokenArticleRequest(String URI, int port, String newToken, String oldToken) throws IOException {
        super(URI, port, null);
        this.newToken = newToken;
        this.oldToken = oldToken;
    }

    @Override
    public void write() throws IOException
    {

    }

    @Override
    protected ArrayList<String> call() throws Exception
    {
        System.out.println("TOKEN ARTICLE REQUEST IS BEING CALLED");
        Socket soc = getRequestSocket();
        BufferedWriter write = new BufferedWriter(new OutputStreamWriter(soc.getOutputStream()));
        write.write("PREVIOUS_TOKEN\n");
        write.write(newToken+"\n");
        write.write(oldToken+"\n");
        write.flush();
        BufferedReader reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        String line = null;
        ArrayList<String> articles = new ArrayList<>();
        while ((line =reader.readLine()) != null)
        {
            articles.add(line);
        }
        System.out.println("ARTICLES HERE: "+articles);
        close();
        File file = new File(System.getProperty("user.dir") + "/tokens/oldtoken.txt");
        if (!file.exists())
        {
            file.getParentFile().mkdirs();
        }
        PrintWriter out = new PrintWriter(file);
        out.write(newToken);
        out.flush();
        out.close();
        return articles;
    }
}
