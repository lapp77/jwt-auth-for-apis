package com.manning.simplysend.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.manning.simplysend.dto.OrderDTO;
import com.manning.simplysend.entities.Item;
import com.manning.simplysend.entities.Order;
import com.manning.simplysend.entities.Order.StatusEnum;
import com.manning.simplysend.entities.User;
import com.manning.simplysend.exceptions.InvalidProfileException;
import com.manning.simplysend.exceptions.OrderAlreadyApprovedException;
import com.manning.simplysend.exceptions.OrderAlreadyDeniedException;
import com.manning.simplysend.exceptions.OrderNotFoundException;
import com.manning.simplysend.exceptions.SimplySendException;
import com.manning.simplysend.mappers.OrderMapper;
import com.manning.simplysend.repositories.ItemsRepository;
import com.manning.simplysend.repositories.OrdersRepository;
import com.manning.simplysend.repositories.UserRepository;
import com.manning.simplysend.services.OrdersService;

@Service
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;
    private final ItemsRepository itemsRepository;
    private final UserRepository userRepository;

    public OrdersServiceImpl(OrdersRepository ordersRepository, ItemsRepository itemsRepository,
            UserRepository userRepository) {
        this.ordersRepository = ordersRepository;
        this.itemsRepository = itemsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<OrderDTO> listOrders(String status, Integer page, Integer limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);

        Page<Order> orders = (status == null || status.isBlank()) ? ordersRepository.findAll(pageRequest)
                : ordersRepository.findAllByStatus(StatusEnum.valueOf(status), pageRequest);

        return orders.map(OrderMapper::toDTO);
    }

    @Override
    public void createOrder(OrderDTO orderDTO, String userEmailId) {
        Order order = OrderMapper.fromDTO(orderDTO);
        order.setStatus(StatusEnum.REQUESTED);

        // make sure items are valid
        List<Item> validItems = order.getItems().stream().map(
                item -> itemsRepository.findById(item.getId()).orElseThrow(SimplySendException::new))
                .collect(Collectors.toList());
        order.setItems(validItems);

        User user = userRepository.findByEmail(userEmailId).orElseThrow(InvalidProfileException::new);
        order.setUser(user);

        ordersRepository.save(order);
    }

    @Override
    public boolean approveOrder(Long orderID) {
        try {
            Order order = ordersRepository.findById(orderID).orElseThrow(OrderNotFoundException::new);
            order.approve();
            ordersRepository.save(order);
        } catch (OrderAlreadyApprovedException e) {
            return true;
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    @Override
    public boolean rejectOrder(Long orderID) {
        try {
            Order order = ordersRepository.findById(orderID).orElseThrow(OrderNotFoundException::new);
            order.reject();
            ordersRepository.save(order);
        } catch (OrderAlreadyDeniedException e) {
            return true;
        } catch (Exception e) {
            throw e;
        }
        return true;
    }
}
