package com.janluk.meeteevent.base;

import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity implements Persistable<UUID> {
    @Id
    @Column(name = "id")
    protected UUID id = UUID.randomUUID();

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
