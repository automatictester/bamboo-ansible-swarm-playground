package uk.co.automatictester.bas.specs.config;

import com.atlassian.bamboo.specs.api.builders.BambooKey;
import com.atlassian.bamboo.specs.api.builders.plan.Job;
import com.atlassian.bamboo.specs.api.builders.plan.Plan;
import com.atlassian.bamboo.specs.api.builders.plan.Stage;
import com.atlassian.bamboo.specs.api.builders.plan.branches.BranchCleanup;
import com.atlassian.bamboo.specs.api.builders.plan.branches.PlanBranchManagement;
import com.atlassian.bamboo.specs.api.builders.project.Project;
import com.atlassian.bamboo.specs.builders.task.ScriptTask;
import uk.co.automatictester.bas.specs.PlanConfig;
import uk.co.automatictester.bas.specs.ResourceReader;

public class TestSimpleAppPlanConfig extends PlanConfig {

    @Override
    public Plan getPlan() {
        return new Plan(new Project().name(PROJECT_NAME).key(getProjectKey()).description(DEFAULT_DESCRIPTION), "Test simple-app", getPlanKey()).description(DEFAULT_DESCRIPTION)
                .stages(new Stage("Default Stage")
                        .jobs(new Job("Default Job", new BambooKey("JOB1"))
                                .tasks(new ScriptTask()
                                                .description("Call app")
                                                .inlineBody(ResourceReader.loadAsString("/scripts/test_simple_app.sh")))))
                .planBranchManagement(new PlanBranchManagement()
                        .delete(new BranchCleanup())
                        .notificationForCommitters());
    }

    @Override
    public BambooKey getPlanKey() {
        return new BambooKey("TS");
    }
}
