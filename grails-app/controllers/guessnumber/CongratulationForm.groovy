package guessnumber

import com.jasty.core.Form
import com.jasty.core.EventArgs

class CongratulationForm extends Form {

    private int numberOfTries;

    CongratulationForm(int numberOfTries) {
        this.numberOfTries = numberOfTries
    }

    Object prepareModel() {
        [numberOfTries: numberOfTries]
    }

    void startNewGame(EventArgs e) {
        replaceWith(new MainForm())
    }

    @Override
    protected void processThrowable(Throwable t) {
        invoke("errors", t.message)
    }
}
