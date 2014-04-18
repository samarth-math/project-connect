package GuiElements;

import java.awt.Color;
import java.awt.Component;
import globalfunctions.Contact;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class AppWindowListCellRenderer extends ClickablePanel implements ListCellRenderer<Contact> {
 	private static final long serialVersionUID = 1L;
 	
 	
 	
 	
	@Override
	public Component getListCellRendererComponent(
			JList<? extends Contact> list, Contact person, int index,
			boolean isSelected, boolean cellHasFocus) {

		setUser(person);

        Color background;
        Color foreground;

        // check if this cell is selected
        if (isSelected) 
        {
            background = new Color(200, 250, 250);
            foreground = Color.BLUE;
            this.setSize(255, 60);
        }
        // unselected
        else 
        {
            background = Color.WHITE;
            foreground = Color.BLACK;
            this.setSize(251, 50);
        };

        setBackground(background);
        setForeground(foreground);

        //setImage();
        
        
        
        
        return this;
    }
}