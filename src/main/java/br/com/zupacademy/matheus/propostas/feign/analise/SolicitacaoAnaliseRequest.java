package br.com.zupacademy.matheus.propostas.feign.analise;

import br.com.zupacademy.matheus.propostas.proposta.Proposta;

public class SolicitacaoAnaliseRequest {

    private String documento;
    private String nome;
    private String idProposta;

    public SolicitacaoAnaliseRequest(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId().toString();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
