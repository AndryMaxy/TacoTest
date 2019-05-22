package by.akulich.tacocloud.repository.jdbc;

import by.akulich.tacocloud.domain.Order;
import by.akulich.tacocloud.domain.Taco;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcIOrderRepository implements IOrderRepository {

    private SimpleJdbcInsert orderInsert;
    private SimpleJdbcInsert orderTacoInsert;
    private ObjectMapper objectMapper;

    @Autowired
    public JdbcIOrderRepository(JdbcTemplate jdbcTemplate) {
        this.orderInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");
        this.orderTacoInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Taco_Order_Tacos");
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        for (Taco taco : order.getTacos()) {
            saveTacoToOrder(taco, orderId);
        }
        return order;
    }
    //object mapper needs for creating map which contains
    //key - variable name and value - value of this variable
    //puts placeAt because converter converts Date to long
    private long saveOrderDetails(Order order) {
        @SuppressWarnings("unchecked")
        Map<String, Object> values = objectMapper.convertValue(order, Map.class);
        values.put("placedAt", order.getPlacedAt());

        return orderInsert.executeAndReturnKey(values).longValue();
    }

    private void saveTacoToOrder(Taco taco, long orderId) {
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
        orderTacoInsert.execute(values);
    }
}
