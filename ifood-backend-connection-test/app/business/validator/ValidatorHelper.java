package business.validator;

import com.baidu.unbiz.fluentvalidator.Result;
import sun.security.validator.ValidatorException;

public class ValidatorHelper {
    public static void throwException(Result result) throws ValidatorException {
        if(!result.isSuccess()) {
            throw new ValidatorException(result.getErrors());
        }
    }
}
