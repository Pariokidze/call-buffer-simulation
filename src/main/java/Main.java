import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    private static Boolean Answered = false;
    private static ScheduledExecutorService scheduler;
    private static Simulation simulation = new Simulation();
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
            scheduler = Executors.newScheduledThreadPool(1);
       {
            System.out.println("Choose an option:");
            System.out.println("1. Start the program");
            System.out.println("2. Exit");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    generateClients();
                    startProgram();
                    break;
                case "2":
                    System.out.println("Exiting the program. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please select 1 or 2.");
            }
        }
    }

    public static void startProgram() throws IOException {
        System.out.println("Choose an option:");
        System.out.println("1. Stop generating clients");
        System.out.println("2. Answer the call in all available queues");
        System.out.println("3. View the queue/queues");
        System.out.println("4. Exit");
        Scanner scanner = new Scanner(System.in);
        while(true) {
           String choice = scanner.nextLine();
            switch (choice) {

                case "1":
                    if (!scheduler.isShutdown())
                    {
                        scheduler.shutdown();
                        System.out.println("Stopped the client generation.");
                    }
                    else
                    {
                        System.out.println("Clients are not being generated.");
                    }
                    break;
                case "2":
                    System.out.println("The call has been answered.");
                    Answered = true;
                    System.out.println();
                    System.out.println("To end the call press 1.");
                    String choice2 = scanner.nextLine();
                    simulation.serve();
                    System.out.println("Choose an option:");
                    System.out.println("1. Stop generating clients");
                    System.out.println("2. Answer the call in all available queues");
                    System.out.println("3. View the queue/queues");
                    System.out.println("4. Exit");
                    Answered = false;
                    System.out.println();
                    break;
                case "3":
                    simulation.printQueue();
                    Answered = true;
                    System.out.println("If you're done viewing the queue/queues press 1.");
                    choice2 = scanner.nextLine();
                    System.out.println("Choose an option:");
                    System.out.println("1. Stop generating clients");
                    System.out.println("2. Answer the call in all available queues");
                    System.out.println("3. View the queue/queues");
                    System.out.println("4. Exit");
                    Answered = false;
                    break;
                case "4":
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please select 1, 2, 3 or 4.");
            }
        }
    }
    public static void  generateClients() {
        Runnable commandTask = () -> {
            if (!Answered)
            try {
                simulation.generate();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        };
        scheduler.scheduleAtFixedRate(commandTask, 0, 5, TimeUnit.SECONDS);
    }
}