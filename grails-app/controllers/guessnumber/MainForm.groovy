package guessnumber

import com.jasty.core.Form
import com.jasty.core.EventArgs
import com.jasty.components.JQuery
import com.jasty.components.std.TextBox

class MainForm extends Form {

    private int randomNumber = ((int) Math.floor(Math.random() * 100)) + 1
    private int numberOfTries = 0
    int lowerBound = 1
    int upperBound = 100

    void startNewGame(EventArgs e) {
        replaceWith(new MainForm())
    }

    void processGuess(EventArgs e) {
        def stats = $$(JQuery, "stats")
        def guessEntryField = $$(TextBox, "guessEntryField")

        int guess
        try {
            guess = guessEntryField.getValue() as int
        } catch (NumberFormatException ex) {
            $(JQuery, "#stats .status").text("Your guess was not valid.")
            return
        }

        ++numberOfTries

        if (guess == randomNumber) {
            replaceWith(new CongratulationForm(numberOfTries))
            return
        }

        guessEntryField.value = ""

        def statusText = ""

        if (guess < 1 || guess > 100) {
            statusText = "Your guess, ${guess} was not between 1 and 100."
        } else if (guess < randomNumber) {
            if (guess >= lowerBound) {
                lowerBound = guess + 1
            }
            statusText = "Your guess, ${guess} was too low.  Try again:"
        } else if (guess > randomNumber) {
            statusText = "Your guess, ${guess} was too high.  Try again:"
            if (guess <= upperBound) {
                upperBound = guess - 1
            }
        }

        stats.html(renderFragment("stats",[model: statusText, currentForm: this]))
    }

    String getCounter() {
        // Update number of tries label.
        if(numberOfTries == 0)
            return "You have made no guesses"
        if (numberOfTries == 1)
            return "You have made 1 guess."
        "You have made ${numberOfTries} guesses."
    }

    @Override
    protected void processThrowable(Throwable t) {
        invoke("errors", t.message)
    }
}
