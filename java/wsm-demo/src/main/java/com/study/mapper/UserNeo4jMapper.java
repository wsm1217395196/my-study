package com.study.mapper;

import com.study.model.UserNeo4jModel;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNeo4jMapper extends Neo4jRepository<UserNeo4jModel, Long> {

    @Query("match (u:user) where u.name = {name} return u")
    UserNeo4jModel getByName(@Param("name") String name);
}
