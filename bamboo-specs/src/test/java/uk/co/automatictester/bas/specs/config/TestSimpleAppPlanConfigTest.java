package uk.co.automatictester.bas.specs.config;

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

public class TestSimpleAppPlanConfigTest {

    @Test
    public void checkPlanConfig() throws IOException {
        PlanConfig planConfig = new TestSimpleAppPlanConfig();

        Plan plan = planConfig.getPlan();
        PlanPermissions planPermissions = planConfig.getPermissions();

        EntityPropertiesBuilders.build(plan);
        EntityPropertiesBuilders.build(planPermissions);

        String thisYaml = BambooSpecSerializer.dump(plan);
        String path = "src/test/resources/TestSimpleAppPlanConfigTest.yaml";
        String previousYaml = FileUtils.readFileToString(new File(path).toPath());
        assertThat(thisYaml).isEqualTo(previousYaml);
    }
}
