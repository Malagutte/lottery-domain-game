package br.com.lottery.domain.game.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
public class CaixaGovResponse {
    private Boolean acumulado;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "UTC")
    private LocalDate dataApuracao;
    private String dataProximoConcurso;
    Collection<String> dezenasSorteadasOrdemSorteio;
    private Boolean exibirDetalhamentoPorCidade;
    private String id;
    private Integer indicadorConcursoEspecial;
    Collection<String> listaDezenas;
    private String listaDezenasSegundoSorteio;
    Collection<String> listaMunicipioUFGanhadores;
    ArrayList<CaixaAwardResponse> listaRateioPremio;
    private String listaResultadoEquipeEsportiva;
    private String localSorteio;
    private String nomeMunicipioUFSorteio;
    private String nomeTimeCoracaoMesSorte;
    private Integer numero;
    private Integer numeroConcursoAnterior;
    private Integer numeroConcursoFinal_0_5;
    private Integer numeroConcursoProximo;
    private Integer numeroJogo;
    private String observacao;
    private String premiacaoContingencia;
    private String tipoJogo;
    private Integer tipoPublicacao;
    private Boolean ultimoConcurso;
    private BigDecimal valorArrecadado;
    private BigDecimal valorAcumuladoConcurso_0_5;
    private BigDecimal valorAcumuladoConcursoEspecial;
    private BigDecimal valorAcumuladoProximoConcurso;
    private BigDecimal valorEstimadoProximoConcurso;
    private BigDecimal valorSaldoReservaGarantidora;
    private BigDecimal valorTotalPremioFaixaUm;
}
