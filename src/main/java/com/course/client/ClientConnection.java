package com.course.client;

import com.course.entity.User;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection {
    private static ClientConnection clientConnection;

    private Socket clientSocket;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;

    private String message;

    private User currentUser;

    public User getCurrentUser()
    {
        return currentUser;
    }

    public void setCurrentUser(User user)
    {
        this.currentUser = user;
    }

    public static ClientConnection getInstance()
    {
        if (clientConnection == null)
        {
            clientConnection = new ClientConnection("127.0.0.1", "9006");
        }

        return clientConnection;
    }

    private ClientConnection(String ipAddress, String port){
        try {
            clientSocket = new Socket(ipAddress, Integer.parseInt(port));
            inStream = new ObjectInputStream(clientSocket.getInputStream());
            outStream = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        try {
            outStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendObject(Object object){
        try {

            outStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetOutputStream() {
        try {
            this.outStream.reset();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public String readMessage() throws IOException {
        try {
            message = (String) inStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return message;
    }

    public Object readObject(){
        Object object = new Object();
        try {
            object = inStream.readObject();
        } catch (ClassNotFoundException | IOException e) {

            e.printStackTrace();
        }
        return object;
    }

    public void close() {
        try {
            clientSocket.close();
            //outStream.flush();
            inStream.close();
            outStream.close();
        } catch (EOFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
