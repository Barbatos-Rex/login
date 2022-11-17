package barbatos_rex1.auth.dto;

public class RoleDTO {
    public String roleId;
    public String roleDescription;
    public int accessLevel;

    public RoleDTO(String roleId, String roleDescription, int accessLevel) {
        this.roleId = roleId;
        this.roleDescription = roleDescription;
        this.accessLevel = accessLevel;
    }
}
