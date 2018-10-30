package checkers;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Player {
private String player;
private int checkers;

private boolean currentplayer; // playing now if currentplayer=1
private int currentrow,currentcol,nextrow,nextcol;
private ArrayList <Integer> Movesdone=new ArrayList<Integer>(); //Stores info regarding moves made by player
private ArrayList <Integer> Kingmoves=new ArrayList<Integer>(); //Stores all king moves of player, used to undo king state.


public Player(String player) {
	checkers=0;
	this.player=player;
	if(player=="Red")
	{
		currentplayer=true;
	}
	else
		currentplayer=false;
	}


//getters

public int getnumpieces()
{
return checkers;
}

public String getplayer()
{
	if(player=="Red")
		return "Red";
	else
		return "Black";
}

public boolean iscurrentplayer()
{
	if(currentplayer==true)
		return true;
	else
		return false;
}


public void pieceadded()
{
	checkers++;
}


public void removePiece(Tile t,ArrayList<Piece> p2)
{
	Piece p=t.getoccupantat(t, p2);
    p2.remove(p);
    t.setisoccupied(false);
    checkers--;
}


public void switchplayer(Player next)
{
	
	if (this.currentplayer==true)
	{
		currentplayer=false;
		next.currentplayer=true;
	}
	else if(currentplayer==false)
	{
		currentplayer=true;
		next.currentplayer=true;
	
	}
}

public void Initialisecurrentnextstate(Tile T[][],int posfrom,int posto)
{
	//Returns row and col vales of positions
	  for(int i=0;i<8;i++)
	    {
	    	for(int j=0;j<8;j++)
	        {
	    	
	         if(T[i][j].getposition()==posto)
			 {
				 nextrow=i;	
				 nextcol=j;
			 }
	         if(T[i][j].getposition()==posfrom)
	         {
	        	 currentrow=i;
	        	 currentcol=j;
	         }
	        }
	    }
}

private boolean belongstoCurrentplayer(Piece p) {
	// Returns true if piece belongs to current player else returns false
	if(player=="Red" && p.getcolor()==Color.RED)
	{
		return true;
	}
	else if(player=="Black" && p.getcolor()==Color.BLACK) {
		return true;
	}
	return false;
}

private boolean belongstoNextplayer(Piece p) {
	//Returns true if piece belongs to opponent player else returns false
	if(player=="Red" && p.getcolor()==Color.BLACK)
	{
		return true;
	}
	else if(player=="Black" && p.getcolor()==Color.RED) {
		return true;
	}
	return false;
}

public void movepiece(Tile t,Piece p)
{
p.settile(t);
t.setisoccupied(true);
}


boolean isSingleJumpdone(int r1,int r2, int c1, int c2,Tile T[][], ArrayList<Piece> p2,Piece p,Player Nextplayer) 
{
	//Returns true if single jump is possible. Handled seperately for different types of pieces based on requirement.
	Piece opponent;
	if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8)
        return false; 
	
	if(!p.isking())
	{
		
	 if(r1 - r2 == 2 && p.getcolor()==Color.RED)   //Red: only forward right and left jump
	   {
	    
		 if((c1>c2) )
		   {
			 opponent=T[(r1-1)][(c1-1)].getoccupant(p2);
			 if((opponent!=null) && (belongstoNextplayer(opponent)))
			  {
	 	        Nextplayer.removePiece(T[(r1-1)][(c1-1)],p2);
	 	        return true;
		      }
		   }
		  else if((c2>c1))
	       {
		     opponent=T[(r1-1)][(c1+1)].getoccupant(p2);
			 if(opponent!=null && (belongstoNextplayer(opponent)))
			 {
			 Nextplayer.removePiece(T[(r1-1)][(c1+1)],p2);
			 return true;
	         }
	       }
		    else
		    {
		    	return false;
		    }
	    }
	    else if(r2-r1 ==2 && p.getcolor()==Color.BLACK) //Black: only backward right and left jump
	   {
	    	
		    if((c1>c2))
			 {
		    	opponent=T[(r1+1)][(c1-1)].getoccupant(p2);
		    	if(opponent!=null && (belongstoNextplayer(opponent)))
		    	{
		 	     // T[(r1+1)][(c1-1)].setisoccupied(false);
		 	      Nextplayer.removePiece(T[(r1+1)][(c1-1)],p2);
		 	      return true;
		    	}
			 }
		    else if(c2>c1)
		       {
			     opponent=T[(r1+1)][(c1+1)].getoccupant(p2); 
			     if(opponent!=null && (belongstoNextplayer(opponent)))
			     {
				 //T[(r1+1)][(c1+1)].setisoccupied(false);
				 Nextplayer.removePiece(T[(r1+1)][(c1+1)],p2);
				 return true;
			     }
		       }
			     else
			     {
			    	 return false;
			     }
			}
	 }
	else if(p.isking())    // King: Jumps in all 4 directions possible
	{
    if (r1-r2==2||r1-r2==-2)
	{
	
		if(c1>c2)
		{
			if((r1>r2))
			 {
				opponent=T[(r1-1)][(c1-1)].getoccupant(p2);    //check if the piece to jump over belongs to the opponent
				if(opponent!=null && (belongstoNextplayer(opponent)))
				{
		 	      Nextplayer.removePiece(T[(r1-1)][(c1-1)],p2);
		 	      return true;
				}
				else
					return false;
			  }
		else if((r2>r1) && (T[(r1+1)][(c1-1)].isoccupied()))
	     {
				opponent=T[(r1+1)][(c1-1)].getoccupant(p2); 
				if(opponent!=null && (belongstoNextplayer(opponent)))
				{
		 	      Nextplayer.removePiece(T[(r1+1)][(c1-1)],p2);
		 	      return true;
				}
				else
					return false;
			
			}
		}
		if(c2>c1)
		{
			if((r1>r2))
			{

				opponent=T[(r1-1)][(c1-1)].getoccupant(p2); 
				if(opponent!=null && (belongstoNextplayer(opponent)))
				{
		 	      //T[(r1-1)][(c1+1)].setisoccupied(false);
		 	      Nextplayer.removePiece(T[(r1-1)][(c1+1)],p2);
		 	      return true;
				}
				else
				return false;
			}
			else if((r2>r1))
			{

				opponent=T[(r1+1)][(c1+1)].getoccupant(p2); 
				
				if(opponent!=null && (belongstoNextplayer(opponent)))
				{
		 	     // T[(r1+1)][(c1+1)].setisoccupied(false);
		 	      Nextplayer.removePiece(T[(r1+1)][(c1+1)],p2);
		 	      return true;
				}
				else
					return false;
			
			}
		}
		}
	}
	 return false;
	}



