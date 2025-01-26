package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PositivesTest {

    Positives positives;

    @BeforeEach
    public void init() {
        positives = new Positives(new String[]{"1", "2", "3"});
    }

    @Test
    void sum() {
        assertThat(positives.sum()).isEqualTo(6);
    }
}
