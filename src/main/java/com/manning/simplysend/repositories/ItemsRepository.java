package com.manning.simplysend.repositories;

import com.manning.simplysend.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemsRepository extends PagingAndSortingRepository<Item, Long> {
    Page<Item> findAllByType(String type, Pageable pageable);
}
