package calculator;

import java.util.Arrays;
import java.util.List;

public class Positives {

    private List<Positive> positiveList;

    public Positives(String[] textArray) {
        positiveList = Arrays.stream(textArray)
            .map(Positive::new)
            .toList();
    }

    public int sum() {
        return positiveList.stream()
            .mapToInt(Positive::sum)
            .sum();
    }
}