public boolean isMultiplejumpdone(int r1,int r2, int c1, int c2,Tile T[][],ArrayList<Piece> p2, Piece p,Player Nextplayer)
  //returns true only if all single jumps btw the 2 positions are successful
{
	if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8)
        return false;
	
	int i=2;
	if(r2-r1>2)
	{
	while(r1<r2)
	{
		if((c2<c1)) 
		{
		        if((isSingleJumpdone(r1,r1+i,c1,c1-i,T,p2,p,Nextplayer)))  
	         	{
			         r1=r1+i;
			         c1=c1-i;
		         }
		        else
		         {
			return false;  
		         }
			
		}
	    if (c2>c1)
		{
				if((isSingleJumpdone(r1,r1+i,c1,c1+i,T,p2,p,Nextplayer)))
				{
				
					r1=r1+i;
					c1=c1+i;
				}
				else
				{
					return false;
				}
		}
	}
	if(r1==r2)
	{
		return true;
	}
	else
	{
	   return false;
	}
	
	}
	else if(r1-r2>2)
	{
	while(r1>r2)
	{
	   if(c2<c1) 
		{ 
		  		if (isSingleJumpdone(r1,r1-i,c1,c1-i,T,p2,p,Nextplayer))
		  		{
		  			r1=r1-i;
		  			c1=c1-i;
			
		  		}
		  		else
		  		{
		  			return false;
		  		}
		}
			
		if((c1<c2))
		{
				if((isSingleJumpdone(r1,r1-i,c1,c1+i,T,p2,p,Nextplayer)))
				{
					r1=r1-i;
					c1=c1+i;
				}
				else
				{
			   return false;
				}
	     }
     }
	if(r2==r1)  
	{
		return true;
	}
 }
	   
return false;
}
	


