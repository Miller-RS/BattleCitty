/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.battlecity;




import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.io.*;



public class TCPServerThread50 extends Thread{
    
    private Socket client;
    private TCPServer50 tcpserver;
    private int clientID;                 
    private boolean running = false;
    public PrintWriter mOut;
    public PrintWriter mOut2;
    public BufferedReader in;
    private TCPServer50.OnMessageReceived messageListener = null;
    private String message;
    TCPServerThread50[] cli_amigos;
    public String[][] campo = {{"-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-"},//28
                                    {"|"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ","|"},
                                    {"|"," "," ","#"," "," ","#"," "," ","#"," "," "," "," "," "," ","#"," "," "," ","#","#"," "," ","#","#"," ","#","|"},
                                    {"|"," "," ","#"," "," ","#"," "," ","#","#"," "," "," "," "," ","#"," "," "," ","#","#"," "," "," "," "," "," ","|"},
                                    {"|"," "," ","#"," "," ","#"," "," ","#"," ","#"," "," "," "," ","#"," "," "," ","#","#"," "," "," "," "," "," ","|"},
                                    {"|"," "," ","#"," "," ","#"," "," ","#"," "," ","#"," "," "," ","#"," "," "," ","#","#"," "," "," "," "," "," ","|"},
                                    {"|"," "," ","#"," "," ","#"," "," ","#"," "," "," ","#"," "," ","#"," "," "," ","#","#"," "," ","#"," "," "," ","|"},
                                    {"|"," "," ","#"," "," ","#"," "," ","#"," "," "," "," ","#"," ","#"," "," "," ","#","#"," "," "," ","#"," "," ","|"},
                                    {"|"," "," ","#"," "," ","#"," "," ","#"," "," "," "," "," ","#","#"," "," "," ","#","#"," "," "," "," ","#"," ","|"},
                                    {"|"," "," ","#","#","#","#"," "," ","#"," "," "," "," "," "," ","#"," "," "," ","#","#"," "," "," "," "," ","#","|"},
                                    {"|"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ","|"},
                                    {"|"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ","|"},
                                    {"-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-"}};//13 filas
                
    
    public TCPServerThread50(Socket client_, TCPServer50 tcpserver_, int clientID_,TCPServerThread50[] cli_ami_) {
        this.client = client_;
        this.tcpserver = tcpserver_;
        this.clientID = clientID_;
        this.cli_amigos = cli_ami_;
    }
    
     public void trabajen(int cli){      
         mOut.println("TRABAJAMOS ["+cli+"]...");
    }
    
    public void run() {
        running = true;
        try {
            try {               
                boolean soycontador = false;                
                mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
                System.out.println("TCP Server"+ "C: Sent.");
                messageListener = tcpserver.getMessageListener();
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                //sendMessage("Megaman 8x");
                
                //enviar el campo de batalla a los clientes
                
                //transformamos el campo a bits para enviarlo
               /* ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(campo);
                byte[] bytes = baos.toByteArray();
                OutputStream os = client.getOutputStream();
                os.write(bytes);
                System.out.println("size bytes: "+bytes.length);
                System.out.println("campo size:"+ campo.length); */
               ////////////////////////////////////////////////////////
               
                for(int i = 0; i < campo.length; i++) {
                    for(int j = 0; j < campo[i].length; j++) {
                    sendMessage(campo[i][j]);
                    }
                }
               
               
               /* while (running) {
                   // message = in.readLine();
                    System.out.println("entra 1");
                    //recivimos el campo de batalla actualizado
                    InputStream inputStream = client.getInputStream();
                   // DataInputStream dataInputStream = new DataInputStream(inputStream);
                    //int matrixLength = dataInputStream.readInt();
                    System.out.println("entra 2");
                    byte[] bytes2 = new byte[2086];//size del campo de batalla
                     System.out.println("entra 3");
                    int count = inputStream.read(bytes2);
                     System.out.println("entra 4");
                    ByteArrayInputStream bais = new ByteArrayInputStream(bytes2, 0, count);
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    String[][] campoUpdate = (String[][]) ois.readObject();

                    campo = campoUpdate;
                    
                    System.out.println(campo[1][1]);
                   /* if (message != null && messageListener != null) {
                        messageListener.messageReceived(message);
                    }*/
                    

                  //  message = null;
               //}
               System.out.print(in.ready());
                while(running){
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
                    System.out.println(campo[4][3]);
                    System.out.println(tcpserver.nrcli);
                    for(TCPServerThread50 t : tcpserver.sendclis){
                        System.out.println(t.clientID);
                        if(t != tcpserver.sendclis[tcpserver.nrcli]){
                            mOut2 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(t.client.getOutputStream())), true);
                            for(int i = 0; i < 13; i++) {
                                for(int j = 0; j < 29; j++) {
                                    mOut2.println(campo[i][j]);
                                }
                            }
                        }
                   
                    }
                }
                
               
                
               
                
                System.out.println("RESPONSE FROM CLIENT"+ "S: Received Message: '" + message + "'");
            } catch (Exception e) {
                System.out.println("TCP Server"+ "S: Error"+ e);
            } finally {
                client.close();
            }

        } catch (Exception e) {
            System.out.println("TCP Server"+ "C: Error"+ e);
        }
    }
    
    public void stopClient(){
        running = false;
    }
    
    public void sendMessage(String message){//funcion de trabajo
        if (mOut != null && !mOut.checkError()) {
            mOut.println( message);
            mOut.flush();
        }
    }
    
}
