package sast.freshcup.common.enums;

import lombok.Data;
import lombok.Getter;
import sast.freshcup.entity.Account;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 風楪fy
 * @create: 2022-01-19 22:51
 **/
public enum AuthEnum {
    STUDENT("学生", 0),
    ADMIN("管理员", 1);

    private final String role;
    private final Integer code;
    private static final Map<String, AuthEnum> AUTH_ENUM_MAP;

    static {
        AUTH_ENUM_MAP = new HashMap<>();
        for (AuthEnum authEnum : AuthEnum.values()) {
            AUTH_ENUM_MAP.put(authEnum.toString(), authEnum);
        }
    }

    AuthEnum(String role, Integer code) {
        this.role = role;
        this.code = code;
    }

    public String getRole() {
        return role;
    }

    public Integer getCode() {
        return code;
    }

    public static Boolean checkAuth(Account account, AuthEnum needRole) {
        return needRole.code.equals(account.getRole());
    }
}
