package sast.freshcup.common.enums;

/**
 * @author: 風楪fy
 * @create: 2022-01-15 17:18
 **/
public enum ErrorEnum {

    PARAMS_LOSS(1000, "传参缺失"),

    COMMON_ERROR(5000, "错误"),

    TOKEN_ERROR(5001, "TOKEN错误"),

    NO_LOGIN(5002, "没有登录"),

    WEBSOCKEY_ERROR(5003, "websocket异常"),

    AUTHORITY_ERROR(5005, "权限不足"),

    ROLE_ERROR(5006, "对应角色错误"),

    USER_EXIST(5007, "用户已存在"),

    NO_USER(5008, "没有对应用户"),

    EXPIRED_LOGIN(5009, "登录过期"),

    MONEY_ERROR(6001,"充值失败，充值金额须大于0"),

    NO_DISHESID(6002,"点餐不能为空哦"),


    DISHESID_ERROR(6003,"点餐格式错误"),


    DATE_ERROR(8001,"时间设置错误");

    private Integer errCode;
    private String errMsg;

    ErrorEnum(Integer errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }
}
