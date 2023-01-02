package br.com.lottery.domain.game.entities;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tasks")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TaskEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "init_date")
    private LocalDateTime initDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

}
