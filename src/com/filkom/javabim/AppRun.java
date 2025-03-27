import java.util.Scanner;

public class AppRun {
    public static void main(String[] args) {
        Handler handler = new Handler();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String userInputOption = scanner.nextLine();

            switch (userInputOption) {
                case "1":
                    break;
                default:
                    scanner.close();
                    System.exit(0);
            }
        }

    }
}
