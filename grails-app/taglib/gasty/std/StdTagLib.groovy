package gasty.std

import com.jasty.components.std.*
import gasty.RenderingHelper

class StdTagLib {

    static namespace = "jasty_std"

    def button = { attrs ->
        RenderingHelper.render(Button, out, attrs, false)
    }

	/**
	 * @attr text
	 * @attr onClick
	 * @attr confirmText
	 * @attr confirmTitle
	 */
    def link = { attrs, body ->
        RenderingHelper.render(Link, out, attrs, body, false)
    }

	/**
	 *	@attr id
	 *	@attr value
	 *	@attr placeholder
	 *	@attr disabled
	 *	@attr visible
	 *	@attr value
	 *	@attr rows
	 *	@attr cols
	 *	@attr password
	 *
	 */
    def textBox = { attrs ->
        RenderingHelper.render(TextBox, out, attrs, !attrs.rows)
    }

	def date = { attrs ->
		RenderingHelper.render(DateField, out, attrs, false)
	}

	/**
	 *	@attr id
	 *	@attr options REQUIRED
	 *  @attr value
	 *  @attr onChange
	 *
	 */
    def comboBox = { attrs ->
        RenderingHelper.render(ComboBox, out, attrs, null, false)
    }

    def checkBox = { attrs ->
		if(attrs.checked == null) attrs.checked = false
        RenderingHelper.render(CheckBox, out, attrs, true)
    }

    def fileUpload = { attrs ->
        RenderingHelper.render(FileUpload, out, attrs, true)
    }
}
