package com.tg24h.repository;

import com.tg24h.domain.Category;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Category entity.
 */
@SuppressWarnings("unused")
public interface CategoryRepository extends MongoRepository<Category,String> {

}
