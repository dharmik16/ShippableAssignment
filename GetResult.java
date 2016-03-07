import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import javax.swing.*;

/**
 * Class calling the necessary functions to output a table for open issue statistics 
 */ 

public class GetResult extends JFrame implements ActionListener
{
    JTextField txtdata;
    JButton getLink = new JButton("Get Open Issue Statistics");
    String data;
    
    public GetResult()
    {
        JPanel myPanel = new JPanel();
        add(myPanel);
        myPanel.setLayout(new GridLayout(4, 2));
        txtdata = new JTextField("Enter Your Link Here");
        myPanel.add(txtdata);
        myPanel.add(getLink);
        getLink.addActionListener(this);
        
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == getLink) {
            data = txtdata.getText(); //perform your operation
            GetOpenIssue openIssue = new GetOpenIssue();
    		openIssue.crawl(data);
    		try {
				openIssue.getDiff();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		openIssue.countIssues();
    		openIssue.printTable();
        }
    }

}