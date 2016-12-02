import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.Socket;
import java.util.*;
import javax.swing.*;


public class whiteboardclient extends JFrame implements MouseListener{
	String serverHostname = new String ("127.0.0.1");
	Socket echoSocket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    
    public static void main(String args[]) throws IOException{
        whiteboardclient n = new whiteboardclient();
    }

    public whiteboardclient() throws IOException{
		System.out.println ("Attemping to connect to host " + serverHostname + " on port 23657.");

        try {
            echoSocket = new Socket(serverHostname, 23657);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: " + serverHostname);
            System.exit(1);
        }
		
		Button c = new Button("Big");
        c.setName("Big");
        c.setBounds(150, 200, 80, 30);
        c.addMouseListener(this);
        add(c);
        
        Button e = new Button("Small");
        e.setName("Small");
        e.setBounds(150, 240, 80, 30);
        e.addMouseListener(this);
        add(e);

        Button b = new Button("N");
        b.setName("North");
        b.setBounds(60, 20, 80, 30);
        b.addMouseListener(this);
        add(b);

        Button m = new Button("S");
        m.setName("South");
        m.setBounds(60, 100, 80, 30);
        m.addMouseListener(this);
        add(m);

        Button d = new Button("W");
        d.setName("West");
        d.setBounds(0, 60, 80, 30);
        d.addMouseListener(this);
        add(d);

        Button h = new Button("E");
        h.setName("East");
        h.setBounds(100, 60, 80, 30);
        h.addMouseListener(this);
        add(h);

        Button a = new Button("Up");
        a.setName("Up");
        a.setBounds(30, 200, 80, 30);
        a.addMouseListener(this);
        add(a);

        Button s = new Button("Down");
        s.setName("Down");
        s.setBounds(30, 240, 80, 30);
        s.addMouseListener(this);
        add(s);


        setSize(300, 300);
        setLayout(null);

        setVisible(true);
    }
    
    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
    
    public void mouseClicked(MouseEvent e){
        String n = e.getComponent().getName();
        try{
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		}
		catch (IOException f) {
            System.err.println("Couldn't resolve connection with server");
            System.exit(1);
        }
        
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		try{
				out.println(n);
				if (n.equals("Exit")){
					out.close();
					in.close();
					stdIn.close();
					echoSocket.close();
					System.exit(0);
				}
		}
		catch (IOException f) {
            System.err.println("Couldn't resolve connection with server");
            System.exit(1);
        }
	}
}
