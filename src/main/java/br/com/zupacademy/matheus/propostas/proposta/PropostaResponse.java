package br.com.zupacademy.matheus.propostas.proposta;

import java.math.BigDecimal;

public class PropostaResponse {

    private String nome;
    private String documento;
    private String email;
    private String endereco;
    private BigDecimal salario;
    private StatusProposta status;

    public PropostaResponse(Proposta proposta) {
        nome = proposta.getNome();
        documento = proposta.getDocumento();
        email = proposta.getEmail();
        endereco = proposta.getEndereco();
        salario = proposta.getSalario();
        status = proposta.getStatus();
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public StatusProposta getStatus() {
        return status;
    }
}
