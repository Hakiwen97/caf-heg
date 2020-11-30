package ch.hearc.cafheg.business.allocations;

import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.AllocationMapper;
import io.cucumber.java.BeforeStep;
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
        parents = new ParentDroitAllocation("Neuchâtel", Canton.NE, false, parent1, parent2);
    }

    @BeforeStep
    public void setUp() {
        allocataireMapper = Mockito.mock(AllocataireMapper.class);
        allocationMapper = Mockito.mock(AllocationMapper.class);
        allocationService = new AllocationService(allocataireMapper, allocationMapper,null);
    }


    @And("a parent has a lucrative activity")
    public void aParentHasALucrativeActivity() {
        parents.getParent2().setLucrativeActivity(false);

    }

    @Then("the parent with a lucrative activity gets the right")
    public void the_parent_with_a_lucrative_activity_gets_the_right() {
        Assert.assertEquals(rightfulParent, parent1);

    }

    @When("i ask who has the right")
    public void iAskWhoHasTheRight() {
        rightfulParent = allocationService.getParentDroitAllocation(parents);

    }

    @Then("the parent with a lucrative get the right")
    public void theParentWithALucrativeGetTheRight() {
        Assert.assertEquals(rightfulParent, parent1);

    }

    //B
    @And("two parents have a lucrative activity")
    public void twoParentsHaveALucrativeActivity() {
        parents.getParent1().setLucrativeActivity(true);
        parents.getParent2().setLucrativeActivity(true);

    }

    @And("one has the parental authority")
    public void oneHasTheParentalAuthority() {
        parents.getParent1().setParentalAuthority(true);
        parents.getParent2().setParentalAuthority(false);

    }

    @Then("the parent with the parental authority gets the right")
    public void theParentWithTheParentalAuthorityGetsTheRight() {
        Assert.assertEquals(rightfulParent, parent1);

    }

    @And("they both have the parental authority")
    public void theyBothHaveTheParentalAuthority() {
        parents.getParent1().setParentalAuthority(true);
        parents.getParent2().setParentalAuthority(true);

    }

    @And("they are separated")
    public void theyAreSeparated() {
        parents.setParentsTogether(false);

    }

    @And("one live with the child")
    public void oneLiveWithTheChild() {
        parents.getParent1().setParentAddress("Neuchâtel");
        parents.getParent2().setParentAddress("La Chaux-de-Fonds");
        parents.setChildAddress("Neuchâtel");

    }

    @Then("the parent who lives with the child get the right")
    public void theParentWhoLivesWithTheChildGetTheRight() {
        Assert.assertEquals(rightfulParent, parent1);

    }

    @And("they are together")
    public void theyAreTogether() {
        parents.setParentsTogether(true);

    }

    @And("one parent work in the same canton than the child's residence")
    public void oneParentWorkInTheSameCantonThanTheChildSResidence() {
        parents.getParent1().setWorkingCanton(Canton.NE);
        parents.getParent2().setWorkingCanton(Canton.FR);
        parents.setChildCanton(Canton.NE);

    }

    @And("none parents work in the child's residence")
    public void noneParentWorkinTheSameCantonThanTheChildResidence() {
        parents.setChildCanton(Canton.NE);
        parents.getParent1().setWorkingCanton(Canton.FR);
        parents.getParent2().setWorkingCanton(Canton.BE);
    }

    @Then("the parent who works in the same canton than the child's residence get the right")
    public void theParentWhoWorksInTheSameCantonThanTheChildSResidenceGetTheRight() {
        Assert.assertEquals(rightfulParent, parent1);
    }

    @Then("the parent with the highest salary get the right")
    public void theParentWithTheHighestSalaryGetTheRight() {
        Assert.assertEquals(rightfulParent, parent2);
    }

    @And("they are freelance")
    public void theyAreFreelance() {
        parents.getParent1().setFreelancer(true);
        parents.getParent2().setFreelancer(true);
    }

}
