package view.main.stock;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import model.Item;

/**
 * Window used to see the properties of an item.
 */
public class SeeItemPanel extends JFrame {

	/*===== ATTRIBUTES =====*/
	private JTextField tfName;
	private JTextField tfDescription;
	private JTextField tfPrice;

	/*===== BUILDER =====*/
	public SeeItemPanel(JTable table, Item item) {
		
		setTitle(item.getName());
		setMinimumSize(new Dimension(280, 270));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(true);

		JPanel panel = (JPanel)getContentPane();

		panel.setLayout(new FlowLayout());

		add(new JLabel("Nom"));

		tfName = new JTextField(item.getName());
		tfName.setPreferredSize(new Dimension(260, 30));
		tfName.setEditable(false);
		add(tfName);

		add(new JLabel("Description"));

		tfDescription = new JTextField(item.getDescription());
		tfDescription.setPreferredSize(new Dimension(260, 90));
		tfDescription.setEditable(false);
		add(tfDescription);
		
		add(new JLabel("Prix"));

		tfPrice = new JTextField(String.valueOf(item.getPrice()) + "€");
		tfPrice.setPreferredSize(new Dimension(260, 30));
		tfPrice.setEditable(false);
		add(tfPrice);

		setVisible(true);
	}
	
	/*===== METHODS =====*/
	public void exitWindow() {
		this.dispose();
	}
}
