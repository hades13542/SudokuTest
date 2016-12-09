/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku3000;
/**
   *Klasa do dziedziczenia
   *@author Ryszard Mikulec
   *@version 1.0
*/
import org.fusesource.jansi.AnsiConsole;

abstract class Wyswietlane 
{
	protected static final String ZIELONY = "\u001b[32m";
	protected static final String NORMALNY = "\u001b[0m";
	protected static final String CZERWONY= "\u001b[31m";
	protected static final String NIEBIESKI= "\u001b[34m";
/**
   *Metoda czyszcząca okno teminala
*/
   public void wyczysc()
   {
      for(int i=0; i<50; i++)
         System.out.println();
   }
/**
   *Metoda wyświetlająca cały interfejs
   *@param komunikat wyświetlany na samym dole "okienka"
*/
   abstract void wyswietlKom(String komunikat);
}
