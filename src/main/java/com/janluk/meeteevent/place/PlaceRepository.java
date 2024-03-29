package com.janluk.meeteevent.place;

import com.janluk.meeteevent.base.BaseEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlaceRepository extends BaseEntityRepository<Place, UUID> {

}
