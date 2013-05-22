package testapp

import com.jasty.core.Form
import com.jasty.core.EventArgs

class AddingForm extends Form {

    private int currentCount
    private ListForm parent

    public AddingForm(int currentCount, ListForm parent) {
        this.currentCount = currentCount
        this.parent = parent
    }

    @Override
    public Object prepareModel() {
        [model: currentCount + 1]
    }

    public void okClicked(EventArgs e) {
        parent.addItem(getParameter("text"))
        replaceWith(parent)
    }

    public void cancelClicked(EventArgs e) {
        replaceWith(parent)
    }

    @Override
    protected void processThrowable(Throwable t) {
        invoke("errors", [[message: t.message]])
    }
}
