package com.space.model.coppieLingue;

import java.io.Serializable;

public class Lingua implements Serializable {

    private String codice;
    private String descrizione;

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public String toString() {
        return "Lingua{" +
                "codice='" + codice + '\'' +
                ", descrizione='" + descrizione + '\'' +
                '}';
    }
}
