package br.com.lottery.domain.game.repositories;

import br.com.lottery.domain.game.models.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IGameRepository extends MongoRepository<Game, UUID> {

    Optional<Game> findByTypeAndNumber(String type, Integer number);

}
