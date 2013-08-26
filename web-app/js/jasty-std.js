/**
 * Standard jasty components library
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
jasty.Link = jasty.extend(jasty.Control, {

	init: function(self, opts) {
        this.parent.init(self, opts);
		self.attr("href", "#");
		self.text(opts.text);
		self.click(function() {
			jasty.raiseEvent(self, opts.onClick, {srcId: self.attr("id"), data: opts.data});
			return false;
		});
	},

	text: function(self, value) {
		self.text(value);
	}
});

jasty.Button = jasty.extend(jasty.Control, {

	init: function(self, opts) {
        this.parent.init(self, opts);
        self.text(opts.text);
        self.addClass("jasty-button");
        self.click(function() {
			jasty.raiseEvent(self, opts.onClick, {srcId: self.attr("id"), data: opts.data});
			return false;
		});
	},

	text: function(self, value) {
		self.text(value);
	}
});


jasty.TextBox = jasty.extend(jasty.Control, {
	init: function(self, opts) {
		self.attr("name", opts.id);
        if(opts.maxLength) self.attr("maxlength", opts.maxLength);
        self.addClass("jasty-textbox");
        self.val(opts.value);
        if(opts.rows) self.attr("rows", opts.rows);
        if(opts.cols) self.attr("cols", opts.cols);
    },

	value: function(self, value) {
		self.val(value);
	}
});

jasty.CheckBox = jasty.extend(jasty.Control, {
	init: function(self, opts) {
		self.attr("name", opts.id + "_cb");
        self.addClass("jasty-checkbox");
        var valueHolder = $("<input/>").attr("type", "hidden").attr("name", opts.id);
        self.after(valueHolder);
		if(opts.checked) {
		    self.attr("checked", "checked");
        }
        valueHolder.val(opts.checked ? "1" : "0");
        self.change(function() {
            valueHolder.val(self.attr("checked") == "checked" ? "1" : "0");
            if(opts.onChange) {
                jasty.raiseEvent(self, opts.onChange, {srcId: self.attr("id"),
                data: opts.data,
                value: self.attr("checked") == "checked"});
            }
            return false;
        });
	},

	checked: function(self, value) {
	    if(value)
		    self.attr("checked", "checked");
		else
		    self.removeAttr("checked");
        self.next().val(value ? "1" : "0");
	}
});

jasty.ComboBox = jasty.extend(jasty.Control, {
	init: function(self, opts) {
        this.parent.init(self, opts);
        self.addClass("jasty-combobox");
        self.attr("name", opts.id);
		if(opts.options)
		    this.options(self, opts.options);
		if(opts.value)
		    self.val(opts.value);
		if(opts.onChange)
            self.change(function() {
                jasty.raiseEvent(self, opts.onChange, {srcId: self.attr("id"),
                    data: opts.data,
                    value: self.val()});
                return false;
            });
	},

	value: function(self, value) {
		self.val(value);
	},

	options: function(self, options) {
	    var oldValue = self.val();
	    self.empty();
	    $.each(options, function() {
	        $("<option/>").attr("value", this[0]).text(this[1]).appendTo(self);
	    });
	    self.val(oldValue);
	}
});

jasty.FileUpload = jasty.extend(jasty.Control, {
    init: function(self, opts) {
        self.attr("name", opts.id);
        self.addClass("jasty-fileupload")
    }
});
