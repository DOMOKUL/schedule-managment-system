package com.company.schedule.management.system.model.validator.impl;

import com.company.schedule.management.system.model.validator.GroupName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class GroupNameValidator implements ConstraintValidator<GroupName, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }
        if (value.length() != 10) return false;

        boolean isContainsDash = value.chars().anyMatch(c -> c == '-');
        if (!isContainsDash) return false;

        char[] chars = value.toCharArray();
        char lastChar = chars[chars.length - 1];
        List<Integer> indexesOfDash = getIndexOfDash(chars);

        for (int i = 0; i < indexesOfDash.get(0); i++) {
            if (Character.isDigit(chars[i]) || Character.isLowerCase(chars[i])) return false;
        }

        for (int i = indexesOfDash.get(0) + 1; i < indexesOfDash.get(1); i++) {
            if (Character.isLetter(chars[i])) return false;
        }

        for (int i = indexesOfDash.get(1) + 1; i < Integer.parseInt(String.valueOf(lastChar)); i++) {
            if (Character.isLetter(chars[i])) return false;
        }

        return true;
    }

    private List<Integer> getIndexOfDash(char[] chars) {
        List<Integer> indexesOfDash = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '-') {
                indexesOfDash.add(i);
            }
        }
        return indexesOfDash;
    }
}
