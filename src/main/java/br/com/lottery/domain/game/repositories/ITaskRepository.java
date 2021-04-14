package br.com.lottery.domain.game.repositories;

import br.com.lottery.domain.game.models.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaskRepository extends MongoRepository<Task, String> {
}
