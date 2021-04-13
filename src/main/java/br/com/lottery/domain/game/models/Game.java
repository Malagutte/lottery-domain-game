package br.com.lottery.domain.game.models;

import br.com.lottery.domain.game.dtos.GameDTO;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

import static org.springframework.beans.BeanUtils.copyProperties;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "games")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Game {
    @Id
    private String id;

    private Integer number;

    private String type;

    private LocalDate date;

    @Field("raffle_numbers")
    private Collection<Integer> raffleNumbers;


    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }

    public GameDTO toDTO(){
        var dto = new GameDTO();
        copyProperties(this,dto);
        return dto;
    }

}
