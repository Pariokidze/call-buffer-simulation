import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Simulation simulation = new Simulation();
        for (int i=0; i<14; i++)
        {
         simulation.generate(new Info());
        }

        simulation.printQueue();
    }
}