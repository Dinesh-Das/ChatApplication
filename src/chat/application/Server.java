package chat.application;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.text.*;
import javax.swing.border.*;
import java.net.*;

/**
 *
 * @author dinesh
 */
public class Server  implements ActionListener {

    static JFrame frame = new JFrame();
    JTextField sendMessage;
    JPanel textArea;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout ;

    Server() {
        frame.setLayout(null);
        JPanel p = new JPanel();
        p.setBackground(new Color(7, 94, 84));
        p.setBounds(0, 0, 450, 70);
        p.setLayout(null);
        frame.add(p);

        ImageIcon backIcon = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image backImg = backIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon backImageIcon = new ImageIcon(backImg);
        JLabel back = new JLabel(backImageIcon);
        back.setBounds(5, 20, 25, 25);
        p.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                System.exit(0);
            }
        });

        ImageIcon profileIcon = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image profileImage = profileIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon profileImageIcon = new ImageIcon(profileImage);
        JLabel profile = new JLabel(profileImageIcon);
        profile.setBounds(40, 10, 50, 50);
        p.add(profile);

        ImageIcon videoIcon = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image videoImage = videoIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon videoImageIcon = new ImageIcon(videoImage);
        JLabel video = new JLabel(videoImageIcon);
        video.setBounds(300, 20, 30, 30);
        p.add(video);

        ImageIcon phoneIcon = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image phoneImg = phoneIcon.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon phoneImageIcon = new ImageIcon(phoneImg);
        JLabel phone = new JLabel(phoneImageIcon);
        phone.setBounds(360, 20, 35, 30);
        p.add(phone);

        ImageIcon moreIco = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image moreImg = moreIco.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon moreImgIco = new ImageIcon(moreImg);
        JLabel more = new JLabel(moreImgIco);
        more.setBounds(420, 20, 10, 25);
        p.add(more);

        JLabel name = new JLabel("Paradox");
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p.add(name);

        JLabel status = new JLabel("Active Now");
        status.setBounds(110, 35, 100, 18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        p.add(status);

        textArea = new JPanel();
        textArea.setBounds(5, 75, 440, 570);
        frame.add(textArea);

        sendMessage = new JTextField();
        sendMessage.setBounds(5, 655, 310, 40);
        sendMessage.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        frame.add(sendMessage);

        JButton send = new JButton("Send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        frame.add(send);

        frame.setSize(450, 700);
        frame.setLocation(200, 50);
        frame.setUndecorated(true);
        frame.getContentPane().setBackground(Color.WHITE);

        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try{
        String message = sendMessage.getText();
        JPanel p2 = formatLabel(message);

        textArea.setLayout(new BorderLayout());
        JPanel right = new JPanel(new BorderLayout());
        right.add(p2, BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));

        textArea.add(vertical, BorderLayout.PAGE_START);
        dout.writeUTF(message);
        sendMessage.setText("");
        frame.repaint();
        frame.invalidate();
        frame.validate();
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width:150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        

        return panel;
    }

    public static void main(String[] args) {
        new Server();

        try {
            ServerSocket skt = new ServerSocket(6001);
            while (true) {
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                 dout = new DataOutputStream(s.getOutputStream());
                while(true)
                {
                    String msg= din.readUTF();
                    JPanel panel = formatLabel(msg);
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    frame.validate();
                }
                    
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
