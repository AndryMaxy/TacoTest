package by.akulich.tacocloud;

import by.akulich.tacocloud.domain.Ingredient;
import by.akulich.tacocloud.repository.IngredientRepository;
import by.akulich.tacocloud.repository.jdbc.IIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

  private IngredientRepository ingredientRepository;

  @Autowired
  public IngredientByIdConverter(IngredientRepository ingredientRepository) {
    this.ingredientRepository = ingredientRepository;
  }

  @Override
  public Ingredient convert(String id) {
    return ingredientRepository.findById(id).orElse(null);
  }

}
