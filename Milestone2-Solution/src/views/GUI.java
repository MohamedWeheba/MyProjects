package views;
import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.JButton;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.characters.Direction;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

import javax.swing.*;



public class GUI extends JFrame implements ActionListener , MouseListener{
	
	
	private JPanel panel;
	private JButton[][] gridMap;
	
	private Game engine;
	
	private JLabel labelD;
	
	private ArrayList<JButton> buttons = new ArrayList<>();
	
	private JButton button1;

	private Hero myHero;
	
	private JButton buttonUp;
	private JButton buttonDown;
	private JButton buttonLeft;
	private JButton buttonRight;
	
	private JPanel errorPanel;
	private JButton okError;
	private JLabel errorMessage;
	private boolean errorFlag;
	
	private JPanel HeroesPanel;
	private JLabel heroInformation;
	private int constY = 230; 
	private ArrayList<JLabel> heroLabels = new ArrayList<>();
	
	private Image backgroundImage;
	
	private JButton cureButton;
	private JButton attackButton;
	private JButton specialButton;
	private JButton endTurnB;
	
	private ArrayList<JButton> mapButtons = new ArrayList<>();
	private ArrayList<JButton> heroButtons = new ArrayList<>();
	private ArrayList<JButton> zombieButtons = new ArrayList<>();
	
	private JPanel endPanel;
	private JLabel endMessage;
	
    public GUI(String imagePath) throws IOException   {
    
    	engine.loadHeroes("Heroes.csv");
    	//System.out.print(engine.availableHeroes);
    	this.setLayout(null);
        backgroundImage = new ImageIcon(imagePath).getImage();
        
        initialize();
        this.setLayout(null);

   
		
		for(int i = 0; i < engine.availableHeroes.size(); i++) {
			button1 = new JButton(engine.availableHeroes.get(i).getName());
			
			if(engine.availableHeroes.get(i).getName().equals("Joel Miller"))
			{
				 button1.setBounds(510,435,140,40);
			}
			else if(engine.availableHeroes.get(i).getName().equals("David"))
			{
				button1.setBounds(745,435,100,40);
			}
			else if(engine.availableHeroes.get(i).getName().equals("Bill"))
			{
				 button1.setBounds(1010,435,100,40);
			}
			else if(engine.availableHeroes.get(i).getName().equals("Tess"))
			{
				   button1.setBounds(1250,430,100,40);
			}
			else if(engine.availableHeroes.get(i).getName().equals("Ellie Williams"))
			{
				   button1.setBounds(475,820,140,40);
			}
			else if(engine.availableHeroes.get(i).getName().equals("Tommy Miller"))
			{
				  button1.setBounds(735,820,150,40);
			}
			else if(engine.availableHeroes.get(i).getName().equals("Henry Burell"))
			{
				  button1.setBounds(1015,820,140,40);
			}
			else if(engine.availableHeroes.get(i).getName().equals("Riley Abel"))
			{
				 button1.setBounds(1250,820,140,40);
			}
			
			button1.setFocusable(false);
			button1.setFont(new Font("Stencil",Font.PLAIN, 20));
			button1.setToolTipText(heroInfo(i));
			button1.addActionListener(this);
			buttons.add(button1);
			this.add(button1);	
		}
		panel = new JPanel();
		panel.setBackground(Color.darkGray);
		panel.setBounds(580, 0, 1330, 1000);
		panel.setLayout(new GridLayout(15, 15));
		gridMap = new JButton[15][15];
		for(int i=0;i<gridMap.length;i++){
			for(int j=0;j<gridMap.length;j++){
				gridMap[i][j] = new JButton();
				panel.add(gridMap[i][j]);
			}
		}
		
		errorPanel = new JPanel();
		errorPanel.setBackground(Color.LIGHT_GRAY);
		errorPanel.setBounds(265, 45, 280, 120);
		okError = new JButton("Ok");
		okError.addActionListener(this);
		errorMessage = new JLabel("insert error message");
		errorPanel.add(okError);
		errorPanel.add(errorMessage);
		//error pop-up
		
		endPanel = new JPanel();
		endPanel.setBackground(Color.RED);
		endPanel.setBounds(265, 45, 280, 120);
		endMessage = new JLabel("text here");
		endPanel.add(endMessage);
		
		buttonUp = new JButton("Up");
		buttonUp.setFont(new Font("Arial",Font.PLAIN,15));
		buttonUp.setBounds(400, 830, 75, 75);
		buttonUp.addActionListener(this);
	
		buttonDown = new JButton("Down");
		buttonDown.setFont(new Font("Arial",Font.PLAIN,15));
		buttonDown.setBounds(400, 905, 75, 75);
		buttonDown.addActionListener(this);
		
		buttonLeft = new JButton("Left");
		buttonLeft.setFont(new Font("Arial",Font.PLAIN,15));
		buttonLeft.setBounds(325, 905, 75, 75);
		buttonLeft.addActionListener(this);
		
		buttonRight = new JButton("Right");
		buttonRight.setFont(new Font("Arial",Font.PLAIN,15));
		buttonRight.setBounds(475, 905, 75, 75);
		buttonRight.addActionListener(this);
		
		HeroesPanel = new JPanel();
		HeroesPanel.setBackground(Color.LIGHT_GRAY);
		HeroesPanel.setBounds(0, 200, 580, 600);
		HeroesPanel.setLayout(null);
		
		/*for(int i=0;i<Game.availableHeroes.size();i++){
			heroInformation = new JLabel(getHeroInfo(Game.availableHeroes.get(i)));
			heroInformation.setBounds(35, constY, 520, 30);
			heroInformation.setVisible(true);
			HeroesPanel.add(heroInformation);
			heroLabels.add(heroInformation);
			
			constY = constY + 40;
		}*/
        
       
      // button1.addMouseListener(this);
        
        
         
      
        cureButton = new JButton("Cure");
        cureButton.setBounds(250, 905, 75, 75);
        cureButton.addActionListener(this);
        
        attackButton = new JButton("Attack");
        attackButton.setBounds(175, 905, 75, 75);
        attackButton.addActionListener(this);
        
        specialButton = new JButton("Special");
        specialButton.setBounds(100, 905, 75, 75);
        specialButton.addActionListener(this);
        
        heroButtons = new ArrayList<>();
        zombieButtons = new ArrayList<>();
        this.setVisible(true);
        
        endTurnB = new JButton("End Turn");
        endTurnB.setBounds(100, 830, 100, 75);
        endTurnB.addActionListener(this);
    }
    
