package ch.hearc.cafheg.business.allocations;

import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.AllocationMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mockito;
import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.AllocationMapper;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class GetParentDroitAllocationStepDefinitions {
    private AllocationService allocationService;
    private AllocataireMapper allocataireMapper;
    private AllocationMapper allocationMapper;
    private Parent parent1;
    private Parent parent2;
    private ParentDroitAllocation parents;
    private Parent parentAvecDroit;




    /**
    @io.cucumber.java.en.And("^the other has not$")
    public void theOtherHasNot() {

    }
     **/

    @Given("two parents")
    public void twoParents() {
        parent1 = new Parent(true, true, true, "Neuchâtel",Canton.NE, new BigDecimal(2500));
        parent2 = new Parent(true, false, true, "Bienne", Canton.BE, new BigDecimal(5000));
    }

    @And("a parent has a lucrative activity")
    public void aParentHasALucrativeActivity() {
       parent2.setActiviteLucrative(false);
    }

    @When("i ask who has the right")
    public void iAskWhoHasTheRight() {
        allocataireMapper = Mockito.mock(AllocataireMapper.class);
        allocationMapper = Mockito.mock(AllocationMapper.class);
        allocationService = new AllocationService(allocataireMapper, allocationMapper);
        parentAvecDroit=allocationService.getParentDroitAllocation(parents);

    }

    @Then("the parent with a lucrative get the right")
    public void theParentWithALucrativeGetTheRight() {
        assertThat(parentAvecDroit).isEqualTo(parent1);
    }
    /**

    @And("two parents have a lucrative activity")
    public void twoParentsHaveALucrativeActivity() {
    }

    @And("one has the parental authority")
    public void oneHasTheParentalAuthority() {
    }

    @Then("the parent with the parental authority gets the right")
    public void theParentWithTheParentalAuthorityGetsTheRight() {
    }

    @And("they both have the parental authority")
    public void theyBothHaveTheParentalAuthority() {
    }

    @And("they are separated")
    public void theyAreSeparated() {
    }
    **/
}
