package com.example.demo.controller;

import com.example.demo.model.TicTacToeModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class GameController {

    @FXML
    private Button btn00, btn01, btn02, btn10, btn11, btn12, btn20, btn21, btn22;

    private TicTacToeModel model;
    private boolean isXTurn = true;

    @FXML
    public void initialize() {
        model = new TicTacToeModel();
    }

    @FXML
    private void handleButtonClick(javafx.event.ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String currentSymbol = isXTurn ? "X" : "O";

        // Check if the button has already been clicked
        if (clickedButton.getText().isEmpty()) {
            clickedButton.setText(currentSymbol);
            updateModel(clickedButton);

            int[][] winCombo = model.checkWin(); // Return winning combination if there's a win

            if (winCombo != null) {
                highlightWinningCombo(winCombo); // Highlight the winning row, column, or diagonal
                showWinnerAlert(currentSymbol);
                resetBoard();
            } else if (model.isBoardFull()) {
                showDrawAlert();
                resetBoard();
            }

            // Switch turns
            isXTurn = !isXTurn;
        }
    }

    private void updateModel(Button clickedButton) {
        Integer row = GridPane.getRowIndex(clickedButton);
        Integer col = GridPane.getColumnIndex(clickedButton);

        // Update model with the current move (X or O)
        model.updateBoard(row == null ? 0 : row, col == null ? 0 : col, isXTurn ? 'X' : 'O');
    }

    private void resetBoard() {
        // Reset buttons to empty
        resetButtonStyle(btn00); resetButtonStyle(btn01); resetButtonStyle(btn02);
        resetButtonStyle(btn10); resetButtonStyle(btn11); resetButtonStyle(btn12);
        resetButtonStyle(btn20); resetButtonStyle(btn21); resetButtonStyle(btn22);

        // Clear the game board in the model
        model.clearBoard();
        isXTurn = true;  // Reset turn to X
    }

    // Reset the button style to default
    private void resetButtonStyle(Button button) {
        button.setText("");
        button.setStyle("");  // Reset button style
    }

    // Highlight winning buttons
    private void highlightWinningCombo(int[][] winCombo) {
        for (int[] position : winCombo) {
            int row = position[0];
            int col = position[1];
            Button winningButton = getButtonAt(row, col);
            if (winningButton != null) {
                winningButton.setStyle("-fx-background-color: lightgreen; -fx-font-size: 36px;"); // Highlight winner
            }
        }
    }

    // Display a dialog when someone wins
    private void showWinnerAlert(String winner) {
        Alert alert = new Alert(Alert.AlertType.NONE); // Create an alert without any default icon
        alert.setTitle("Game Over");
        alert.setHeaderText(null);

        // Set the content text with larger font size
        String message = winner + " wins!";
        Label label = new Label(message);
        label.setStyle("-fx-font-size: 24px;"); // Set larger font size for the winner message
        alert.getDialogPane().setContent(label);

        // Add a button to close the alert
        ButtonType buttonType = new ButtonType("OK");
        alert.getButtonTypes().setAll(buttonType);

        alert.showAndWait();
    }


    // Display a dialog for a draw
    private void showDrawAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("It's a draw!");
        alert.showAndWait();
    }

    // Get button at the specified row and column
    private Button getButtonAt(int row, int col) {
        if (row == 0 && col == 0) return btn00;
        if (row == 0 && col == 1) return btn01;
        if (row == 0 && col == 2) return btn02;
        if (row == 1 && col == 0) return btn10;
        if (row == 1 && col == 1) return btn11;
        if (row == 1 && col == 2) return btn12;
        if (row == 2 && col == 0) return btn20;
        if (row == 2 && col == 1) return btn21;
        if (row == 2 && col == 2) return btn22;
        return null;
    }
}
