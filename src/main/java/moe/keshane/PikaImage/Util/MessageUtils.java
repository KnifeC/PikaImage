package moe.keshane.PikaImage.Util;

import moe.keshane.PikaImage.Common.MessageKey;
import moe.keshane.PikaImage.Web.Result.Message;
import org.springframework.ui.ModelMap;

public class MessageUtils {
    public static void sendMessage(ModelMap modelMap, Message message) {
        modelMap.put(MessageKey.MESSAGE,message);
    }


    public static void sendDangerMessage(ModelMap modelMap, String message) {
        sendMessage(modelMap, new Message(message, MessageKey.DANGER));
    }

    public static void sendWarningMessage(ModelMap modelMap, String message) {
        sendMessage(modelMap, new Message(message,MessageKey.WARNING));
    }

    public static void sendSuccessMessage(ModelMap modelMap, String message) {
        sendMessage(modelMap, new Message(message,MessageKey.SUCCESS));
    }
}
