package com.kunbu.spring.bucks.utils;

import com.kunbu.spring.bucks.constant.other.PersonTypeEnum;

public class TokenUtil {

    private static final String TOKEN_SERVICE_TYPE_MANAGE           = "MANAGE";
    private static final String TOKEN_PERSON_TYPE_ADMIN             = PersonTypeEnum.ADMIN.name();
    /** MAN_ADMIN_20190809_K07JHU1Y32G84 */
    public static final String FORMAT_PATTERN_ADMIN                 = "%s_%s_%s_%s";

    private static final String TOKEN_SERVICE_TYPE_BUSINESS         = "BUSINESS";
    private static final String TOKEN_PLATFORM_TYPE_WEB             = "WEB";
    private static final String TOKEN_PLATFORM_TYPE_APP             = "APP";
    private static final String TOKEN_PERSON_TYPE_USER              = PersonTypeEnum.USER.name();
    /** BIZ_WEB_USER_2019090501_LN30DI7H8ESF */
    public static final String FORMAT_PATTERN_USER                  = "%s_%s_%s_%s_%s";

    private static final String SPLITTER = "_";
    private static final String WILDCARD = "*";

    /**
     * 业务系统中的web端用户token
     * BIZ_WEB_USER_20190905001_J0CXB87H97GH13
     *
     * TODO 之后如果需要可在原有基础上追加
     *
     * @param userId
     * @param sessionId
     * @author kunbu
     * @time 2019/9/5 15:51
     * @return
     **/
    public static String generateUserWebToken(String userId, String sessionId) {
        String BIZ_WEB_USER_TOKEN = String.format(
                FORMAT_PATTERN_USER,
                TOKEN_SERVICE_TYPE_BUSINESS,
                TOKEN_PLATFORM_TYPE_WEB,
                TOKEN_PERSON_TYPE_USER,
                userId,
                sessionId);
        return BIZ_WEB_USER_TOKEN;
    }

    /**
     * 业务系统中的app端用户token
     * BIZ_APP_USER_20190905001_J0CXB87H97GH13
     *
     * @param userId
     * @param sessionId
     * @author kunbu
     * @time 2019/9/5 15:51
     * @return
     **/
    public static String generateUserAppToken(String userId, String sessionId) {
        String BIZ_APP_USER_TOKEN = String.format(
                FORMAT_PATTERN_USER,
                TOKEN_SERVICE_TYPE_BUSINESS,
                TOKEN_PLATFORM_TYPE_APP,
                TOKEN_PERSON_TYPE_USER,
                userId,
                sessionId);
        return BIZ_APP_USER_TOKEN;
    }

    /**
     * 后台系统管理员token
     *
     * @param userId
     * @param sessionId
     * @author kunbu
     * @time 2019/9/5 18:10
     * @return
     **/
    public static String generateAdminManToken(String userId, String sessionId) {
        String MAN_ADMIN_TOKEN = String.format(
                FORMAT_PATTERN_ADMIN,
                TOKEN_SERVICE_TYPE_MANAGE,
                TOKEN_PERSON_TYPE_ADMIN,
                userId,
                sessionId);
        return MAN_ADMIN_TOKEN;
    }

    /**
     * 匹配业务系统中web端所有用户
     *
     * @author kunbu
     * @time 2019/9/5 16:08
     * @return
     **/
    public static String getWildCardBizWeb() {
        StringBuilder builder = new StringBuilder();
        builder.append(TOKEN_SERVICE_TYPE_BUSINESS)
                .append(SPLITTER)
                .append(TOKEN_PLATFORM_TYPE_WEB)
                .append(SPLITTER)
                .append(TOKEN_PERSON_TYPE_USER)
                .append(SPLITTER)
                .append(WILDCARD);
        return builder.toString();
    }

    /**
     * 匹配业务系统中app端所有用户
     *
     * @author kunbu
     * @time 2019/9/5 16:08
     * @return
     **/
    public static String getWildCardBizApp() {
        StringBuilder builder = new StringBuilder();
        builder.append(TOKEN_SERVICE_TYPE_BUSINESS)
                .append(SPLITTER)
                .append(TOKEN_PLATFORM_TYPE_APP)
                .append(SPLITTER)
                .append(TOKEN_PERSON_TYPE_USER)
                .append(SPLITTER)
                .append(WILDCARD);
        return builder.toString();
    }


    /**
     * 是否是admin
     *
     * @param token
     * @author kunbu
     * @time 2019/9/5 18:12
     * @return
     **/
    public static boolean checkAdmin(String token) {
        if (token != null && token.length() > 0) {
            if (token.indexOf(PersonTypeEnum.ADMIN.name()) > 0) {
                return true;
            }
        }
        return false;
    }

}
