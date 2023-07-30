package chat.application;

import static chat.application.Server.dout;
import static chat.application.Server.formatLabel;
import static chat.application.Server.frame;
import static chat.application.Server.vertical;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author dinesh
 */
public class Client implements ActionListener {

    static JFrame f2 = new JFrame();
    JTextField sendMessage;
    static JPanel textArea;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;

    Client() {
        f2.setLayout(null);
        JPanel p = new JPanel();
        p.setBackground(new Color(7, 94, 84));
        p.setBounds(0, 0, 450, 70);
        p.setLayout(null);
        f2.add(p);

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

        ImageIcon profileIcon = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
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

        JLabel name = new JLabel("Strange");
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
        f2.add(textArea);

        sendMessage = new JTextField();
        sendMessage.setBounds(5, 655, 310, 40);
        sendMessage.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f2.add(sendMessage);

        JButton send = new JButton("Send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f2.add(send);

        f2.setSize(450, 700);
        f2.setLocation(800, 50);
        f2.setUndecorated(true);
        f2.getContentPane().setBackground(Color.WHITE);
        f2.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String message = sendMessage.getText();
            JPanel p2 = formatLabel(message);

            textArea.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            dout.writeUTF(message);
            textArea.add(vertical, BorderLayout.PAGE_START);
            sendMessage.setText("");
            f2.repaint();
            f2.invalidate();
            f2.validate();
        } catch (Exception e) {
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
        new Client();

        try {
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            while (true) {
                textArea.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);
                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                textArea.add(vertical, BorderLayout.PAGE_START);
                frame.validate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
