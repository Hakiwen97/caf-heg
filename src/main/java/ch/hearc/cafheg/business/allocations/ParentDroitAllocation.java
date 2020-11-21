package ch.hearc.cafheg.business.allocations;

import java.math.BigDecimal;

public class ParentDroitAllocation {
    private String enfantResidance;
    private boolean parentsEnsemble;
    private Parent parent1;
    private Parent parent2;

    public ParentDroitAllocation(String enfantResidance, boolean parentsEnsemble, Parent parent1, Parent parent2) {
        this.enfantResidance = enfantResidance;
        this.parentsEnsemble = parentsEnsemble;
        this.parent1 = parent1;
        this.parent2 = parent2;
    }

    public String getEnfantResidance() {
        return enfantResidance;
    }

    public void setEnfantResidance(String enfantResidance) {
        this.enfantResidance = enfantResidance;
    }

    public boolean isParentsEnsemble() {
        return parentsEnsemble;
    }

    public void setParentsEnsemble(boolean parentsEnsemble) {
        this.parentsEnsemble = parentsEnsemble;
    }

    public Parent getParent1() {
        return parent1;
    }

    public void setParent1(Parent parent1) {
        this.parent1 = parent1;
    }

    public Parent getParent2() {
        return parent2;
    }

    public void setParent2(Parent parent2) {
        this.parent2 = parent2;
    }
}
