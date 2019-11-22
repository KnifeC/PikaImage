package moe.keshane.PikaImage.Util;

import moe.keshane.PikaImage.Common.KeySet;
import moe.keshane.PikaImage.Web.Result.Message;
import org.springframework.ui.ModelMap;

public class MessageUtils {
    public static void sendMessage(ModelMap modelMap, Message message) {
        modelMap.put(KeySet.MESSAGE,message);
    }


    public static void sendDangerMessage(ModelMap modelMap, String message) {
        sendMessage(modelMap, new Message(message,KeySet.DANGER));
    }

    public static void sendWarningMessage(ModelMap modelMap, String message) {
        sendMessage(modelMap, new Message(message,KeySet.WARNING));
    }

    public static void sendSuccessMessage(ModelMap modelMap, String message) {
        sendMessage(modelMap, new Message(message,KeySet.SUCCESS));
    }
}
