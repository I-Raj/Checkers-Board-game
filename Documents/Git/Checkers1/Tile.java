package checkers;

import java.util.ArrayList;

import checkers.Piece;
import checkers.Tile;

public class Tile {
private int row;
private int col;
private int position;
private boolean isoccupied;


public Tile(int i, int j, boolean b) {
	this.row=i;
	this.col=j;
	this.isoccupied=b;
}

//getters

public int getrow()
{
	return row;
}
public int getcol()
{
	return col;
}
public int getposition()
{
	return position;
}
public boolean isoccupied()
{
	return isoccupied;
}

//setters

public void setrowcol(int row,int col)
{
	this.row=row;
	this.col=col;
}

public void setisoccupied(boolean value)
{
	isoccupied=value;
}

public void setrowcol(int pos)
{
	int count=0;
	for(int i=0;i<8;i++)
	{
		for(int j=0;j<8;j++)
		{
		   if(i%2==j%2)
			{
			count=count+1;
			if(count==pos)
				{
				this.row=i;
				this.col=j;
				}
			}
			}
		}
}


public void setposition()
{
	int count=0;
	for(int i=0;i<8;i++)
	{
		for(int j=0;j<8;j++)
		{
			
			if(i%2==j%2)
			{
				count=count+1;
				if(row==i &&col==j)
				{
			      position=count;
				}
			
			}
		
	}
}
}

public Piece getoccupant(ArrayList<Piece> p2)
{
for(Piece p:p2)
{
	if(p.gettile().isoccupied==true && p.getpiecerow()==getrow() && p.getpiececol()==getcol())
	{
		return p;
	}
}
return null;
}


public Piece getoccupantat(Tile t,ArrayList<Piece> p2)
{
for(Piece p:p2)
{
	if(p.gettile().isoccupied==true && p.getpiecerow()==t.getrow() && p.getpiececol()==t.getcol())
	{
		return p;
	}
}
return null;
}

public Piece setOccupant(Piece p,int pos)
{
	p.gettile().setrowcol(pos);
    return p;
}
}