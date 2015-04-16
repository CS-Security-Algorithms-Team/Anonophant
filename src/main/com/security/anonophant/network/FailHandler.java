/**
 * TestHandler
 *
 * @author Aaron Weaver  (waaronl@okstate.edu)
 * @version 1.0
 * @since 4/15/15
 */

package main.com.security.anonophant.network;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import main.com.security.anonophant.utils.LoggingUtil;

/**
 * Created by weava on 4/15/15.
 */
public class FailHandler implements EventHandler<WorkerStateEvent>
{
    private String message;
    private final String FAIL_THREAD = "REQUEST FAILED";

    public FailHandler(String message)
    {
        this.message = message;
    }

    @Override
    public void handle(WorkerStateEvent event)
    {
        System.out.println(message);
    }
}
