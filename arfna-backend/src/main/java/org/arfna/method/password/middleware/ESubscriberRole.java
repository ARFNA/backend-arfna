package org.arfna.method.password.middleware;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ESubscriberRole {
    ADMIN_ROLE(1, "admin"),
    MAINT_ROLE(2, "maint"),
    WRITER_ROLE(3, "writer"),
    NONE_ROLE(5, "none");

    private int roleNumber;
    private String roleName;
    
    private static Map<String, ESubscriberRole> roleNameToRoleMap;
    
    ESubscriberRole(int roleNum, String roleName) {
        this.roleNumber = roleNum;
        this.roleName = roleName;
    }

    public int getRoleNumber() {
        return roleNumber;
    }

    public String getRoleName() {
        return roleName;
    }

    public static ESubscriberRole getRoleFromName(String roleName) {
        if (roleNameToRoleMap == null) {
            roleNameToRoleMap = Arrays.stream(ESubscriberRole.values()).collect(Collectors.toMap(k -> k.roleName, v -> v));
        }
        return roleNameToRoleMap.get(roleName);
    }
}
