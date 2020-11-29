package ch.hearc.cafheg.business.allocations;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

    @RunWith(Cucumber.class)
    @CucumberOptions(
            features ="src/test/resources/allocations/GetParentDroitAllocation.feature", glue = {"GetParentDroitAllocationStepDef"}
    )
            public class RunCucumberTest { }

