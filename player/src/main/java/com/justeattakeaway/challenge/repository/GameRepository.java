package com.justeattakeaway.challenge.repository;

import com.justeattakeaway.challenge.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, String> {

}
