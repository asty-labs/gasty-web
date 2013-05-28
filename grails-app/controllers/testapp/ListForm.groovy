package testapp

import com.jasty.core.Form
import com.jasty.components.std.Link
import com.jasty.components.JQuery
import com.jasty.core.EventArgs

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

    void searchClicked(EventArgs e) {
        $(JQuery, ".list").empty()
        def text = getParameter("searchText")
        items.findAll {it.contains(text)}.each {displayItem(it)}
    }

    void addClicked(EventArgs e) {
        replaceWith(new AddingForm(items.size(), this))
    }

    void deleteClicked(EventArgs e) {
        $$(Link, e.srcId).remove()
        items.remove(e.get("data"))
    }

    void addItem(String item) {
        if(!items.contains(item))
            items.add(item)
    }

    @Override
    protected void processThrowable(Throwable t) {
        invoke("errors", [[message: t.message]])
    }
}
