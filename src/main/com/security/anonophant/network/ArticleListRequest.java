/**
 * ArticleListRequest
 *
 * @author Aaron Weaver  (waaronl@okstate.edu)
 * @version 1.0
 * @since 4/17/15
 */

package main.com.security.anonophant.network;

import java.io.IOException;
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
        return null;
    }
}
