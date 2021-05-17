package br.com.zupacademy.matheus.propostas.carteira;

public class ResultadoCarteira {

    private TipoResultadoCarteira resultado;
    private String id;

    public ResultadoCarteira() {
    }

    public ResultadoCarteira(TipoResultadoCarteira resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
