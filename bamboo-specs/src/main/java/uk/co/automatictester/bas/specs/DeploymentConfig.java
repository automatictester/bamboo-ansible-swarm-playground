package uk.co.automatictester.bas.specs;

import com.atlassian.bamboo.specs.api.builders.deployment.Deployment;
import com.atlassian.bamboo.specs.api.builders.permission.DeploymentPermissions;
import com.atlassian.bamboo.specs.api.builders.permission.EnvironmentPermissions;
import com.atlassian.bamboo.specs.api.builders.permission.PermissionType;
import com.atlassian.bamboo.specs.api.builders.permission.Permissions;

public interface DeploymentConfig {
    Deployment getDeploymentPlan();

    String getDeploymentPlanName();

    String getEnvName();

    default DeploymentPermissions getDeploymentPermissions() {
        return new DeploymentPermissions(getDeploymentPlanName())
                .permissions(new Permissions().userPermissions(ServerConfig.getAdminUser(), PermissionType.EDIT, PermissionType.VIEW));
    }

    default EnvironmentPermissions getEnvironmentPermissions() {
        return new EnvironmentPermissions(getDeploymentPlanName())
                .environmentName(getEnvName())
                .permissions(new Permissions().userPermissions(ServerConfig.getAdminUser(), PermissionType.EDIT, PermissionType.VIEW, PermissionType.BUILD));
    }
}
