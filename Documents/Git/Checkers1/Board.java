package checkers;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class Board extends JFrame implements ActionListener{
	
private static final long serialVersionUID = 1L;
private Player R,B;
private Player Currentplayer,Nextplayer;
private Tile [][] T=new Tile[8][8];
private ArrayList <Piece> P=new ArrayList<Piece>();
private List <Integer> Movestomake=new ArrayList<Integer>();
private final String loc="/Users/Karthik/desktop/assignment0.txt";
private int pointer=0;
private JButton Next,Previous;
private JLabel objMessage,gameMessage,winnerMessage;


public Board()
{
	R=new Player("Red");
    B=new Player("Black");
    Boardsetup();
    Setcurrentplayer();
    Readmovesfromfile();
	SetupLabels();
	Setupbuttons();
	gameMessage.setText("Begin Checkers Game..");
    objMessage.setText(Currentplayer.getplayer() + ": Its your turn!");
	
 }

public void Boardsetup()
{
        for(int i=0;i<8;i++)
		{
		for(int j=0;j<8;j++)
		{
			
		 T[i][j]=new Tile(i,j,false);
		 T[i][j].setposition();
		    if((i%2)==(j%2))
		     {
		     if((i>4)) //RED on top 3 rows
		     {
			   Piece reds=new Piece(R,T[i][j],Color.RED,i,j);
		       P.add(reds);
		       R.pieceadded();
		      }
		      if((i<3)) //BLACK on bottom 3 rows 
			   {
	           Piece blacks=new Piece(B,T[i][j],Color.BLACK,i,j);
	           P.add(blacks);
		       B.pieceadded();
		      }
		     }
		    }
		   }
   }

 public void SetupLabels()  
 {
	objMessage = new JLabel("", JLabel.CENTER);
    objMessage.setFont(new Font("Serif", Font.BOLD, 14));
    objMessage.setForeground(Color.red);
	objMessage.setBounds(420, 250, 350, 100);
	add(objMessage);
	
	gameMessage = new JLabel("", JLabel.CENTER);
	gameMessage.setFont(new Font("Serif", Font.BOLD, 14));
	gameMessage.setForeground(Color.red);
	gameMessage.setBounds(420, 200, 350, 100);
	add(gameMessage);
	
	winnerMessage = new JLabel("", JLabel.CENTER);
	winnerMessage.setFont(new Font("Serif", Font.BOLD, 14));
	winnerMessage.setForeground(Color.red);
	winnerMessage.setBounds(420, 300, 350, 100);
	add(winnerMessage);
	
	
 }


public void Setupbuttons()
{
	Next=new JButton("Next");
	Next.setBounds(525,100,150,30);
	add(Next);
	
	Previous=new JButton("Previous");
	Previous.setBounds(525,150,150,30);
	add(Previous);
	
	Next.addActionListener(this);
	Previous.addActionListener(this);
}

@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	Object src=e.getSource();
	
	if(src==Next)
	{
		int posfrom=0,posto=0;
        gameMessage.setText("Game In Progress......");
        if((!checkgameover()) && pointer<Movestomake.size())
        {
         	      posfrom=Movestomake.get(pointer);
         		  posto=Movestomake.get(pointer+1);
         		  pointer=pointer+2;
                  Currentplayer.makemove(posfrom,posto,T,P,Nextplayer);
        	      Setcurrentplayer();
         		  repaint();
         		  objMessage.setText((Currentplayer.getplayer()) + ": Its your turn!");
        }
        else if(checkgameover())
        {
        	gameMessage.setText("Game Over!!");
        	objMessage.setText("Checkers left:"+"    "+"Red Player:"+ R.getnumpieces() +"    "+"Black Checkers:"+ B.getnumpieces());
        	Declarewinner();
        }
        else
        {
        	gameMessage.setText("All moves are made. Game Over!!");
        	objMessage.setText("Checkers left:"+"    "+"Red Player:"+ R.getnumpieces() +"    "+"Black Checkers:"+ B.getnumpieces());
        	Declarewinner();
         }
	}
	
	else if(src==Previous)
	{
		if(pointer==0)
		{
			gameMessage.setText("Begin Checkers Game..");
		    objMessage.setText(Currentplayer.getplayer() + ": Its your turn!");
		}
    	if(pointer<2)
    	{
    		JOptionPane.showMessageDialog(null, "NO More Previous States.", "No more previous states.", JOptionPane.ERROR_MESSAGE);
    	}
    	else
    	{
    		
    		 Currentplayer.switchplayer(Nextplayer);
        	 Setcurrentplayer();
        	 objMessage.setText(null);
        	 winnerMessage.setText(null);
    	     gameMessage.setText("Undo done. Click Next to Redo.");
     	     int posto =Movestomake.get(pointer-2);
     		 int posfrom =Movestomake.get(pointer-1);
     		 int result=Currentplayer.wasmovedone(posfrom, posto);
     		
     		 if(result==1)  //if single move
     		 {
             Currentplayer.Reversepiece(posfrom,posto,T,P);
             pointer=pointer-2;
             }
     		 else if(result==2 || result==3) //if single jump or multiple jumps
     		 {
     			Currentplayer.Reversepiece(posfrom,posto,T,P);
     			Currentplayer.Undojumps(P,T,Nextplayer);
     			pointer=pointer-2;
     		 }
     		 else if(result==-1 || result == 0) //if invalid move
     		 {
     			JOptionPane.showMessageDialog(null, "Invalid Move", "It was an Invalid Move.", JOptionPane.ERROR_MESSAGE);
     			pointer=pointer-2;
     			Currentplayer.switchplayer(Nextplayer);
            	Setcurrentplayer();
     		}
     		
     		 repaint();
    	}
	}
}
 


