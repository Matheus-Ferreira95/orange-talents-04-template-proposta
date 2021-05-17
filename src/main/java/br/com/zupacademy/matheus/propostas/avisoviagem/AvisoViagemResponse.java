package br.com.zupacademy.matheus.propostas.avisoviagem;

public class AvisoViagemResponse {

    private ResultadoAvisoViagem resultado;

    public AvisoViagemResponse() {
    }

    public AvisoViagemResponse(ResultadoAvisoViagem resultado) {
        this.resultado = resultado;
    }

    public ResultadoAvisoViagem getResultado() {
        return resultado;
    }
}
