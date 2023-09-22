package br.org.eldorado.cst.collector.constants;

public class Constants {
    public static final String TAG = "CST";
    public static final int APP_VERSION = 0;
    // If you change the database schema, you must increment the database version.
    public static final int DB_VERSION = (APP_VERSION * 10) + 1;
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
        public static final String COLLECT = "service.handler.msg.collect";
    }

    public static class SYNCED_DATA {
        public static final int YES = 1;
        public static final int NO = 0;
        public static final int ON_GOING = 2;
    }

    public static class PREFERENCES {
        public static final String PROTOCOL_KEY = "network_protocol";
        public static final String ADDRESS_KEY = "network_address";
        public static final String PORT_KEY = "network_port";
        public static final Boolean PROTOCOL_VALUE = false;
        public static final String ADDRESS_VALUE = "vm.hiaac.ic.unicamp.br";
        public static final Integer PORT_VALUE = 8089;
    }
}

