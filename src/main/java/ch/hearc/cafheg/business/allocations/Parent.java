package ch.hearc.cafheg.business.allocations;

import java.math.BigDecimal;

public class Parent {
    private boolean lucrativeActivity;
    private String parentAddress;
    private boolean isFreelancer;
    private Canton workingCanton;
    private BigDecimal salary;
    private boolean parentalAuthority;

    public Parent(boolean lucrativeActivity, boolean isFreelancer, boolean parentalAuthority, String parentAddress, Canton workingCanton, BigDecimal salary) {
        this.lucrativeActivity = lucrativeActivity;
        this.isFreelancer = isFreelancer;
        this.parentalAuthority = parentalAuthority;
        this.parentAddress = parentAddress;
        this.workingCanton = workingCanton;
        this.salary = salary;

    }

    public boolean isFreelancer() {
        return isFreelancer;
    }

    public void setFreelancer(boolean freelancer) {
        isFreelancer = freelancer;
    }

    public boolean isLucrativeActivity() {
        return lucrativeActivity;
    }

    public void setLucrativeActivity(boolean lucrativeActivity) {
        this.lucrativeActivity = lucrativeActivity;
    }

    public Canton getWorkingCanton() {
        return workingCanton;
    }

    public void setWorkingCanton(Canton workingCanton) {
        this.workingCanton = workingCanton;
    }

    public boolean isParentalAuthority() {
        return parentalAuthority;
    }

    public void setParentalAuthority(boolean parentalAuthority) {
        this.parentalAuthority = parentalAuthority;
    }

    public boolean hasLucrativeActivity() {
        return lucrativeActivity;
    }


    public boolean hasAutoriteParentale() {
        return parentalAuthority;
    }

    public String getParentAddress() {
        return parentAddress;
    }

    public void setParentAddress(String parentAddress) {
        this.parentAddress = parentAddress;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
