package com.course.server;

import com.course.server.controller.AuthorizationController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Worker implements Runnable {

    protected Socket clientSocket = null;
    ObjectInputStream sois;
    ObjectOutputStream soos;

    public Worker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            soos = new ObjectOutputStream(clientSocket.getOutputStream());
            sois = new ObjectInputStream(clientSocket.getInputStream());

            while (true) {
                String controller = sois.readObject().toString();
                String action = sois.readObject().toString();

                switch (controller) {
                    case "AuthorizationController": {
                        AuthorizationController.performAction(action, sois, soos);
                    }
                    break;
                }
            }
        }
        catch(IOException ex)
            {
                ex.printStackTrace();
            }
        catch(ClassNotFoundException e)
            {
                e.printStackTrace();
            }
    }
}
