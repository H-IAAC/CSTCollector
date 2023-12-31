package br.org.eldorado.cst.collector.constants;

public class Constants {
    public static final String TAG = "CST";

    //  Notification channel id must be unique per package.
    public static final String NOTIFICATION_CHANNEL_ID = "br.org.eldorado.cst.channelId";
    // The identifier for the notification needs to be changeable
    public static final int NOTIFICATION_SERVICE_ID = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
    public static final String STOP_SERVICE_BROADCAST = "br.org.eldorado.cst.collector.STOP_SERVICE_BROADCAST";
    public static final String CUSTOM_BROADCAST = "br.org.eldorado.cst.collector.CUSTOM_BROADCAST";

    public static class HANDLER_MESSAGE {
        public static final String COMMAND = "service.handler.command";
    }
    public static class HANDLER_ACTION {
        public static final String START = "service.handler.msg.start";
        public static final String STOP = "service.handler.msg.stop";
    }
}

