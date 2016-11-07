import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Александра on 07.11.2016.
 */
public class SimpleClass {
    public static void main(String[] args) throws IOException, InterruptedException {
        for(int i=0;i<49;i++){
            Quarantine quarantine= new Quarantine();
            quarantine.oneLoop();
        }
        Quarantine quarantine= new Quarantine();
        quarantine.oneLoop();
        System.out.println("Original size: "+quarantine.originalLength);
        System.out.println("Statistics of results: ");
        Set<Integer> keys = Quarantine.loopStatistics.keySet();
        for(int key:keys){
            System.out.println("size: "+key+"; count: "+Quarantine.loopStatistics.get(key));

        }
    }
}
