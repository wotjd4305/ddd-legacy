package kitchenpos.application;

import jakarta.validation.constraints.NotNull;
import kitchenpos.domain.*;
import kitchenpos.infra.FakeProfanityClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    private ProductRepository productRepository = new InMemoryProductRepository();

    private MenuRepository menuRepository = new InMemoryMenuRepository();

    private FakeProfanityClient profanityClient = new FakeProfanityClient();

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, menuRepository, profanityClient);
    }

    @DisplayName("상품의 가격은 0원 미만이면 예외가 발생한다")
    @ValueSource(longs = {-1000, -1})
    @ParameterizedTest
    void create_price(final Long price) {
        // given
        final var request = createdProductRequest(price);

        // when
        // then
        assertThatThrownBy(() -> productService.create(request))
            .isInstanceOf(ProductPriceException.class);
    }

    @DisplayName("상품의 이름에 비속어가 포함되면 예외가 발생한다")
    @ValueSource(strings = {"비속어", "욕설"})
    @ParameterizedTest
    void create_name(final String name) {
        // given
        final var request = createdProductRequest(name);
        profanityClient.setProfanities(List.of("비속어", "욕설"));

        // when
        // then
        assertThatThrownBy(() -> productService.create(request))
            .isInstanceOf(ProductNameException.class);
    }

    @Test
    void create() {
        // given
        final var request = createdProductRequest("후라이드");

        // when
        final var response = productService.create(request);

        // then
        assertThat(response.getId()).isNotNull();
    }

    @DisplayName("상품의 가격이 메뉴의 가격보다 큰 경우 메뉴는 숨겨진다.")
    @Test
    void changePrice() {
        // given
        final var product = createdProduct(UUID.randomUUID(), 20_000L);
        productRepository.save(product);

        final var menu = createMenu(true, product);
        menuRepository.save(menu);

        final var request = createdProductRequest(10_000L);

        // when
        final var response = productService.changePrice(product.getId(), request);

        // then
        assertThat(response.getPrice()).isEqualTo(BigDecimal.valueOf(10_000L));
        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("상품의 가격이 메뉴의 가격보다 큰 경우 메뉴는 숨겨지지 않는다.")
    @Test
    void changePrice2() {
        // given
        final var product = createdProduct(UUID.randomUUID(), 20_000L);
        productRepository.save(product);

        final var menu = createMenu(true, product);
        menuRepository.save(menu);

        final var request = createdProductRequest(30_000L);

        // when
        final var response = productService.changePrice(product.getId(), request);

        // then
        assertThat(response.getPrice()).isEqualTo(BigDecimal.valueOf(30_000L));
        assertThat(menu.isDisplayed()).isTrue();
    }

    private static Menu createMenu(boolean displayed, Product product) {
        final var menu = new Menu();
        menu.setId(UUID.randomUUID());
        menu.setName("후라이드");
        menu.setPrice(BigDecimal.valueOf(20_000L));
        menu.setDisplayed(displayed);

        final var menuProduct = new MenuProduct();
        menuProduct.setQuantity(1L);
        menuProduct.setProduct(product);
        final var menuProducts = List.of(menuProduct);
        menu.setMenuProducts(menuProducts);
        return menu;
    }

    @NotNull
    private Product createdProduct(UUID id, Long price) {
        final var product = new Product();
        product.setId(UUID.randomUUID());
        product.setPrice(BigDecimal.valueOf(20_000L));
        product.setName("후라이드");
        return product;
    }

    @NotNull
    private static Product createdProduct() {
        final var product = new Product();
        product.setId(UUID.randomUUID());
        product.setPrice(BigDecimal.valueOf(20_000L));
        product.setName("후라이드");
        return product;
    }

    @NotNull
    private static Product createdProductRequest(long price) {
        return createdProductRequest("후라이드", price);
    }

    @NotNull
    private static Product createdProductRequest(String name) {
        return createdProductRequest(name, 20_000L);
    }

    private static Product createdProductRequest(String name, long price) {
        final var product = new Product();
        product.setPrice(BigDecimal.valueOf(price));
        product.setName(name);
        return product;
    }
}
