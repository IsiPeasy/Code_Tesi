package com.space.model.corrispondenzecorsiperabbreviazioni;

import java.io.Serializable;

public class Corrispondenza implements Serializable {

    private Integer annoField;
    private String codicePratica;
    private String codiceImmatricolazione;


    public Integer getAnnoField() {
        return annoField;
    }

    public void setAnnoField(Integer annoField) {
        this.annoField = annoField;
    }

    public String getCodicePratica() {
        return codicePratica;
    }

    public String getCodiceImmatricolazione() {
        return codiceImmatricolazione;
    }

    public void setCodicePratica(String codicePratica) {
        this.codicePratica = codicePratica;
    }

    public void setCodiceImmatricolazione(String codiceImmatricolazione) {
        this.codiceImmatricolazione = codiceImmatricolazione;
    }

    @Override
    public String toString() {
        return "Corrispondenza{" +
                "codicePratica='" + codicePratica + '\'' +
                ", codiceImmatricolazione='" + codiceImmatricolazione + '\'' +
                '}';
    }
}
