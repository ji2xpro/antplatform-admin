package com.antplatform.admin.biz.infrastructure.exception;

/**
 * <p>
 * 1001: 企业名称已存在
 * <p>
 * 1002：企业未找到
 * <p>
 * 1003：未知的企业状态
 * <p>
 * 1004：企业不可用
 * <p>
 * 2001: 场馆名称已存在
 * <p>
 * 2002：场馆未找到
 * <p>
 * 2003：未知的场馆状态
 * <p>
 * 2005：场馆不可用
 * <p>
 * 3001: 角色名称已存在
 * <p>
 * 3002：角色未找到
 * <p>
 * 3003：未知的角色状态
 * <p>
 * 3004：角色下有未删除的信息
 * <p>
 * 3005：角色不可用
 * <p>
 * 4001: 账户名已存在
 * <p>
 * 4002：账户未找到
 * <p>
 * 4003：未知的账户状态
 * <p>
 * 4004：账户下有未删除的信息
 * <p>
 * 4005: 账户名错误
 * <p>
 * 4006：密码错误
 * <p>
 * 4007：账户不可用
 * <p>
 * 4008：账户已绑定
 * <p>
 * 4009：账户未绑定
 * <p>
 * 5001: 机构名称已存在
 * <p>
 * 5002：机构未找到
 * <p>
 * 5003：未知的机构状态
 * <p>
 * 5004：机构下有未删除的用户
 * <p>
 * 5005：机构不可用
 * <p>
 * 6001: 主办方名称已存在
 * <p>
 * 6002：主办方未找到
 * <p>
 * 6003：未知的主办方状态
 * <p>
 * 6004：主办方下有未删除的用户
 * <p>
 * 6005：主办方不可用
 * <p>
 * 7001: 分销商名称已存在
 * <p>
 * 7002：分销商未找到
 * <p>
 * 7003：未知的分销商状态
 * <p>
 * 7004：分销商下有未删除的用户
 * <p>
 * 7005：分销商不可用
 * <p>
 * 8001: 菜单名称已存在
 * <p>
 * 8002：菜单未找到
 * <p>
 * 8003：未知的菜单状态
 * <p>
 * 8004：菜单不可用
 * <p>
 * 8005：菜单结构错误
 * <p>
 * 9001: 权限KEY已存在
 * <p>
 * 9002：权限未找到
 * <p>
 * 9003：未知的权限状态
 * <p>
 * 9004：权限已分配给角色
 * <p>
 * 9005：权限不可用
 * <p>
 * <p>
 * </p>
 *
 * @author zijie.cao
 * @date 2019-03-14 10:50:01
 */
public interface ErrorCode {
    /**
     * Tech-Tier Error Code
     */
    int VALIDATION_CODE = 400000;
    int ILLEGAL_ARGUMENT = 400001;
    int ILLEGAL_STATE = 400002;

    int DATA_PROCESS_ERROR = 400300;
    int COMMUNICATION_ERROR = 400400;

    /**
     * Company Business Error Code
     */
    int COMPANY_NAME_EXITS = 1001;
    int COMPANY_NOT_FOUND = 1002;
    int UNKNOWN_COMPANY_STATUS = 1003;
    int COMPANY_INVALID = 1004;

    /**
     * Venue Business Error Code
     */
    int Venue_Name_Exists = 2001;
    int Venue_Not_Found = 2002;
    int Unknown_Venue_Status = 2003;
    int Venue_Invalid = 2004;

    /**
     * role Business Error Code
     */

    int Role_Name_Exists = 3001;
    int Role_Not_Found = 3002;
    int UNKNOWN_ROLE_STATUS = 3003;
    int ROLE_HAS_RELATED_ACCOUNT = 3004;

    /**
     * Account
     */
    int Account_Name_Exists = 4001;
    int Account_Not_Found = 4002;
    int UNKNOWN_Account_STATUS = 4003;

    /**
     * 专业版 获取passportId失败 错误码
     */
    int Professional_PassportUser_Not_Found = 10001;
    /**
     * 专业版 获取ePassportToken失败 错误码
     */
    int Professional_EPassportUser_Not_Found = 10002;

    /**
     * 专业版 用户绑定失败 错误码
     */
    int Professional_BindUser_Error = 10003;

    /**
     * 专业版账号重复绑定错误码
     */
    int Professional_RepeatBind_Error = 10004;

    /**
     * 商家账号重复绑定错误码
     */
    int Merchant_RepeatBind_Error = 10005;

    /**
     * 公司未同步到猫眼通错误码
     */
    int Merchant_COMPANY_NOSYNC = 10006;
}
