package uk.co.automatictester.bas.specs.config;

import com.atlassian.bamboo.specs.api.builders.deployment.Deployment;
import com.atlassian.bamboo.specs.api.builders.permission.DeploymentPermissions;
import com.atlassian.bamboo.specs.api.builders.permission.EnvironmentPermissions;
import com.atlassian.bamboo.specs.api.builders.permission.PlanPermissions;
import com.atlassian.bamboo.specs.api.builders.plan.Plan;
import com.atlassian.bamboo.specs.api.util.EntityPropertiesBuilders;
import org.junit.Test;
import uk.co.automatictester.bas.specs.PlanConfig;

public class SimpleAppPlanConfigTest {

    @Test
    public void checkPlanConfig() {
        PlanConfig planConfig = new SimpleAppPlanConfig();

        Plan plan = planConfig.getPlan();
        PlanPermissions planPermissions = planConfig.getPermissions();
        Deployment deploymentPlan = planConfig.getDeploymentConfig().get().getDeploymentPlan();
        DeploymentPermissions deploymentPermissions = planConfig.getDeploymentConfig().get().getDeploymentPermissions();
        EnvironmentPermissions environmentPermissions = planConfig.getDeploymentConfig().get().getEnvironmentPermissions();

        EntityPropertiesBuilders.build(plan);
        EntityPropertiesBuilders.build(planPermissions);
        EntityPropertiesBuilders.build(deploymentPlan);
        EntityPropertiesBuilders.build(deploymentPermissions);
        EntityPropertiesBuilders.build(environmentPermissions);
    }
}
