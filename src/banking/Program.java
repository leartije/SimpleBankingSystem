package banking;

import banking.entity.Account;
import banking.services.AccountService;

import java.sql.SQLException;
import java.util.Scanner;

import static banking.constants.Text.*;

public class Program {

    private final Scanner scanner;
    private final AccountService accountService;

    public Program() throws SQLException {
        this.scanner = new Scanner(System.in);
        this.accountService = new AccountService();
        accountService.setupIdContinuity();
    }

    public void start() throws SQLException {
        accountService.getSqLite().createTable();
        while (true) {
            System.out.println(MENU);
            String op = scanner.nextLine();
            switch (op) {
                case "1":
                    accountService.generateCard();
                    break;
                case "2":
                    Account login = accountService.login();
                    if (login != null) {
                        System.out.println(SUCCESSFULLY_LOGGED_IN);
                        loginMenu(login);
                    }
                    break;
                case "0":
                    accountService.getSqLite().closeConnection();
                    System.out.println(BYE);
                    System.exit(0);
                default:
                    System.out.println(DEFAULT_MSG_MAIN);
                    break;
            }
        }
    }

    private void loginMenu(Account currentAccount) {
        while (true) {
            System.out.println(LOG_IN_MENU);
            String op = scanner.nextLine();
            switch (op) {
                case "1":
                    accountService.getBalance(currentAccount.getBalance());
                    break;
                case "2":
                    accountService.addIncome(currentAccount);
                    break;
                case "3":
                    accountService.transfer(currentAccount);
                    break;
                case "4":
                    accountService.closeAccount(currentAccount);
                    return;
                case "5":
                    System.out.println(SUCCESSFULLY_LOGGED_OUT);
                    return;
                case "0":
                    System.out.println(BYE);
                    System.exit(0);
                default:
                    System.out.println(DEFAULT_MSG);
                    break;
            }
        }
    }

}
