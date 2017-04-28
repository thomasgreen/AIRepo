import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class AILog extends JFrame {

	private JPanel contentPane;
	JTextArea textArea;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public AILog() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		textArea = new JTextArea();
		textArea.setRows(14);
		JScrollPane scrollPane = new JScrollPane(textArea);
		contentPane.add(scrollPane, BorderLayout.NORTH);
		setTitle("Ai Move History");
		
		
	}
	public void appendLog(String move){
		textArea.append(move + " \n");
	}

}
