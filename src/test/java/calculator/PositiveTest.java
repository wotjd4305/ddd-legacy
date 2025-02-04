package calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class PositiveTest {

    @DisplayName(value = "문자열로 객체 생성")
    @Test
    void StringConstructorText() {
        assertThat(new Positive(1)).isEqualTo(new Positive("1"));
    }

    @DisplayName(value = "int로 객체 생성")
    @Test
    void intConstructorText() {
        assertThat(new Positive(1)).isEqualTo(new Positive(1));
    }

    @DisplayName(value = "sum 메소드 호출 시, int 반환 확인")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void sum(final int number) {
        Positive positive = new Positive(number);

        assertThat(positive.getNumber()).isEqualTo(number);
    }
}
