package com.janluk.meeteevent.user;

import com.janluk.meeteevent.utils.base.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends BaseEntityRepository<User, UUID> {

    Optional<User> findUserByLogin(String login);

    Optional<User> findUserByEmail(String email);

    @Query(
            value = "select users.* from meeteevent_user join user_event on meeteevent_user.id = user_event.user_id " +
                    "where user_event.event_id = ?1",
            nativeQuery = true
    )
    List<User> findAllUsersByEventId(UUID eventId);
}