    public String heroInfo(int Index)
    {
    	Hero h = engine.availableHeroes.get(Index);
    	
    	String heroDescription = "Name: "+h.getName() + "   Attack: " +h.getAttackDmg() +"   Actions: "+h.getActionsAvailable() +"   Total Hp: "+h.getCurrentHp()+" Class: "+h.getHeroClass();
    	return heroDescription;
    	
    	
    }
    public static String getHeroInfo(Hero h){
    	String description ="Name: "+h.getName() + "   Attack: " +h.getAttackDmg() +"   Actions: "+h.getActionsAvailable() +"   Total Hp: "+h.getCurrentHp()+"   Supplies: "+h.getSupplyInventory().size()+"   Vaccines: "+h.getVaccineInventory().size()+" Class: "+h.getHeroClass();
    	return description;
    }
        
    

    private void initialize() {
        setTitle("GAME MENU");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(500, 500));
        setContentPane(new ImagePanel());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class ImagePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
           
        }
    }
    
    
    


public static void main(String[] args) throws IOException  {
    String imagePath = "Start Menu.png";
    new GUI(imagePath);
	//new GUI(null);
}






@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
	
}

@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void actionPerformed(ActionEvent e) {
	if(buttons.contains(e.getSource())) {
		myHero = engine.availableHeroes.get(buttons.indexOf(e.getSource()));

	    for(int i=0;i<buttons.size();i++)
	    {
	    	buttons.get(i).setVisible(false);
	    }
		this.getContentPane().add(panel);
		this.getContentPane().add(buttonUp);
		this.getContentPane().add(buttonDown);
		this.getContentPane().add(buttonLeft);
		this.getContentPane().add(buttonRight);
		this.getContentPane().add(cureButton);
		this.getContentPane().add(attackButton);
		this.getContentPane().add(specialButton);
		this.getContentPane().add(HeroesPanel);
		this.getContentPane().add(endTurnB);
		this.setVisible(true);
		engine.startGame(myHero);
		for(int i=0;i<gridMap.length;i++){
			for(int j=0;j<gridMap.length;j++){
				mapButtons.add(gridMap[i][j]);
				gridMap[i][j].addActionListener(this);
			}
		}
		this.updateInfo();
		this.repaint();
		this.revalidate();
		//gridMap[myHero.getLocation().x][myHero.getLocation().y].setBackground(Color.BLUE);
		for(int i=0;i<Game.map.length;i++){
			for(int j=0;j<Game.map.length;j++){
				if(Game.map[i][j].isVisible()){
					if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Supply){
						gridMap[i][j].setBackground(Color.YELLOW);
						gridMap[i][j].setText("");
					}
					if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Vaccine){
						gridMap[i][j].setBackground(Color.GREEN);
						gridMap[i][j].setText("");
					}
					if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Hero){
						gridMap[i][j].setBackground(Color.BLUE);
						gridMap[i][j].setText(((Hero)((CharacterCell)Game.map[i][j]).getCharacter()).getName());
						
					}
					if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie){
						gridMap[i][j].setBackground(Color.RED);
						gridMap[i][j].setText("");
					}
					if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter()==null){
						gridMap[i][j].setBackground(Color.WHITE);
						gridMap[i][j].setText("");
					}
					if(Game.map[i][j] instanceof TrapCell){
						gridMap[i][j].setBackground(Color.BLACK);
						gridMap[i][j].setText("");
					}
					
				}
				else{
					gridMap[i][j].setBackground(Color.DARK_GRAY);
					gridMap[i][j].setText("");
				}
			}
		}
	}
	if(e.getSource()==buttonUp && errorFlag==false){
		try {
			myHero.move(Direction.DOWN);
		} catch (MovementException e1) {
			errorMessage.setText(e1.getMessage());
			this.getContentPane().add(errorPanel);
			errorPanel.setVisible(true);
			errorFlag = true;
			this.repaint();
			this.revalidate();
		} catch (NotEnoughActionsException e1) {
			errorMessage.setText(e1.getMessage());
			this.getContentPane().add(errorPanel);
			errorPanel.setVisible(true);
			errorFlag = true;
			this.repaint();
			this.revalidate();
		}
		this.updateInfo();
		//gridMap[myHero.getLocation().x-1][myHero.getLocation().y].setBackground(Color.WHITE);
		if(errorFlag==false){
		for(int i=0;i<Game.map.length;i++){
			for(int j=0;j<Game.map.length;j++){
				if(Game.map[i][j].isVisible()){
					if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Supply){
						gridMap[i][j].setBackground(Color.YELLOW);
						gridMap[i][j].setText("");
					}
					if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Vaccine){
						gridMap[i][j].setBackground(Color.GREEN);
						gridMap[i][j].setText("");
					}
					if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Hero){
						gridMap[i][j].setBackground(Color.BLUE);
						gridMap[i][j].setText(((Hero)((CharacterCell)Game.map[i][j]).getCharacter()).getName());
					}
					if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie){
						gridMap[i][j].setBackground(Color.RED);
						gridMap[i][j].setText("");
					}
					if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter()==null){
						gridMap[i][j].setBackground(Color.WHITE);
						gridMap[i][j].setText("");
					}
					if(Game.map[i][j] instanceof TrapCell){
						gridMap[i][j].setBackground(Color.BLACK);
						gridMap[i][j].setText("");
					}
				}
				else{
					gridMap[i][j].setBackground(Color.DARK_GRAY);
					gridMap[i][j].setText("");
				}
			}
			this.updateInfo();
		}
		}
	}
	if(e.getSource()==buttonRight && errorFlag==false){
		try {
			myHero.move(Direction.RIGHT);
		} catch (MovementException e1) {
			errorMessage.setText(e1.getMessage());
			this.getContentPane().add(errorPanel);
			errorPanel.setVisible(true);
			errorFlag = true;
			this.repaint();
			this.revalidate();
		} catch (NotEnoughActionsException e1) {
			errorMessage.setText(e1.getMessage());
			this.getContentPane().add(errorPanel);
			errorPanel.setVisible(true);
			errorFlag = true;
			this.repaint();
			this.revalidate();
		}
		this.updateInfo();
		//gridMap[myHero.getLocation().x][myHero.getLocation().y-1].setBackground(Color.WHITE);
		if(errorFlag==false){
		for(int i=0;i<Game.map.length;i++){
			for(int j=0;j<Game.map.length;j++){
				if(Game.map[i][j].isVisible()){
					if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Supply){
						gridMap[i][j].setBackground(Color.YELLOW);
						gridMap[i][j].setText("");
					}
					if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Vaccine){
						gridMap[i][j].setBackground(Color.GREEN);
						gridMap[i][j].setText("");
					}
					if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Hero){
						gridMap[i][j].setBackground(Color.BLUE);
						gridMap[i][j].setText(((Hero)((CharacterCell)Game.map[i][j]).getCharacter()).getName());
					}
					if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie){
						gridMap[i][j].setBackground(Color.RED);
						gridMap[i][j].setText("");
					}
					if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter()==null){
						gridMap[i][j].setBackground(Color.WHITE);
						gridMap[i][j].setText("");
					}
					if(Game.map[i][j] instanceof TrapCell){
						gridMap[i][j].setBackground(Color.BLACK);
						gridMap[i][j].setText("");
					}
				}
				else{
					gridMap[i][j].setBackground(Color.DARK_GRAY);
					gridMap[i][j].setText("");
				}
			}
			
		}
		this.updateInfo();
		}
	}
	if(e.getSource()==buttonLeft && errorFlag==false){
		try {
			myHero.move(Direction.LEFT);
		} catch (MovementException e1) {
			errorMessage.setText(e1.getMessage());
			this.getContentPane().add(errorPanel);
			errorPanel.setVisible(true);
			errorFlag = true;
			this.repaint();
			this.revalidate();
		} catch (NotEnoughActionsException e1) {
			errorMessage.setText(e1.getMessage());
			this.getContentPane().add(errorPanel);
			errorPanel.setVisible(true);
			errorFlag = true;
			this.repaint();
			this.revalidate();
		}
		this.updateInfo();
		//gridMap[myHero.getLocation().x][myHero.getLocation().y-1].setBackground(Color.WHITE);
		if(errorFlag==false){
		for(int i=0;i<Game.map.length;i++){
			for(int j=0;j<Game.map.length;j++){
				if(Game.map[i][j].isVisible()){
					if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Supply){
						gridMap[i][j].setBackground(Color.YELLOW);
						gridMap[i][j].setText("");
					}
					if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Vaccine){
						gridMap[i][j].setBackground(Color.GREEN);
						gridMap[i][j].setText("");
					}
					if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Hero){
						gridMap[i][j].setBackground(Color.BLUE);
						gridMap[i][j].setText(((Hero)((CharacterCell)Game.map[i][j]).getCharacter()).getName());
					}
					if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie){
						gridMap[i][j].setBackground(Color.RED);
						gridMap[i][j].setText("");
					}
					if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter()==null){
						gridMap[i][j].setBackground(Color.WHITE);
						gridMap[i][j].setText("");
					}
					if(Game.map[i][j] instanceof TrapCell){
						gridMap[i][j].setBackground(Color.BLACK);
						gridMap[i][j].setText("");
					}
				}
				else{
					gridMap[i][j].setBackground(Color.DARK_GRAY);
					gridMap[i][j].setText("");
				}
			}
		}
		this.updateInfo();
		}
	}
	if(e.getSource()==buttonDown && errorFlag==false){
		try {
			myHero.move(Direction.UP);
		} catch (MovementException e1) {
			errorMessage.setText(e1.getMessage());
			this.getContentPane().add(errorPanel);
			errorPanel.setVisible(true);
			errorFlag = true;
			this.repaint();
			this.revalidate();
		} catch (NotEnoughActionsException e1) {
			errorMessage.setText(e1.getMessage());
			this.getContentPane().add(errorPanel);
			errorPanel.setVisible(true);
			errorFlag = true;
			this.repaint();
			this.revalidate();
		}
		this.updateInfo();
		//gridMap[myHero.getLocation().x][myHero.getLocation().y-1].setBackground(Color.WHITE);
		if(errorFlag==false){
		for(int i=0;i<Game.map.length;i++){
			for(int j=0;j<Game.map.length;j++){
				if(Game.map[i][j].isVisible()){
					if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Supply){
						gridMap[i][j].setBackground(Color.YELLOW);
						gridMap[i][j].setText("");
					}
					if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Vaccine){
						gridMap[i][j].setBackground(Color.GREEN);
						gridMap[i][j].setText("");
					}
					if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Hero){
						gridMap[i][j].setBackground(Color.BLUE);
						gridMap[i][j].setText(((Hero)((CharacterCell)Game.map[i][j]).getCharacter()).getName());
					}
					if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie){
						gridMap[i][j].setBackground(Color.RED);
						gridMap[i][j].setText("");
					}
					if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter()==null){
						gridMap[i][j].setBackground(Color.WHITE);
						gridMap[i][j].setText("");
					}
					if(Game.map[i][j] instanceof TrapCell){
						gridMap[i][j].setBackground(Color.BLACK);
						gridMap[i][j].setText("");
					}
				}
				else{
					gridMap[i][j].setBackground(Color.DARK_GRAY);
					gridMap[i][j].setText("");
				}
			}
		}
		this.updateInfo();
		}
	}
	this.repaint();
	this.revalidate();
	this.updateInfo();
	if(e.getSource()==okError){
		errorFlag=false;
		errorPanel.setVisible(false);
		this.getContentPane().remove(errorPanel);
		this.repaint();
		this.revalidate();
		this.updateInfo();
	}
	if(mapButtons.contains(e.getSource())) {
		for(int i=0;i<gridMap.length;i++){
			for(int j=0;j<gridMap.length;j++){
				if(gridMap[i][j].equals(e.getSource())){
					if(heroButtons.contains(gridMap[i][j]))
						myHero = (Hero) ((CharacterCell)engine.map[i][j]).getCharacter();
					if(zombieButtons.contains(gridMap[i][j]))
						myHero.setTarget((Zombie)((CharacterCell)engine.map[i][j]).getCharacter());
			}
			}
			}
		this.updateInfo();
		this.updateButtons();
	}
		if(e.getSource()==attackButton){
			try {
				myHero.attack();
			} catch (NotEnoughActionsException e1) {
				errorMessage.setText(e1.getMessage());
				this.getContentPane().add(errorPanel);
				errorPanel.setVisible(true);
				errorFlag = true;
				this.repaint();
				this.revalidate();
			} catch (InvalidTargetException e1) {
				errorMessage.setText(e1.getMessage());
				this.getContentPane().add(errorPanel);
				errorPanel.setVisible(true);
				errorFlag = true;
				this.repaint();
				this.revalidate();
			}
			this.updateButtons();
			this.updateInfo();
			for(int i=0;i<Game.map.length;i++){
				for(int j=0;j<Game.map.length;j++){
					if(Game.map[i][j].isVisible()){
						if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Supply){
							gridMap[i][j].setBackground(Color.YELLOW);
							gridMap[i][j].setText("");
						}
						if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Vaccine){
							gridMap[i][j].setBackground(Color.GREEN);
							gridMap[i][j].setText("");
						}
						if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Hero){
							gridMap[i][j].setBackground(Color.BLUE);
							gridMap[i][j].setText(((Hero)((CharacterCell)Game.map[i][j]).getCharacter()).getName());
						}
						if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie){
							gridMap[i][j].setBackground(Color.RED);
							gridMap[i][j].setText("");
						}
						if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter()==null){
							gridMap[i][j].setBackground(Color.WHITE);
							gridMap[i][j].setText("");
						}
						if(Game.map[i][j] instanceof TrapCell){
							gridMap[i][j].setBackground(Color.BLACK);
							gridMap[i][j].setText("");
						}
					}
					else{
						gridMap[i][j].setBackground(Color.DARK_GRAY);
						gridMap[i][j].setText("");
					}
				}
			}
		}
		if(e.getSource()==cureButton){
			try {
				myHero.cure();
			} catch (NoAvailableResourcesException e1) {
				errorMessage.setText(e1.getMessage());
				this.getContentPane().add(errorPanel);
				errorPanel.setVisible(true);
				errorFlag = true;
				this.repaint();
				this.revalidate();
			} catch (InvalidTargetException e1) {
				errorMessage.setText(e1.getMessage());
				this.getContentPane().add(errorPanel);
				errorPanel.setVisible(true);
				errorFlag = true;
				this.repaint();
				this.revalidate();
			} catch (NotEnoughActionsException e1) {
				errorMessage.setText(e1.getMessage());
				this.getContentPane().add(errorPanel);
				errorPanel.setVisible(true);
				errorFlag = true;
				this.repaint();
				this.revalidate();
			}
			this.updateButtons();
			this.updateInfo();
			for(int i=0;i<Game.map.length;i++){
				for(int j=0;j<Game.map.length;j++){
					if(Game.map[i][j].isVisible()){
						if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Supply){
							gridMap[i][j].setBackground(Color.YELLOW);
							gridMap[i][j].setText("");
						}
						if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Vaccine){
							gridMap[i][j].setBackground(Color.GREEN);
							gridMap[i][j].setText("");
						}
						if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Hero){
							gridMap[i][j].setBackground(Color.BLUE);
							gridMap[i][j].setText(((Hero)((CharacterCell)Game.map[i][j]).getCharacter()).getName());
						}
						if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie){
							gridMap[i][j].setBackground(Color.RED);
							gridMap[i][j].setText("");
						}
						if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter()==null){
							gridMap[i][j].setBackground(Color.WHITE);
							gridMap[i][j].setText("");
						}
						if(Game.map[i][j] instanceof TrapCell){
							gridMap[i][j].setBackground(Color.BLACK);
							gridMap[i][j].setText("");
						}
					}
					else{
						gridMap[i][j].setBackground(Color.DARK_GRAY);
						gridMap[i][j].setText("");
					}
				}
			}
			this.updateButtons();
			this.updateInfo();
			this.repaint();
			this.revalidate();
		}
		if(e.getSource()==endTurnB){
			try {
				engine.endTurn();
			} catch (NotEnoughActionsException e1) {
				errorMessage.setText(e1.getMessage());
				this.getContentPane().add(errorPanel);
				errorPanel.setVisible(true);
				errorFlag = true;
				this.repaint();
				this.revalidate();
			} catch (InvalidTargetException e1) {
				errorMessage.setText(e1.getMessage());
				this.getContentPane().add(errorPanel);
				errorPanel.setVisible(true);
				errorFlag = true;
				this.repaint();
				this.revalidate();
			}
			this.updateButtons();
			this.updateInfo();
			for(int i=0;i<Game.map.length;i++){
				for(int j=0;j<Game.map.length;j++){
					if(Game.map[i][j].isVisible()){
						if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Supply){
							gridMap[i][j].setBackground(Color.YELLOW);
							gridMap[i][j].setText("");
						}
						if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Vaccine){
							gridMap[i][j].setBackground(Color.GREEN);
							gridMap[i][j].setText("");
						}
						if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Hero){
							gridMap[i][j].setBackground(Color.BLUE);
							gridMap[i][j].setText(((Hero)((CharacterCell)Game.map[i][j]).getCharacter()).getName());
						}
						if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie){
							gridMap[i][j].setBackground(Color.RED);
							gridMap[i][j].setText("");
						}
						if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter()==null){
							gridMap[i][j].setBackground(Color.WHITE);
							gridMap[i][j].setText("");
						}
						if(Game.map[i][j] instanceof TrapCell){
							gridMap[i][j].setBackground(Color.BLACK);
							gridMap[i][j].setText("");
						}
					}
					else{
						gridMap[i][j].setBackground(Color.DARK_GRAY);
						gridMap[i][j].setText("");
					}
				}
			}
			this.repaint();
			this.revalidate();
			if(Game.checkGameOver()){
				endMessage.setText("Game Over Loser");
				this.getContentPane().add(endPanel);
				this.getContentPane().add(endPanel);
				this.repaint();
				this.revalidate();
				this.updateButtons();
				this.updateInfo();
			}
			if(Game.checkWin()){
				endMessage.setText("Game Over Winner");
				this.getContentPane().add(endPanel);
				this.repaint();
				this.revalidate();
				this.updateButtons();
				this.updateInfo();
			}
		}
		if(e.getSource()==specialButton){
			if(myHero instanceof Fighter){
				try {
					myHero.useSpecial();
				} catch (NoAvailableResourcesException | InvalidTargetException e1) {
					errorMessage.setText(e1.getMessage());
					this.getContentPane().add(errorPanel);
					errorPanel.setVisible(true);
					errorFlag = true;
					this.repaint();
					this.revalidate();
				}
				this.updateButtons();
				this.updateInfo();
				for(int i=0;i<Game.map.length;i++){
					for(int j=0;j<Game.map.length;j++){
						if(Game.map[i][j].isVisible()){
							if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Supply){
								gridMap[i][j].setBackground(Color.YELLOW);
								gridMap[i][j].setText("");
							}
							if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Vaccine){
								gridMap[i][j].setBackground(Color.GREEN);
								gridMap[i][j].setText("");
							}
							if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Hero){
								gridMap[i][j].setBackground(Color.BLUE);
								gridMap[i][j].setText(((Hero)((CharacterCell)Game.map[i][j]).getCharacter()).getName());
							}
							if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie){
								gridMap[i][j].setBackground(Color.RED);
								gridMap[i][j].setText("");
							}
							if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter()==null){
								gridMap[i][j].setBackground(Color.WHITE);
								gridMap[i][j].setText("");
							}
							if(Game.map[i][j] instanceof TrapCell){
								gridMap[i][j].setBackground(Color.BLACK);
								gridMap[i][j].setText("");
							}
						}
						else{
							gridMap[i][j].setBackground(Color.DARK_GRAY);
							gridMap[i][j].setText("");
						}
					}
				}
				this.repaint();
				this.revalidate();
			}
				if(myHero instanceof Medic){
					try {
						myHero.useSpecial();
					} catch (NoAvailableResourcesException e1) {
						errorMessage.setText(e1.getMessage());
						this.getContentPane().add(errorPanel);
						errorPanel.setVisible(true);
						errorFlag = true;
						this.repaint();
						this.revalidate();
					} catch (InvalidTargetException e1) {
						errorMessage.setText(e1.getMessage());
						this.getContentPane().add(errorPanel);
						errorPanel.setVisible(true);
						errorFlag = true;
						this.repaint();
						this.revalidate();
					}
					this.updateButtons();
					this.updateInfo();
					for(int i=0;i<Game.map.length;i++){
						for(int j=0;j<Game.map.length;j++){
							if(Game.map[i][j].isVisible()){
								if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Supply){
									gridMap[i][j].setBackground(Color.YELLOW);
									gridMap[i][j].setText("");
								}
								if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Vaccine){
									gridMap[i][j].setBackground(Color.GREEN);
									gridMap[i][j].setText("");
								}
								if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Hero){
									gridMap[i][j].setBackground(Color.BLUE);
									gridMap[i][j].setText(((Hero)((CharacterCell)Game.map[i][j]).getCharacter()).getName());
								}
								if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie){
									gridMap[i][j].setBackground(Color.RED);
									gridMap[i][j].setText("");
								}
								if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter()==null){
									gridMap[i][j].setBackground(Color.WHITE);
									gridMap[i][j].setText("");
								}
								if(Game.map[i][j] instanceof TrapCell){
									gridMap[i][j].setBackground(Color.BLACK);
									gridMap[i][j].setText("");
								}
							}
							else{
								gridMap[i][j].setBackground(Color.DARK_GRAY);
								gridMap[i][j].setText("");
							}
						}
					}
					this.repaint();
					this.revalidate();
				}
				if(myHero instanceof Explorer){
					try {
						myHero.useSpecial();
					} catch (NoAvailableResourcesException
							| InvalidTargetException e1) {
						errorMessage.setText(e1.getMessage());
						this.getContentPane().add(errorPanel);
						errorPanel.setVisible(true);
						errorFlag = true;
						this.repaint();
						this.revalidate();
					}
					this.updateButtons();
					this.updateInfo();
					for(int i=0;i<Game.map.length;i++){
						for(int j=0;j<Game.map.length;j++){
							if(Game.map[i][j].isVisible()){
								if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Supply){
									gridMap[i][j].setBackground(Color.YELLOW);
									gridMap[i][j].setText("");
								}
								if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Vaccine){
									gridMap[i][j].setBackground(Color.GREEN);
									gridMap[i][j].setText("");
								}
								if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Hero){
									gridMap[i][j].setBackground(Color.BLUE);
									gridMap[i][j].setText(((Hero)((CharacterCell)Game.map[i][j]).getCharacter()).getName());
								}
								if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie){
									gridMap[i][j].setBackground(Color.RED);
									gridMap[i][j].setText("");
								}
								if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter()==null){
									gridMap[i][j].setBackground(Color.WHITE);
									gridMap[i][j].setText("");
								}
								if(Game.map[i][j] instanceof TrapCell){
									gridMap[i][j].setBackground(Color.BLACK);
									gridMap[i][j].setText("");
								}
							}
							else{
								gridMap[i][j].setBackground(Color.DARK_GRAY);
								gridMap[i][j].setText("");
							}
						}
					}
					this.repaint();
					this.revalidate();
				}
			}
		}
		

