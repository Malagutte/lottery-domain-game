package br.com.lottery.domain.game.repositories;

import br.com.lottery.domain.game.entities.GameEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface IGameRepository extends CrudRepository<GameEntity, String> {

    Optional<GameEntity> findByTypeAndNumber(String type, Integer number);

    Collection<GameEntity> findByTypeAndDateBetween(String type, LocalDate toDate, LocalDate fromDate);

}
