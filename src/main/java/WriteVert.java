import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by p14n on 13/08/2014.
 */
public class WriteVert extends Verticle {

    AtomicInteger count = new AtomicInteger(0);

    public void start() {

        getVertx().setPeriodic(5000, new Handler<Long>() {
            @Override
            public void handle(Long event) {
                System.out.println("Sending to writer");
                getVertx().eventBus().send("writehandler", String.valueOf(count.getAndIncrement()));
            }
        });

    }
}
