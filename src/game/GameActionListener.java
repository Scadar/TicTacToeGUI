package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameActionListener implements ActionListener {
    private int row;
    private int cell;
    private GameButton button;

    public GameActionListener(int row, int cell, GameButton gButton) {
        this.row = row;
        this.cell = cell;
        this.button = gButton;  // Передаем номер ряда, номер столбца и ссылку на кнопку, к которой привязыаем наш GameActionListener
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameBoard board = button.getBoard();

        if (board.isTurnable(row, cell)) {
            updateByPlayersData(board);
            checkFull(board);

            updateByAiData(board);
            checkFull(board);
        } else {
            board.getGame().showMessage("Некорректный ход");
        }

    }

    private void checkFull(GameBoard board){
        if (board.isFull()) {
            board.getGame().showMessage("Ничья");
            board.emptyField();
        }
    }
    /**
     * Ход человека
     *
     * @param board GameBoard() - ссылка на игровое поле
     */
    private void updateByPlayersData(GameBoard board) {
        // Обновляем матрицу игры
        board.updateGameField(row, cell);
        // Обновляем содержимое кнопки
        button.setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));
        // После хода проверим состояние победы
        if (board.checkWin()) {
            button.getBoard().getGame().showMessage("Вы выиграли!");
            board.emptyField();
        } else {
            board.getGame().passTurn();
        }
    }

    /**
     * Ход комппьютера
     *
     * @param board GameBoard() - ссылка на игровое поле
     */
    private void updateByAiData(GameBoard board) {
        int x = -1;
        int y = -1;

        int max = 0;
        //Подсчет соседей
        for (int i = 0; i < GameBoard.dimension; i++) {
            for (int j = 0; j < GameBoard.dimension; j++) {
                if(board.isTurnable(i, j)){
                    int count = board.checkNeighbors(i, j);
                    if(count > max){
                        max = count;
                        x = i;
                        y = j;
                    }
                }
            }
        }
        Random random = new Random();
        if(max == 0){//Рандомный ход
            do {
                x = random.nextInt(GameBoard.dimension);
                y = random.nextInt(GameBoard.dimension);
            }while(!board.isTurnable(x, y));
        }
        // Обновим матрицу игры
        board.updateGameField(x, y);

        // Обновим содержимое кнопки
        int cellIndex = GameBoard.dimension * x + y;
        board.getButton(cellIndex).setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));

        // Проверить победу
        if (board.checkWin()) {
            button.getBoard().getGame().showMessage("Компьютер выиграл!");
            board.emptyField();
        } else {
            board.getGame().passTurn();
        }
    }

}