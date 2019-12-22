package uk.co.automatictester.bas.specs.config;

import com.atlassian.bamboo.specs.api.builders.permission.PlanPermissions;
import com.atlassian.bamboo.specs.api.builders.plan.Plan;
import com.atlassian.bamboo.specs.api.util.EntityPropertiesBuilders;
import org.junit.Test;
import uk.co.automatictester.bas.specs.PlanConfig;

public class UpgradeDockerPlanConfigTest {

    @Test
    public void checkPlanConfig() {
        PlanConfig planConfig = new UpgradeDockerPlanConfig();

        Plan plan = planConfig.getPlan();
        PlanPermissions planPermissions = planConfig.getPermissions();

        EntityPropertiesBuilders.build(plan);
        EntityPropertiesBuilders.build(planPermissions);
    }
}
