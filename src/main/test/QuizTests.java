/**
 * Tests consisted of running various text files of trivia questions through the program. These will
 * be committed to the repo for viewing (directory TestFiles). They featured incorrect and correct format types. I also utilized
 * the built-in debugger in IntelliJ Idea. Had time to create simple test in JUnit 5.
 */
import com.example.quizkata.QuizController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class QuizControllerTest {

    @Test
    void testFileLoading() {
        QuizController quizController = new QuizController();
        quizController.setStopBeforeLoadNextQuestion(true);

        String testFilePath = "C:\\Users\\schuberte\\OneDrive - Milwauk" +
                "ee School of Engineering\\DSA\\Week 1\\QuizKata\\TestFiles\\quizQnA.txt";

        assertDoesNotThrow(() -> quizController.loadFile(testFilePath));
    }
}
