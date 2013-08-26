package testapp

import com.jasty.core.Form
import com.jasty.components.std.Link
import com.jasty.components.JQuery

class ListForm extends Form {

    private def items = []

    @Override
    void afterInit() {
        super.afterInit();
        items.each {
            displayItem(it);
        }
    }

    private void displayItem(String text) {
        Link link = $$(Link, null);
        link.text = text
        link.data = text
        link.onClick = "deleteClicked"
        $(JQuery, ".list").append(link)
    }

    void searchClicked(String searchText) {
        $(JQuery, ".list").empty()
        items.findAll {it.contains(searchText)}.each {displayItem(it)}
    }

    void addClicked() {
        replaceWith(new AddingForm(items.size(), this))
    }

    void deleteClicked(Link eventSource, String eventData) {
        eventSource.remove()
        items.remove(eventData)
    }

    void addItem(String item) {
        if(!items.contains(item))
            items.add(item)
    }
}
