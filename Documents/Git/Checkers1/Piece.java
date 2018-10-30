package checkers;
import java.awt.Color;
import java.util.ArrayList;

public class Piece 
{
private boolean isking;
private Color color;
private Player player;
private Tile t;

public Piece(Player p,Color color)
{
	p=player;
	this.color=color;
}
public Piece(Player p,Tile t,Color color,int row,int col)
{
	p=player;
	this.t=t;
	t.setisoccupied(true);
	this.color=color;
	t.setrowcol(row, col);
}


public void Makeking(String currentplayer)  
{
 
	 if (currentplayer=="Red" && t.getrow()==0)   
	{
		isking=true;
		
	}
	else if (currentplayer=="Black" && t.getrow()==7)
	{
		isking=true;
		
	}
	 
	 
}

public void Checkking(String currentplayer,ArrayList<Integer> K,int posfrom,int posto)  // for previous button
{
	if(K.size()>0)
	{
	for(int i=0;i<K.size()-1;i=i+2)
	{
	if(K.get(i)==posto && K.get(i+1)==posfrom)
	{
		isking=false;
	}
	}
	}
}

public boolean isking()
{
	if(isking)
	{
		return true;
	}
	else
	{
		return false;
	}
}



//setters

public void settile(Tile tile)
{
	this.t=tile;
}

//getters

public String getplayer()
{
	if(color==Color.RED)
	{
		return "Red";
	}
	else
		return "Black";
}



public int getpiecerow()
{
	return t.getrow();
}


public int getpiececol()
{
	return t.getcol();
}


public Tile gettile()
{
	return t;
}

public Color getcolor()
{
	return color;
}
}

