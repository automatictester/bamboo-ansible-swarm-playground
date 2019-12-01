package uk.co.automatictester.bas.specs;

import com.atlassian.bamboo.specs.api.BambooSpec;
import com.atlassian.bamboo.specs.util.BambooServer;

@BambooSpec
public class PlanPublisher {

    public static void main(String[] args) {
        String serverUrl = ServerConfig.getServerUrl();
        BambooServer server = new BambooServer(serverUrl);
        for (PlanConfig planConfig : PlanConfigs.getAll()) {
            server.publish(planConfig.getPlan());
            server.publish(planConfig.getPermissions());

            planConfig.getDeploymentConfig().ifPresent(deploymentConfig -> {
                server.publish(deploymentConfig.getDeploymentPlan());
                server.publish(deploymentConfig.getDeploymentPermissions());
                server.publish(deploymentConfig.getEnvironmentPermissions());
            });
        }
    }
}
