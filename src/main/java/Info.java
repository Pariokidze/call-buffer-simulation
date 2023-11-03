import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Info {
    boolean isVip;
    String phone;
    static Scanner scanner;

    public Info() throws FileNotFoundException {
        if (scanner == null){
        File file = new File("callers.txt");
        scanner = new Scanner(file);
    }}

    void enterData() throws FileNotFoundException {
        isVip = scanner.nextBoolean();
        phone = scanner.next();
        scanner.nextLine();
        if (scanner.hasNextLine() == false)
            System.out.println("No more callers");
    }
    public String toString() {
        // Return a meaningful representation of the Info object
        return "Info: " + isVip + " " + phone;
}}
