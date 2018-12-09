package views;

import board_players.board.Board;
import board_players.players.Player;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.client.FieldCircle;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class BoardView {
    //game elements
    private List<Player> players = new ArrayList<>();
    //board parameters
    private int ch;
    private double hGap, wGap, radius;
    //window elements
    private Stage boardStage;
    private Group group;
    private double windowWidth, windowHeight;

    public BoardView(int s, int c, double w, double h) {
        this.boardStage=new Stage();
        this.group=new Group();
        this.ch =c;
        this.wGap =w;
        this.hGap =h;
        this.windowWidth=s;
        this.radius=(windowWidth - wGap *(3* ch +2))/(2*(3* ch + 1));
        this.windowHeight=(2*(4* ch +1))*radius+2*abs(hGap)-4* ch * hGap;
    }

    public void initialize() {
        Scene scene = new Scene(group, windowWidth, windowHeight);
        boardStage.setScene(scene);
        boardStage.setResizable(false);
        boardStage.setTitle("Trylma");
        boardStage.initModality(Modality.APPLICATION_MODAL);
    }

    public void show() {
        boardStage.show();
    }

    public void hide() {
        boardStage.hide();
    }

    public void initializeFields() {
        Board.generateFields(radius, ch, wGap, hGap);
        draw(Board.getFieldCircles());
        Board.addHandlersToCircles(radius, ch, wGap, hGap);
        addNewPlayer(true);
        addNewPlayer(true);
        addNewPlayer(true);
        addNewPlayer(true);
        addNewPlayer(true);
        addNewPlayer(true);
    }

    private void draw(List<FieldCircle> fieldCircles) {
        for (FieldCircle fieldCircle : fieldCircles) {
            group.getChildren().add(fieldCircle.getCircle());
        }
    }

    public Player getPlayer(int i) {
        return this.players.get(i-1);
    }

    public void addNewPlayer(boolean isThisMe) {
        int playerID = players.size()+1;
        Player player = new Player(playerID);
        player.generateFields(isThisMe, radius, ch, wGap, hGap);
        players.add(player);
        draw(player.getFieldCircles());
    }
}
