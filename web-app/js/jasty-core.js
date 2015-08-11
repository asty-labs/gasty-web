/**
 * Core jasty functions and components
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */

var jQ = jQuery;

var jQi = function(id) {
	return jQuery(document.getElementById(id));
};


var jasty = {

    block: function() { },

    unblock: function() { },

    confirm: function(title, text, op) {
        if(confirm(text)) {
            op();
        }
    },

    call: function (url, data, opts) {
        opts = opts || {};

        var useBlock = !opts.noBlock

        if(useBlock) jasty.block();

        var props = {
            url: url,
            type: "POST",
            dataType: "script",
            data: data,
            success: function (data) {
                if (opts && opts.success)
                    opts.success();
                if(useBlock) jasty.unblock();
            },
            error: function (xhr, status, e) {
                if (opts && opts.error) {
                    opts.error(xhr, status, e);
                }
                if(useBlock) jasty.unblock();
            }
        };
        jQuery.ajax(props);
    },

	raiseEvent: function(self, eventHandler, eventArgs, opts) {
		var form = self.closest(".jasty-form");
        if(eventHandler.indexOf(":") >= 0) {
            var clientHandler = eventHandler.substring(eventHandler.indexOf(":") + 1, eventHandler.length);
            var parameter = eventHandler.substring(0, eventHandler.indexOf(":"));
            if(!jasty.Form[clientHandler]) throw "No form handler: " + clientHandler;
            jasty.Form[clientHandler](parameter, self, form, eventArgs, opts);
        } else
		    jasty.Form.raiseEvent(form, eventHandler, eventArgs, opts);
	},

	extend: function(baseClass, extension) {
		return jQuery.extend({}, baseClass, {parent: baseClass}, extension);
	},

	render: function(obj, htmlClosure) {
        if(typeof obj == 'string')
            htmlClosure(obj);
        else {
            htmlClosure(obj.html);
            obj.script();
        }
	},

    replace: function(self, contentClosure) {
        var prev = self.prev()
        if(prev.length > 0) {
            prev.after(contentClosure());
            return
        }
        var next = self.next()
        if(next.length > 0) {
            next.before(contentClosure());
            return
        }
        var parent = self.parent()
        parent.append(contentClosure())
    }
};

jasty.settings = {};

(function($) {
    $.fn.jasty = function(className, method, params) {
	var result = [];
        this.each(
            function() {
		var obj = jasty[className]
                var allParams = params !== undefined ? [$(this)].concat(params) : [$(this)];
                try {
                    result.push(obj[method].apply(obj, allParams));
                }
                catch(e) {
                    alert("Cannot call method 'jasty." + className + "." + method + "'" + e);
                }
            }
        );
        if(result.length > 0 && result[0] !== undefined) {
            if(result.length > 1)
                return result;
            return result[0];
        }
        return this;
    };
})
(jQuery);

(function($) {

    jasty.Control = {
        init: function (self, opts) {
            if (!opts.visible)
                self.hide();
            if (opts.clazz)
                self.addClass(opts.clazz);
            if (opts.title)
                self.attr("title", opts.title);

        },

        addClass: function (self, value, duration) {
            self.addClass(value, duration);
        },

        removeClass: function (self, value, duration) {
            self.removeClass(value, duration);
        },

        toggleClass: function (self, value, toSet) {
            self.toggleClass(value, toSet);
        },

        data: function (self, name, value) {
            self.data(name, value);
        },


        remove: function (self) {
            self.remove();
        },

        visible: function (self, value) {
            if (value) self.show();
            else self.hide();
        },

        replaceWith: function (self, content) {
            var prev = self.prev()
            if (prev.length > 0) {
                self.remove();
                jasty.render(content, function (html) {
                    prev.after(html);
                });
                return
            }
            var next = self.next()
            if (next.length > 0) {
                self.remove();
                jasty.render(content, function (html) {
                    next.before(html);
                });
                return
            }
            var parent = self.parent()
            self.remove();
            jasty.render(content, function (html) {
                parent.append(html);
            });
        }
    }

    jasty.Form = jasty.extend(jasty.Control, {
        init: function (self, opts) {
            self.addClass("jasty-form");
        },

        update: function (self, state) {
            self.data("state", state);
        },

        raiseEvent: function (self, eventHandler, eventArgs, opts) {
            opts = opts || {};
            var data = {eventHandler: eventHandler};
            if (eventArgs) {
                jQuery.each(eventArgs, function (key, value) {
                    data["EVT." + key] = value;
                });
            }
            var useBlock = !opts.download;

            if(useBlock) jasty.block();

            data["state"] = self.data("state");
            var props = {
                url: jasty.settings.formEngineUrl,
                type: "POST",
                dataType: "script",
                data: data,
                success: function (data) {
                    if (opts && opts.success)
                        opts.success();
                    if(useBlock) jasty.unblock();
                },
                error: function (xhr, status, e) {
                    if (opts && opts.error) {
                        opts.error(xhr, status, e);
                    }
                    if(useBlock) jasty.unblock();
                }
            };
            if(opts.download) props.iframe = true;
            self.closest("form").ajaxSubmit(props);
        },

        errors: function (self, errorMessage) {
            alert(errorMessage);
        }
    });

    jasty.JQuery = jasty.extend(jasty.Control, {

		val: function (self, value) {
			self.val(value);
		},

		text: function (self, value) {
            self.text(value);
        },

        html: function (self, value) {
            jasty.render(value, function (html) {
                self.empty();
                self.append(html);
            });
        },

        append: function (self, content) {
            jasty.render(content, function (html) {
                self.append(html);
            })
        },

        empty: function (self) {
            self.empty();
        },

        toggle: function (self, value) {
            self.toggle(value);
        },

        attr: function (self, name, value) {
            self.attr(name, value);
        },

        prop: function (self, name, value) {
            self.prop(name, value);
        },

		trigger: function (self, eventName, params) {
			self.trigger(eventName, params);
		},

        moveTo: function(self, query) {
            self.detach().appendTo(query)
        }

    });

})(jQuery);