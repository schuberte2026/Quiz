module com.example.quizkata {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.quizkata to javafx.fxml;
    exports com.example.quizkata;
}