public void updateInfo(){
	int constY=230;
	HeroesPanel.removeAll();
	for(int i=0;i<Game.heroes.size();i++){
		
		JLabel tmpLabel = new JLabel(getHeroInfo(Game.heroes.get(i)));
		tmpLabel.setBounds(0, constY, 520, 30);
		tmpLabel.setVisible(true);
		HeroesPanel.add(tmpLabel);
		constY = constY + 40;
	}
}
public void updateButtons(){
	heroButtons.removeAll(heroButtons);
	zombieButtons.removeAll(zombieButtons);
	for(int i=0;i<gridMap.length;i++){
		for(int j=0;j<gridMap.length;j++){
			if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Hero){
				heroButtons.add(gridMap[i][j]);
			}
			if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie){
				zombieButtons.add(gridMap[i][j]);
			}
		}
	}
}


/*for(int i=0;i<engine.availableHeroes.size();i++){
	heroInformation = new JLabel(getHeroInfo(engine.availableHeroes.get(i)));
	heroInformation.setBounds(35, constY, 520, 30);
	heroInformation.setVisible(true);
	HeroesPanel.add(heroInformation);
	heroLabels.add(heroInformation);
	
	constY = constY + 40;
}
*/


}

//button1 = new JButton("JOE MILLER");
//button1.setBounds(510,435,100,40);
//button1.addActionListener(this);
//this.getContentPane().add(button1);
//button1.addActionListener(this);
//
//button2 = new Je;Button("DAVID");
//button2.setBounds(745,435,100,40);
//button2.addActionListener(this);
//this.getContentPane().add(button2);
//
//button3 = new JButton("BILL");
//button3.setBounds(1010,435,100,40);
//button3.addActionListener(this);
//this.getContentPane().add(button3);
//
//button4 = new JButton("TESS");
//button4.setBounds(1250,430,100,40);
//button4.addActionListener(this);
//this.getContentPane().add(button4);
//
//button5 = new JButton("ELLIE WILLIAMS");
//button5.setBounds(475,820,120,40);
//button5.addActionListener(this);
//this.getContentPane().add(button5);
//
//button6 = new JButton("TOMMY MILLER");
//button6.setBounds(735,820,120,40);
//button6.addActionListener(this);
//this.getContentPane().add(button6);
//
//button7 = new JButton("HENRY BURELL");
//button7.setBounds(1015,820,120,40);
//button7.addActionListener(this);
//this.getContentPane().add(button7);
//
//button8 = new JButton("RILEY ABEL");
//button8.setBounds(1250,820,120,40);
//button8.addActionListener(this);
//this.getContentPane().add(button8);




