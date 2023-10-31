
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Iterator;
public class Simulation {
    int lastVip=-1;
    Deque<Info> eile = new ArrayDeque<>();
    boolean CheckVip(Info eile){
        return eile.isVip;
    }
    void generate (Info client, int lastVip){
        client.EnterData();
    if (CheckVip(client) && lastVip==-1){
        eile.addFirst(client);
        lastVip=0;
    }
    else if (CheckVip(client)) {
        Iterator<Info> iterator = eile.iterator();
        iterator = eile.iterator();
        for (int i = 0; i < lastVip; i++) {
            iterator.next();
        }
        iterator.add(client);
        lastVip++;
    }
    else {
        eile.addLast(client); // Add standard callers to the end of the line
    }
    }
    void serve(Info client, int lastVip){
        if (CheckVip(client) && lastVip==0){
            eile.removeFirst();
            lastVip=-1;
        }
        else if (CheckVip(client)){
            eile.remove(lastVip);
            lastVip--;
        }
        else{
            eile.removeFirst();
        }
    }
}
