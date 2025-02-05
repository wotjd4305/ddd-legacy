package kitchenpos.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuRepository {

    List<Menu> findAllByProductId(UUID productId);

    Optional<Menu> findById(UUID id);

    List<Menu> findAll();

    Menu save(Menu menu);

    List<Menu> findAllByIdIn(List<UUID> ids);
}
