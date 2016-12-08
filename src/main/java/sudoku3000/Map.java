/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku3000;
import java.util.*;
import java.io.Serializable;

public class Map implements Serializable
{
	protected int[][] board = new int[9][9];
/**
*Sprawdza czy sudoku jest poprawnie wypelnione
*Czy ilosc wierszy i kolumn jest rowna 9
*Czy liczby nie koliduja w wierszach
*Czy liczny nie koliduja w kolumnach
*Czy liczby nie koliduja w blokach 3x3
*@return true jesli wszystko jest poprawne
*/
	public boolean validator()  
	{
        	if(this.board.length != 9) 
		{
            	   return false;
		}
        	for(int i=0; i<9; i++) 
		{
          		if(this.board[i].length != 9)
			{
                		return false;
			}
       		 }
        	boolean[] boo = new boolean[10]; 
        
        	for(int i = 0; i<9; i++)  
		{
           		for(int j = 0; j<9; j++) 
			{
                		if(this.board[i][j] == 0)
				{
                    			continue;
				}
                		if(boo[this.board[i][j]])
				{
                    			return false;
				}
                		boo[this.board[i][j]] = true;
            		}
            		Arrays.fill(boo, false);
        	}
        
        	for(int i = 0; i<9; i++)  
		{
            		for(int j = 0; j<9; j++) 
			{
                		if(this.board[j][i] == 0)
				{
                   			continue;
				}
                		if(boo[this.board[j][i]])
				{
                    			return false;
				}
                		boo[this.board[j][i]] = true;
           		}
            		Arrays.fill(boo, false);
        	}
        
        	for(int i=0; i<9; i+=3)  
		{
            		for(int j=0; j<9; j+=3)
			{
                		for(int k=0; k<3; k++) 
				{
                    			for(int l=0; l<3; l++) 
					{
                        			if(this.board[i+k][j+l] == 0)
						{
                            				continue;
						}
                        			if(boo[this.board[i+k][j+l]])
						{
                            				return false;
						}
                        			boo[this.board[i+k][j+l]] = true;
                    			}
                		}
               			Arrays.fill(boo, false);
            		}
        	}
        	return true;
    	}

	public void printBoard() //wypisuje tablice
	{
		for(int i = 0; i<9; i++) 
		{
			for(int j = 0; j<9; j++) 
			{
				System.out.print(this.board[i][j] + " ");
			}	
			System.out.println();
		}
	}
/**
*funkcja wstawia podaną cyfrę pod podane wspołrzędne
*@param x - wiersz
*@param y - kolumna
*@param z - cyfra do wstawienia
*/
	public void setBoard(int x, int y, int z)		
	{
		this.board[x][y] = z;
	}
}
