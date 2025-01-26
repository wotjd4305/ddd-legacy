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
        return sum(numbersString);
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

    private static int sum(String[] numbers) {
        Positives positives = new Positives(numbers);
        return positives.sum();
    }
}
