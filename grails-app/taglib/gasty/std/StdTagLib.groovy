package gasty.std

import gasty.RenderingHelper
import com.jasty.components.std.Button
import com.jasty.components.std.Link
import com.jasty.components.std.TextBox
import com.jasty.components.std.ComboBox
import com.jasty.components.std.CheckBox
import com.jasty.components.std.FileUpload

class StdTagLib {

    static namespace = "jasty_std"

    def button = { attrs ->
        RenderingHelper.render(Button, out, attrs, false)
    }

    def link = { attrs, body ->
        RenderingHelper.render(Link, out, attrs, body, false)
    }

    def textBox = { attrs ->
        RenderingHelper.render(TextBox, out, attrs, !attrs.rows)
    }

    def comboBox = { attrs ->
        RenderingHelper.render(ComboBox, out, attrs, null, false)
    }

    def checkBox = { attrs ->
        RenderingHelper.render(CheckBox, out, attrs, true)
    }

    def fileUpload = { attrs ->
        RenderingHelper.render(FileUpload, out, attrs, true)
    }
}
