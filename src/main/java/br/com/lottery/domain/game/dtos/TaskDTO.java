package br.com.lottery.domain.game.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TaskDTO {


    private String id;

    private LocalDateTime initDate;

    private LocalDateTime endDate;

    public long getProcessDurationSeconds() {
        if (initDate == null || endDate == null) {
            return 0;
        }
        Duration duration = Duration.between(initDate, endDate);

        return duration.toSeconds();
    }
}
