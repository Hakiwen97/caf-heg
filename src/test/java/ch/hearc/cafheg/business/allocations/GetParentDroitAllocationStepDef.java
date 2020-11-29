package ch.hearc.cafheg.business.allocations;

import ch.hearc.cafheg.business.allocations.AllocationService;
import ch.hearc.cafheg.business.allocations.Canton;
import ch.hearc.cafheg.business.allocations.Parent;
import ch.hearc.cafheg.business.allocations.ParentDroitAllocation;
import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.AllocationMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.mockito.Mockito;

import java.math.BigDecimal;

public class GetParentDroitAllocationStepDef {
    private AllocationService allocationService;
    private AllocataireMapper allocataireMapper;
    private AllocationMapper allocationMapper;
    private Parent parent1;
    private Parent parent2;
    private ParentDroitAllocation parents;
    private Parent rightfulParent;


    //A
    @Given("two parents")
    public void twoParents() {
        parent1 = new Parent(true, true, true, "Neuchâtel", Canton.NE, new BigDecimal(2500));
        parent2 = new Parent(true, false, true, "Bienne", Canton.BE, new BigDecimal(5000));
        throw new cucumber.api.PendingException();
    }

    @And("a parent has a lucrative activity")
    public void aParentHasALucrativeActivity() {
        parents.getParent2().setLucrativeActivity(false);
        throw new cucumber.api.PendingException();
    }

    @When("i ask who has the right")
    public void iAskWhoHasTheRight() {
        allocataireMapper = Mockito.mock(AllocataireMapper.class);
        allocationMapper = Mockito.mock(AllocationMapper.class);
        allocationService = new AllocationService(allocataireMapper, allocationMapper);
        parents = new ParentDroitAllocation("Neuchâtel", Canton.NE, false, parent1, parent2);
        rightfulParent = allocationService.getParentDroitAllocation(parents);
        throw new cucumber.api.PendingException();
    }

    @Then("the parent with a lucrative get the right")
    public void theParentWithALucrativeGetTheRight() {
        Assert.assertEquals(rightfulParent,parent1);
        throw new cucumber.api.PendingException();
    }

    //B
    @And("two parents have a lucrative activity")
    public void twoParentsHaveALucrativeActivity() {
        parents.getParent1().setLucrativeActivity(true);
        parents.getParent2().setLucrativeActivity(true);
        throw new cucumber.api.PendingException();
    }

    @And("one has the parental authority")
    public void oneHasTheParentalAuthority() {
        parents.getParent1().setParentalAuthority(true);
        parents.getParent2().setParentalAuthority(false);
        throw new cucumber.api.PendingException();
    }

    @Then("the parent with the parental authority gets the right")
    public void theParentWithTheParentalAuthorityGetsTheRight() {
        Assert.assertEquals(rightfulParent,parent1);
        throw new cucumber.api.PendingException();
    }

    @And("they both have the parental authority")
    public void theyBothHaveTheParentalAuthority() {
        parents.getParent1().setParentalAuthority(true);
        parents.getParent2().setParentalAuthority(true);
        throw new cucumber.api.PendingException();
    }

    @And("they are separated")
    public void theyAreSeparated() {
        parents.setParentsTogether(false);
        throw new cucumber.api.PendingException();
    }

    @And("one live with the child")
    public void oneLiveWithTheChild() {
        parents.getParent1().setParentAddress("Neuchâtel");
        parents.getParent2().setParentAddress("La Chaux-de-Fonds");
        parents.setChildAddress("Neuchâtel");
        throw new cucumber.api.PendingException();
    }

    @Then("the parent who lives with the child get the right")
    public void theParentWhoLivesWithTheChildGetTheRight() {
        Assert.assertEquals(rightfulParent,parent1);
        throw new cucumber.api.PendingException();
    }

    @And("they are together")
    public void theyAreTogether() {
        parents.setParentsTogether(true);
        throw new cucumber.api.PendingException();
    }

    @And("one parent work in the same canton than the child's residence")
    public void oneParentWorkInTheSameCantonThanTheChildSResidence() {
        parents.getParent1().setWorkingCanton(Canton.NE);
        parents.getParent2().setWorkingCanton(Canton.FR);
        parents.setChildCanton(Canton.NE);
        throw new cucumber.api.PendingException();
    }

    @Then("the parent who works in the same canton than the child's residence get the right")
    public void theParentWhoWorksInTheSameCantonThanTheChildSResidenceGetTheRight() {
        Assert.assertEquals(rightfulParent,parent1);
        throw new cucumber.api.PendingException();
    }

    @Then("the parent with the highest salary get the right")
    public void theParentWithTheHighestSalaryGetTheRight() {
        Assert.assertEquals(rightfulParent,parent2);
        throw new cucumber.api.PendingException();
    }

    @And("they are freelance")
    public void theyAreFreelance() {
        parents.getParent1().setFreelancer(true);
        parents.getParent2().setFreelancer(true);
        throw new cucumber.api.PendingException();
    }
}
