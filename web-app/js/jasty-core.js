/**
 * Core jasty functions and components
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
var $$ = function(id) {
	return $(document.getElementById(id));
};

var jasty = {

	raiseEvent: function(self, eventHandler, eventArgs, opts) {
		var form = self.parents("form")[0];
		jasty.Form.raiseEvent($(form), eventHandler, eventArgs, opts);
	},

	extend: function(baseClass, extension) {
		return jQuery.extend({parent: baseClass}, baseClass, extension);
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

jasty.Control = {
    init: function(self, opts) {
        if(!opts.visible)
            self.hide();
        if(opts.clazz)
            self.addClass(opts.clazz);
        if(opts.title)
            self.attr("title", opts.title);

    },

	addClass: function(self, value, duration) {
		self.addClass(value, duration);
	},

	removeClass: function(self, value, duration) {
		self.removeClass(value, duration);
	},

	remove: function(self) {
		self.remove();
	},

	visible: function(self, value) {
	    if(value) self.show();
	    else self.hide();
	},

    replaceWith: function(self, content) {
        var prev = self.prev()
        if(prev.length > 0) {
            self.remove();
            jasty.render(content, function(html) {
                prev.after(html);
            });
            return
        }
        var next = self.next()
        if(next.length > 0) {
            self.remove();
            jasty.render(content, function(html) {
                next.before(html);
            });
            return
        }
        var parent = self.parent()
        self.remove();
        jasty.render(content, function(html) {
            parent.append(html);
        });
    }
}

jasty.Form = jasty.extend(jasty.Control, {
	init: function(self, opts) {
	},

	update: function(self, state) {
		self.data("state", state);
	},

	raiseEvent: function(self, eventHandler, eventArgs, opts) {
		opts = opts || {};
        var data = {eventHandler : eventHandler};
		if(eventArgs) {
		    jQuery.each(eventArgs, function(key, value) {
			    data["EVT." + key] = value;
		    });
		}
        $(".jasty-blocker").addClass("enabled").css("cursor", 'wait').delay(800).queue(function() {
            $(this).addClass("visible");
        });

        data["state"] = self.data("state");
		var props = {
		    url: jasty.settings.formEngineUrl,
		    type: "POST",
		    dataType: "script",
		    data: data,
		    success: function(data) {
		        if (opts && opts.success)
		            opts.success();
                $(".jasty-blocker").stop(true).removeClass("enabled").removeClass("visible").css("cursor", 'auto');
            },
		    error: function(xhr, status, e) {
                if(opts && opts.error) {
                    opts.error(xhr, status, e);
                }
                $(".jasty-blocker").stop(true).removeClass("enabled").removeClass("visible").css("cursor", 'auto');
            }
		};
        self.ajaxSubmit(props);
	},

    openModal: function(self, content, width) {
        jasty.render(content, function(html) {
            var div = $("<div/>");
            $("body").append(div);
            div.append(html);
            var title = $(".FormTitle", div).text();
            $(".FormTitle", div).remove();
            div.dialog({
                modal: true,
                width: width,
                title: title,
                close: function() {
                    div.remove();
                }
            });
            div.parents(".ui-dialog").prepend($("<div/>").addClass("Background"));
        });
    },

    close: function(self) {
        self.parent("div.ui-dialog-content").dialog("close");
    },

    errors: function(self, errors) {
        $(".errorContainer", self).errorMessage({});
        $(".WithError", self).controlState({});
        var globalErrors = "";
        $.each(errors, function() {
            if(this.name && $("#" + this.name).length) {
                $("#" + this.name).controlState({errors:[this.message]});
            }
            else {
                globalErrors += this.message + "\n";
            }
        });
        if(globalErrors != "") {
            if($(".errorContainer", self).length) {
                $(".errorContainer", self).errorMessage({message: globalErrors});
            }else
                alert(globalErrors);
        }
    }

});


; (function($) {
    $.fn.controlState = function(opts) {

        function enableMarker(elem) {
            var element = $(elem)
            if (element.parents(".jasty-error-marker").length > 0)
                return;
            var messageText = "";
            $.each(opts.errors, function() {
                messageText += this + "\n";
            });

            jasty.replace(element, function() {
                var table = $("<div/>").addClass("jasty-error-marker display-text");
                var icon = $("<div/>").addClass("jasty-error-icon").appendTo(table);
                $("<div/>").addClass("container").append($("<div/>").addClass("text").text(messageText)).appendTo(icon);
                icon.click(function() {
                    table.toggleClass("display-text");
                });
                element.detach()
                element.addClass("WithError")
                table.prepend(element)
                return table
            })

        }

        function disableMarker(elem) {
            var element = $(elem)
            if (element.parents(".jasty-error-marker").length == 0)
                return;
            var marker = $(element.parents(".jasty-error-marker")[0])

            jasty.replace(marker, function() {
                element.detach()
                element.removeClass("WithError")
                marker.remove()
                return element
            })
        }

        return this.each(
            function() {
                if (opts.errors && opts.errors.length > 0) {
                    enableMarker(this);
                }
                else {
                    disableMarker(this);
                }
            }
        );
    };
})
(jQuery);

; (function($) {
    $.fn.errorMessage = function(opts) {

        return this.each(
            function() {
                if (opts.message) {
                    if(!$(this).find(".closeBtn").length) {
                        var $this = $(this);
                        $(this).addClass("jasty-errorMessage");
                        $("<div/>").addClass("message").appendTo(this);
                        $("<div/>").addClass("closeBtn").click(function() {
                            $this.hide();
                        }).appendTo(this);
                    }
                    $(this).show();
                    $(this).find(".message").text(opts.message);
                }
                else {
                    $(this).hide();
                }
            }
        );
    };
})
    (jQuery);

jasty.Page = {

    reload: function() {
        window.location.reload(true);
    },

    sessionTimeout: function(self, message) {
        alert(message);
        window.location.reload(true);
    }
}