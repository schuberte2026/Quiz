package com.example.quizkata;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Controller class for the Quiz program.
 */
public class QuizController {
    @FXML
    private TextField questionBox;

    @FXML
    private RadioButton optionOne;

    @FXML
    private RadioButton optionTwo;

    @FXML
    private RadioButton optionThree;

    @FXML
    private RadioButton optionFour;

    @FXML
    private TextField optionOneDisplayBox;

    @FXML
    private TextField optionTwoDisplayBox;

    @FXML
    private TextField optionThreeDisplayBox;

    @FXML
    private TextField optionFourDisplayBox;

    @FXML
    private TextField scoreBox;

    @FXML
    TextField Timerbox;

    @FXML
    TextField resultBox;

    private int score = 0;
    private Timeline timeline;
    private int timerSeconds = 10;
    private String correctAnswer;

    //will need to replace filename for each new one for now,
    // ran out of time to make it more customizable
    private String filePath = "C:\\Users\\schuberte\\OneDrive - Milwaukee School of Engineering\\DSA\\Week 1\\QuizKata\\TestFiles\\quizQnAfinal.txt";

    private BufferedReader bufferedReader;

    /**
     * Method that initializes a BufferedReader on the file to be read from.
     */
    @FXML
    public void initialize() {
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            loadNextQuestion();
        } catch (IOException e) {
            handleIOException(e);
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Incorrect file format. Ensure file format is correct.");
            System.exit(0);
        }
    }


    /**
     * Clears the radio button selections for each round of the trivia game.
     */
    private void clearRadioButtons() {
        optionOne.setSelected(false);
        optionTwo.setSelected(false);
        optionThree.setSelected(false);
        optionFour.setSelected(false);
    }

    /**
     * Loads the questions and answers on the application.
     * @throws IOException - if the format isn't matched.
     */
    @FXML
    private void loadNextQuestion() throws IOException {
        try {
            clearRadioButtons();
            String line;
            while ((line = bufferedReader.readLine()) != null && line.trim().isEmpty()) {
                //this skips the blank lines in the text files
            }

            if (line != null) {
                String[] parts = line.split(": ");
                if (parts[0].equals("Question")) {
                    displayQuestion(parts[1]);
                    readAndDisplayOptions();

                    correctAnswer = bufferedReader.readLine().substring(8).trim(); //reads correct answers, trims spaces

                    startTimer();
                }
            } else {
                endQuiz();
            }
        } catch (IOException e) {
            handleIOException(e);
        }
    }

    /**
     * Displays the question in the questionBox TextField.
     * @param question - string of question being asked
     */
    private void displayQuestion(String question) {
        questionBox.setText(question);
    }

    /**
     * Displays the answers in the TextFields.
     * @throws IOException - if format is incorrect.
     */
    private void readAndDisplayOptions() throws IOException {
        // Display options in the corresponding display boxes
        optionOneDisplayBox.setText(bufferedReader.readLine().substring(12).replace(":", ""));
        optionTwoDisplayBox.setText(bufferedReader.readLine().substring(12).replace(":", ""));
        optionThreeDisplayBox.setText(bufferedReader.readLine().substring(12).replace(":", ""));
        optionFourDisplayBox.setText(bufferedReader.readLine().substring(12).replace(":", ""));
    }

    /**
     * Starts the timer at 10 seconds, turns timer red once it hits 5 seconds
     * to remind user to answer.
     */
    private void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            Timerbox.setText(String.valueOf(timerSeconds));

            if (timerSeconds == 0) {
                handleTimeout();
            } else if (timerSeconds <= 5) {

                Timerbox.setStyle("-fx-text-fill: red;"); //changes text color when time less than 5 sec
            }

            timerSeconds--;
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * If user doesn't select a question in 10 seconds,
     * program moves to next round in trivia.
     */
    private void handleTimeout() {
        timeline.stop();
        try {
            loadNextQuestion();
        } catch (IOException e) {
            handleIOException(e);
        } finally {
            // Reset the timerSeconds for the next question
            timerSeconds = 10;
            // Reset the text color to black
            Timerbox.setStyle("-fx-text-fill: black;");
        }
    }

    /**
     * Determines the feedback user receives on the trivia game.
     */
    private void endQuiz() {

        if(score > 0) {
            resultBox.setText("You got one!");
        } else {
            resultBox.setText("Hm..try again.");
        }
    }

    /**
     * Prints stack trace for the IO exception.
     * @param e
     */
    private void handleIOException(IOException e) {
        resultBox.setText("Please ensure text" +
                "\n file is in correct format.");
    }

    /**
     * Checks whether the user selected the correct option by comparing radio button text
     * to the correct answer in the text file.
     */
    @FXML
    private void handleAnswerSelection() {
        timeline.stop();

        try {
            //checks the option that is selected and compares it with the correct answer
            RadioButton selectedOption = null;

            if (optionOne.isSelected()) {
                selectedOption = optionOne;
            } else if (optionTwo.isSelected()) {
                selectedOption = optionTwo;
            } else if (optionThree.isSelected()) {
                selectedOption = optionThree;
            } else if (optionFour.isSelected()) {
                selectedOption = optionFour;
            }

            if (selectedOption != null && selectedOption.getText().trim().equals(correctAnswer)) {
                score += 10; // Increase the score by 10 for a correct answer
            }

            scoreBox.setText(String.valueOf(score));

            loadNextQuestion();
        } catch (IOException e) {
            handleIOException(e);
        } finally {
            timerSeconds = 10;
            Timerbox.setStyle("-fx-text-fill: black;"); //resets text color to black
        }
    }
}