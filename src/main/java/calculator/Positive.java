package calculator;

import java.util.Objects;

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

    public Positive plus(Positive other) {
        return new Positive(this.number + other.number);
    }

    private boolean isNegative(int number) {
        return number < 0;
    }

    public int getNumber() {
        return this.number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Positive positive = (Positive) o;
        return number == positive.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
