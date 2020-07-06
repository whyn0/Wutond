package proj.map.progettoMap1920.adventure.fileInitializer;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import proj.map.progettoMap1920.adventure.parser.grammar.Production;
import proj.map.progettoMap1920.adventure.parser.grammar.ProductionSide;

public class GrammarInit {
  private List<String> nTerminals = new ArrayList<>();
  private List<String> terminals = new ArrayList<>();
  private List<Production> productions = new ArrayList<>();

  public GrammarInit(String path) throws FileNotFoundException, IOException {
    grammarReader(path);
  }

  private void grammarReader(String filename) throws FileNotFoundException, IOException {
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
      while ((inputLine = buffer.readLine()) != null) {
        if (count == 0) {
          while (!(inputLine = buffer.readLine()).equals("=====NTERMINALS=====")) {
            inputLine.trim();
            terminals.add(inputLine);
          }
        }

        count++;
        if (count == 1) {
          while (!(inputLine = buffer.readLine()).equals("=====CNF=====")) {
            inputLine.trim();
            nTerminals.add(inputLine);
          }
        }

        count++;
        if (count == 2) {
          inputLine = buffer.readLine();
          inputLine.trim();
        }
        inputLine.trim();
        tokenized = inputLine.split(" -> ");
        tokenized1 = tokenized[1].split("\\s");
        if (tokenized1.length > 1) {
          productions.add(new Production(new ProductionSide(tokenized[0]), new ProductionSide(tokenized1[0], tokenized1[1])));
        } else {
          productions.add(new Production(new ProductionSide(tokenized[0]), new ProductionSide(tokenized[1])));
        }

      }

    } catch (EOFException e) {

    }
    file.close();
  }

  public List<String> getnTerminals() {
    return nTerminals;
  }

  public List<String> getTerminals() {
    return terminals;
  }

  public List<Production> getProductions() {
    return productions;
  }

}
