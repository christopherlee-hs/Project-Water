package rank;

import javax.swing.*;
import java.awt.*;
import javax.swing.filechooser.*;
import org.apache.poi.openxml4j.exceptions.*;
import java.awt.event.*;
import java.io.IOException;

@SuppressWarnings("serial")
public class Panel extends JPanel {
	String input;
	String console;
	static JFileChooser jfc;
	public Panel() {
		input="No File Selected";
		console="";

		this.setSize(360, 420);
		this.setPreferredSize(new Dimension(360, 420));
		this.setVisible(true);
		this.setLayout(null);

		JButton inputSel = new JButton("Select Input File");

		JButton run = new JButton("Run Program");

		jfc = new JFileChooser();
		jfc.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));

		inputSel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int val=jfc.showOpenDialog(new JPanel());
				if(val == 0){
					input = jfc.getSelectedFile().getName();
				}
				repaint();
			}
		});
		inputSel.setBounds(130, 200, 100, 25);
		inputSel.setMargin(new Insets(0, 0, 0, 0));
		this.add(inputSel);


		run.setBounds(80, 280, 200, 50);
		run.setMargin(new Insets(0, 0, 0, 0));
		this.add(run);
		
		
		
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Runnable r = new Runnable() {
					public void run() {
						console = "Running";
						repaint();
						try{
							new Tourney(jfc.getSelectedFile().getAbsolutePath());//TODO-Implement New Tourney Class
							console="Execution Successful";
						}
						catch(InvalidFormatException ex){
							console="Error: Invalid File";
						}
						catch(NotOfficeXmlFileException ex){
							console="Error: Invalid File";
						}
						catch(Exception ex){
							console="Error: Contact Sunjae";
							ex.printStackTrace(System.out);
						}
						repaint();
					}
				};
				
				Thread t = new Thread(r);
				t.start();
				
				/*
				try{
					new Tourney(jfc.getSelectedFile().getAbsolutePath());//TODO-Implement New Tourney Class
					console="Execution Successful";
				}
				catch(InvalidFormatException ex){
					console="Error: Invalid File";
				}
				catch(NotOfficeXmlFileException ex){
					console="Error: Invalid File";
				}
				catch(Exception ex){
					console="Error: Contact Sunjae";
					ex.printStackTrace(System.out);
				}
				repaint();
				*/
			}
		});
		

	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Font font = new Font("Times New Roman", Font.PLAIN, 36);
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics(font);
		g.drawString("Dodgeball Rankings", ((360 - fm.stringWidth("Dodgeball Rankings")) / 2), 50);
		
		Font font2=new Font("Courier", Font.PLAIN, 12);
		FontMetrics fm2=g.getFontMetrics(font2);
		String line1;
		int cutoff=230;
		if(fm2.stringWidth(input)>cutoff){
			line1="File: ..."+input.substring(findCutoff(input,cutoff,fm2));
		}
		else{
			line1="File: "+input;
		}

		g.setFont(font2);
		g.drawString(line1, ((360 - fm2.stringWidth(line1)) / 2), 180);

		Font font3=new Font("Times New Roman", Font.BOLD, 24);
		FontMetrics fm3=g.getFontMetrics(font3);
		g.setFont(font3);
		g.drawString("File Selection",((360 - fm3.stringWidth("File Selection")) / 2), 140);

		g.drawString(console, ((360 - fm3.stringWidth(console)) / 2), 380);
	}

	private int findCutoff(String s, int cutoff, FontMetrics fm){
		String temp=s;
		int i=0;
		while(fm.stringWidth(temp)>cutoff){
			temp=s.substring(i);
			i++;
		}
		return i;
	}
}