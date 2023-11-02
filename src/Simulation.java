
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Scanner;

public class Simulation {
    int lastVip = -1;
    int lastVip2 = -1;
    boolean SecondQueue = false;
    boolean ThirdQueue = false;

    int lastVip3 = -1;
    String comment;
    Deque<Info> eile = new ArrayDeque<>();
    Deque<Info> eile2 = new ArrayDeque<>();
    Deque<Info> eile3 = new ArrayDeque<>();

    boolean CheckVip(Info eile) {
        return eile.isVip;
    }


    void generate(Info client) throws FileNotFoundException {
        client.enterData();
        if(eile.size() == 10)
            SecondQueue = true;
        if (CheckVip(client) && eile.size() < 10 && !SecondQueue) { // 1 eile
            if (lastVip == -1) {
                eile.addFirst(client);
                lastVip = 0;
            }
            else {

                Deque<Info> tempDeque = new ArrayDeque<>();
                for (int i = 0; i <= lastVip ; i++) {
                    tempDeque.addFirst(eile.pollFirst());
                }
                tempDeque.addFirst(client);
                int size = tempDeque.size();
                for (int i = 0; i < size; i++)
                {
                    eile.addFirst(tempDeque.pollFirst());
                }
                lastVip++;
        } }
            else if (eile.size() < 10 && !SecondQueue) // 1 eile
            {
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
           else if (SecondQueue){

                if (CheckVip(client) && eile2.size() < 10) {
                    if (lastVip2 == -1) {
                        eile2.addFirst(client);
                        lastVip2 = 0;
                    }
                    else {

                        Deque<Info> tempDeque = new ArrayDeque<>();
                        for (int i = 0; i <= lastVip2 ; i++) {
                            tempDeque.addFirst(eile2.pollFirst());
                        }
                        tempDeque.addFirst(client);
                        int size = tempDeque.size();
                        for (int i = 0; i < size; i++)
                        {
                            eile2.addFirst(tempDeque.pollFirst());
                        }
                        lastVip2++;
                    } }
                else
                {

                    eile2.addLast(client);
                }
            }}
    void serve(Info client) throws IOException {
       if (eile.size()>0)
       {
           System.out.println("1 eileje Nurodykite komentarus/priezasti del skambucio: ");
           Scanner scanner = new Scanner(System.in);
           comment = scanner.nextLine();
           eile.removeFirst();
       }
       if (eile2.size()>0)
       {
           System.out.println("2 eileje Nurodykite komentarus/priezasti del skambucio: ");
           Scanner scanner = new Scanner(System.in);
           comment = scanner.nextLine();
           eile2.removeFirst();
       }

       if (eile2.size()== 0)
           SecondQueue = false;
       }

    void printQueue() {
        System.out.println("Current Queue:");
        for (Info client : eile) {
            System.out.println(client.toString());
        }
        System.out.println("Second Queue:");
        for (Info client : eile2) {
            System.out.println(client.toString());
        }
    }
}