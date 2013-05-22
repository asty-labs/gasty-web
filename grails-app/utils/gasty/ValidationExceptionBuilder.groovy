package gasty

import grails.validation.ValidationException
import org.springframework.validation.FieldError
import org.springframework.validation.BeanPropertyBindingResult

/**
 * Created with IntelliJ IDEA.
 * User: stas
 * Date: 1/10/13
 * Time: 11:14 PM
 * To change this template use File | Settings | File Templates.
 */
class ValidationExceptionBuilder {

    private BeanPropertyBindingResult errors = new BeanPropertyBindingResult(null, "")

    ValidationExceptionBuilder addWithTarget(obj, String errorCode, String field, Object value) {
        def objectName = obj.class.name
        errors.addError(new FieldError(objectName, field, value, false, [objectName + "." + errorCode + ".error"] as String[], [value] as Object[], objectName + "/" + errorCode + ": {0}"))
        this
    }

    ValidationExceptionBuilder add(String field, String messageKey, Object value = "") {
        errors.addError(new FieldError("", field, value, false, [messageKey] as String[], [value] as Object[], messageKey + ": {0}"))
        this
    }

    boolean hasErrors() {
        errors.hasErrors()
    }

    ValidationException build()
    {
        new ValidationException("", errors)
    }

    ValidationException buildSingle(String field, String messageKey, Object value = "") {
        add(field, messageKey, value)
        build()
    }
}
