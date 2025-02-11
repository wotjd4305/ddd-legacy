package kitchenpos.application;

import kitchenpos.domain.OrderRepository;
import kitchenpos.domain.OrderStatus;
import kitchenpos.domain.OrderTable;
import kitchenpos.domain.OrderTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class OrderTableServiceTest {

    private OrderTableRepository orderTableRepository;
    private OrderRepository orderRepository;
    private OrderTableService orderTableService;

    @BeforeEach
    void setUp() {
        orderTableRepository = mock(OrderTableRepository.class);
        orderRepository = mock(OrderRepository.class);
        orderTableService = new OrderTableService(orderTableRepository, orderRepository);
    }

    @Nested
    @DisplayName("가게 테이블 생성할 수 있다.")
    class CreateOrderTable {

        @Test
        @DisplayName("가게 테이블을 정상 생성")
        void create() {
            // given
            OrderTable request = new OrderTable();
            request.setName("1번 테이블");

            given(orderTableRepository.save(any(OrderTable.class)))
                .willAnswer(invocation -> {
                    OrderTable saved = invocation.getArgument(0);
                    saved.setId(UUID.randomUUID());
                    return saved;
                });

            // when
            OrderTable created = orderTableService.create(request);

            // then
            assertThat(created.getName()).isEqualTo("1번 테이블");
            assertThat(created.getNumberOfGuests()).isZero();
            assertThat(created.isOccupied()).isFalse();
            verify(orderTableRepository).save(any(OrderTable.class));
        }

        @DisplayName("가게 테이블명은 비어있을 수 없다.")
        @ParameterizedTest(name = "가게 테이블명 : `{0}`")
        @NullAndEmptySource
        void create_fail_no_name(final String name) {
            // given
            OrderTable request = new OrderTable();
            request.setName(name);

            // when & then
            assertThatThrownBy(() -> orderTableService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
