package banking.services;

import java.util.Random;

public class Generate {

    private final int NUM_TO_GENERATE = 9;
    private final int PIN_NUM = 4;

    private final Random random = new Random();
    private StringBuilder builder;

    public String generateCardNum() {
        builder = new StringBuilder("400000");
        for (int i = 0; i < NUM_TO_GENERATE; i++) {
            int generate = random.nextInt(10);
            builder.append(generate);
        }
        builder.append(accountIdentifier(builder.toString()));
        return builder.toString();
    }

    public String generatePinNum() {
        builder = new StringBuilder();
        for (int i = 0; i < PIN_NUM; i++) {
            int generate = random.nextInt(10);
            builder.append(generate);
        }

        return builder.toString();
    }

    private int accountIdentifier(String cardNum) {
        int sum = 0;
        for (int i = 0; i < cardNum.length(); i++) {
            int parseInt = Integer.parseInt(String.valueOf(cardNum.charAt(i)));
            if (i % 2 == 0) {
                sum += parseInt * 2 > 9 ? (parseInt * 2) - 9 : parseInt * 2;
                continue;
            }
            sum += parseInt;
        }
        return sum % 10 == 0 ? 0 : 10 - (sum % 10);
    }

}
