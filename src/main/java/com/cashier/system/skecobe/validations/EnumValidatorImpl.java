package com.cashier.system.skecobe.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {
    private Enum<?>[] enumValues;

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClazz();
        enumValues = enumClass.getEnumConstants();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null values are considered valid
        }

        // Cek apakah nilai ada dalam daftar enumValues
        for (Enum<?> enumValue : enumValues) {
            if (enumValue.name().equals(value)) {
                return true; // Nilai valid
            }
        }

        // Jika nilai tidak ada dalam enumValues, maka nilai tidak valid
        return false;
    }
}
