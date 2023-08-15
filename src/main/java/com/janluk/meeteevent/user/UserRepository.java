package com.janluk.meeteevent.user;

import com.janluk.meeteevent.utils.base.BaseEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends BaseEntityRepository<User, UUID> {

    Optional<User> findUserByLogin(String login);

    Optional<User> findUserByEmail(String email);
}
