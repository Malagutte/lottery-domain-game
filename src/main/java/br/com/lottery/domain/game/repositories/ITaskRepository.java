package br.com.lottery.domain.game.repositories;

import br.com.lottery.domain.game.models.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ITaskRepository extends MongoRepository<Task, UUID> {
}
