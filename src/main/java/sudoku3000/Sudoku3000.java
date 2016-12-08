/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku3000;

import java.util.*;
import java.util.Scanner;
import java.lang.System;
import java.io.File;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.Collections;
import java.io.Serializable;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Scanner;
import java.lang.System;
import java.io.File;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.Collections;
import java.io.Serializable;
import java.io.FileNotFoundException;

/**
*klasa realizująca rozgrywkę
*/

public class Sudoku3000
{
/**
	*W funkcji main wybieramy etap, na którym jest rozgrywka
	*działa w pętli do czasu, aż nie zostanie wybrana opcja wyjście
	*@param args argumenty
*/
   public static void main(String[] args) throws Exception
   {
      	Scanner in = new Scanner(System.in);
      	Menu menu = new Menu(new String[] {"1)Nowa gra", "2)Wczytaj grę", "3)Wyjście"});							//użytkownik znajduje się w menu
      	Menu nowaGra = new Menu(new String[] {"1)poziom łatwy", "2)poziom średni", "3)poziom trudny", "4)gra własna"});			//użytkownik wybrał nową grę
     	Menu wyjscie = new Menu(new String[] {"Miłego dnia!"});					//użytkownik wybrał wyjscie - po wybraniu tej opcji pętla kończy swoje działanie wraz z programem
      	Menu wczytajGre = new Menu();								//użytkownik wybrał opcje wczytania poprzednio zapisanej gry 
	Menu gramy = new Menu();								//toczy się rogrywka
      	Wyswietlane etap = menu;								//zmienna przechowująca informacje na jakim etapie jesteśmy
      	String komunikat = new String("Menu");							//komunikat wyświetlany w dolnym lewym rogu
      	Gra newGame = new Gra();					
	Interfejs interfejs = new Interfejs();
	String kat="";
	while(etap != wyjscie)
	{
		etap.wyczysc();
        	etap.wyswietlKom(komunikat);
		String wyb ="";
		wyb = new String(in.next());
		int wybor = 0;
		if(wyb.length() == 1 && wyb.codePointAt(0)>47 && wyb.codePointAt(0) < 57)
		{
			wybor = Character.getNumericValue(wyb.codePointAt(0));
		}
        	if(etap == menu)
		{
        		switch(wybor)			//menu glowne
            		{
               			case 1:
                  			etap = nowaGra;
                  			komunikat = new String("Nowa Gra");
                 		 	break;
              			case 2:
		  			kat = new String(wczytajGre.wczytaj());
					if(kat.length()==1 && kat.charAt(0)=='M')
					{
                  				etap = menu;
						komunikat = new String("Menu");
					}
					else
					{
						etap = wczytajGre;
						komunikat = new String("Wczytaj Gre");
					}
                 			
                  			break;
               			case 3:
                  			etap = wyjscie;
                  			komunikat = new String("");
                  			break;
               			default:
                  			komunikat = new String("Zła opcja, wybierz ponownie");
                  
            		}
		}
        	else if(etap == nowaGra)
		{
        		switch(wybor)			//menu z wyborem poziomu trudnosci
            		{
               			case 1:
	    	 			newGame.loadMap();
	   	 			newGame.swapRows();
	   	 			newGame.swapColumns();
	   	 			newGame.setPodpowiedzi(3);
	   	 			newGame.kebabRemover(70);
					newGame.prepare();
					komunikat = new String("Poziom Latwy");
					newGame.setPoziom(new String(komunikat));
					etap = gramy;
                  			break;
               			case 2:
	    				newGame.loadMap();
	   	 			newGame.swapRows();
	   	 			newGame.swapColumns();
	   	 			newGame.setPodpowiedzi(2);
	   				newGame.kebabRemover(50);
					newGame.prepare();
					komunikat = new String("Poziom Sredni");
					newGame.setPoziom(new String(komunikat));
					etap = gramy;
                  			break;
               			case 3:
	    	 			newGame.loadMap();
	   	 			newGame.swapRows();
	   	 			newGame.swapColumns();
	   	 			newGame.setPodpowiedzi(1);
	   	 			newGame.kebabRemover(20);
					newGame.prepare();
					komunikat = new String("Poziom Trudny");
					newGame.setPoziom(new String(komunikat));
					etap = gramy;
					break;
               			case 4:
					Menu wlasnaGra = new Menu(new String [] {"Prosze wprowadzić sudoku lub ścieżkę do pliku", "(wówczas jako pierwsze dwa znaki wprowadź F:)","Puste miejsca oznaczamy zerami.","Jako separatora można użyć", "dowolnego znaku nie będącego cyfrą"});
					etap=wlasnaGra;
					boolean czypop=false;
					komunikat=new String("Własna gra");
        				etap.wyczysc();
        				etap.wyswietlKom(komunikat);
					Scanner wlasna = new Scanner(System.in);
					komunikat = new String("Wlasna Gra");
					while(!czypop)
					{	
						etap=wlasnaGra;
						String wlasneSudoku = new String(wlasna.next());
						if(wlasneSudoku.charAt(0) == 'F' && wlasneSudoku.charAt(1) == ':')
						{
							try
							{
								String filename = wlasneSudoku.substring(2, wlasneSudoku.length());
								BufferedReader read = new BufferedReader(new FileReader(filename));
								String sudokuPlik = new String("");
								String x="";
								do
								{
									sudokuPlik += new String(x);
									x = read.readLine();
								}
								while(x != null);
								wlasneSudoku = new String(sudokuPlik);
							}
							catch(FileNotFoundException e)
							{
								komunikat = new String("Nie znaleziono pliku");
								etap.wyczysc();
        							etap.wyswietlKom(komunikat);
								czypop = false;
								continue;
							}
						}	
						if(wlasneSudoku.length() == 1 && wlasneSudoku.charAt(0) == 'M')
						{
							etap = menu;
							komunikat = new String("Menu");
							break;
						}
						if(!(czypop = newGame.loadCon(new String(wlasneSudoku))))
						{
							etap.wyczysc();
        						etap.wyswietlKom("zla ilosc cyfr, sprobuj ponownie");
							continue;
						}	
						czypop = newGame.validator();		//sprawdzamy czy podane przez uzytkownika sudoku nie jest sprzeczne z zasadami gry
						if(!czypop)	
						{
							komunikat = new String("Wprowadzone sudoku jest wbrew zasadom gry. Spróbuj ponownie");
						}
						else if(newGame.countSolutions(newGame.returnPuzzle())>1)	//sprawdzamy czy sudoku jest jednoznaczne
						{
							komunikat=new String("Podane sudoku jest niejednoznaczne. Sprobuj ponownie");
							czypop = false;
						}
						else if(newGame.czyRoz())				//sprawdzamy czy sudoku jest juz rozwiazane
						{
							komunikat= new String("Podane sudoku jest już rozwiązane. Gratulacje, wygrales ;)");
							etap.wyczysc();
        						etap.wyswietlKom(komunikat);
							System.exit(0);
						}
						else
						{
                                                        newGame.solveSudoku(newGame.returnPuzzle());
                                                        newGame.setPoziom(new String("Poziom wlasny"));
							etap.wyczysc();
        						etap.wyswietlKom("wprowadzanie zakonczono sukcesem! Ile chcesz miec podpowiedzi?:");
							komunikat = new String("wprowadzanie zakonczono sukcesem!");
						}
						etap.wyczysc();
        					etap.wyswietlKom(komunikat);
						etap = gramy;
					}
                  			break;
               			default:
					if(wyb.length()==1 && wyb.charAt(0)=='M')
					{
                 				komunikat = new String("Menu");
						etap = menu;
					}
					else
					{
						komunikat = new String("Zła opcja, wybierz ponownie");
					}
   
            		}
	 	}
         	else if(etap == wczytajGre)
		{
			CharSequence tmp = File.separator;
			//if(!kat.contains(tmp))	//sprawdzamy czy nazwa zawiera separator
			//{
				kat += File.separator;	//jeśli nie to go dodajemy 
			//}
			try
			{
           			newGame = Gra.wczytaj(new String(kat + wczytajGre.nazwa(wybor)));	//wczytujemy gre 
				if(newGame != null)							//sprawdzamy czy gra się wczytała poprawnie
				{	
					etap=gramy;											//jeśli tak to ustawiamy etap na gramy 
					komunikat = new String("wczytano:" + wczytajGre.nazwa(wybor) + ", " + newGame.getPoziom());	//i zmieniamy komunikat
				}
				else
				{
					komunikat = new String("Błąd wczytywania, spróbuj ponownie");
				}
			}
			catch(ArrayIndexOutOfBoundsException e)			//podana zostala pozycja, która nie istnieje
			{
				komunikat = "Brak takiej pozycji";
			}
			if(wyb.length()==1 && wyb.charAt(0)=='M')
			{
				etap = menu;
				komunikat = new String("Menu");
			}	
	 	}
		if(etap == gramy)
		{
                        newGame.timeAtStart=System.currentTimeMillis();
			interfejs.setInterfejs(newGame);
			interfejs.wyczysc();
		       	interfejs.wyswietl(komunikat, newGame.returnPuzzle(), newGame.getOryginal());
            		while(interfejs.pobierzPolecenie());			//rozpoczecie wlasiwej gry
			etap = menu;
		}
	}
   }
}
