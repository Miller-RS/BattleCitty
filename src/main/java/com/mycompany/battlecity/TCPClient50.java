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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;


public class TCPClient50 {

    private String servermsj;
    public  String SERVERIP;
    public static final int SERVERPORT = 4444;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;
    public  String[][] matrixUpdate;//matrix a actualizarse
    String[][] campo = new String[13][29];

    public PrintWriter out;
    public BufferedReader in;

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
                /*InputStream inputStream = socket.getInputStream();
                //DataInputStream dataInputStream = new DataInputStream(inputStream);
                //int matrixLength = dataInputStream.readInt();
                //System.out.println(matrixLength);
                byte[] bytes = new byte[2086];//size del campo de batalla
                
                int count = inputStream.read(bytes);

                ByteArrayInputStream bais = new ByteArrayInputStream(bytes, 0, count);
                ObjectInputStream ois = new ObjectInputStream(bais);
                String[][] campo = (String[][]) ois.readObject();
                ///////////////////////////////*/
                
                 for (int i = 0; i < campo.length; i++) {
                    for (int j = 0; j < campo[i].length; j++) {
                        campo[i][j] = in.readLine();
                    }
                }
                
                window ventana = new window(campo, this);
                ventana.setVisible(true);
                
                while(mRun){
                    for (int i = 0; i < 13; i++) {
                        for (int j = 0; j < 29; j++) {
                            campo[i][j] = in.readLine();
                        }
                    }
                    System.out.println("Recivio el campo actualizado");
                    for (int i = 0; i < 13; i++) {
                        for (int j = 0; j < 29; j++) {
                            System.out.print(campo[i][j] + " ");
                        }
                        System.out.println();
                    }
                }
                
                
                
                /*if(ventana.buttonClicked.get()){
                    System.out.println("Entre para enviar ");
                    matrixUpdate = ventana.mapa;
                    
                    //pasar la matrix actualizada a bits
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(matrixUpdate);
                    byte[] bytes2 = baos.toByteArray();
                    OutputStream os = socket.getOutputStream();
                    os.write(bytes2);
                    
                }*/
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
    public void setMatrixUpdate(String[][] matrix){
        matrixUpdate = matrix;
    }
}