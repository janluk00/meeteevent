package com.janluk.meeteevent.utils.base;

import com.janluk.meeteevent.utils.exception.ResourceNotFound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.ArrayList;
import java.util.List;

@NoRepositoryBean
public interface BaseEntityRepository<T, ID> extends JpaRepository<T, ID> {

    default T findByIdOrThrow(ID id) {
        return this.findById(id).orElseThrow(() -> new ResourceNotFound("Entity with " + id + " not found!"));
    }

    default List<T> findAllList() {
        List<T> result = this.findAll();

        return new ArrayList<>(result);
    }
}
