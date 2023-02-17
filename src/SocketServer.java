import java.io.*;
import java.net.*;

public class SocketServer {

    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 9876;

    public static int x;
    public static int y;
    public static String operation;




    public int parseRequest(String request) {
        if(request.strip().startsWith("change")) {
            String operand = request.strip().split(" ")[1];
            int value = Integer.parseInt(request.strip().split(" ")[3]);
            if(operand.equalsIgnoreCase("x")) {
                x = value;
            } else if (operand.equalsIgnoreCase("y")){
                y = value;
            }
        }
        else {
            String operands = request.strip().split(" ")[0];
            String op = request.strip().split(" ")[1];;
            x = Integer.parseInt(operands.strip().split(",")[0]);
            y = Integer.parseInt(operands.strip().split(",")[1]);
            operation = op;
        }

        if(operation.equalsIgnoreCase("add")){
            return x + y;
        }
        else if(operation.equalsIgnoreCase("subtract")) {
            return x - y;
        }
        else if(operation.equalsIgnoreCase("multiply")) {
            return x * y;
        }
        else if(operation.equalsIgnoreCase("divide")){
            return x / y;
        } else {
            return 0;
        }
    }


    public static void main(String args[]) throws IOException, ClassNotFoundException{
        //create the socket server object
        server = new ServerSocket(port);
        SocketServer ss = new SocketServer();
        //keep listens indefinitely until receives 'exit' call or program terminates

        while(true){
            Socket socket = server.accept();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String request = (String) ois.readObject();

            int val = ss.parseRequest(request);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(String.valueOf(val));

            ois.close();
            oos.close();
            socket.close();

            // Terminate on exit
            if(request.equalsIgnoreCase("exit")) break;
        }
        System.out.println("Shutting down Socket server");
        //close the ServerSocket object
        server.close();
    }

}