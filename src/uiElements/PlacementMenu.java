package uiElements;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import helpers.Coordinate;
import towers.*;

public class PlacementMenu extends JPanel {
	private Coordinate pos;
	private BufferedImage pMenuImg;
	private JLabel pMenuLabel;
	private JPanel sPanel;
	private ArrayList tEL;
	private int aPos;
	private int type;
	private Coordinate towerFoundPos;
	private Tower sObject;

	public PlacementMenu(JPanel superPanel,int type, File imgFile, Coordinate pos, ArrayList towerEntityList, int arrayPos, Tower sObject) {
		//switch type: (änderung von größe und newFile()
		this.pos = pos; 
		this.sPanel=superPanel;
		this.tEL = towerEntityList;
		this.aPos = arrayPos;
		this.type = type;
		this.towerFoundPos = sObject.getPos();
		this.sObject = sObject;
		
		try {
			pMenuImg = ImageIO.read(imgFile);
		} catch (IOException e) {
			System.out.println(e);
			System.out.print(" Error in PlacementMenu");
		}
		
		setLayout(null);
		setBounds(this.pos.getX(),this.pos.getY(),35,35); //35 height,width
		sPanel.add(this);
		setVisible(false);
		addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent e) {
	       	if(isVisible()) {
	           System.out.println("Image clicked!");
	           //generateTower();
	        }
	        }
	    });
		}
	/*public void setVisible(boolean visbility) {
		setVisible(visbility);
	}*/
	/*public void generateTower() {
		switch(type) {
		case  1:
			tEL.set(aPos, new ArrowTower(sPanel, towerFoundPos, type,tEL,aPos ));
			break;
		case 2: 
			tEL.set(aPos, new MageTower(sPanel, towerFoundPos, type,tEL,aPos ));
			break;
		case 3: 
			tEL.set(aPos, new RocketTower(sPanel, towerFoundPos, type,tEL,aPos ));
			break;
		case 4:
			tEL.set(aPos, new SnipTower(sPanel, towerFoundPos, type,tEL,aPos ));
			break;
		}
	}
	public void vanish() {
	//	pMenuLabel.setVisible(false);
		sPanel.remove(this);
		setVisible(false);
	}
		
	 @Override
	public void paintComponent(Graphics g) {
		 if(isVisible()) {
	        super.paintComponent(g);
	        g.drawImage(pMenuImg, pos.getX(), pos.getY(), null);
	    }
	 }
	*/
}
