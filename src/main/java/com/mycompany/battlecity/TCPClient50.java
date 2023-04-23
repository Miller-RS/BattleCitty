/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.battlecity;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JFrame;
import com.mycompany.battlecity.window;

public class TCPClient50 {

    private String servermsj;
    public  String SERVERIP;
    public static final int SERVERPORT = 4444;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;

    PrintWriter out;
    BufferedReader in;

    public TCPClient50(String ip,OnMessageReceived listener) {
        SERVERIP = ip;
        mMessageListener = listener;
    }
    public void sendMessage(String message){
        if (out != null && !out.checkError()) {
            out.println(message);
            out.flush();
        }
    }
    public void stopClient(){
        mRun = false;
    }
    public void run() {
        mRun = true;
        try {
         
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
            System.out.println("TCP Client"+ "C: Conectando...");
            Socket socket = new Socket(serverAddr, SERVERPORT);
            try {
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                System.out.println("TCP Client"+ "C: Sent.");
                System.out.println("TCP Client"+ "C: Done.");
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
               
                //recivimos el campo de batalla
                InputStream inputStream = socket.getInputStream();
                byte[] bytes = new byte[523];//size del campo de batalla
                int count = inputStream.read(bytes);

                ByteArrayInputStream bais = new ByteArrayInputStream(bytes, 0, count);
                ObjectInputStream ois = new ObjectInputStream(bais);
                String[][] campo = (String[][]) ois.readObject();
                
                window ventana = new window(campo);
                ventana.setVisible(true);

                /*for(int i = 0; i < 9; i++) {
                    for(int j = 0; j < 8; j++) {
                           ventana.setMapa(campo[i][j] + " ");
                    }
                        ventana.setMapa("\n");
                }*/
                //ventana.jButton1.doClick();
                while (mRun) {
                    servermsj = in.readLine();
                    //ventana.setJTextArea(servermsj);
                    if (servermsj != null && mMessageListener != null) {
                        mMessageListener.messageReceived(servermsj);
                    }
                    servermsj = null;
                }
            } catch (Exception e) {
                System.out.println("TCP"+ "S: Error"+e);

            } finally {
                socket.close();
            }
        } catch (Exception e) {
            System.out.println("TCP"+ "C: Error"+ e);
        }
    }
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}