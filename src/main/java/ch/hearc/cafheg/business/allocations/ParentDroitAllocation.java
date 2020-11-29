package ch.hearc.cafheg.business.allocations;

public class ParentDroitAllocation {
    private String childAddress;
    private Canton childCanton;
    private boolean parentsTogether;
    private Parent parent1;
    private Parent parent2;

    public ParentDroitAllocation(String childAddress, Canton childCanton, boolean parentsTogether, Parent parent1, Parent parent2) {
        this.childAddress = childAddress;
        this.childCanton = childCanton;
        this.parentsTogether = parentsTogether;
        this.parent1 = parent1;
        this.parent2 = parent2;
    }

    public Canton getChildCanton() {
        return childCanton;
    }

    public void setChildCanton(Canton childCanton) {
        this.childCanton = childCanton;
    }

    public String getChildAddress() {
        return childAddress;
    }

    public void setChildAddress(String childAddress) {
        this.childAddress = childAddress;
    }

    public boolean isParentsTogether() {
        return parentsTogether;
    }

    public void setParentsTogether(boolean parentsTogether) {
        this.parentsTogether = parentsTogether;
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
