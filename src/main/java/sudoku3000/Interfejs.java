/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku3000;
import java.util.*;
import java.io.File;
import org.fusesource.jansi.AnsiConsole;
/**
   *Klasa ogsługująca interfejs
   *@author Ryszard Mikulec
   *@version 1.0
*/
public class Interfejs extends Wyswietlane
{

   Interfejs(){}
   private Gra obecnaGra;
/**
   *Funkcja przypisująca interfejs do gry
   *@param gra gra której dotyczy interfejs
*/
   public void setInterfejs(Gra gra)
   {
      this.obecnaGra = gra;
   }
	
   private int rozmiarX = 70,
   rozmiarY = 21,
   liniaP = 5,
   rozmiarPY = 12,
   rozmiarPX = 24;
   public void setLiniaP(int liniaP) {
	this.liniaP = liniaP;
}
private String miniInstrukcja = new String("Format wprowadzania danych: [wiersz]x[kolumna]:[cyfra], np. 2x4:6"); //miejsce na jakas instrukcje albo cos innego
   private String[] menuBoczne = new String[] {"(Z)apisz grę", "(P)okaż rozwiązanie", "P(o)dpowiedź", "(M)enu główne"};
   private Scanner in = new Scanner(System.in);

   public void setMenuBoczne(String[] menuBoczne) {
	   this.menuBoczne=menuBoczne;
   }
   
