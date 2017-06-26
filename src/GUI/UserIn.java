package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import controller.QueryGenerator;
import model.DBData;
import model.StaticData;
import model.UserInterest;
import model.UserType;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.io.BufferedWriter;
import java.io.FileWriter;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class UserIn {

	private JFrame frame;
	private JTextField textField;

	private static void loadPrereq() throws Exception {
		DBData.loadAllDBData();
		StaticData.prepareAllStaticData();
		UserInterest.prepareAllUserInterests();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserIn window = new UserIn();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserIn() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame = new JFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 500);

		frame.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Click me");
		btnNewButton.setBounds(170, 145, 89, 23);
		frame.getContentPane().add(btnNewButton);

		JLabel lblNewLabel = new JLabel("Interest");
		lblNewLabel.setBounds(61, 106, 79, 14);
		frame.getContentPane().add(lblNewLabel);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(
				new String[] { "lake", "river", "mountain", "gdp", "state", "city", "road", "border" }));
		comboBox.setBounds(170, 98, 79, 30);
		frame.getContentPane().add(comboBox);

		textField = new JTextField();
		textField.setBounds(170, 55, 271, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblQuestion = new JLabel("Question");
		lblQuestion.setBounds(61, 58, 79, 14);
		frame.getContentPane().add(lblQuestion);
		
	/*	JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"How many rivers are in America?", "How many mountains are in America", "How many cities are in America", "How many states are in America", "How many roads are in America", "", "what are the richest states", "what are the largest states", "what are the richest cities", "what are the largest cities", "", "what is the richest state", "what is the largest mountain", "what is the largest mountain in Colorado", "what is the largest lake in Minnesota", "what is the largest state in USA", "", "", "which roads pass through Tennessee", "which states does Road 95 pass through ", "which states have cities named Mobile", "which rivers pass through Arkansas", "Give me the states that border with Utah", "", "tell me the cities of Kansas", "tell me the rivers of Arkansas", "tell me the lakes of Kansas", "tell me the mountains of Missouri", "tell me the length of Mississippi river", "what is the population of Kansas", "what is the area of Wichita", ""}));
		comboBox_1.setMaximumRowCount(20);
		comboBox_1.setBounds(170, 24, 270, 20);
		frame.getContentPane().add(comboBox_1);
		
		JLabel label = new JLabel("Question");
		label.setBounds(61, 27, 79, 14);
		frame.getContentPane().add(label);
		*/
	/*	JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"tourist", "businessman"}));
		comboBox_2.setBounds(170, 55, 130, 20);
		frame.getContentPane().add(comboBox_2);
		
		JLabel lblProfession = new JLabel("Profession");
		lblProfession.setBounds(61, 58, 79, 14);
		frame.getContentPane().add(lblProfession);
*/
		JTextPane answerPane = new JTextPane();
		answerPane.setBounds(61, 200, 450, 222);
		JScrollPane jsp = new JScrollPane(answerPane);
		jsp.setBounds(61, 200, 450, 222);
		frame.getContentPane().add(jsp);
		

		
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {


			    //String Question = (String) comboBox_1.getSelectedItem();
		    	Object Interest = comboBox.getSelectedItem();
		    	//String Question = (String) comboBox.getSelectedItem();
		    	
		    	
		    	
		    	String Question = (String) textField.getText();

				String header = "@relation user_preference\n@attribute 'interest' string\n@attribute 'class' {businessman,tourist}\n@data\n";
				String data = header + Interest + "," + "?";
				BufferedWriter writer = null;
				try {
					writer = new BufferedWriter(new FileWriter("C:/Users/shuvro/Desktop/ThesisData1/test.arff"));
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				try {
					writer.write(data.toString());
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				try {
					writer.flush();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				try {
					writer.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}

				try {
			     	String userType = ClassifierModel.getUserInterest();
			     	System.out.println("user type :"+userType);
			//		String userType = (String) comboBox_2.getSelectedItem();
					loadPrereq();
					String answer = "";
					
					try{

					if (userType.equals("tourist")) {
						answer = QueryGenerator.getAnswerForQuestion(Question, UserType.TOURIST); // TOURIST
																									// //
																									// BUSINESSMAN//
																									// NONE
					} else if (userType.equals("businessman")) {
						answer = QueryGenerator.getAnswerForQuestion(Question, UserType.BUSINESSMAN); // TOURIST
																										// //
																										// BUSINESSMAN//
																										// NONE
					} else {
						System.err.println("Unknown User Type.");
						System.exit(1);
					}
					
					
					answerPane.setText(answer);
					}
					catch(Exception e){
						answerPane.setText("THIS QUESTION IS NOT IN OUR DATABASE!!!");
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		}

	}


