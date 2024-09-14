package capstone.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SSNLongValidator implements ConstraintValidator<SSNValidator, Long> {

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value== null) {
            return false;
        }
        // Convert to string and check if it has exactly 9 digits
        String ssnStr = String.valueOf(value);
        return ssnStr.length() == 9 && ssnStr.matches("\\d{9}");
    }
    }

