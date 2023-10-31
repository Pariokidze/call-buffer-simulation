import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Info {
    boolean isVip;
    String phone;
    File file = new File("callers.txt");

    public static void EnterData() {
    }

    void enterData() throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        isVip = scanner.nextBoolean();
        phone = scanner.next();
        scanner.close();
    }

}
