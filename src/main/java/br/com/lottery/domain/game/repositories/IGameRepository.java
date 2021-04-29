package br.com.lottery.domain.game.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.lottery.domain.game.models.Game;

@Repository
public interface IGameRepository extends MongoRepository<Game, String> {

    Optional<Game> findByTypeAndNumber(String type, Integer number);

}
