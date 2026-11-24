import controller.GameController;
import model.*;
import javax.swing.*;
import gui.BoardPanel;
import java.awt.*;

public class RulesTest {
    static GameController c=new GameController();
    static Position p(String s){return Position.fromChessNotation(s);}
    static void play(String... moves){for(String m:moves)c.move(p(m.substring(0,2)),p(m.substring(2)));}
    static void require(boolean ok,String msg){if(!ok)throw new AssertionError(msg);}
    static void reject(String move){
        GameState old=c.state();
        try{play(move);throw new AssertionError("accepted "+move);}
        catch(IllegalArgumentException expected){require(old==c.state(),"mutated after "+move);}
    }
    static JButton button(Container panel,String name){
        for(Component item:panel.getComponents()){
            if(item instanceof JButton b && name.equalsIgnoreCase(b.getName())) return b;
            if(item instanceof Container nested){JButton b=button(nested,name);if(b!=null)return b;}
        }
        return null;
    }
    public static void main(String[] args)throws Exception{
        reject("e2e5");reject("a1a4");reject("c1h6");reject("b1b3");reject("e7e6");
        play("e2e4","d7d5","e4d5"); require(c.state().capturedBy(Piece.Color.WHITE).size()==1,"capture");
        GameSession saved=c.exportSession(); c.newGame();c.restoreSession(saved);c.undo();
        require(c.state().pieceAt(p("d5")).color()==Piece.Color.BLACK,"undo restored pawn");
        c.newGame();play("e2e4","d7d6","f1b5","c7c6","a2a3");reject("c6c5");
        c.newGame();play("e2e4","d7d5","f1b5");require(c.state().inCheck(),"bishop check");
        reject("a7a6");play("c7c6");require(!c.state().inCheck(),"blocked check");reject("b5e8");
        c.newGame();play("f2f3","e7e5","g2g4","d8h4");
        require(c.state().winner()==Piece.Color.BLACK && c.state().pieceAt(p("e1")).type()==Piece.Type.KING,"Fool's mate");reject("a2a3");
        c.newGame();play("e2e4","e7e5","f1c4","b8c6","d1h5","g8f6","h5f7");
        require(c.state().winner()==Piece.Color.WHITE,"Scholar's mate");
        c.newGame();SwingUtilities.invokeAndWait(()->{
            BoardPanel ui=new BoardPanel(c);
            button(ui,"e2").doClick();button(ui,"e5").doClick();
            require(c.state().moves().isEmpty(),"illegal Swing click");
            button(ui,"e4").doClick();require(c.state().turn()==Piece.Color.BLACK,"legal Swing move");
            ui.detach();
        });
        System.out.println("PASS: rule rejection, capture, replay, undo, check response, pins, both mates and Swing input");
    }
}
