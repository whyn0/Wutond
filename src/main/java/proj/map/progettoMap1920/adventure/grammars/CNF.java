package proj.map.progettoMap1920.adventure.grammars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CNF {
  private static final int MAX = 20;
  private static String[][] grammar = new String[MAX][MAX]; // store grammar
  private static String[] dpr = new String[MAX];
  private static int p; // np-> number of productions
  private static int np;
  private String input;
  private String string;
  private int lineCount;
  private String epsilonFound = "";
  // map variable with production ( variable -> production)
  private Map<String, List<String>> mapVariableProduction = new LinkedHashMap<>();// mappa avra key variable, value una lista di produzioni

  public CNF() {
    setInputandLineCount("S -> aAb | ab" + "\n" + "A -> ab",2);
    convertStringtoMap();
    convertCFGtoCNF();
    System.out.print("ciao");
  }

  public void setString(String string) {
    this.string = string;
  }

  public void setInputandLineCount(String input, int lineCount) {
    this.input = input;
    this.lineCount = lineCount;

  }

  public Map<String, List<String>> getMapVariableProduction() {
    return mapVariableProduction;
  }

  private void insertNewStartSymbol() {

    String newStart = "S0";
    List<String> newProduction = new ArrayList<>();
    newProduction.add("S");

    mapVariableProduction.put(newStart, newProduction);
  }

  private String[] splitEnter(String input) {// split tutto l'input col newLine

    String[] tmpArray = new String[lineCount];
    for (int i = 0; i < lineCount; i++) {
      tmpArray = input.split("\\n");
    }
    return tmpArray;
  }

  private void convertStringtoMap() {

    String[] splitedEnterInput = splitEnter(input);

    for (int i = 0; i < splitedEnterInput.length; i++) {

      String[] tempString = splitedEnterInput[i].split("->|\\|");// from S -> Ab|b|e
      String variable = tempString[0].trim();// trim spazio dopo varabile

      String[] production = Arrays.copyOfRange(tempString, 1, tempString.length);// copio le produzioni
      List<String> productionList = new ArrayList<>();

      // trim the empty space
      for (int k = 0; k < production.length; k++) {
        production[k] = production[k].trim();
      }

      // import array into ArrayList
      for (int j = 0; j < production.length; j++) {
        productionList.add(production[j]);
      }

      // insert element into map
      mapVariableProduction.put(variable, productionList);
    }
  }

  private void eliminateEpsilon() {
    for (int i = 0; i < lineCount; i++) {
      removeEpsilon();
    }
  }

  private void removeEpsilon() {

    Iterator itr = mapVariableProduction.entrySet().iterator();
    Iterator itr2 = mapVariableProduction.entrySet().iterator();

    while (itr.hasNext()) {
      Map.Entry<String, List<String>> entry = (Entry<String, List<String>>) itr.next();
      ArrayList<String> productionRow = (ArrayList<String>) entry.getValue();

      if (productionRow.contains("e")) {// ricerca sulla epsilon
        if (productionRow.size() > 1) {
          productionRow.remove("e");
          epsilonFound = entry.getKey().toString();

        } else {

          // remove if less than 1
          epsilonFound = entry.getKey().toString();
          mapVariableProduction.remove(epsilonFound);
        }
      }
    }

    // find B and eliminate them
    while (itr2.hasNext()) {

      Map.Entry<String, List<String>> entry = (Map.Entry) itr2.next();
      ArrayList<String> productionList = (ArrayList<String>) entry.getValue();

      for (int i = 0; i < productionList.size(); i++) {
        String temp = productionList.get(i); // temp = singola produzione

        for (int j = 0; j < temp.length(); j++) {// al max 2 perche le produzioni sono del tipo AA | b
          if (epsilonFound.equals(Character.toString(productionList.get(i).charAt(j)))) {// se trovo all'interno della produzione una
                                                                                         // variabile che ha una epsilon-proudzione

            if (temp.length() == 2) { // se la produzione ha lunghezza due

              // rimuovo direttamente la variabile
              temp = temp.replace(epsilonFound, "");

              if (!mapVariableProduction.get(entry.getKey().toString()).contains(temp)) {// se la singola produzione non è contenuta
                                                                                         // all'interno della mappa poichè rimossa
                mapVariableProduction.get(entry.getKey().toString()).add(temp);// la riaggiungiamo senza la variabile con la produzione
                                                                               // epsilon
              }

            } else if (temp.length() == 3) {// se la produzione ha lunghezza tre

              String deletedTemp = new StringBuilder(temp).deleteCharAt(j).toString();
              // come prima, se la produzione manipolata non è contenuta all'interno della mappa in corrispondenza della entry, la aggiungo
              if (!mapVariableProduction.get(entry.getKey().toString()).contains(deletedTemp)) {
                mapVariableProduction.get(entry.getKey().toString()).add(deletedTemp);
              } // potrebbe succedere, infatti una configurazione del genere S -> ABC | AB C -> e in questo caso viene rimossa ABC e non
                // viene inserita nuovamente AB

            } else if (temp.length() == 4) {

              String deletedTemp = new StringBuilder(temp).deleteCharAt(j).toString();

              if (!mapVariableProduction.get(entry.getKey().toString()).contains(deletedTemp)) {
                mapVariableProduction.get(entry.getKey().toString()).add(deletedTemp);
              }
            } else {

              if (!mapVariableProduction.get(entry.getKey().toString()).contains("e")) {
                mapVariableProduction.get(entry.getKey().toString()).add("e");
              }
            }
          }
        }
      }
    }
  }

  private void removeDuplicateKeyValue() {

    Iterator itr3 = mapVariableProduction.entrySet().iterator();

    while (itr3.hasNext()) {
      Map.Entry<String, List<String>> entry = (Map.Entry) itr3.next();
      ArrayList<String> productionRow = (ArrayList<String>) entry.getValue();

      for (int i = 0; i < productionRow.size(); i++) {
        if (productionRow.get(i).contains(entry.getKey().toString())) {
          productionRow.remove(entry.getKey().toString());
        }
      }
    }
  }

  private void eliminateSingleVariable() {
    for (int i = 0; i < lineCount; i++) {
      removeSingleVariable();
    }
  }

  private void removeSingleVariable() {

    Iterator itr4 = mapVariableProduction.entrySet().iterator();
    String key = null;

    while (itr4.hasNext()) {

      Map.Entry<String, List<String>> entry = (Map.Entry) itr4.next();
      Set<String> set = mapVariableProduction.keySet();
      ArrayList<String> keySet = new ArrayList<String>(set);// va reinizializzato poichè andremo a rimuovere keys dal set
      ArrayList<String> productionList = (ArrayList<String>) entry.getValue();

      for (int i = 0; i < productionList.size(); i++) {
        String temp = productionList.get(i);

        for (int j = 0; j < temp.length(); j++) {

          for (int k = 0; k < keySet.size(); k++) {
            if (keySet.get(k).equals(temp)) {// se all'interno ho una produzione a variabile singola, contenuta all'interno del keyset

              key = entry.getKey().toString();
              List<String> productionValue = mapVariableProduction.get(temp);// prendo le produzioni di temp(che è anche una variabile
                                                                             // produttrice)
              productionList.remove(temp);// rimuovo dalla lista delle produzioni la variabile temp

              for (int l = 0; l < productionValue.size(); l++) {
                mapVariableProduction.get(key).add(productionValue.get(l));
              }
            }
          }
        }
      }
    }
  }

  private void onlyTwoTerminalandOneVariable() {// Assign new variable for two non-terminal or one terminal

    Iterator itr5 = mapVariableProduction.entrySet().iterator();
    String key = null;
    int asciiBegin = 71; // G

    Map<String, List<String>> tempList = new LinkedHashMap<>();

    while (itr5.hasNext()) {

      Map.Entry entry = (Map.Entry) itr5.next();
      Set set = mapVariableProduction.keySet();

      ArrayList<String> keySet = new ArrayList<String>(set);
      ArrayList<String> productionList = (ArrayList<String>) entry.getValue();
      Boolean found1 = false;
      Boolean found2 = false;
      Boolean found = false;

      for (int i = 0; i < productionList.size(); i++) {
        String temp = productionList.get(i);

        for (int j = 0; j < temp.length(); j++) {

          if (temp.length() == 3) {

            String newProduction = temp.substring(1, 3); // SA

            if (checkDuplicateInProductionList(tempList, newProduction) && checkDuplicateInProductionList(mapVariableProduction, newProduction)) {
              found = true;
            } else {
              found = false;
            }

            if (found) {

              ArrayList<String> newVariable = new ArrayList<>();
              newVariable.add(newProduction);
              key = Character.toString((char) asciiBegin);

              tempList.put(key, newVariable);
              asciiBegin++;
            }

          } else if (temp.length() == 2) { // if only two substring

            for (int k = 0; k < keySet.size(); k++) {

              if (!keySet.get(k).equals(Character.toString(productionList.get(i).charAt(j)))) { // if substring not equals to keySet
                found = false;

              } else {
                found = true;
                break;
              }

            }

            if (!found) {
              String newProduction = Character.toString(productionList.get(i).charAt(j));

              if (checkDuplicateInProductionList(tempList, newProduction) && checkDuplicateInProductionList(mapVariableProduction, newProduction)) {

                ArrayList<String> newVariable = new ArrayList<>();
                newVariable.add(newProduction);
                key = Character.toString((char) asciiBegin);

                tempList.put(key, newVariable);

                asciiBegin++;

              }
            }
          } else if (temp.length() == 4) {

            String newProduction1 = temp.substring(0, 2); // SA
            String newProduction2 = temp.substring(2, 4); // SA

            if (checkDuplicateInProductionList(tempList, newProduction1) && checkDuplicateInProductionList(mapVariableProduction, newProduction1)) {
              found1 = true;
            } else {
              found1 = false;
            }

            if (checkDuplicateInProductionList(tempList, newProduction2) && checkDuplicateInProductionList(mapVariableProduction, newProduction2)) {
              found2 = true;
            } else {
              found2 = false;
            }

            if (found1) {

              ArrayList<String> newVariable = new ArrayList<>();
              newVariable.add(newProduction1);
              key = Character.toString((char) asciiBegin);

              tempList.put(key, newVariable);
              asciiBegin++;
            }

            if (found2) {
              ArrayList<String> newVariable = new ArrayList<>();
              newVariable.add(newProduction2);
              key = Character.toString((char) asciiBegin);

              tempList.put(key, newVariable);
              asciiBegin++;
            }
          }
        }
      }
    }
    mapVariableProduction.putAll(tempList);

  }

  private Boolean checkDuplicateInProductionList(Map<String, List<String>> map, String key) {

    Boolean notFound = true;

    Iterator itr = map.entrySet().iterator();
    outerloop:

    while (itr.hasNext()) {

      Map.Entry entry = (Map.Entry) itr.next();
      ArrayList<String> productionList = (ArrayList<String>) entry.getValue();

      for (int i = 0; i < productionList.size(); i++) {
        if (productionList.size() < 2) {

          if (productionList.get(i).equals(key)) {
            notFound = false;
            break outerloop;
          } else {
            notFound = true;
          }
        }
      }
    }

    return notFound;
  }

  private void eliminateThreeTerminal() {

    System.out.println("Replace two terminal variable with new variable ... ");

    for (int i = 0; i < lineCount; i++) {
      removeThreeTerminal();
    }

  }

  private void removeThreeTerminal() {

    Iterator itr = mapVariableProduction.entrySet().iterator();
    ArrayList<String> keyList = new ArrayList<>();
    Iterator itr2 = mapVariableProduction.entrySet().iterator();

    // obtain key that use to eliminate two terminal and above
    while (itr.hasNext()) {
      Map.Entry entry = (Map.Entry) itr.next();
      ArrayList<String> productionRow = (ArrayList<String>) entry.getValue();

      if (productionRow.size() < 2) {
        keyList.add(entry.getKey().toString());
      }
    }

    // find more than three terminal or combination of variable and terminal to eliminate them
    while (itr2.hasNext()) {

      Map.Entry entry = (Map.Entry) itr2.next();
      ArrayList<String> productionList = (ArrayList<String>) entry.getValue();

      if (productionList.size() > 1) {
        for (int i = 0; i < productionList.size(); i++) {
          String temp = productionList.get(i);

          for (int j = 0; j < temp.length(); j++) {

            if (temp.length() > 2) {
              String stringToBeReplaced1 = temp.substring(j, temp.length());
              String stringToBeReplaced2 = temp.substring(0, temp.length() - j);

              for (String key : keyList) {

                List<String> keyValues = new ArrayList<>();
                keyValues = mapVariableProduction.get(key);
                String[] values = keyValues.toArray(new String[keyValues.size()]);
                String value = values[0];

                if (stringToBeReplaced1.equals(value)) {

                  mapVariableProduction.get(entry.getKey().toString()).remove(temp);
                  temp = temp.replace(stringToBeReplaced1, key);

                  if (!mapVariableProduction.get(entry.getKey().toString()).contains(temp)) {
                    mapVariableProduction.get(entry.getKey().toString()).add(i, temp);
                  }
                } else if (stringToBeReplaced2.equals(value)) {

                  mapVariableProduction.get(entry.getKey().toString()).remove(temp);
                  temp = temp.replace(stringToBeReplaced2, key);

                  if (!mapVariableProduction.get(entry.getKey().toString()).contains(temp)) {
                    mapVariableProduction.get(entry.getKey().toString()).add(i, temp);
                  }
                }
              }
            } else if (temp.length() == 2) {

              for (String key : keyList) {

                List<String> keyValues = new ArrayList<>();
                keyValues = mapVariableProduction.get(key);
                String[] values = keyValues.toArray(new String[keyValues.size()]);
                String value = values[0];

                for (int pos = 0; pos < temp.length(); pos++) {
                  String tempChar = Character.toString(temp.charAt(pos));

                  if (value.equals(tempChar)) {

                    mapVariableProduction.get(entry.getKey().toString()).remove(temp);
                    temp = temp.replace(tempChar, key);

                    if (!mapVariableProduction.get(entry.getKey().toString()).contains(temp)) {
                      mapVariableProduction.get(entry.getKey().toString()).add(i, temp);
                    }
                  }
                }
              }
            }

          }
        }
      } else if (productionList.size() == 1) {

        for (int i = 0; i < productionList.size(); i++) {
          String temp = productionList.get(i);

          if (temp.length() == 2) {

            for (String key : keyList) {

              List<String> keyValues = new ArrayList<>();
              keyValues = mapVariableProduction.get(key);
              String[] values = keyValues.toArray(new String[keyValues.size()]);
              String value = values[0];

              for (int pos = 0; pos < temp.length(); pos++) {
                String tempChar = Character.toString(temp.charAt(pos));

                if (value.equals(tempChar)) {

                  mapVariableProduction.get(entry.getKey().toString()).remove(temp);
                  temp = temp.replace(tempChar, key);

                  if (!mapVariableProduction.get(entry.getKey().toString()).contains(temp)) {
                    mapVariableProduction.get(entry.getKey().toString()).add(i, temp);
                  }
                }
              }
            }

          }
        }
      }
    }
  }
  public void convertCFGtoCNF() {

    insertNewStartSymbol();
    convertStringtoMap();
    eliminateEpsilon();
    removeDuplicateKeyValue();
    eliminateSingleVariable();
    onlyTwoTerminalandOneVariable();
    eliminateThreeTerminal();

}
}
