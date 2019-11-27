package uk.co.automatictester.bas.specs;

import uk.co.automatictester.bas.specs.config.BootstrapSwarmHostsPlanConfig;
import uk.co.automatictester.bas.specs.config.InitializeSwarmManagerPlanConfig;
import uk.co.automatictester.bas.specs.config.JoinWorkersToExistingSwarmPlanConfig;
import uk.co.automatictester.bas.specs.config.TestSimpleAppPlanConfig;

import java.util.Arrays;
import java.util.List;

public class PlanConfigs {

    public static List<PlanConfig> getAll() {
        return Arrays.asList(
                new BootstrapSwarmHostsPlanConfig(),
                new InitializeSwarmManagerPlanConfig(),
                new JoinWorkersToExistingSwarmPlanConfig(),
                new TestSimpleAppPlanConfig()
        );
    }
}
