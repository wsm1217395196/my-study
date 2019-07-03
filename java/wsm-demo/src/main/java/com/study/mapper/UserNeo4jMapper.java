package com.study.mapper;

import com.study.model.UserNeo4jModel;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNeo4jMapper extends Neo4jRepository<UserNeo4jModel,Long> {

//    @Query("macth(u:user) where u.id = {id} return u")
//    List<UserNeo4jModel> getById(Long id);
}
