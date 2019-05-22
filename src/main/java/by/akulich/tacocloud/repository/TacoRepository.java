package by.akulich.tacocloud.repository;

import by.akulich.tacocloud.domain.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
