package com.janluk.meeteevent.utils.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseEntityRepository<T, ID> extends JpaRepository<T, ID> {

    default Optional<T> fetchById(ID id) {
        return this.findById(id);
    }

    default List<T> findAllList() {
        List<T> result = this.findAll();

        return new ArrayList<>(result);
    }
}
