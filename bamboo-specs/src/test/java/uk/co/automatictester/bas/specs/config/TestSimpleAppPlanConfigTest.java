package uk.co.automatictester.bas.specs.config;

import com.atlassian.bamboo.specs.api.builders.permission.PlanPermissions;
import com.atlassian.bamboo.specs.api.builders.plan.Plan;
import com.atlassian.bamboo.specs.api.util.EntityPropertiesBuilders;
import org.junit.Test;

public class TestSimpleAppPlanConfigTest {

    @Test
    public void checkPlanConfig() {
        Plan plan = new TestSimpleAppPlanConfig().getPlan();
        PlanPermissions planPermissions = new TestSimpleAppPlanConfig().getPermissions();
        EntityPropertiesBuilders.build(plan);
        EntityPropertiesBuilders.build(planPermissions);
    }
}
