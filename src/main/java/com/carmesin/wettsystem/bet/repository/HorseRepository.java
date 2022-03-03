package com.carmesin.wettsystem.bet.repository;

import com.carmesin.wettsystem.bet.model.HorseModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HorseRepository extends MongoRepository<HorseModel, String> {

}
