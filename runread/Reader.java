import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

/**
 * Created by p14n on 13/08/2014.
 */
public class Reader extends Verticle {

    public static void main(String[] args) {
        new Reader().start();
    }

    public void start() {

        System.out.print("Reader alive!");
        ODB o = new ODB();
        o.activate("reader");
        System.out.println("FOUND"+read());


        vertx.eventBus().registerHandler("readhandler",new Handler<Message>() {
            @Override
            public void handle(Message event) {
                System.out.println("Reader received request");
                event.reply("Count "+read());
            }
        });


    }

    private String read() {

        ODatabaseDocumentTx t = new ODatabaseDocumentTx(ODB.URL).open("admin","admin");
        long count = t.countClusterElements("mydoc");
        t.close();
        return String.valueOf(count);

    }

}
