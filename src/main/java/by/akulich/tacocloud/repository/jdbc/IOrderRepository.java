package by.akulich.tacocloud.repository.jdbc;

import by.akulich.tacocloud.domain.Order;

public interface IOrderRepository {

    Order save(Order order);
}
