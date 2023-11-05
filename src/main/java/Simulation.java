import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Simulation {
    int lastVip = -1;
    int lastVip2 = -1;
    static boolean SecondQueue = false;
    boolean ThirdQueue = false;
    boolean ThirdQueueisFull = false;
    boolean FirstQueueisFull = false;

    int x=0;

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
        if(eile2.size() == 10)
            ThirdQueue = true;
        if (eile.size() == 10)
            FirstQueueisFull = true;
        if (eile.size() < 10)
            FirstQueueisFull = false;
        if (eile3.size() == 10)
            ThirdQueueisFull = true;
        if (eile3.size() < 10)
            ThirdQueueisFull = false;
        if (eile.size() < 10 && !SecondQueue || ThirdQueueisFull && eile.size() < 10) // 2 eile
        {
            if (CheckVip(client) && eile.size() < 10) {
                System.out.println();
                System.out.println("New VIP client added to the first queue: " + client);
                eile.addFirst(client);
                lastVip++;
            } else {
                System.out.println();
                System.out.println("New standard client added to the first queue: " + client);
                eile.addLast(client);
            }
        }
        else if (eile.size() == 10 && lastVip > 0 && !ThirdQueueisFull) {  // 2 eile
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
                eile2.addLast(eile.pollLast());
        }
        else if (eile.size() == 10 && lastVip<0 && !ThirdQueueisFull)
        {
            eile2.addLast(eile.pollLast());
        }
        if ((SecondQueue && eile2.size()<10 && !ThirdQueue) || ThirdQueueisFull && eile2.size()<10 && FirstQueueisFull) // 2 eile
        {
            if (CheckVip(client) && eile2.size() < 10) {
                System.out.println();
                System.out.println("New VIP client added to the second queue: " + client);
                eile2.addFirst(client);
                lastVip2++;
            } else {
                System.out.println();
                System.out.println("New standard client added to the second queue: " + client);
                eile2.addLast(client);
            }
        }
             if((eile2.size() == 10 & lastVip>0 && lastVip2>0 && ThirdQueue) && eile3.size()<10)
        {
            Deque<Info> tempDeque = new ArrayDeque<>();
            for (int i = 0; i <= lastVip ; i++) {
                tempDeque.addFirst(eile.pollFirst());
            }
            int size = tempDeque.size();
            eile3.addFirst(tempDeque.pollFirst());
            lastVip--;
            size--;
            for (int i = 0; i < size; i++)
            {
                eile.addFirst(tempDeque.pollFirst());
            }
                eile3.addLast(eile.pollLast());
            tempDeque = new ArrayDeque<>();
            for (int i = 0; i <= lastVip2 ; i++) {
                tempDeque.addFirst(eile2.pollFirst());
            }
            size = tempDeque.size();
            eile3.addFirst(tempDeque.pollFirst());
            lastVip2--;
            size--;
            for (int i = 0; i < size; i++)
            {
                eile2.addFirst(tempDeque.pollFirst());
            }
                eile3.addLast(eile2.pollLast());
        }
             else if (eile2.size() == 10 && lastVip2<=0 && lastVip>0 && ThirdQueue)
             {
                 {
                        Deque<Info> tempDeque = new ArrayDeque<>();
                        for (int i = 0; i <= lastVip ; i++) {
                            tempDeque.addFirst(eile.pollFirst());
                        }
                        int size = tempDeque.size();
                        eile3.addFirst(tempDeque.pollFirst());
                        lastVip--;
                        size--;
                        for (int i = 0; i < size; i++)
                        {
                            eile.addFirst(tempDeque.pollFirst());
                        }
                            eile3.addLast(eile.pollLast());
                 }
                 eile3.addLast(eile2.pollLast());
             }
             else if (eile2.size() == 10 && lastVip<=0 && lastVip2>0 && ThirdQueue)
             {
                 {
                     Deque<Info> tempDeque = new ArrayDeque<>();
                     for (int i = 0; i <= lastVip2; i++) {
                         tempDeque.addFirst(eile2.pollFirst());
                     }
                     int size = tempDeque.size();
                     eile3.addFirst(tempDeque.pollFirst());
                     lastVip2--;
                     size--;
                     for (int i = 0; i < size; i++)
                     {
                         eile2.addFirst(tempDeque.pollFirst());
                     }
                         eile3.addLast(eile2.pollLast());
                 }
                 eile3.addLast(eile.pollLast());
             }
        if ((ThirdQueue && eile3.size()<10)) // 2 eile

            if (CheckVip(client) && eile3.size() < 10)
            {
                System.out.println();
                System.out.println("New VIP client added to the third queue: " + client);
                eile3.addFirst(client);
            }
            else
            {
                System.out.println();
                System.out.println("New standard client added to the third queue: " + client);
                eile3.addLast(client);
            }
            if (FirstQueueisFull && eile2.size() == 10 && ThirdQueueisFull)
                System.out.println("All queues are full. Please wait for a client to be served.");
            else
                x++;
    }
    void serve() {

        if (!eile.isEmpty()) {
            System.out.println("Servicing the first queue");
            Info callerInfo = eile.peekFirst();
            String comment = getCommentFromUser(callerInfo);
            if (callerInfo != null) {
                callerInfo.setComment(comment);
            }
            servedCallers.add(callerInfo);
            eile.removeFirst();

        }

        if (!eile2.isEmpty()) {
            System.out.println("Servicing the second Queue");
            Info callerInfo = eile2.peekFirst();
            String comment = getCommentFromUser(callerInfo);
            if (callerInfo != null) {
                callerInfo.setComment(comment);
            }
            servedCallers.add(callerInfo);
            eile2.removeFirst();

        }
        if (!eile3.isEmpty()) {
            System.out.println("Servicing the third Queue");
            Info callerInfo = eile3.peekFirst();
            String comment = getCommentFromUser(callerInfo);
            if (callerInfo != null) {
                callerInfo.setComment(comment);
            }
            servedCallers.add(callerInfo);
            eile3.removeFirst();

        }
        if (eile2.isEmpty())
            SecondQueue = false;
        if (eile3.isEmpty())
            ThirdQueue = false;

        try {
            objectMapper.writeValue(jsonFile, servedCallers);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        if (!eile3.isEmpty()){
            System.out.println("Third Queue:");
            for (Info client : eile3) {
                System.out.println(client.toString());
        }
    }
}
}