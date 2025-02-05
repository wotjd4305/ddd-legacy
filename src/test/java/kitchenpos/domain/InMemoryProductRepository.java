package kitchenpos.domain;

import java.util.*;

public class InMemoryProductRepository implements ProductRepository {

    private final Map<UUID, Product> products = new HashMap<>();

    @Override
    public List<Product> findAllByIdIn(List<UUID> ids) {
        return products.values().stream()
            .filter(it -> ids.contains(it.getId()))
            .toList();
    }

    @Override
    public Product save(Product product) {
        final var id = UUID.randomUUID();
        product.setId(id);
        products.put(id, product);
        return product;
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }
}
