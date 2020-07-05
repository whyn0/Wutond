package proj.map.progettoMap1920.adventure.main;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import proj.map.progettoMap1920.adventure.games.Wutond;
import proj.map.progettoMap1920.adventure.parser.ItParser;
import proj.map.progettoMap1920.adventure.parser.Parser;
import proj.map.progettoMap1920.adventure.parser.ParserOutput;
import proj.map.progettoMap1920.adventure.type.AdvObject;
import proj.map.progettoMap1920.adventure.type.AdvObjectContainer;
import proj.map.progettoMap1920.adventure.type.CommandType;
import proj.map.progettoMap1920.adventure.utils.Gui;
import proj.map.progettoMap1920.adventure.utils.LockT;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

import proj.map.progettoMap1920.adventure.exceptions.NullOutputException;

public class Engine {

    private final GameDescription game;
    private final Parser parser;
    private final Gui gui;
    //private final Object lock = new Object();

    public Engine(GameDescription game) {
        gui = new Gui();
        this.game = game;
        try {
            this.game.init();
        } catch (FileNotFoundException e) {
            System.err.print(e);
        } catch (IOException e) {
            System.err.print(e);

        }
        parser = new ItParser();//italian parser
        
        gui.setVisible(true);
    }

    public void run() {
        JTextArea output = gui.getOutputArea();
        JTextArea input = gui.getInputArea();
        output.append(intro());
        output.append(game.getCurrentRoom().getName() + '\n');
        output.append("================================================" + '\n');
        output.append(game.getCurrentRoom().getDescription() + '\n');
        output.append("================================================" + '\n');
        for(int i = 0; i < output.getColumns(); i++) {
          output.append("=");
        }

        while (true) {
            boolean flag = true;
            ((DefaultCaret)output.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
            synchronized (LockT.lock) {
                try {
                    LockT.lock.wait();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            String command = gui.getString();
            List<AdvObject> containerItems = new ArrayList<>();
            for (AdvObject a : game.getCurrentRoom().getObjects_list()) {
                if (a instanceof AdvObjectContainer) {
                    if (((AdvObjectContainer) a).isOpened()) {
                        containerItems.addAll(((AdvObjectContainer) a).getList());
                    }
                }
            }
            ParserOutput p = new ParserOutput();
            try {
                p = parser.parse(command,
                        game.getInventory().getList(),
                        game.getCurrentRoom().getObjects_list(),
                        containerItems,
                        game.getCurrentRoom().getNpc_list(),
                        game.getCommands(),
                        game.getArticles(),
                        game.getPrepositions(),
                        game.getParticles(),
                        game.getGrammar());
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                System.out.print(e);
            } catch (NullOutputException e1) {
                output.append(e1.getMsg());
                flag = false;
            }

            if (p.getCommand() != null && p.getCommand().getType() == CommandType.END) {
                output.append("Addio!");
                //scanner.close();
                break;
            } else {
                if (flag) {
                    game.nextMove(p, output,input, gui);
                    output.append("\n");
                    output.append("================================================" + '\n');
                }

                gui.setString(null);
            }
        }
        gui.setVisible(false);
    }
    public String intro() {
      String intro = "Sei Alfoso Paccagnello, un detective padovano. "+ "\n"
        + "Sei stato inviato nell'apparententemente tranquilla cittadina di Wutond "+ "\n"
        + "per risolvere il mistero dell'omicidio del proprietario del più famoso bar cittadino: "+ "\n"
        + "il bar Castello .Ti trovi in questura completamente solo." + "\n"
        + "Non hai nulla con te se non il tuo intuito, inizi a guardarti intorno..." + "\n"
        + "================================================" + '\n';
      return intro;
    }

    public static void main(String[] args) {
        Engine engine = new Engine(new Wutond());
        engine.run();
    }

}
