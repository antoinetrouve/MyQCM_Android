package fr.iia.cdsmat.myqcm.configuration;

/**
 * Created by Antoine Trouvé on 14/05/2016.
 * antoinetrouve.france@gmail.com
 */
public class MyQCMConstants {

    public final static String CONST_IPSERVER = "http://192.168.0.23";
    public final static String CONST_URL_BASE = "/qcm/web/app_dev.php/api";
    public final static String CONST_URL_AUTH = "/userauths";
    public final static String CONST_URL_USERINFO = "/userinformations";
    public final static String CONST_URL_USERCATEGORIES = "/categoriesusers";
    public final static String CONST_URL_GETUSERPROFIL = "/profils";

    //Format flow
    public static final String CONST_FLOW_FORMAT = "json";

    //Timeout AsyncHTTPClient
    public static final int CONST_CONNECT_TIMEOUT = 60000;
    public static final int CONST_SET_TIMEOUT = 600000;

    public final static String CONST_VALUE_LOGIN = "login";
    public final static String CONST_VALUE_PWD = "password";

    public static final int CONST_VERSION = 1;
}
