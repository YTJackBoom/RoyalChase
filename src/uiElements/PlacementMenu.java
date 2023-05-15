package uiElements;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.synth.SynthPopupMenuUI;

import basics.GameScreen;
import basics.GameWindow;
import basics.Status;
import gameObjects.Coordinate;
import gameObjects.GameObject;
import towers.*;

public class PlacementMenu {
	private Coordinate pos;
	private ImageIcon pMenuImg;
	private JLabel pMenuLabel;
	private Status status;
	private GameScreen sPanel;
	private ArrayList tEL;
	private int aPos;
	private int type;
	private Coordinate towerFoundPos;
	private GameObject sObject;

	public PlacementMenu(GameScreen superPanel,int type, File imgFile, Coordinate pos, ArrayList towerEntityList, int arrayPos, GameObject sObject) {
		//switch type: (änderung von größe und newFile()
		this.pos = pos;
		this.sPanel=superPanel;
		status=Status.OK;
		this.tEL = towerEntityList;
		this.aPos = arrayPos;
		this.type = type;
		this.towerFoundPos = sObject.getPos();
		this.sObject = sObject;
		
		try {
			pMenuImg = new ImageIcon(ImageIO.read(imgFile));
		} catch (IOException e) {
			System.out.println(e);
			System.out.print(" Error in PlacementMenu");
			status = Status.ERR;
		}
		if(status==Status.OK) {
			pMenuLabel = new JLabel(pMenuImg);
			//System.out.println("h");
		}
		
		pMenuLabel.setLayout(null);
		pMenuLabel.setBounds(this.pos.getX(),this.pos.getY(),35,35); //35 height,width
		sPanel.add(pMenuLabel);
		pMenuLabel.setVisible(false);
		pMenuLabel.addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent e) {
	       	if(pMenuLabel.isVisible()) {
	           System.out.println("Image clicked!");
	           generateTower();
	        }
	        }
	    });
		}
	public void setVisible(boolean visbility) {
		pMenuLabel.setVisible(visbility);
	}
	public void generateTower() {
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
			
		sObject.vanish();
		//System.out.println("hi");
	}
	public void vanish() {
	//	pMenuLabel.setVisible(false);
		sPanel.remove(pMenuLabel);
		pMenuLabel = null;
	}
		
		
}
