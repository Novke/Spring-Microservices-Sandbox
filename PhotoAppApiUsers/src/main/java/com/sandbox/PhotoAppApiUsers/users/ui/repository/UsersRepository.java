package com.sandbox.PhotoAppApiUsers.users.ui.repository;

import com.sandbox.PhotoAppApiUsers.users.data.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface UsersRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

    UserEntity findByUserId(String userId);
}
