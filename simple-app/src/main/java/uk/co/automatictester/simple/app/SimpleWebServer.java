package uk.co.automatictester.simple.app;

import io.javalin.Javalin;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SimpleWebServer {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);
        app.get("/", ctx -> ctx.result(getHostname()));
    }

    private static String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "unknown host " + e.getMessage();
        }
    }
}
