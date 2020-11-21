package ch.hearc.cafheg.business.allocations;

import java.math.BigDecimal;

public class Parent {
    private boolean activiteLucrative;
    private String residence;
    private BigDecimal salaire;

    public Parent(boolean activiteLucrative, String residence, BigDecimal salaire) {
        this.activiteLucrative = activiteLucrative;
        this.residence = residence;
        this.salaire = salaire;
    }

    public boolean isActiviteLucrative() {
        return activiteLucrative;
    }

    public void setActiviteLucrative(boolean activiteLucrative) {
        this.activiteLucrative = activiteLucrative;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public BigDecimal getSalaire() {
        return salaire;
    }

    public void setSalaire(BigDecimal salaire) {
        this.salaire = salaire;
    }
}
