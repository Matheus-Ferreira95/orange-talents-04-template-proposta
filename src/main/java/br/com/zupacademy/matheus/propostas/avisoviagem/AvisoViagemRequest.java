package br.com.zupacademy.matheus.propostas.avisoviagem;

import br.com.zupacademy.matheus.propostas.cartao.Cartao;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank
    private String destino;

    @NotNull
    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate validoAte;

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setTermino(LocalDate validoAte) {
        this.validoAte = validoAte;
    }

    @Override
    public String toString() {
        return "AvisoViagemRequest{" +
                "destino='" + destino + '\'' +
                ", termino=" + validoAte +
                '}';
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public AvisoViagem toModel(String ip, String user_agente, Cartao cartao) {
        return new AvisoViagem(destino, validoAte, ip, user_agente, cartao);
    }
}
