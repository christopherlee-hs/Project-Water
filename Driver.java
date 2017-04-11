import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Driver{
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(UnsupportedLookAndFeelException e){
			// handle exception
		}
		catch(ClassNotFoundException e){
			// handle exception
		}
		catch(InstantiationException e){
			// handle exception
		}
		catch(IllegalAccessException e){
			// handle exception
		}

		JFrame frame=new JFrame();
		Panel p=new Panel();
		frame.add(p);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
	}
}
