package com.carmesin.wettsystem.bet.repository;

import com.carmesin.wettsystem.auth.model.UserModel;
import com.carmesin.wettsystem.bet.model.HorseModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HorseRepository extends MongoRepository<HorseModel, String> {

    HorseModel findByName (String name);

}
