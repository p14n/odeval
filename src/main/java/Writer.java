import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.server.OServer;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

/**
 * Created by p14n on 13/08/2014.
 */
public class Writer extends Verticle {

    public static void main(String[] args) {
        new Writer().start();
    }

    public void start() {

        System.out.print("Writer alive!");
        ODB o = new ODB();
        final OServer s =  o.activateAndOpen("writer");

        try {
            write(s,"A");
            System.out.println("FOUND"+read());
        } catch (Exception e){
        }

        try {
            vertx.eventBus().registerHandler("writehandler",new Handler<Message<String>>() {
                @Override
                public void handle(Message<String> event) {
                    System.out.println("Writer received request");
                    write(s,event.body());
                }
            });

        } catch (Exception e){

        }


    }

    private String read() {

        ODatabaseDocumentTx t = new ODatabaseDocumentTx(ODB.URL).open("admin","admin");
        long count = t.countClusterElements("mydoc");
        t.close();
        return String.valueOf(count);

    }

    private void write(OServer s, String body) {

        ODatabaseDocumentTx t = new ODatabaseDocumentTx(ODB.URL).open("admin","admin");
        ODocument d = t.newInstance("mydoc");
        d.field("body",body);
        d.save();
        t.commit();
        t.close();
    }


}
