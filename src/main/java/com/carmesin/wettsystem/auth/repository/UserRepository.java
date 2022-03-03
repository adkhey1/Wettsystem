package com.carmesin.wettsystem.auth.repository;

import com.carmesin.wettsystem.auth.model.UserModel;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<UserModel, String> {

    List<UserModel> findByName(String name);

    UserModel findByUuid(String uuid);

    void deleteByName(String name);

}
