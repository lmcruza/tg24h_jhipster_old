package com.tg24h.repository;

import com.tg24h.domain.Resolution;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Resolution entity.
 */
@SuppressWarnings("unused")
public interface ResolutionRepository extends MongoRepository<Resolution,String> {

}