   public void setObecnaGra(Gra gra) {
	   this.obecnaGra=gra;
   }
/**
   *Metoda wyświetlająca planszę
   *@param numery linini w któeych ma być wyświetlona plansza
*/
   public void wyswietlPlansze(int linia, int[][] sudo, int [][]sudo2)//raczej nie będzie trzeba tego ruszać chyba że będziecie chcieli zmienić trochę ustawienie
   {
      for(int j=0; j<62; j++)
         System.out.print('\b');
      if((linia-liniaP)%4==0)
         System.out.print("+-------+-------+-------+");
      else
      {
         for(int j=0; j<9; j++)
         {
            if(j%3==0)
               System.out.print("| ");
            if(sudo[(linia-liniaP)*3/4][j]==0)
               System.out.print("  ");
            else
	    {
		if(sudo2 != null && sudo2[(linia-liniaP)*3/4][j] != 0)
		{
				AnsiConsole.systemInstall();
				AnsiConsole.out.print( ZIELONY + sudo[(linia-liniaP)*3/4][j] + NORMALNY);
               	AnsiConsole.systemUninstall();
			System.out.print(" ");
		}
		else
		{
			System.out.print(sudo[(linia-liniaP)*3/4][j]+" ");
		}
            }
         }
         System.out.print('|');
      }
      if(linia >= liniaP+rozmiarPY/3 && linia < liniaP+rozmiarPY/3+menuBoczne.length)
         System.out.print("\t"+menuBoczne[linia-liniaP-rozmiarPY/3]);
         if(linia-liniaP-rozmiarPY/3==2)
             System.out.print(": " + obecnaGra.getPodpowiedzi()+" dostepne");
   }
/**
   *Metoda obsługująca komunikacje z użytkownikiem i realizująca rogrywkę
   *@return true jeśli należy kontynuować grę
*/
   public boolean pobierzPolecenie() throws Exception	
   {
	if(obecnaGra.czyWin())				//sprawdzamy czy sudoku jest już wygrane
	{
		wyczysc();
        	wyswietl(new String("Gratulacje!! Wygrales!! Gra zajela ci: " + (System.currentTimeMillis()-obecnaGra.timeAtStart)/1000 + "s"), this.obecnaGra.returnPuzzle(), this.obecnaGra.getOryginal());

	}
	String x = new String(in.next());
	System.out.print(x.length());
	if(x.length()==5)
	{
		if(x.codePointAt(0)>48 && x.codePointAt(0)<58 && x.codePointAt(2)>48 && x.codePointAt(2)<58 && x.codePointAt(4)>47 && x.codePointAt(4)<58 && x.codePointAt(1)==120 && x.codePointAt(3)==58)	//sprawdzamy czy wprowadzony string jest zgodny z formatem, ktory sobie ustalilismy
		{
			if(this.obecnaGra.czyMozna(Character.getNumericValue(x.codePointAt(0))-1, Character.getNumericValue(x.codePointAt(2))-1))	//sprawdza czy graczowqi wolno edytowac podane pole
			{
				this.obecnaGra.setBoard(Character.getNumericValue(x.codePointAt(0))-1, Character.getNumericValue(x.codePointAt(2))-1, Character.getNumericValue(x.codePointAt(4)));
				wyczysc();
         			wyswietl(new String("Wykonano: " + x), this.obecnaGra.returnPuzzle(), this.obecnaGra.getOryginal());
				return true;
			}
			else
			{ 
				wyczysc();
         			wyswietl(new String("Nielegalny ruch, FBI w drodze..."), this.obecnaGra.returnPuzzle(), this.obecnaGra.getOryginal());
				return true;
			}
		}
		wyczysc();
         	wyswietl(new String("Bledne dane lub format"), this.obecnaGra.returnPuzzle(), this.obecnaGra.getOryginal());
		return true;
		
	}
	else if(x.length() == 1)
	{
		if(x.charAt(0)=='o')		//wybrano podpowiedz
		{
			String temp = new String(this.obecnaGra.losowaPodpowiedz());		
			wyczysc();
         		wyswietl(new String(temp), this.obecnaGra.returnPuzzle(), this.obecnaGra.getOryginal());
			return true;
		}
		else if(x.charAt(0)=='P')	//wybrano pokazanie rozwiazania
		{
			wyczysc();
         		wyswietl(new String("Dowolny znak, aby kontunuowac"), this.obecnaGra.returnSolution(), this.obecnaGra.getOryginal());
			in.next();
			wyczysc();
         		wyswietl(new String(this.obecnaGra.getPoziom()), this.obecnaGra.returnPuzzle(), this.obecnaGra.getOryginal());
			return true;
		}
		else if(x.charAt(0)=='M')	//wybrano powrot do Menu
		{
			return false;
		}
		else if(x.charAt(0)=='Z')	//wybrano zapis
		{
			wyczysc();
        		wyswietl(new String("Podaj nazwe pliku:"), this.obecnaGra.returnPuzzle(), this.obecnaGra.getOryginal());
			String nazwa = in.next();		//gracz wprowadza nazwe pliku
			boolean czymozna = true;
			if(nazwa.length()==1 && nazwa.charAt(0)=='*')	//jeśli zostanie wprowadzona gwiazdka to plik jest nadpisywany
			{
				if(this.obecnaGra.getName().length()==0)
				{
					czymozna = false;
				}
				else
				{
					czymozna = true;
					nazwa = new String(this.obecnaGra.getName());
				}
			}
			CharSequence tempo = File.separator;
			if(!nazwa.contains(tempo))
			{
                            String katalog = System.getenv("APPDATA");
                            if (katalog == null) {
                                katalog = System.getProperty("user.home");
                            }
                             File HOME = new File(katalog, "Sudoku3000").getAbsoluteFile();
                             HOME.mkdirs();
				nazwa = katalog + File.separator + "Sudoku3000" + File.separator + nazwa;
			}
			if(czymozna)
			{
				if(obecnaGra.zapisz(new String(nazwa)))
				{
					wyczysc();
         				wyswietl(new String(nazwa + " - zapisano"), this.obecnaGra.returnPuzzle(), this.obecnaGra.getOryginal());
				}
				else
				{
					wyczysc();
         				wyswietl("błąd zapisu", this.obecnaGra.returnPuzzle(), this.obecnaGra.getOryginal());
				}
			}
			else
			{
				wyczysc();
         			wyswietl("Brak nazwy pliku, prosze podac nazwe pliku", this.obecnaGra.returnPuzzle(), this.obecnaGra.getOryginal());
			}
			return true;		
		}
		wyczysc();
        	wyswietl(new String("Brak takiej opcji, sprobuj ponownie:"), this.obecnaGra.returnPuzzle(), this.obecnaGra.getOryginal());
		return true;
	}
	wyczysc();
        wyswietl(new String("Brak takiej opcji, sprobuj ponownie:"), this.obecnaGra.returnPuzzle(), this.obecnaGra.getOryginal());
	return true;	
   }

/**
   *Metoda wyświetlająca cały interfejs
   *@param komunikat wyświetlany na samym dole "okienka"
   *@param sudo sudoku do wyświetlenia
*/
   public void wyswietl(String komunikat, int[][] sudo,  int[][] sudo2) throws Exception//raczej tylko niewielkie modyfikacje czy dodatki
   {
      //mozna dolozyc jeszcze jakies informacje o tym ile zostalo podpowiedzi i ewentualnie pasek postepu
      //troche nagmatwane(wiem moglem podzielic to na jakies drobniejsze moduly) wiec jak cos to sam to moge dolozyc
      int licznik = 0;
      System.out.print('+');
      for(int i=0; i<rozmiarX-2; i++)
         System.out.print('-');
      System.out.println('+');
      for(int i=1; i<rozmiarY-1; i++)
      {
         System.out.print('|');
         for(int j=0; j<rozmiarX -2; j++)
            System.out.print(' ');
         System.out.print('|');
         if(i==1)
         {
            for(int j=0; j<45; j++)
               System.out.print('\b');
            System.out.print("Sudoku ver 1.0");
         }
         if(i==3)
         {
            for(int j=0; j<rozmiarX-1; j++)
               System.out.print('\b');
            System.out.print(miniInstrukcja);
         }
         if(i==rozmiarY-2)
         {
            for(int j=0; j<rozmiarX-1; j++)
               System.out.print('\b');
		    AnsiConsole.systemInstall();
			AnsiConsole.out.print( NIEBIESKI + komunikat + NORMALNY);
            AnsiConsole.systemUninstall();
         }
         if(i>=liniaP && i<=liniaP+rozmiarPY)
            wyswietlPlansze(i, sudo, sudo2);
         System.out.print('\n');
      }
      System.out.print('+');
      for(int i=0; i<rozmiarX -2; i++)
         System.out.print('-');
      System.out.println('+');
   }
   void wyswietlKom(String komunikat){}
}
