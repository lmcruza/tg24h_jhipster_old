package com.tg24h.repository;

import com.tg24h.domain.Company;
import com.tg24h.domain.User;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Company entity.
 */
@SuppressWarnings("unused")
public interface CompanyRepository extends MongoRepository<Company,String> {

    Optional<Company> findOneByNameIgnoreCase(String name);
    
}
