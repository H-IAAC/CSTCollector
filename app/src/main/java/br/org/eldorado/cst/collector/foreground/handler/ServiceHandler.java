package br.org.eldorado.cst.collector.foreground.handler;

import android.content.Context;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Message;


// Handler that receives messages from the thread
public final class ServiceHandler {
    private final Context context;
    private static MessageHandler messageHandler;
    public ServiceHandler(Context context, HandlerThread thread) {
        this.context = context;
        messageHandler = new MessageHandler(thread.getLooper());
    }

    public static Message obtainMessage() {
        return messageHandler.obtainMessage();
    }

    public static boolean sendMessage(Message msg) {
        return messageHandler.sendMessage(msg);
    }

    public static void sendMessage(Bundle bundle) {
        Message msg = ServiceHandler.obtainMessage();
        msg.setData(bundle);
        ServiceHandler.sendMessage(msg);
    }
}