package br.com.lottery.domain.game.models;

import br.com.lottery.domain.game.dtos.TaskDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "tasks")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Task {

    @Id
    private String id;

    @Field("init_date")
    private LocalDateTime initDate;

    @Field("end_date")
    private LocalDateTime endDate;

    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }

    public TaskDTO toDTO(){
        var dto = new TaskDTO();
        BeanUtils.copyProperties(this,dto);
        return dto;
    }
}
