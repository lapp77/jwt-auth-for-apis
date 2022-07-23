package com.manning.simplysend.repositories;

import com.manning.simplysend.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends PagingAndSortingRepository<Order, Long> {
    Page<Order> findAllByUser(String user, Pageable pageable);

    Page<Order> findAllByStatus(Order.StatusEnum status, Pageable pageable);

    Page<Order> findAllByUserAndStatus(String user, Order.StatusEnum status, Pageable pageable);
}
