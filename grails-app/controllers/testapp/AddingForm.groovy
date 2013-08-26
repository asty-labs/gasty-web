package testapp

import com.jasty.core.Form

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

    public void okClicked(String text) {
        parent.addItem(text)
        replaceWith(parent)
    }

    public void cancelClicked() {
        replaceWith(parent)
    }
}
