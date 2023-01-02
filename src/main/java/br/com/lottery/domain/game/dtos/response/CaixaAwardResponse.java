package br.com.lottery.domain.game.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CaixaAwardResponse {
    private String descricaoFaixa;
    private Integer faixa;
    private Integer numeroDeGanhadores;
    private BigDecimal valorPremio;

}
