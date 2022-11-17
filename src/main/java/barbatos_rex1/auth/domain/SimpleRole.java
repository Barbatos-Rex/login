package barbatos_rex1.auth.domain;

import barbatos_rex1.auth.domain.contract.Role;
import barbatos_rex1.auth.dto.RoleDTO;

public class SimpleRole implements Role {
    private String roleId;
    private String roleDescription;
    private int accessLevel;

    public SimpleRole(String roleId, String roleDescription, int accessLevel) {
        this.roleId = roleId;
        this.roleDescription = roleDescription;
        this.accessLevel = accessLevel;
    }

    @Override
    public String roleId() {
        return roleId;
    }

    @Override
    public String roleDescription() {
        return roleDescription;
    }

    @Override
    public int accessLevel() {
        return accessLevel;
    }

    @Override
    public RoleDTO toDto() {
        return new RoleDTO(roleId,roleDescription,accessLevel);
    }
}
