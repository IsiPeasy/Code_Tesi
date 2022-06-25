package com.space.model.coppieLingue;

import java.io.Serializable;

public class CoppiaLingue implements Serializable {

    private Integer annoacc;
    private String cscCod;
    private String codLingua1;
    private String desLingua1;
    private String codLingua2;
    private String desLingua2;

    public Integer getAnnoacc() {
        return annoacc;
    }

    public void setAnnoacc(Integer annoacc) {
        this.annoacc = annoacc;
    }

    public String getCscCod() {return cscCod; }

    public void setCscCod(String cscCod) {
        this.cscCod = cscCod;
    }

    public String getCodLingua1() { return codLingua1; }

    public void setCodLingua1(String codLingua1) {
        this.codLingua1 = codLingua1;
    }

    public String getDesLingua1() {return desLingua1;}

    public void setDesLingua1(String desLingua1) {
        this.desLingua1 = desLingua1;
    }

    public String getCodLingua2() {return codLingua2; }

    public void setCodLingua2(String codLingua2) {
        this.codLingua2 = codLingua2;
    }

    public String getDesLingua2() {
        return desLingua2;
    }

    public void setDesLingua2(String desLingua2) {
        this.desLingua2 = desLingua2;
    }

    @Override
    public String toString() {
        return "CoppiaLingue{" +
                "annoacc=" + annoacc +
                ", cscCod='" + cscCod + '\'' +
                ", codLingua1='" + codLingua1 + '\'' +
                ", desLingua1='" + desLingua1 + '\'' +
                ", codLingua2='" + codLingua2 + '\'' +
                ", desLingua2='" + desLingua2 + '\'' +
                '}';
    }
}
