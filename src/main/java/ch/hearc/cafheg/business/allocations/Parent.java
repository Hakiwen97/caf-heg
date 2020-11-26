package ch.hearc.cafheg.business.allocations;

import java.math.BigDecimal;

public class Parent {
    private boolean activiteLucrative;
    private String residence;
    private boolean isIndependant;
    private Canton cantonTravail;
    private BigDecimal salaire;
    private boolean autoriteParentale;

    public Parent(boolean activiteLucrative, boolean isIndependant, boolean autoriteParentale, String residence, Canton cantonTravail, BigDecimal salaire) {
        this.activiteLucrative = activiteLucrative;
        this.isIndependant = isIndependant;
        this.autoriteParentale = autoriteParentale;
        this.residence = residence;
        this.cantonTravail = cantonTravail;
        this.salaire = salaire;

    }

    public boolean isIndependant() {
        return isIndependant;
    }

    public void setIndependant(boolean independant) {
        isIndependant = independant;
    }

    public boolean isActiviteLucrative() {
        return activiteLucrative;
    }

    public void setActiviteLucrative(boolean activiteLucrative) {
        this.activiteLucrative = activiteLucrative;
    }

    public Canton getCantonTravail() {
        return cantonTravail;
    }

    public void setCantonTravail(Canton cantonTravail) {
        this.cantonTravail = cantonTravail;
    }

    public boolean isAutoriteParentale() {
        return autoriteParentale;
    }

    public void setAutoriteParentale(boolean autoriteParentale) {
        this.autoriteParentale = autoriteParentale;
    }

    public boolean hasActiviteLucrative() {
        return activiteLucrative;
    }

    ;

    public boolean hasAutoriteParentale() {
        return autoriteParentale;
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