public boolean isValidmove(int r1,int r2,int c1, int c2,Tile movetot,Piece movep) 
{
	//Returns true if move is possible else returns false
	if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8)
	{
		
        return false;
	}
	

    if (movetot.isoccupied()==true)
    {
    		
    	   return false;  
    }
         

	if(movep.isking()==false && (c2-c1==1||c1-c2==1))
	     {
		  
		  if(player=="Red")
            {
	           if( movep.getcolor()==Color.red && (r1-r2==1))
	            {
	    	      return true;
                }
            }
            else if(player=="Black")
            {
	            if(movep.getcolor()==Color.BLACK && (r2-r1==1))
	              {
		          return true;
	              }
	         }
            
	         else
	         {
	        	 
	         return false;
	          }
	     }
	else if(movep.isking()==true && (c2-c1==1||c1-c2==1) && ((r1-r2==1)||(r2-r1==1)) )
	   {
    	 
	   if((player=="Red") && (movep.getcolor()==Color.red))
	    {
		 
    	 return true;
        }
        
       else if((player=="Black") && (movep.getcolor()==Color.BLACK))
	   {
    	   
		  return true;
	   }
	   else
       {
    	  
	   return false;
        }
	   }
	return false;

    }


public void makemove(int posfrom,int posto,Tile T[][],ArrayList<Piece> p2, Player Nextplayer)
{ 
	//To verify the type of move possible for piece from posfrom to posto and make the move along with handling other error states
	   boolean ismove=false,issinglejump=false,isdoublejump=false;
	   Initialisecurrentnextstate(T,posfrom,posto);
	   Piece p=T[currentrow][currentcol].getoccupant(p2);
	           if(p!=null && belongstoCurrentplayer(p) && T[nextrow][nextcol].isoccupied()==false) 
			    {
			    	if(nextrow-currentrow>2 || currentrow-nextrow>2 )  //double jump possible
					 {
			    		
					 if(isMultiplejumpdone(currentrow,nextrow,currentcol,nextcol,T,p2,p,Nextplayer))
					    {
						  
						         isdoublejump=true;	    
						         p.gettile().setisoccupied(false);
							     movepiece(T[nextrow][nextcol], p);
							     Recordmovementinfo(posfrom,posto,3);
							     if(nextrow==0 || nextrow==7)
							     {
							    	 p.Makeking(this.player);
							    if(p.isking()==true) {
							    	Kingmoves.add(posfrom);
							    	Kingmoves.add(posto);
							    }
							     }
							    
							     switchplayer(Nextplayer);
							    
							     
					    }
					 }
					 else if(nextrow-currentrow==2 || currentrow-nextrow==2 ) //single jump possible
					 {
					   if ((isSingleJumpdone(currentrow,nextrow,currentcol,nextcol,T,p2,p,Nextplayer)))
					    {
						    issinglejump=true;
						 p.gettile().setisoccupied(false);
					     movepiece(T[nextrow][nextcol], p);
					     Recordmovementinfo(posfrom,posto,2);
					     if(nextrow==0 || nextrow==7)
					     {
					      p.Makeking(this.player);
					      if(p.isking()==true) {
						    	Kingmoves.add(posfrom);
						    	Kingmoves.add(posto);
						    }
					     }
						     switchplayer(Nextplayer);
					    }
					 }
					 else if(nextrow-currentrow==1 || currentrow-nextrow==1)
					 {
					
					    if((isValidmove(currentrow,nextrow,currentcol,nextcol,T[nextrow][nextcol],p)))
				          {
					    	
						 ismove=true;
						 p.gettile().setisoccupied(false);
					     movepiece(T[nextrow][nextcol], p);
					     Recordmovementinfo(posfrom,posto,1);
					     if(nextrow==0 || nextrow==7)
					     {
					    	 p.Makeking(this.player);
					         if(p.isking()==true) {
						    	Kingmoves.add(posfrom);
						    	Kingmoves.add(posto);
						    }
					     }
					     switchplayer(Nextplayer);
                        }
					    
				   }
			    }
	    else
	    {
	                 if(p==null)
	                 {
	                	JOptionPane.showMessageDialog(null, "No Piece Found in specified location.", "No piece found in specified location.", JOptionPane.WARNING_MESSAGE);
	                 }
	                 	else if((belongstoCurrentplayer(p))==false){
	                 	JOptionPane.showMessageDialog(null, "Piece does not belong to current player.", "Piece does not belong to current player.", JOptionPane.WARNING_MESSAGE);
	                 }
	                 if(T[nextrow][nextcol].isoccupied()==true)
	                 {
	                	 JOptionPane.showMessageDialog(null, "You are trying to move piece to an already occupied tile.", "You are trying to move piece to an already occupied tile.", JOptionPane.WARNING_MESSAGE);
	                 }
	    }
		
	    if((ismove==false) && (issinglejump==false) && (isdoublejump==false))
		{
	     Recordmovementinfo(posfrom,posto,0);
	     JOptionPane.showMessageDialog(null, "Invalid Move!!", "Invalid Move.Click Next to try a different move.", JOptionPane.ERROR_MESSAGE);
		}
	   }
	 


