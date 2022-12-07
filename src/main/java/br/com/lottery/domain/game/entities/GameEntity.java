package br.com.lottery.domain.game.entities;

import br.com.lottery.domain.game.entities.converter.NumbersField;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "games")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private Integer number;

    @Column(length = 50)
    private String type;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, columnDefinition = "jsonb")
    @Convert(converter = NumbersField.class)
    private Collection<Integer> raffleNumbers;

    @Column(nullable = false, name = "hits_to_win", columnDefinition = "jsonb")
    @Convert(converter = NumbersField.class)
    private Collection<Integer> hitsToWin;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<AwardEntity> awards;
}
