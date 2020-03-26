package game;

import javax.swing.*;

public class Game {
    private GameBoard board; // Ссылка на игровое поле
    private GamePlayer[] gamePlayers = new GamePlayer[2]; // Массив игроков
    private int playerTurn = 0; // Индекс текущего игрока

    public Game(){
        this.board = new GameBoard(this);
    }

    public void initGame(){
        gamePlayers[0] = new GamePlayer(true, 'X');
        gamePlayers[1] = new GamePlayer(false, 'O');
    }

    /**
     * Метод передачи хода
     */
    void passTurn(){
        if(playerTurn == 0){
            playerTurn = 1;
        } else {
            playerTurn = 0;
        }
    }

    /**
     * Получение объекта текущего игрока
     * @return GamePlayer объект игрока
     */
    GamePlayer getCurrentPlayer(){
        return gamePlayers[playerTurn];
    }

    /**
     * Метод показа всплывающих сообщения для пользователя
     * @param messageText - текс тообщений
     */
    void showMessage(String messageText){
        JOptionPane.showMessageDialog(board, messageText); //Вызываем диалоговое окно с привязкой к игровому полю с передаваемым сообщением
    }
}