package by.akulich.tacocloud.controller;

import by.akulich.tacocloud.domain.Order;
import by.akulich.tacocloud.domain.User;
import by.akulich.tacocloud.props.OrderProps;
import by.akulich.tacocloud.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    //JDBC
    //private IOrderRepository orderRepository;
    private final OrderRepository orderRepository;
    private final OrderProps orderProps;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderProps orderProps) {
        this.orderRepository = orderRepository;
        this.orderProps = orderProps;
    }

    @GetMapping()
    public String userOrders(@AuthenticationPrincipal User user, Model model){
        Pageable pageable = PageRequest.of(0, orderProps.getPageSize());
        List<Order> orders = orderRepository.findByUserOrderByPlacedAtDesc(user, pageable);
        model.addAttribute("orders", orders);
        return "userOrders";
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping()
    public String processOrder(@Valid Order order, Errors errors, SessionStatus status,
                               @AuthenticationPrincipal User user) {

        if (errors.hasErrors()) {
            return "orderForm";
        }

        order.setUser(user);

        Order saved = orderRepository.save(order);
        status.setComplete();

        log.info("Order..." + saved + " saved.");
        return "redirect:/";
    }
}
