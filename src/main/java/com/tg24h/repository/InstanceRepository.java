package com.tg24h.repository;

import com.tg24h.domain.Instance;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Instance entity.
 */
@SuppressWarnings("unused")
public interface InstanceRepository extends MongoRepository<Instance,String> {

}
