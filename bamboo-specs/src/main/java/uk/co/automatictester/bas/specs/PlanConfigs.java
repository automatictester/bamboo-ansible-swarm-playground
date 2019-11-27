package uk.co.automatictester.bas.specs;

import uk.co.automatictester.bas.specs.config.BootstrapSwarmHostsPlanConfig;

import java.util.Arrays;
import java.util.List;

public class PlanConfigs {

    public static List<ParentPlanConfig> getAll() {
        return Arrays.asList(
                new BootstrapSwarmHostsPlanConfig()
        );
    }
}
