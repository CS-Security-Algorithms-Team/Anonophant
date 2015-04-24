/**
 * ArticleListRequest
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
 */
public class ArticleListRequest extends BaseRequest
{
    public ArticleListRequest(String URI, int port, String[] serverStrings) throws IOException
    {
        super(URI, port, serverStrings);

        this.setOnFailed(new FailHandler("Could not connect to provider."));
    }

    @Override
    public void write()
    {

    }

    @Override
    protected ArrayList<String> call() throws Exception
    {
        Socket soc = getRequestSocket();
        BufferedWriter write = new BufferedWriter(new OutputStreamWriter(soc.getOutputStream()));
        write.write("CONTENT_LIST\n");
        write.write("NO_TOKEN\n");
        write.write("*\n");
        write.flush();
        BufferedReader reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        String line = null;
        ArrayList<String> articles = new ArrayList<>();
        while ((line =reader.readLine()) != null)
        {
            articles.add(line);
        }
        close();
        return articles;
    }
}