public void Readmovesfromfile() 
  //read moves from file and store it in arraylist movestomake
    {
    	try{
            BufferedReader buf = new BufferedReader(new FileReader(loc));
            String lineJustFetched = null;
            String[] wordsArray;

            while(true){
                try {
					lineJustFetched = buf.readLine();
					 } catch (IOException e1) {
					
					e1.printStackTrace();
				     }
                if(lineJustFetched == null){  
                    break; 
                }else{
                    wordsArray = lineJustFetched.split("-");
                    for(String each : wordsArray){
                        if(!"".equals(each)){
                            Movestomake.add(Integer.parseInt(each));
                      }
                    }
                }
                
            }

     }
    	catch (FileNotFoundException e1) {
       
        e1.printStackTrace();
     }
	   catch (InputMismatchException e1)
	 {
		e1.printStackTrace();
	 }	
    	
     }
  
    
  public void paint(Graphics g)
       {
	    super.paint(g);
	    
	     //Print Board
	    for(int i=0;i<8;i++)    
		{
		 for(int j=0;j<8;j++)
		  {
		  if((i%2)==(j%2))     
	        {
		       g.setColor(Color.white);
	        }
	     else
	        {
		       g.setColor(Color.gray);
	        }
		 g.fillRect((i*40)+20, (j*40)+38, 40, 40);
		   }
		}
	    //Print pieces
	    for(int i=0;i<8;i++)
	    {
	    	for(int j=0;j<8;j++)
	    	{
		       if(T[i][j].isoccupied()==true)
		       {
			  Piece p=T[i][j].getoccupant(P);
			  g.setColor(p.getcolor());
			  g.fillOval((p.getpiececol()*40)+30, (p.getpiecerow()*40)+40, 20, 20);    //Print pieces
			  if(p.isking())          //If piece is king
			         {
				       g.setColor(Color.cyan);
			    		g.drawString("K", (p.getpiececol()*40)+36, (p.getpiecerow()*40)+53);
			         }
		        }
	    	}
		  }
       }
		
		
  public Boolean checkgameover()
  {
  	if(R.getnumpieces()==0 || B.getnumpieces()==0)
  	{
  		return true;
  	}
  	return false;
  }



public void Setcurrentplayer()   
{
	if(R.iscurrentplayer())
	{
		Currentplayer=R;
		Nextplayer=B;
	}
	else
	{
		Currentplayer=B;
	   Nextplayer=R;
	}
}

public void Declarewinner()
{
	if (R.getnumpieces()>B.getnumpieces())
	{
		  winnerMessage.setText("Red Won!!");
	}
	else if(R.getnumpieces()==B.getnumpieces())
	{
		winnerMessage.setText("Its a draw Match!!");
	}
	else 
	{
		winnerMessage.setText("Black Won!!");
	}
		
}
}















	








	










