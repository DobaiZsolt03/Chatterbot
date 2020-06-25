package com.example.chatterbot;

import android.os.AsyncTask;

import com.example.chatterbot.Chatrooms.ArtsActivity;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Client {
    private DatagramSocket socket;
    private InetAddress address;
    private int port;

    private boolean running;
    private String name;



    public Client(String address, int port) {
        try {
            this.address = InetAddress.getByName(address);
            this.port = port;


            socket = new DatagramSocket();

            running = true;
            new a_listen().execute();
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void send (String message){
        try {

            if(!message.startsWith("\\")) {
                message = HomeActivity.name+": "+message;
            }
            message+= "\\e";
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            socket.send(packet);
            System.out.println("Sent message to "+address.getHostAddress()+":"+port);

        }catch(Exception e) {

            e.printStackTrace();
        }
    }

    public class Send implements Runnable{

        @Override
        public void run() {
            send("\\con:"+HomeActivity.name);
        }
    }

    public void ThreadSend(){
        Thread thread = new Thread(new Send());
        thread.start();
    }

    public class Disconnect implements Runnable{

        @Override
        public void run() {
            send("\\dis:"+HomeActivity.name);
            try {
                socket.setSoTimeout(1000);
            } catch (SocketException e) {
                e.printStackTrace();
            }
            socket.disconnect();

        }
    }

    public void ThreadDisconnect(){
        Thread thread = new Thread(new Disconnect());
        thread.start();
    }

    public class SendMessage_arts implements Runnable{

        @Override
        public void run() {
            if(!ArtsActivity.editText.getText().toString().equals("")){
                send(ArtsActivity.editText.getText().toString());
                ArtsActivity.editText.setText("");
            }
        }
    }

    public void ThreadSendMessage_arts(){
        Thread thread = new Thread(new SendMessage_arts());
        thread.start();
    }



    public class a_listen extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Thread listenThread = new Thread("ChatProgram Listener") {
                public void run() {
                    try {
                        while(running) {

                            byte[] data = new byte[1024];
                            DatagramPacket packet = new DatagramPacket(data, data.length);
                            socket.receive(packet);

                            String message = new String(data);
                            message = message.substring(0, message.indexOf("\\e"));

                            //we're managing messages with that \\e->we're checking the end of a message with
                            //that \\e identifier!

                            if(!isCommand(message, packet)) {
                                //send message
                                ArtsActivity.printToConsole(message);


                            }

                        }

                    }catch(Exception e) {
                        e.printStackTrace();
                    }

                }

            }; listenThread.start();
            return null;
        }
    }



    public static boolean isCommand(String message, DatagramPacket packet) {

        if(message.startsWith("\\con:")){
            return true;
        }

        if(message.startsWith("\\dis:")){
            return true;
        }

        return false;
    }

}
