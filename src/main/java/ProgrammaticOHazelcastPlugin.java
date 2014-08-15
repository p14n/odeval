import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.orientechnologies.orient.server.hazelcast.OHazelcastPlugin;

import java.io.FileNotFoundException;

/**
* Created by p14n on 14/08/2014.
*/
public class ProgrammaticOHazelcastPlugin extends OHazelcastPlugin {

    //For 1.7rc2 and above
    @Override
    protected HazelcastInstance configureHazelcast() throws FileNotFoundException {

        HazelcastInstance instance;
        try {
            instance = Hazelcast.getAllHazelcastInstances().iterator().next();
        } catch (Exception e){
            instance = Hazelcast.newHazelcastInstance();
        }
        System.out.println("\nInstance " + instance.getName());
        return instance;
    }

}
