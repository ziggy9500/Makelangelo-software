package com.marginallyclever.fbp;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class FBPComponent extends JPanel {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int NONE = 0;
	public static final int INBOUND = 1;
	public static final int OUTBOUND = 2;
	
	protected int connectionType=NONE;

	public FBPComponent(JComponent child,int t) {
		super();
		connectionType = t;
		//setBorder(BorderFactory.createLineBorder(Color.RED,1));
        setLayout(new GridBagLayout());
        setChild(child);
	}
	public FBPComponent(JComponent child) {
		this(child,NONE);
	}
	
	public FBPComponent() {
		this(new JLabel("null"),NONE);
	}
	
	public FBPComponent(int t) throws Exception {
		this(new JLabel("null"),t);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// draw connection point (if any)
		int x=-1, y=getHeight()/2;

		switch(connectionType) {
		case NONE: return;
		case OUTBOUND:  x=getWidth()-10;  break;
		default: break;
		}
		g.translate(x, y);
		g.drawPolygon(new int[] {0,10,0}, new int[] {-5,0,5}, 3);
		g.translate(-x, -y);
	}
	
	public int getConnectionType() {
		return connectionType;
	}

	// returns location of the connection point relative to the screen
	public Point getConnectionPoint() throws Exception {
		Point p = new Point();
		Rectangle r = getBounds();
		
		switch(connectionType) {
		case INBOUND :  p.setLocation(0      , r.height/2);  break;
		case OUTBOUND:  p.setLocation(r.width, r.height/2);  break;
		default:  throw new Exception("Invalid connection type.");
		}
		
		Point loc = getLocationOnScreen();
		p.x+=loc.x;
		p.y+=loc.y;
		
		return p;
	}
	
	public Component setChild(Component comp) {
		removeAll();

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx=1;
		gbc.gridx=0;
		gbc.gridy=0;
		add(comp,gbc);
		
		Dimension d1=comp.getPreferredSize();
		Insets in = getInsets();
		Dimension d2 = getPreferredSize();
		d2.width = d1.width+in.right+in.left+30;
		d2.height = d1.height +in.top+in.bottom;
    	setPreferredSize(d2);
    	setMinimumSize(d2);
    	setSize(d2);
		validate();
		return comp;
	}
}
