package calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

    private static final String DEFAULT_DELIMITER = ",";
    private static final String COLON_DELIMITER = ":";
    private static final Pattern pattern = Pattern.compile("//(.)\n(.*)");

    public static int add(String text) {
        if (isBlank(text)) {
            return 0;
        }

        String[] numbersString = split(text);
        return sum(toIntArrays(numbersString));
    }

    public static String[] split(String text) {
        Matcher m = pattern.matcher(text);

        if (m.find()) {
            String customDelimiter = m.group(1);
            return m.group(2).split(customDelimiter);
        }

        return text.split(String.format("%s|%s", DEFAULT_DELIMITER, COLON_DELIMITER));
    }

    private static boolean isBlank(String text) {
        return (text == null || text.isBlank());
    }

    private static boolean isNegative(int number) {
        return number < 0;
    }

    private static int[] toIntArrays(String[] values) {
        int[] numbers = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            int number = Integer.parseInt(values[i]);
            if (isNegative(number)) {
                throw new RuntimeException("음수는 불가능합니다.");
            }
            numbers[i] = number;
        }
        return numbers;
    }

    private static int sum(int[] numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }
}
