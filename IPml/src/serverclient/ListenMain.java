package serverclient;
import java.io.*;
//import java.net.*;
//import java.util.Enumeration;
 
public class ListenMain {
    public static void main(String[] args) throws IOException 
    {
        new ListenThread().start();
    }
}