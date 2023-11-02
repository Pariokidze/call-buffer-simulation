
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Scanner;

public class Simulation {
    int lastVip = -1;
    String comment;
    Deque<Info> eile = new ArrayDeque<>();

    boolean CheckVip(Info eile) {
        return eile.isVip;
    }

    void generate(Info client) throws FileNotFoundException {
        client.enterData();
        if (CheckVip(client)) {
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
            else {
            eile.addLast(client);
        }
    }




    void serve(Info client) throws IOException {

        System.out.println("Nurodykite komentarus/priezasti del skambucio: ");
        Scanner scanner = new Scanner(System.in);
        comment = scanner.nextLine();
        eile.removeFirst();

    }

    void printQueue() {
        System.out.println("Current Queue:");
        for (Info client : eile) {
            System.out.println(client.toString());
        }
    }
}