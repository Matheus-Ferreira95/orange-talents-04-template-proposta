package br.com.zupacademy.matheus.propostas.compartilhado.handler;

import java.util.Collection;

public class ErroPadronizado {

    private Collection<String> messages;

    public ErroPadronizado(Collection<String> messages) {
        this.messages = messages;
    }

    public Collection<String> getMessages() {
        return messages;
    }
}
