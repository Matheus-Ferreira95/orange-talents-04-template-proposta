package br.com.zupacademy.matheus.propostas.compartilhado.handler;

public class MessageError {

    private String campo;
    private String mensagem;

    public MessageError(String campo, String mensagem) {
        this.campo = campo;
        this.mensagem = mensagem;
    }

    public String getCampo() {
        return campo;
    }

    public String getMensagem() {
        return mensagem;
    }
}
