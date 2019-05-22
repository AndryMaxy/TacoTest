package by.akulich.tacocloud.repository.jdbc;

import by.akulich.tacocloud.domain.Taco;

public interface ITacoRepository {

    Taco save(Taco taco);
}
