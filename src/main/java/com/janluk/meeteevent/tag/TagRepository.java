package com.janluk.meeteevent.tag;

import com.janluk.meeteevent.base.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagRepository extends BaseEntityRepository<Tag, UUID> {

    @Query(
            value = "select tag.* from tag left join event_tag on tag.id = event_tag.tag_id and " +
                    "event_tag.event_id = ?1 where event_tag.event_id is null",
            nativeQuery = true
    )
    List<Tag> findAllUnassignedEventTagsByEventId(UUID eventId);
}
