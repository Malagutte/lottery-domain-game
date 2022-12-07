package br.com.lottery.domain.game.repositories;

import br.com.lottery.domain.game.entities.TaskEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaskRepository extends CrudRepository<TaskEntity, String> {
}
