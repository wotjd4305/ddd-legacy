package calculator;

public class Positive {

    private final int number;

    public Positive(String text) {
        this.number = Integer.parseInt(text);
        if (isNegative(this.number)) {
            throw new RuntimeException("음수는 입력할 수 없습니다.");
        }
    }

    private static boolean isNegative(int number) {
        return number < 0;
    }

    public int getNumber() {
        return this.number;
    }
}
