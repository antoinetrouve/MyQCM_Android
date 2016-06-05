package fr.iia.cdsmat.myqcm.configuration;

/**
 * Created by Antoine Trouvé on 14/05/2016.
 * antoinetrouve.france@gmail.com
 */
public class MyQCMConstants {

    //region ENTITY : User
    public static final String CONST_USER_IDSERVER = "idServer";
    public static final String CONST_USER_USERNAME = "username";
    public static final String CONST_USER_EMAIL = "email";
    public static final String CONST_USER_LASTLOGIN = "last_login";
    public static final String CONST_USER_CREATEDAT = "createdAt";
    public static final String CONST_USER_UPDATEDAT = "updatedAt";
    public static final String CONST_USER_PASSWORD = "password";
    //endregion

    //region FLOW : URL Webservice
    public final static String CONST_IPSERVER = "http://192.168.0.23";
    public final static String CONST_URL_BASE = "/qcm/web/app_dev.php/api";
    public final static String CONST_URL_AUTH = "/userauths";
    public final static String CONST_URL_USERINFO = "/userinformations";
    public final static String CONST_URL_USERCATEGORIES = "/categoriesusers";
    public final static String CONST_URL_USERMCQS = "/mcqsusers";
    public final static String CONST_URL_GETUSERPROFIL = "/profils";
    //endregion

    //region FLOW : format
    public static final String CONST_FLOW_FORMAT = "json";
    //endregion

    //region FLOW : parameters
    public final static String CONST_VALUE_LOGIN = "login";
    public final static String CONST_VALUE_PWD = "password";
    public static final String CONST_VALUE_USERID = "userId";
    public static final String CONST_VALUE_CATEGORYID = "categoryId";
    //endregion

    //region  ASYNCHTTPCLIENT : timeout
    public static final int CONST_CONNECT_TIMEOUT = 60000;
    public static final int CONST_SET_TIMEOUT = 600000;
    //endregion

    //region MESSAGE
    public final static String CONST_MESS_UPDATEDB = "Mise à jours de votre profil";
    public final static String CONST_MESS_CREATEDB = "Bienvenue ";
    //endregion

    //region APPLICATION
    public static final int CONST_VERSION = 1;
    //endregion
}
