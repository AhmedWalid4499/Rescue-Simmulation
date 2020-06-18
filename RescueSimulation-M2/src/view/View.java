package view;
import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.PopupFactory;

//import com.sun.prism.Image;

import model.*;
import model.disasters.Infection;
import model.disasters.Injury;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import model.units.UnitState;
import simulation.Address;
import simulation.Simulator;
import controller.CommandCenter;

import exceptions.*;

import java.awt.*;

import model.disasters.*;
public class View  extends JFrame implements ActionListener {
	private JPanel p;
	private JPanel p1;
	private ImageIcon icon;
	private ImageIcon icon1;
	private JButton[][] gridB;
	private CommandCenter c;
	private JButton dead;
	private JLabel l2;
	private JLabel l;
	private JFrame f;
	private FireTruck fr;
	private JButton resp;
	private Unit u;
	private JPanel pside;
	private Simulator s;
	private JPanel start;
	private JPanel pleft;
	private JPanel punder;
	private JComboBox targets;
	private JButton[] btrp;
	private JComboBox message;


	public View() throws Exception{
		gui();
	}
	public void gui() throws Exception{
		this.c= new CommandCenter();
		this.s= c.getEngine();
		f = new JFrame("انقذهم الاهي تنستر");
		f.setVisible(false);
		f.setExtendedState(f.MAXIMIZED_BOTH);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//Combobox trail:
		JButton o = new JButton("يلا بينا؟؟");
		o.setFont(new Font("Italic", Font.BOLD, 16));
		o.setPreferredSize(new Dimension(200,100));

		JFrame Start = new JFrame("Start the game?");
		Start.setVisible(true);
		Start.setExtendedState(Start.MAXIMIZED_BOTH);
		JPanel po = new JPanel();
		po.setBackground(Color.cyan);
		Start.add(po);
		o.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				f.setVisible(true);;
				Start.setVisible(false);

			}
		});
		JButton ex = new JButton("لا يا عم");
		ex.setFont(new Font("Italic", Font.BOLD, 16));
		ex.setPreferredSize(new Dimension(200,100));
		ex.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);

			}
		});
		JLabel rule = new JLabel();
		rule.setText("The Rules: Save the Citizens and the Buildings. You have to send the right Unit to the"
				+	"\n" + "right place. Take care that each" +"\n"+"cycle the citizen or building's disaster will Increase! Goodluck! ");
		rule.setFont(new Font("Italic", Font.BOLD, 16));
		//	rule.setSize(new Dimension(200,100));
		po.add(o);
		po.add(ex);
		po.add(rule);


		p = new JPanel();
		p.setBackground(Color.CYAN);
		p.setLayout(new GridLayout(10,10, 5, 10));
		f.add(p, BorderLayout.EAST);	

		pleft = new JPanel();
		pleft.setBackground(Color.CYAN);
		pleft.setLayout(new GridLayout(10,10, 5, 10));
		f.add(pleft, BorderLayout.WEST);	


		icon = new ImageIcon("img/img1.jpg");
		f.setIconImage(icon.getImage());
		icon1 = new ImageIcon("img/iconfinder_construction-industry-building-13_4137175.png");
		//		JLabel image = new JLabel(icon.getImage());
		//		image.setSize(new Dimension(1920, 1080));
		//		Start.add(image);
		Start.setIconImage(icon.getImage());
		icon1 = new ImageIcon("img/iconfinder_construction-industry-building-13_4137175.png");

		JPanel grid = new JPanel();
		grid.setLayout(new GridLayout(10,10));
		f.add(grid, BorderLayout.CENTER);
		//Respond
		resp = new JButton("Respond الاهي تنستر");
		resp.setFont(new Font("book", Font.ITALIC, 15));
		resp.setActionCommand("resp");
		resp.addActionListener(this);

		//GridLoop
		gridB= new JButton[10][10];
		for(int i=0; i<10; i++){
			for(int x =0; x<10; x++){
				gridB[i][x]= new JButton();
				gridB[i][x].setBackground(Color.cyan.darker());
				gridB[i][x].setBorder(BorderFactory.createLineBorder(Color.BLUE.brighter()));
				gridB[i][x].addActionListener(this);
				gridB[i][x].setPreferredSize(new Dimension(100, 100));	
				grid.add(gridB[i][x]);
				gridB[i][x].setText(" " + i + "," + x);
			}
		}

		//		pside = new JPanel();
		//		pside.setBackground(Color.BLUE);
		//		pside.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		//		pside.setLayout(new GridLayout(5,10, 10, 10));
		//		
		////		pside.add(rule);
		//		f.add(pside, BorderLayout.NORTH);


		//Options button
		JButton Exit = new JButton("Exit");
		Exit.setFont(new Font("book", Font.ITALIC, 15));

		Exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Object[] Op = {"اه باي"};
				int n = JOptionPane.showOptionDialog(f, "ايه ده خلاص كده؟" ,
						"اه باي", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,//do not use a custom Icon
						Op,//the titles of buttons
						Op[0]);//default button title
				System.exit(0);
			}
		});

		//	Ambulance
		JButton b1= new JButton();
		b1.setFont(new Font("book", Font.ITALIC, 15));
		b1.setText("اسعاااف");
		b1.setIcon(icon);
		b1.setActionCommand("AMB");
		b1.setBackground(Color.GRAY);
		b1.addActionListener(this);

		//fireTruck
		JButton bb1= new JButton();
		bb1.setFont(new Font("book", Font.ITALIC, 15));
		bb1.setText("مطشافي");
		bb1.setBackground(Color.GRAY);
		bb1.setActionCommand("FT");
		bb1.setIcon(icon);
		bb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				for(int i=0; i<c.getEmergencyUnits().size();i++){
					if(c.getEmergencyUnits().get(i) instanceof FireTruck){
						for(int j=0; j<c.getVisibleBuildings().size(); j++){
							if(c.getVisibleBuildings().get(j).getDisaster() instanceof Fire){
								try {
									c.getEmergencyUnits().get(i).respond(c.getVisibleBuildings().get(j));
								} catch (UnitException e) {
									// TODO Auto-generated catch block
									e.getMessage();
								}
							}
						}
					}
				}
			}
		});

		JButton bb2= new JButton();
		bb2.setFont(new Font("book", Font.ITALIC, 15));
		bb2.setText("مكافحة الغازات");
		bb2.setBackground(Color.GRAY);
		bb2.setIcon(icon);
		bb2.setActionCommand("GCU");
		bb2.addActionListener(this);

		JButton bb3= new JButton();
		bb3.setFont(new Font("book", Font.ITALIC, 15));
		bb3.setText("مكافحة السموم");
		bb3.setBackground(Color.GRAY);
		bb3.setIcon(icon);
		bb3.setActionCommand("DCU");
		bb3.addActionListener(this);;

		JButton bb4= new JButton();
		bb4.setFont(new Font("book", Font.ITALIC, 15));
		bb4.setText("يلا نفضي");
		bb4.setBackground(Color.GRAY);
		bb4.setIcon(icon);
		bb4.setActionCommand("EVC");
		bb4.addActionListener(this);

		//Disaster button
		JButton dis = new JButton("Disaster الاهي تنفضح");
		dis.setFont(new Font("book", Font.ITALIC, 15));
		dis.setActionCommand("Dis");
		dis.addActionListener(this);
		// is game over??
		JButton gover = new JButton("Game is OVER?");
		gover.setFont(new Font("book", Font.ITALIC, 15));
		gover.setActionCommand("gover");
		gover.addActionListener(this);


		l = new JLabel();
		l.setText("Number of the dead :-): " + s.calculateCasualties());
		//Next cycle
		JButton nc = new JButton("Press here for next cycle");
		nc.setFont(new Font("book", Font.ITALIC, 15));
		nc.setActionCommand("Next cycle");
		nc.addActionListener(this);

		nc.setBackground(Color.gray.brighter());
		dis.setBackground(Color.gray.brighter());
		Exit.setBackground(Color.gray.brighter());
		l2 = new JLabel("Current Cycle:" + s.getCurrentCycle());
		p.add(b1);
		p.add(bb3);
		p.add(bb4);
		p.add(bb1);
		p.add(bb2);
		pleft.add(nc);
		//		p.add(resp);
		pleft.add(dis);
		//		pleft.add(gover);
		pleft.add(Exit);
		//		l.setLocation(500, 1000);
		p.add(l2);
		p.add(l);
		p.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		f.revalidate();

	}




	public void actionPerformed(ActionEvent bttn)  {
		ArrayList<ResidentialBuilding> ar = new ArrayList<ResidentialBuilding>();
		if(bttn.getActionCommand()== "Next cycle")
			try{
				s.nextCycle();
				for(int i=0; i< c.getVisibleCitizens().size();i++){

					gridB[c.getVisibleCitizens().get(i).getLocation().getX()][c.getVisibleCitizens().get(i).getLocation().getY()].setText("مواطن شقيان");
					gridB[c.getVisibleCitizens().get(i).getLocation().getX()][c.getVisibleCitizens().get(i).getLocation().getY()].setActionCommand("Citizen");

					if(c.getVisibleCitizens().get(i).getDisaster().isActive()== true){
						gridB[c.getVisibleCitizens().get(i).getLocation().getX()][c.getVisibleCitizens().get(i).getLocation().getY()].setText(" "+c.getVisibleCitizens().get(i).getDisaster());
					}
					if(c.getVisibleCitizens().get(i).getState()== CitizenState.DECEASED){
						gridB[c.getVisibleCitizens().get(i).getLocation().getX()][c.getVisibleCitizens().get(i).getLocation().getY()].setText("البقاء لله");

					}

				}

				for(int i=0; i< c.getVisibleBuildings().size();i++){
					gridB[c.getVisibleBuildings().get(i).getLocation().getX()][c.getVisibleBuildings().get(i).getLocation().getY()].setText("مبني غلبان");
					gridB[c.getVisibleBuildings().get(i).getLocation().getX()][c.getVisibleBuildings().get(i).getLocation().getY()].setActionCommand("Buildings");
					if(c.getVisibleBuildings().get(i).getOccupants().size()==0){
						gridB[c.getVisibleBuildings().get(i).getLocation().getX()][c.getVisibleBuildings().get(i).getLocation().getY()].setText("مبني فاضي");
					}

					if(c.getVisibleBuildings().get(i).getDisaster().isActive()== true){
						gridB[c.getVisibleBuildings().get(i).getLocation().getX()][c.getVisibleBuildings().get(i).getLocation().getY()].setText(" "+c.getVisibleBuildings().get(i).getDisaster());
					}
					if(c.getVisibleBuildings().get(i).getStructuralIntegrity()==0){
						gridB[c.getVisibleBuildings().get(i).getLocation().getX()][c.getVisibleBuildings().get(i).getLocation().getY()].setText("مبني وقع ");
					}
				}

				for(int i=0; i< c.getEmergencyUnits().size();i++){
					Address ad = c.getEmergencyUnits().get(i).getLocation();
					//					gridB[c.getEmergencyUnits().get(i).getLocation().getX()][c.getEmergencyUnits().get(i).getLocation().getY()].setText(""+ c.getEmergencyUnits().size());
					gridB[ad.getX()][ad.getY()].setText("ويييواااا");
					gridB[ad.getX()][ad.getY()].setActionCommand("Unit");





					l2.setText("Current Cycle: " + s.getCurrentCycle());
					l.setText("Number of the dead :-): "+ s.calculateCasualties());


				}
			}

		catch (DisasterException e) {
			e.printStackTrace();
		}


		if(bttn.getActionCommand()=="Unit"){
			for(int i=0; i<c.getEmergencyUnits().size();i++){
				Address ad = c.getEmergencyUnits().get(i).getLocation();
				//				gridB[c.getEmergencyUnits().get(i).getLocation().getX()][c.getEmergencyUnits().get(i).getLocation().getY()].setText(""+ c.getEmergencyUnits().size());


				if(c.getEmergencyUnits().get(i) instanceof Ambulance)
					JOptionPane.showMessageDialog(this, c.getEmergencyUnits().get(i), "Ambulance" , JOptionPane.INFORMATION_MESSAGE);
				gridB[ad.getX()][ad.getY()].setText("اسعاف");
				if(c.getEmergencyUnits().get(i) instanceof GasControlUnit)
					JOptionPane.showMessageDialog(this, c.getEmergencyUnits().get(i), "Gas Control!" , JOptionPane.INFORMATION_MESSAGE);
				gridB[ad.getX()][ad.getY()].setText("مكافحو الغاز");
				if(c.getEmergencyUnits().get(i) instanceof DiseaseControlUnit)
					JOptionPane.showMessageDialog(this, c.getEmergencyUnits().get(i), "Disease Control Unit!" , JOptionPane.INFORMATION_MESSAGE);
				gridB[ad.getX()][ad.getY()].setText("مكافحة السموم");
				if(c.getEmergencyUnits().get(i) instanceof FireTruck)
					JOptionPane.showMessageDialog(this, c.getEmergencyUnits().get(i), "FireTruck!" , JOptionPane.INFORMATION_MESSAGE);
				gridB[ad.getX()][ad.getY()].setText("مطشافي");
				if(c.getEmergencyUnits().get(i) instanceof Evacuator)
					JOptionPane.showMessageDialog(this, c.getEmergencyUnits().get(i) + "\n"+ "MaxCapacity: " + ((Evacuator) c.getEmergencyUnits().get(i)).getMaxCapacity() 
							+"\n" + "Number of Passengers: " +((Evacuator) c.getEmergencyUnits().get(i)).getPassengers().size(), 
							"Evacuator Information" , JOptionPane.INFORMATION_MESSAGE);
				gridB[ad.getX()][ad.getY()].setText("المضياتي");
			}

		}
		if(bttn.getActionCommand()=="Buildings" ){

			for(int i=0; i<c.getVisibleBuildings().size();i++){


				JOptionPane.showMessageDialog(null, c.getVisibleBuildings().get(i)
						+"\n"
						+"Occupants in building: " + c.getVisibleBuildings().get(i).getOccupants().size()+"\n"+
						"الحقة الاهي تنستر", "Building information" 
						, JOptionPane.INFORMATION_MESSAGE);

				if(c.getVisibleBuildings().get(i).getStructuralIntegrity()==0){
					JOptionPane.showMessageDialog(null, "Building collapsed! " +" "+c.getVisibleBuildings().get(i).getLocation());
				}
				for(int j=0; j<c.getVisibleBuildings().get(i).getOccupants().size();j++){
					JOptionPane.showMessageDialog(null, "Citizen in Building of Location: " + c.getVisibleBuildings().get(i).getOccupants().get(j).getLocation()
							+"\n" 
							+"Citizen State: "+ c.getVisibleBuildings().get(i).getOccupants().get(j).getState()
							+ "\n"
							+ "Citizen HP: " + c.getVisibleBuildings().get(i).getOccupants().get(j).getHp()
							+"\n"
							+ "Citizen Disaster: "+ c.getVisibleBuildings().get(i).getOccupants().get(j).getDisaster()
							+"\n"+
							"الحقة الاهي تنستر"
							, "Occupants information: " + c.getVisibleBuildings().get(i).getLocation(), JOptionPane.INFORMATION_MESSAGE);
				}
			}


		}


		if(bttn.getActionCommand()=="Citizen"){
			for(int i=0; i<c.getVisibleCitizens().size();i++){
				if(c.getVisibleCitizens().get(i).getState()==CitizenState.IN_TROUBLE ){
					JOptionPane.showMessageDialog(this,c.getVisibleCitizens().get(i)
							+"\n"
							+"الحقني يا فخري انا بموت" +"\n " + "Flase or true: " +c.getVisibleCitizens().get(i).getDisaster().isActive() ,
							"Citizen Information" , 
							JOptionPane.INFORMATION_MESSAGE);
				}
				else if(c.getVisibleCitizens().get(i).getState()==CitizenState.DECEASED ){
					JOptionPane.showMessageDialog(this,c.getVisibleCitizens().get(i)
							+"\n"
							+"مات الاهي تنعر"
							, "Citizen Information" ,
							JOptionPane.INFORMATION_MESSAGE);

				}
			}
		}


		if (bttn.getActionCommand()=="GCU"){

			for(int i=0; i<c.getEmergencyUnits().size();i++){
				if(c.getEmergencyUnits().get(i) instanceof GasControlUnit){
					for(int j=0; j<c.getVisibleBuildings().size(); j++){
						if(c.getVisibleBuildings().get(j).getDisaster() instanceof GasLeak){
							try {
								c.getEmergencyUnits().get(i).respond(c.getVisibleBuildings().get(j));
							} catch (UnitException e) {
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(this, " "+e.getMessage());
							}
						}
					}
				}
			}

		}

		if (bttn.getActionCommand()=="DCU"){
			for(int i=0; i<c.getEmergencyUnits().size();i++){
				if(c.getEmergencyUnits().get(i) instanceof DiseaseControlUnit){
					for(int j=0; j<c.getVisibleCitizens().size(); j++){
						if(c.getVisibleCitizens().get(j).getDisaster() instanceof Infection){
							try {
								c.getEmergencyUnits().get(i).respond(c.getVisibleCitizens().get(j));
							} catch (UnitException e) {
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(this, " "+e.getMessage());
							}
						}
					}
				}
			}
		}
		if (bttn.getActionCommand()=="EVC"){


			for(int i=0; i<c.getEmergencyUnits().size();i++){
				if(c.getEmergencyUnits().get(i) instanceof Evacuator){
					for(int j=0; j<c.getVisibleBuildings().size(); j++){
						if(c.getVisibleBuildings().get(j).getDisaster() instanceof Collapse){
							try {
								c.getEmergencyUnits().get(i).respond(c.getVisibleBuildings().get(j));
							} catch (UnitException e) {
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(this, " "+e.getMessage());
							}
						}
					}
				}
			}



		}
		if (bttn.getActionCommand()=="AMB"){
			for(int i=0; i<c.getEmergencyUnits().size();i++){
				if(c.getEmergencyUnits().get(i) instanceof Ambulance){
					for(int j=0; j<c.getVisibleCitizens().size(); j++){
						if(c.getVisibleCitizens().get(j).getDisaster() instanceof Injury){
							try {
								c.getEmergencyUnits().get(i).respond(c.getVisibleCitizens().get(j));
							} catch (UnitException e) {
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(this, " "+e.getMessage());
							}
						}
					}
				}
			}
		}

		//Respond Mehtod gamda awyyyyyy!!!!!!!!1800kys
		if (bttn.getActionCommand()=="resp"){}


		if(bttn.getActionCommand()=="Dis"){
			for(int i=0; i<c.getEngine().getExecutedDisasters().size();i++){

				JOptionPane.showMessageDialog(this, "Disaster is: " + c.getEngine().getExecutedDisasters().get(i)
						+"\n"
						+ c.getEngine().getExecutedDisasters().get(i).getTarget() 
						+"\n"
						+ "Cycle Struck in "+ c.getEngine().getExecutedDisasters().get(i).getStartCycle()
						+"\n"
						+"الحقة الاهي تنستر", 
						"Disaster of: " + c.getEngine().getExecutedDisasters().get(i).getTarget().getLocation(),
						JOptionPane.INFORMATION_MESSAGE);
			}
		}


		if(s.checkGameOver()==true){
			JOptionPane.showMessageDialog(this, "The game is over" 
					+"\n" 
					+" Your Score: " + s.calculateCasualties() , "The game is over" , JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);

		}

	}



	public static void main(String[] args) throws Exception {

		View x = new View();


	}


}














