package uk.co.automatictester.bas.specs.config;

import com.atlassian.bamboo.specs.api.builders.permission.PlanPermissions;
import com.atlassian.bamboo.specs.api.builders.plan.Plan;
import com.atlassian.bamboo.specs.api.util.EntityPropertiesBuilders;
import org.junit.Test;

public class InitializeSwarmManagerPlanConfigTest {

    @Test
    public void checkPlanConfig() {
        Plan plan = new InitializeSwarmManagerPlanConfig().getPlan();
        PlanPermissions planPermissions = new InitializeSwarmManagerPlanConfig().getPermissions();
        EntityPropertiesBuilders.build(plan);
        EntityPropertiesBuilders.build(planPermissions);
    }
}