//Below code for previous button implementation
public void Reversepiece(int posfrom,int posto,Tile T[][],ArrayList<Piece> P) //for previous button
{
	     Initialisecurrentnextstate(T,posfrom,posto);
		 Piece p=T[currentrow][currentcol].getoccupant(P);
		 T[currentrow][currentcol].setisoccupied(false);
		 movepiece(T[nextrow][nextcol],p);
		 if(p.isking())
		   { p.Checkking(player,Kingmoves,posfrom,posto);}
}


public void Recordmovementinfo(int posfrom,int posto,int flag)
{   //Movesdone arraylist stores all the moves done 
	// invalid_move=0,valid_move=1,single_jump=2,double_jump=3;
	
	Movesdone.add(posfrom);
	Movesdone.add(posto);
	Movesdone.add(flag);
	
}

public int wasmovedone(int posfrom,int posto)
{
	//check Movesdone arraylis if there was any move from posfrom to posto. 
	// If true return value based on type of move
	
	for(int i=0;i<Movesdone.size()-2;i=i+3)
	{
		if(Movesdone.get(i)==posto &&  Movesdone.get(i+1) == posfrom)
		     {
			return Movesdone.get(i+2);
		     }
	}
	return -1;
}


public void Undojumps(ArrayList <Piece> P,Tile T[][],Player Nextplayer)
{
	//Identify cells to create new pieces in case of single or multiple jumps
	if(currentrow>nextrow)
	{
		int k=(currentrow-nextrow);
		 if(currentcol>nextcol)
		 {
		      for(int i=1;i<=k;i=i+2)	 
		      {
		    
		    	  Piece p= addpiece(P,Nextplayer);
		    	  p.settile(T[currentrow-(i)][currentcol-(i)]);
		    	  T[currentrow-(i)][currentcol-(i)].setisoccupied(true);
		    	  
		      }
		 }
		 else
		 {
			 for(int i=1;i<=k;i=i+2)	 
		      {
				  Piece p= addpiece(P,Nextplayer);
		    	  p.settile(T[currentrow-i][currentcol+i]);
		    	  T[currentrow-i][currentcol+i].setisoccupied(true);
		      }
		 }
	}
	else
	{
		int k=(nextrow-currentrow);
		if(currentcol>nextcol)
		 {
			 for(int i=1;i<=k;i=i+2)	 
		      {
		    	  Piece p= addpiece(P,Nextplayer);
		    	  p.settile(T[currentrow+(i)][currentcol-(i)]);
		    	  T[currentrow+(i)][currentcol-(i)].setisoccupied(true);
		    
		      }
		 }
		else
		 {
			 for(int i=1;i<=k;i=i+2)	 
		      {
		    	  Piece p= addpiece(P,Nextplayer);
		    	  p.settile(T[currentrow+(i)][currentcol+(i)]);
		    	  T[currentrow+(i)][currentcol+(i)].setisoccupied(true);
		    	
		      } 
		 }
	}
}

public Piece addpiece(ArrayList <Piece> P,Player Nextplayer)
//Method to add new piece to arraylist P
{
	Color c;
	if(player=="Red")
	{
		 c=Color.BLACK;
	}
	else
	{
		c=Color.RED;
	}
   Piece p=new Piece(this,c);
   P.add(p);
   Nextplayer.pieceadded();
   return p;
  
}
}



