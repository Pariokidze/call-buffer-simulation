import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Simulation {
    int lastVip = -1;
    int lastVip2 = -1;
    static boolean SecondQueue = false;
    boolean ThirdQueue = false;
    int x=0;
    int lastVip3 = -1;
    String comment;
    List<Info> servedCallers = new ArrayList<>();
    Deque<Info> eile = new ArrayDeque<>();
    Deque<Info> eile2 = new ArrayDeque<>();
    Deque<Info> eile3 = new ArrayDeque<>();
    ObjectMapper objectMapper = new ObjectMapper();
    File jsonFile = new File("servedCallers.json");
    boolean CheckVip(Info eile) {
        return eile.isVip;
    }


    void generate() throws IOException {
       Info client = Info.readData().get(x);
        if(eile.size() == 10)
            SecondQueue = true;
        if ((CheckVip(client) && eile.size() < 10 && !SecondQueue) || (CheckVip(client)) && eile.isEmpty())  // 1 eile
        { // {
                System.out.println();
                System.out.println("New VIP client added to the first queue: " + client.toString());
                eile.addFirst(client);
                lastVip++;
        }
        else if (eile.size() < 10 && !SecondQueue) // 1 eile
        {
            System.out.println();
            System.out.println("New standard client added to the first queue: " + client.toString());
            eile.addLast(client);
        }
        else if (eile.size() == 10 & lastVip>0 && SecondQueue) {  // 2 eile
            Deque<Info> tempDeque = new ArrayDeque<>();
            for (int i = 0; i <= lastVip ; i++) {
                tempDeque.addFirst(eile.pollFirst());
            }
            int size = tempDeque.size();
            eile2.addFirst(tempDeque.pollFirst());
            lastVip--;
            size--;
            lastVip2 = 0;
            for (int i = 0; i < size; i++)
            {
                eile.addFirst(tempDeque.pollFirst());
            }
            if(!eile.peekLast().isVip)
                eile2.addLast(eile.pollLast());
        }
        if (SecondQueue && eile2.size()<10 && !ThirdQueue) // 2 eile

            if (CheckVip(client) && eile2.size() < 10)
            {
                System.out.println();
                System.out.println("New VIP client added to the second queue: " + client.toString());
                    eile2.addFirst(client);
                    lastVip2++;
            }
            else
            {
                System.out.println();
                System.out.println("New standard client added to the second queue: " + client.toString());
                eile2.addLast(client);
            }
    x++;
    }
    void serve() throws IOException {

        if (!eile.isEmpty()) {
            System.out.println("Servicing the first queue");
            Info callerInfo = eile.peekFirst();
            String comment = getCommentFromUser(callerInfo);
            callerInfo.setComment(comment);
            servedCallers.add(callerInfo);
            eile.removeFirst();

        }

        if (!eile2.isEmpty()) {
            System.out.println("Servicing the second Queue");
            Info callerInfo = eile2.peekFirst();
            String comment = getCommentFromUser(callerInfo);
            callerInfo.setComment(comment);
            servedCallers.add(callerInfo);
            eile2.removeFirst();

        }

        if (eile2.isEmpty()) {
            SecondQueue = false;
        }
        try {
            objectMapper.writeValue(jsonFile, servedCallers);
        } catch (IOException e) {
            e.printStackTrace();
        }
   }

    private String getCommentFromUser(Info callerInfo)
    {
        Scanner scanner = new Scanner(System.in);
        if (CheckVip(callerInfo)) {
            System.out.println("VIP: Leave reasons/comments about the phone call : ");
        } else {
            System.out.println("Leave reasons/comments about the phone call : ");
        }
        return scanner.nextLine();
    }

    void printQueue() {
        System.out.println();
        if (!eile.isEmpty()){
        System.out.println("First Queue:");
        for (Info client : eile) {
            System.out.println(client.toString());
        }}
        if (!eile2.isEmpty()){
        System.out.println("Second Queue:");
        for (Info client : eile2) {
            System.out.println(client.toString());
        }}
    }
}