package proj.map.progettoMap1920.adventure.fileInitializer;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import proj.map.progettoMap1920.adventure.parser.Production;
import proj.map.progettoMap1920.adventure.parser.ProductionSide;

public class GrammarInit {
  List<String> nTerminals = new ArrayList<>();
  List<String> terminals = new ArrayList<>();
  List<Production> productions = new ArrayList<>();
  public GrammarInit() {
    // TODO Auto-generated constructor stub
  }
  public void grammarReader(String filename)throws FileNotFoundException, IOException{
    // -----------------------------
    FileReader file;
    BufferedReader buffer;
    file = new FileReader(filename);
    buffer = new BufferedReader(file);
    // -----------------------------
    String inputLine;
    String[] tokenized;
    String[] tokenized1;
    int count = 0;
    try {
      while((inputLine = buffer.readLine()) != null) {
        if(count == 0) {
          while(!(inputLine = buffer.readLine()).equals("=====NTERMINALS=====")) {
            terminals.add(inputLine);
          }
        }
        
        count++;
        if(count == 1) {
          while(!(inputLine = buffer.readLine()).equals("=====CNF=====")) {
            nTerminals.add(inputLine);
          }
        }
        
        count++;
        if(count == 2) {
          inputLine = buffer.readLine();
        }
        tokenized = inputLine.split(" -> ");
        tokenized1 = tokenized[1].split("\\s");
        if(tokenized1.length > 1) {
          productions.add(new Production(new ProductionSide(tokenized[0]),new ProductionSide(tokenized1[0],tokenized1[1])));
        }
        else {
          productions.add(new Production(new ProductionSide(tokenized[0]),new ProductionSide(tokenized[1])));
        }

      }

    }
    catch(EOFException e) {
      
    }
    file.close();
  }

}