/*import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;
public class GUI extends JFrame implements ActionListener{
	private JLabel imageLabel;
	private JLabel descriptionLabel;
	private JPanel imagePanel;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JButton button5;
	private JButton button6;
	private JButton button7;
	private JButton button8;

	public GUI(){
		this.setLayout(null);
		imageLabel = new JLabel();
		ImageIcon imageIcon = new ImageIcon("Start Menu.png");
		imageLabel.setIcon(imageIcon);

		this.setResizable(true);
		//button1 = new JButton("Press this");
		//button1.setBounds(100, 100, 130, 30);
		//button1.addActionListener(this);
		//this.getContentPane().add(button1);
		int labelWidth = imageIcon.getIconWidth();
        int labelHeight = imageIcon.getIconHeight();
        int frameWidth = labelWidth + 20; // Adding some padding
        int frameHeight = labelHeight + 20; // Adding some padding
        imageLabel.setBounds(0,0,1920,1080);

        // Add the label to the frame
        getContentPane().add(imageLabel);
        
        button1 = new JButton("Press this");
		button1.setBounds(600, 100, 130, 30);
		button1.addActionListener(this);
		this.getContentPane().add(button1);
		this.repaint();
        // Set the size of the frame to accommodate the label
        setSize(1920,1080);
		this.setVisible(true);
		
		this.setSize(1920, 1040);
		imagePanel = new JPanel();
		imagePanel.setBounds(0, 0, 1920, 1080);
		imagePanel.add(imageLabel);
		this.getContentPane().add(imagePanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent h) {
		if(h.getSource()==button1)
			button1 = new JButton("Great Job");
		this.repaint();
	}
	public static void main(String[] args) {
		GUI g = new GUI();
	}
	
}
*/
