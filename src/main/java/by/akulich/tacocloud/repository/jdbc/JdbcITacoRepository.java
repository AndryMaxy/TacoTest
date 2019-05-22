package by.akulich.tacocloud.repository.jdbc;

import by.akulich.tacocloud.domain.Ingredient;
import by.akulich.tacocloud.domain.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcITacoRepository implements ITacoRepository {

    private static final String INSERT = "insert into Taco (name, createdAt) values (?, ?)";
    private static final String INSERT_INGREDIENT = "insert into Taco_Ingredients (taco, ingredient) values (?, ?)";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcITacoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for (Ingredient ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient, tacoId);
        }
        return taco;
    }

    private long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory factory =
                new PreparedStatementCreatorFactory(INSERT, Types.VARCHAR, Types.TIMESTAMP);
        List list = Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime()));
        factory.setGeneratedKeysColumnNames("id");
        PreparedStatementCreator creator = factory.newPreparedStatementCreator(list);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(creator, keyHolder);

        Number number = keyHolder.getKey();
        return number == null ? 0 : number.longValue();
    }

    private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
        jdbcTemplate.update(INSERT_INGREDIENT, tacoId, ingredient.getId());
    }
}
