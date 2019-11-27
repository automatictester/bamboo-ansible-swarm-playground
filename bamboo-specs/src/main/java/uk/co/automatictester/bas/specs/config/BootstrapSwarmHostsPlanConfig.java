package uk.co.automatictester.bas.specs.config;

import com.atlassian.bamboo.specs.api.builders.BambooKey;
import com.atlassian.bamboo.specs.api.builders.plan.Job;
import com.atlassian.bamboo.specs.api.builders.plan.Plan;
import com.atlassian.bamboo.specs.api.builders.plan.Stage;
import com.atlassian.bamboo.specs.api.builders.plan.branches.BranchCleanup;
import com.atlassian.bamboo.specs.api.builders.plan.branches.PlanBranchManagement;
import com.atlassian.bamboo.specs.api.builders.project.Project;
import com.atlassian.bamboo.specs.builders.repository.git.GitRepository;
import com.atlassian.bamboo.specs.builders.repository.viewer.GitHubRepositoryViewer;
import com.atlassian.bamboo.specs.builders.task.CheckoutItem;
import com.atlassian.bamboo.specs.builders.task.ScriptTask;
import com.atlassian.bamboo.specs.builders.task.VcsCheckoutTask;
import uk.co.automatictester.bas.specs.ParentPlanConfig;
import uk.co.automatictester.bas.specs.ResourceReader;

public class BootstrapSwarmHostsPlanConfig extends ParentPlanConfig {

    @Override
    public Plan getPlan() {
        return new Plan(new Project().name("Swarm").key(getProjectKey()).description(DEFAULT_DESCRIPTION), "Bootstrap Swarm hosts", getPlanKey()).description(DEFAULT_DESCRIPTION)
                .stages(new Stage("Default Stage")
                        .jobs(new Job("Default Job", new BambooKey("JOB1"))
                                .tasks(new VcsCheckoutTask()
                                                .description("Git clone")
                                                .checkoutItems(new CheckoutItem().defaultRepository())
                                                .cleanCheckout(true),
                                        new ScriptTask()
                                                .description("Run Ansible playbook")
                                                .inlineBody(ResourceReader.loadAsString("/scripts/bootstrap_swarm_hosts.sh"))
                                                .workingSubdirectory("ansible"))))
                .planRepositories(new GitRepository()
                        .name("bamboo-ansible-swarm-playground")
                        .url("https://github.com/automatictester/bamboo-ansible-swarm-playground.git")
                        .repositoryViewer(new GitHubRepositoryViewer()))
                .planBranchManagement(new PlanBranchManagement()
                        .delete(new BranchCleanup())
                        .notificationForCommitters());
    }

    @Override
    public BambooKey getProjectKey() {
        return new BambooKey("SWAR");
    }

    @Override
    public BambooKey getPlanKey() {
        return new BambooKey("AN");
    }
}
