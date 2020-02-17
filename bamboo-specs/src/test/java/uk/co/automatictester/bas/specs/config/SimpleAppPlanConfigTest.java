package uk.co.automatictester.bas.specs.config;

import com.atlassian.bamboo.specs.api.builders.deployment.Deployment;
import com.atlassian.bamboo.specs.api.builders.permission.DeploymentPermissions;
import com.atlassian.bamboo.specs.api.builders.permission.EnvironmentPermissions;
import com.atlassian.bamboo.specs.api.builders.permission.PlanPermissions;
import com.atlassian.bamboo.specs.api.builders.plan.Plan;
import com.atlassian.bamboo.specs.api.util.EntityPropertiesBuilders;
import com.atlassian.bamboo.specs.util.BambooSpecSerializer;
import com.atlassian.bamboo.specs.util.FileUtils;
import org.junit.Test;
import uk.co.automatictester.bas.specs.PlanConfig;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleAppPlanConfigTest {

    @Test
    public void checkPlanConfig() throws IOException {
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

        String thisYaml = BambooSpecSerializer.dump(plan);
        String path = "src/test/resources/SimpleAppPlanConfigTest.yaml";
        String previousYaml = FileUtils.readFileToString(new File(path).toPath());
        assertThat(thisYaml).isEqualTo(previousYaml);

        String thisDeploymentPlanYaml = BambooSpecSerializer.dump(deploymentPlan);
        String deploymentPlanPath = "src/test/resources/SimpleAppPlanConfigTest-DeploymentPlan.yaml";
        String previousDeploymentPlanYaml = FileUtils.readFileToString(new File(deploymentPlanPath).toPath());
        assertThat(thisDeploymentPlanYaml).isEqualTo(previousDeploymentPlanYaml);

        String thisDeploymentPermissionsYaml = BambooSpecSerializer.dump(deploymentPermissions);
        String deploymentPermissionsPath = "src/test/resources/DeploymentPermissions.yaml";
        String previousDeploymentPermissionsYaml = FileUtils.readFileToString(new File(deploymentPermissionsPath).toPath());
        assertThat(thisDeploymentPermissionsYaml).isEqualTo(previousDeploymentPermissionsYaml);

        String thisEnvironmentPermissionsYaml = BambooSpecSerializer.dump(environmentPermissions);
        String environmentPermissionsPath = "src/test/resources/EnvironmentPermissions.yaml";
        String previousEnvironmentPermissionsYaml = FileUtils.readFileToString(new File(environmentPermissionsPath).toPath());
        assertThat(thisEnvironmentPermissionsYaml).isEqualTo(previousEnvironmentPermissionsYaml);
    }
}
