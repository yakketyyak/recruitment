package com.recruitment.casttest.domain.user.repository;

import com.recruitment.casttest.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsById(Long id);
}
