/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku3000;
import java.util.Arrays;
import java.io.Serializable;

public class Solver extends Map implements Serializable
{
	protected int numberOfSolutions; //licznik rozwiazan
	protected int[][] solution = new int[9][9]; //tablica przechowujaca rozwiazane sudoku
/**
*Metoda rozpoczynajaca rozwiazywanie sudoku
*@param sudo tablica zawierajaca sudoku do rozwiazania
*/
    public void solveSudoku(int[][] sudo)
	{
        	solve(sudo, 0);       
    	}
/**
*Metoda wywolywana rekurencyjnie do rozwiazywania sudoku
*jesli doszlismy do konca tablicy, rozwiazanie jest kopiowane do tablicy solution
*@param sudo tablica zawierajaca sudoku do rozwiazania
*@param index indeks aktualnie przetwarzanego pola
*/	
	protected void solve(int[][] sudo, int index) //przyjmuje sudoku i indeks od ktorego zaczynamy rozwiazywanie
	{
		if(index == 81)
		{
			for(int i=0; i<9; i++) 
			{
				System.arraycopy(sudo[i], 0, solution[i], 0, 9);
			}
		}
		else
		{
			int row = index / 9;
			int column = index % 9;
			if(sudo[row][column] !=0)
			{
				solve(sudo, index+1); 
			}
			else
			{
				for(int i = 1; i<=9; i++)
				{
					if(canBePlaced(sudo, row, column, i)) 
					{
						sudo[row][column]=i;
						solve(sudo, index+1); 
						sudo[row][column]=0; 
					}
				}
			}	
		}
	}    

/**
*Metoda rozpoczynajaca zliczanie rozwiazan
*@param sudo tablica zawierajaca sudoku do przeliczenia rozwiazan
*@return liczba mozliwych rozwiazan
*/
	public int countSolutions(int[][] sudo) 
	{
		numberOfSolutions=0;
        	counter(sudo, 0);
        	//System.out.println("Number of Solutions: " + numberOfSolutions);
        	return numberOfSolutions;
	}
/**
*Metoda wywolywana rekurencyjnie do zliczania rozwiazan
*jesli doszlismy do konca tablicy licznik jest zwiekszany 
*a funkcja wraca w rekurencji na poziom wyzej
*@param sudo tablica zawierajaca sudoku do przeliczenia rozwiazan
*@param index indeks aktualnie przetwarzanego pola
*/	
	protected void counter(int[][] sudo, int index) 
	{
		if(index == 81) 
		{
			numberOfSolutions++;
		}
		if(index<81 && index!=-1) 
		{
			int row = index / 9;
			int column = index % 9;
			if(sudo[row][column] !=0) 
			{
				counter(sudo, index+1);
			}
			else
			{
				for(int i = 1; i<=9; i++)
				{
					if(canBePlaced(sudo, row, column, i)) 
					{
						sudo[row][column]=i;
						if(index <81) 
						{
							counter(sudo, index+1);
						}
						sudo[row][column]=0;
					}
				}
			}	
		}
	}
/**
*Metoda sprawdzajaca czy dana cyfra moze zostac wstawiona w dane miejsce.
*Sprawdza czy cyfra nie bedzie kolidowac z innymi cyframi w kolumnie/wierszu/bloku 3x3
*@param sudo tablica w ktorej sprawdzamy cyfre
*@param row wiersz w ktory chcemy wstawic cyfre
*@param colmun kolumna w ktory chcemy wstawic cyfre
*@param num cyfra ktora chcemy wstawic
*@return true jesli mozna wstawic cyfre w dane miejsce
*/	
	boolean canBePlaced(int[][] sudo, int row, int column, int num) 
	{
		for(int i = 0; i < 9; i++)
		{
        		if(sudo[row][i] == num) 
			{			
				return false;
			}
        		if(sudo[i][column] == num) 
			{
				return false;
			}
		
        	}
		int rowStart = row - row % 3; 
       		int colStart = column - column % 3;
       		for(int m = 0; m < 3; m++)
		{
           		for(int k = 0; k < 3; k++)
			{
               			if(sudo[rowStart + k][colStart + m] == num)
				{
					return false;
				}
           		}
       		}
       		return true;
	}
}
