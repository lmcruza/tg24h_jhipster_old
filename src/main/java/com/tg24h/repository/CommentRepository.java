package com.tg24h.repository;

import com.tg24h.domain.Comment;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Comment entity.
 */
@SuppressWarnings("unused")
public interface CommentRepository extends MongoRepository<Comment,String> {

}
