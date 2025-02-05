package kitchenpos.domain;

import java.util.*;

public class InMemoryMenuRepository implements MenuRepository {

    private Map<UUID, Menu> menus = new HashMap<>();

    @Override
    public List<Menu> findAllByProductId(UUID productId) {
        return menus.values().stream()
            .filter(menu -> menu.getMenuProducts().stream().anyMatch(product -> product.getProduct().getId().equals(productId)))
            .toList();
    }

    @Override
    public Optional<Menu> findById(UUID id) {
        return Optional.ofNullable(menus.get(id));
    }

    @Override
    public List<Menu> findAll() {
        return new ArrayList<>(menus.values());
    }

    @Override
    public Menu save(Menu menu) {
        final var id = UUID.randomUUID();
        menu.setId(id);
        menus.put(id, menu);
        return menu;
    }

    @Override
    public List<Menu> findAllByIdIn(List<UUID> ids) {
        return menus.values().stream()
            .filter(it -> ids.contains(it))
            .toList();
    }
}
