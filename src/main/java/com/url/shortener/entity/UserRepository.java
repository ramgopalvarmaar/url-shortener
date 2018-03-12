package com.url.shortener.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByPrincipalId(String principalId);
    User findByApiKey(String apiKey);
    User findByUserIdAndPassword(String userId, String password);
}
