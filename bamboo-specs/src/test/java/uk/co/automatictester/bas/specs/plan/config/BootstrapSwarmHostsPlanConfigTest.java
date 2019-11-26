package uk.co.automatictester.bas.specs.plan.config;

import com.atlassian.bamboo.specs.api.builders.permission.PlanPermissions;
import com.atlassian.bamboo.specs.api.builders.plan.Plan;
import com.atlassian.bamboo.specs.api.util.EntityPropertiesBuilders;
import org.junit.Test;

public class BootstrapSwarmHostsPlanConfigTest {

    @Test
    public void checkPlanConfig() {
        Plan plan = new BootstrapSwarmHostsPlanConfig().getPlan();
        PlanPermissions planPermissions = new BootstrapSwarmHostsPlanConfig().getPermissions();
        EntityPropertiesBuilders.build(plan);
        EntityPropertiesBuilders.build(planPermissions);
    }
}
