package uk.co.automatictester.bas.specs;

import com.atlassian.bamboo.specs.api.builders.BambooKey;
import com.atlassian.bamboo.specs.api.builders.permission.PermissionType;
import com.atlassian.bamboo.specs.api.builders.permission.Permissions;
import com.atlassian.bamboo.specs.api.builders.permission.PlanPermissions;
import com.atlassian.bamboo.specs.api.builders.plan.Plan;

import java.util.Optional;

public abstract class PlanConfig {

    protected static final String DEFAULT_DESCRIPTION = "Config generated automatically from Bamboo Spec. Any manual changes will be lost when it gets autogenerated again";
    protected static final String PROJECT_NAME = "Swarm";

    public abstract BambooKey getPlanKey();

    public abstract Plan getPlan();

    protected BambooKey getProjectKey() {
        return new BambooKey("SWAR");
    }

    public Optional<DeploymentConfig> getDeploymentConfig() {
        return Optional.empty();
    }

    public PlanPermissions getPermissions() {
        return new PlanPermissions(getProjectKey(), getPlanKey()).permissions(
                new Permissions().userPermissions(
                        ServerConfig.getAdminUser(), PermissionType.EDIT, PermissionType.VIEW, PermissionType.ADMIN, PermissionType.CLONE, PermissionType.BUILD));
    }
}
