package barbatos_rex1.auth.domain.contract;

import barbatos_rex1.auth.dto.RoleDTO;
import barbatos_rex1.util.DTOAble;

public interface Role extends DTOAble<RoleDTO> {
    String roleId();
    String roleDescription();
    int accessLevel();
}
