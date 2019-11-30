package uk.co.automatictester.bas.specs.config;

import com.atlassian.bamboo.specs.api.builders.BambooKey;
import com.atlassian.bamboo.specs.api.builders.deployment.Deployment;
import com.atlassian.bamboo.specs.api.builders.deployment.Environment;
import com.atlassian.bamboo.specs.api.builders.deployment.ReleaseNaming;
import com.atlassian.bamboo.specs.api.builders.permission.DeploymentPermissions;
import com.atlassian.bamboo.specs.api.builders.permission.EnvironmentPermissions;
import com.atlassian.bamboo.specs.api.builders.permission.PermissionType;
import com.atlassian.bamboo.specs.api.builders.permission.Permissions;
import com.atlassian.bamboo.specs.api.builders.plan.Job;
import com.atlassian.bamboo.specs.api.builders.plan.Plan;
import com.atlassian.bamboo.specs.api.builders.plan.PlanIdentifier;
import com.atlassian.bamboo.specs.api.builders.plan.Stage;
import com.atlassian.bamboo.specs.api.builders.plan.artifact.Artifact;
import com.atlassian.bamboo.specs.api.builders.plan.branches.BranchCleanup;
import com.atlassian.bamboo.specs.api.builders.plan.branches.PlanBranchManagement;
import com.atlassian.bamboo.specs.api.builders.project.Project;
import com.atlassian.bamboo.specs.builders.task.*;
import com.atlassian.bamboo.specs.model.task.InjectVariablesScope;
import uk.co.automatictester.bas.specs.PlanConfig;
import uk.co.automatictester.bas.specs.ResourceReader;
import uk.co.automatictester.bas.specs.ServerConfig;

import static uk.co.automatictester.bas.specs.Environment.DEV;

public class SimpleAppPlanConfig extends PlanConfig {

    private static final String DEPLOYMENT_PLAN_NAME = "Deploy simple-app to Docker Swarm";

    @Override
    public Plan getPlan() {
        return new Plan(new Project().name(PROJECT_NAME).key(getProjectKey()).description(DEFAULT_DESCRIPTION), "Build and release simple-app", getPlanKey()).description(DEFAULT_DESCRIPTION)
                .stages(new Stage("Default Stage")
                        .jobs(new Job("Default Job", new BambooKey("JOB1"))
                                .artifacts(new Artifact()
                                        .name("version.properties")
                                        .copyPattern("version.properties")
                                        .location("simple-app")
                                        .shared(true)
                                        .required(true))
                                .tasks(new CleanWorkingDirectoryTask(),
                                        new ScriptTask()
                                                .description("Git clone simple-app")
                                                .inlineBody("git clone git@github.com:automatictester/simple-app.git"),
                                        new ScriptTask()
                                                .description("Build, push to docker registry and git")
                                                .inlineBody(ResourceReader.loadAsString("/scripts/simple_app.sh"))
                                                .workingSubdirectory("simple-app"),
                                        new ScriptTask()
                                                .description("Prune Docker images")
                                                .inlineBody("docker image prune -f"),
                                        new InjectVariablesTask()
                                                .description("Get version number")
                                                .path("simple-app/version.properties")
                                                .namespace("release")
                                                .scope(InjectVariablesScope.RESULT))))
                .planBranchManagement(new PlanBranchManagement()
                        .delete(new BranchCleanup())
                        .notificationForCommitters());
    }

    @Override
    public boolean hasDeploymentPlan() {
        return true;
    }

    @Override
    public Deployment getDeploymentPlan() {
        return new Deployment(new PlanIdentifier(getProjectKey(), getPlanKey()), DEPLOYMENT_PLAN_NAME)
                .releaseNaming(new ReleaseNaming("${bamboo.release.version}"))
                .environments(new Environment(DEV.toString())
                        .tasks(new CleanWorkingDirectoryTask(),
                                new ScriptTask()
                                        .description("Get Ansible scripts")
                                        .inlineBody("git clone https://github.com/automatictester/bamboo-ansible-swarm-playground.git bas"),
                                new ArtifactDownloaderTask()
                                        .description("Get version number from build plan")
                                        .artifacts(new DownloadItem().artifact("version.properties")),
                                new InjectVariablesTask()
                                        .description("Get simple-app version")
                                        .path("version.properties")
                                        .namespace("inject")
                                        .scope(InjectVariablesScope.LOCAL),
                                new ScriptTask()
                                        .description("Deploy to Swarm")
                                        .inlineBody(ResourceReader.loadAsString("/scripts/deploy_to_swarm.sh"))
                                        .workingSubdirectory("bas"),
                                new ScriptTask()
                                        .description("Call app")
                                        .inlineBody(ResourceReader.loadAsString("/scripts/call_app.sh"))));
    }

    @Override
    public BambooKey getPlanKey() {
        return new BambooKey("SIM");
    }

    public DeploymentPermissions getDeploymentPermission() {
        return new DeploymentPermissions(DEPLOYMENT_PLAN_NAME)
                .permissions(new Permissions().userPermissions(ServerConfig.getAdminUser(), PermissionType.EDIT, PermissionType.VIEW));
    }

    public EnvironmentPermissions getEnvironmentPermission() {
        return new EnvironmentPermissions(DEPLOYMENT_PLAN_NAME)
                .environmentName(DEV.toString())
                .permissions(new Permissions().userPermissions(ServerConfig.getAdminUser(), PermissionType.EDIT, PermissionType.VIEW, PermissionType.BUILD));
    }
}
