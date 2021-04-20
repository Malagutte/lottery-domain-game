package br.com.lottery.domain.game.dtos.response;

import br.com.lottery.domain.game.dtos.GameDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class SearchResponseDTO {
    private Collection<GameDTO> elements;
    private Integer page;
}
