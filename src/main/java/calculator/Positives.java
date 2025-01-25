package calculator;

import java.util.ArrayList;
import java.util.List;

public class Positives {

    private List<Positive> positiveList;

    public Positives(String[] textArray) {
        positiveList = new ArrayList<>();

        for(String text : textArray){
            positiveList.add(new Positive(text));
        }
    }

    public int sum() {
        return positiveList.stream()
            .mapToInt(Positive::getNumber)
            .sum();
    }
}
