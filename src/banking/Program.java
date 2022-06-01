package banking;

import banking.entity.Account;
import banking.services.Methods;

import java.util.Scanner;

import static banking.constants.Text.*;

public class Program {

    private final Scanner scanner;
    private final Methods methods;


    public Program() {
        this.scanner = new Scanner(System.in);
        this.methods = new Methods();
    }

    public void start() {
        while (true) {
            System.out.println(MENU);
            String op = scanner.nextLine();
            switch (op) {
                case "1":
                    methods.generateCard();
                    break;
                case "2":
                    Account login = methods.login();
                    if (login != null) {
                        System.out.println(SUCCESSFULLY_LOGGED_IN);
                        loginMenu(login);
                    }
                    break;
                case "0":
                    System.out.println(BYE);
                    System.exit(0);
                default:
                    System.out.println("Try 1, 2 or 0");
                    break;
            }
        }

    }

    private void loginMenu(Account login) {
        while (true) {
            System.out.println(LOG_IN_MENU);
            String op = scanner.nextLine();
            switch (op) {
                case "1":
                    System.out.println("Balance: " + login.getAmount());
                    break;
                case "2":
                    System.out.println(SUCCESSFULLY_LOGGED_OUT);
                    return;
                case "0":
                    System.out.println(BYE);
                    System.exit(0);
                default:
                    System.out.println("Try 1, 2 or 0");
            }
        }
    }

}
