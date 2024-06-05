package core.basesyntax.validators;

import core.basesyntax.exeptions.InvalidUserException;
import core.basesyntax.model.User;

public class UserForRegistrationValidator extends UserValidator {
    private final static String INCORRECT_PASSWORD_PATTERN_MESSAGE = "Password must contain at least one digit [0-9].\n" +
            "Password must contain at least one lowercase Latin character [a-z].\n" +
            "Password must contain at least one uppercase Latin character [A-Z].\n" +
            "Password must contain at least one special character like ! @ # & ( ).\n" +
            "Password must contain a length of at least 8 characters and a maximum of 20 characters.";
    private final static String NEGATIVE_AGE_MESSAGE = "Age could not be a negative number";
    private final static String MINIMUM_USER_AGE_MESSAGE = "User age should be equal or more than ";
    private final static String PASSWORD_REGEXP =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private int minimumUserAge;

    public UserForRegistrationValidator(User user, int minimumUserAge) {
        super(user);
        this.minimumUserAge = minimumUserAge;
    }

    public UserForRegistrationValidator(User user) {
        super(user);
    }

    @Override
    public void validateUser() throws InvalidUserException {
        super.validateUser();
        validatePasswordStrength();
        validateUserAge();
    }

    private void validatePasswordStrength() throws InvalidUserException {
        if (!user.getPassword().matches(PASSWORD_REGEXP)) {
            throw new InvalidUserException(INCORRECT_PASSWORD_PATTERN_MESSAGE);
        }
    }

    private void validateUserAge() throws InvalidUserException {
        if (user.getAge() < 0) {
            throw new InvalidUserException(NEGATIVE_AGE_MESSAGE);
        }
        if (user.getAge() < minimumUserAge) {
            throw new InvalidUserException(MINIMUM_USER_AGE_MESSAGE + minimumUserAge);
        }
    }
}