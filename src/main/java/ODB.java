import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.server.OServer;
import com.orientechnologies.orient.server.OServerMain;
import com.orientechnologies.orient.server.config.*;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by p14n on 14/08/2014.
 */
public class ODB {

    public final static String URL = "plocal:./databases/test";

    public OServer activate(String name) {
        OServerConfiguration config = createServerConfiguration(name);

        try {
            OServer server = OServerMain.create();
            server.startup(config);
            System.out.println("Startup run");
            server.activate();
            return server;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public OServer activateAndOpen(String name) {
        OServer s = activate(name);

        try {
            ODatabaseDocumentTx db = new ODatabaseDocumentTx(URL);
            if (db.exists()) {
                db.open("admin", "admin");
            } else {
                db.create();
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    private OServerConfiguration createServerConfiguration(String name) {
        OServerConfiguration config = new OServerConfiguration();

        config.handlers = new LinkedList<>();
        OServerHandlerConfiguration oServerHandlerConfiguration = new OServerHandlerConfiguration();
        oServerHandlerConfiguration.clazz = ProgrammaticOHazelcastPlugin.class.getName();
        oServerHandlerConfiguration.parameters = new OServerParameterConfiguration[]{
                new OServerParameterConfiguration("nodeName", name),
                new OServerParameterConfiguration("enabled", "true"),
                new OServerParameterConfiguration("configuration.db.default", "default-distributed-db-config.json"),
                new OServerParameterConfiguration("conflict.resolver.impl", "com.orientechnologies.orient.server.distributed.conflict.ODefaultReplicationConflictResolver"),
                new OServerParameterConfiguration("sharding.strategy.round-robin", "com.orientechnologies.orient.server.hazelcast.sharding.strategy.ORoundRobinPartitioninStrategy"),
        };

        config.handlers.add(oServerHandlerConfiguration);
        /*config.storages = new OServerStorageConfiguration[1];
        config.storages[0] = new OServerStorageConfiguration();
        config.storages[0].name = "plocal:test";
        config.storages[0].path = "./"+name+"/test";*/

        config.network = new OServerNetworkConfiguration();
        config.storages = new OServerStorageConfiguration[0];
        //config.storages[0] = new ODistributedStorage();
        config.network.protocols = new ArrayList<>();
        config.network.listeners = new ArrayList<>();
        config.users = new OServerUserConfiguration[0];
        return config;
    }
   /* public void write(String body) {
        ODatabaseDocumentTx db = new ODatabaseDocumentTx( "plocal:./testy" ) ;
        db. create ( ) ;
        ODocument doc = db. newInstance ( ) ;
        doc. field ( "name" , "doodle" ) ;
        doc. field ( "body" , body ) ;
        doc. save() ;
        db. close ( ) ;
    }
    public void read() {
        ODatabaseDocumentTx db = new ODatabaseDocumentTx( "plocal:./testy" ) ;
        ODocument doc = db. newInstance ( ) ;
        doc. field ( "name" , "doodle" ) ;
        doc. field ( "body" , body ) ;
        doc. save() ;
        db. close ( ) ;
    }*/

}
