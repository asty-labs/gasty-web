; (function($) {
    var markerClass = "watermark";
    var markerClassInput = "watermarkInput";
    var watermarkText = "watermarkText";
    var inputName = "watermarkName";
    var rreturn = /\s/g;

	// Detects if the browser can handle native placeholders
	var hasNativePlaceholder = ( "placeholder" in document.createElement( "input" ) );

    function show($input) {
        var elem = $input[0];
        var val = (elem.value || "").replace(rreturn, "");
        if (val.length > 0) {
            $input.removeClass(markerClass);
            $input.attr("name", $input.data(inputName));
            $input.next("input.watermarkEmptyValue").remove();
        }
        else {
            elem.value = $input.data(watermarkText);
            $input.attr("name", $input.data(inputName) + "_wmdummy");
            $input.addClass(markerClass);
            if(!$input.next("input.watermarkEmptyValue").length) {
                $input.after($("<input/>").attr("name", $input.data(inputName)).attr("type", "hidden").addClass("watermarkEmptyValue"));
            }
        }
    }

    function hide($input, focus) {
        var elem = $input[0];
        $input.next("input.watermarkEmptyValue").remove();
        if($input.hasClass(markerClass)) {
            $input.removeClass(markerClass);
            elem.value = "";
            $input.attr("name", $input.data(inputName))
        }
    }

    $.fn.watermark = function(opts) {
        return this.each(
            function() {
                var $input = $(this);

	            if (hasNativePlaceholder) {
		            $input.attr( "placeholder", opts.text );
		            return;
	            }

                $input.focus(
                    function () {
                        hide($input, true);
                    }
                ).blur(
                    function (e) {
                        show($input);
                    }
                ).bind("dragenter",
                    function (e) {
                        hide($input);
                    }
                ).bind("dragleave",
                    function (e) {
                        show($input);
                    }
                ).bind("dragend",
                    function () {
                        window.setTimeout(function () { show($input); }, 1);
                    }
                ).bind("drop",
                    function (evt) {
                        var elem = $input[0];
                        $input.focus();
                    }
                );
                $input.addClass(markerClassInput);
                $input.data(watermarkText, opts.text);
                $input.data(inputName, $input.attr("name"));
                if($input.is(":focus"))
                    hide($input, false);
                else
                    show($input);
            }
        );
    };

    (function (valOld) {

        $.fn.val = function () {

            if ( !this.length ) {
                return arguments.length? this : undefined;
            }

            if ( !arguments.length ) {
                if ( this.hasClass(markerClass) ) {
                    return "";
                }
                else {
                    return valOld.apply( this, arguments );
                }
            }
            else {
                valOld.apply( this, arguments );
                this.filter("." + markerClassInput).each (
                    function() {
                        show($(this));
                    }
                );
                return this;
            }
        };

    })($.fn.val);
})
(jQuery);