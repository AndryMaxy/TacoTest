package by.akulich.tacocloud.repository.jdbc;

import by.akulich.tacocloud.domain.Ingredient;

public interface IIngredientRepository {

    Iterable<Ingredient> findAll();

    Ingredient findById(String id);

    Ingredient save(Ingredient ingredient);
}
