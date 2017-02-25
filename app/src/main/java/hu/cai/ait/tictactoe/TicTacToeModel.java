package hu.cai.ait.tictactoe;

/**
 * Created by caiglencross on 2/23/17.
 */

public class TicTacToeModel {

    private static TicTacToeModel instance = null;



    //private constructor makes it a singleton
    private TicTacToeModel(){

    }

    //responsible for creating instance if it doesn't already exist
    public static TicTacToeModel getInstance(){
        if (instance == null){
            instance = new TicTacToeModel();
        }

        return instance;
    }

    public static final short EMPTY = 0;
    public static final short CIRCLE = 1;
    public static final short CROSS = 2;
    public static final short CAT = 3;

    private short[][] model = {
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY}
    };

    private short nextPlayer = CIRCLE;

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (model[i][j]==EMPTY){
                    return false;
                }
            }
        }
        return true;
    }

    public short checkWinner() {
        //check diagonal
        if ((model[0][0] == model[1][1] && model[1][1] == model[2][2]) ||
                (model[0][2] == model[1][1] && model[2][0]==model[1][1])){
            return model[1][1];
        }
        //check rows & columns
        for (int i = 0; i < 3; i++) {
            if (model[i][0] == model[i][1] && model[i][1] == model[i][2]) {
                return model[i][0];
            } else if (model[0][i] == model[1][i] && model[1][i] == model[2][i]) {
                return model[0][i];
            }
        }
        //check if the board is full (if so then the cat wins)
        if (isBoardFull()){
            return CAT;
        }

        return EMPTY;
    }

    public void resetModel(){
        for (int i = 0; i<3; i++){
            for (int j = 0; j < 3 ; j++) {
                model[i][j]=EMPTY;
            }
        }
        nextPlayer = CIRCLE;
    }


    public void makeMove(int x, int y, short player){
        model[x][y] = player;
    }

    public short getFieldContent(int x, int y){
        return model[x][y];
    }

    public short getNextPlayer() {
        return nextPlayer;
    }

    public void changeNextPlayer(){
        nextPlayer = (nextPlayer == CIRCLE) ? CROSS : CIRCLE;
    }

}
