package br.com.lottery.domain.game.entities;

import br.com.lottery.domain.game.dtos.response.CaixaGovResponse;
import br.com.lottery.domain.game.entities.converter.DetailsObject;
import br.com.lottery.domain.game.entities.converter.NumbersField;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "games")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
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

    @Column(nullable = false, name = "details", columnDefinition = "jsonb")
    @Convert(converter = DetailsObject.class)
    private CaixaGovResponse details;
}
