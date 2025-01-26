package calculator;

public class Positive {

    private final int number;

    public Positive(int number) {
        if (isNegative(number)) {
            throw new RuntimeException("음수는 입력할 수 없습니다.");
        }
        this.number = number;
    }

    public Positive(String text) {
        this(Integer.parseInt(text));
    }

    public int sum() {
        return this.number;
    }

    private boolean isNegative(int number) {
        return number < 0;
    }
}
