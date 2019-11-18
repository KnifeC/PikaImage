package moe.keshane.PikaImage.Util;

import moe.keshane.PikaImage.Common.KeySet;

import javax.servlet.http.HttpSession;

public class SessionUtils {
    public static void setSessionData(HttpSession session,String key,String value){
        session.setAttribute(key,value);
    }
    public static void clearSession(HttpSession session){
        session.invalidate();
    }

    public static Object getSessionData(HttpSession session,String key){
        return session.getAttribute(key);
    }

    public static String getSessionStringData(HttpSession session,String key){
        return session.getAttribute(key).toString();
    }

    public static String getUserIdFromSession(HttpSession session){
        return getSessionStringData(session, KeySet.USERID);
    }

    public static String getUserNameFromSession(HttpSession session){
        return getSessionStringData(session,KeySet.USERNAME);
    }

    public static String getUserTypeFromSession(HttpSession session){
        return getSessionStringData(session,KeySet.USERTYPE);
    }
}
