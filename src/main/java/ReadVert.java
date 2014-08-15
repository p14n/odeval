import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

/**
 * Created by p14n on 13/08/2014.
 */
public class ReadVert extends Verticle {

    public void start() {

        getVertx().setPeriodic(5000, new Handler<Long>() {
            @Override
            public void handle(Long event) {
                System.out.println("Sending to reader");
                getVertx().eventBus().send("readhandler", "hi", new Handler<Message<String>>() {
                    @Override
                    public void handle(Message<String> event) {

                        System.out.println("Read handler got "+event.body());
                    }
                });
            }
        });


    }
}
