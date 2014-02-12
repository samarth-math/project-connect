import java.io.*;
//import java.net.*;
//import java.util.Enumeration;
 
public class QuoteServer {
    public static void main(String[] args) throws IOException 
    {
        new QuoteServerThread().start();
    }
}