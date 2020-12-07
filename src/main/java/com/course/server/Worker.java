package com.course.server;

import com.course.server.controller.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

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
                        break;
                    }
                    case "DataLoaderController": {
                        DataLoaderController.performAction(action, sois, soos);
                        break;
                    }
                    case "DataChangerController":{
                        DataChangerController.performAction(action, sois, soos);
                        break;
                    }
                    case "InsertDataController": {
                        InsertDataController.performAction(action, sois, soos);
                        break;
                    }
                    case "DeleteDataController": {
                        DeleteDataController.performAction(action, sois, soos);
                    }
                }
            }
        }
        catch(SocketException eee)
        {
            System.out.println("Клиент отключился.");
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
