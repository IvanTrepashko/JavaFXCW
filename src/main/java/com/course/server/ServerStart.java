package com.course.server;

public class ServerStart {
    public static final int PORT_WORK = 9006;

    public static void main(String[] args)
    {
        Server server = new Server(PORT_WORK);
        new Thread(server).start();
    }

}
