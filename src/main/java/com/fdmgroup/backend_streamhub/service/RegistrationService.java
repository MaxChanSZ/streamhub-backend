/**
 * Service class that handles registration-related operations.
 */

package com.fdmgroup.backend_streamhub.service;

import com.fdmgroup.backend_streamhub.exceptions.*;
import com.fdmgroup.backend_streamhub.model.User;
import com.fdmgroup.backend_streamhub.repository.UserRepository;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    /**
     * Logger to monitor operational flow and facilitate troubleshooting.
     */
    private static final Logger registrationServiceLogger = LogManager.getLogger(RegistrationService.class);

    private final UserRepository userRepository;

    @Autowired
    public RegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registers the User.
     *
     * @param username     The username entered during registration.
     * @param email        The email address entered during registration.
     * @param password     The password entered during registration.
     * @throws InvalidUsernameException Exception thrown for invalid username.
     */
    public void registerUser(String username, String email, String password) throws InvalidUsernameException,
                                                                                    InvalidEmailAddressException,
                                                                                    InvalidPasswordException,
                                                                                    UnavailableUsernameException,
                                                                                    UnavailableEmailAddressException,
                                                                                    UnavailablePasswordException {
        registrationServiceLogger.info("Registration attempt | Username: {}, Email Address: {}, Password: {}",
                                        username, email, password);

        if (!isValidUsername(username)) {
            throw new InvalidUsernameException();
        }

        if (!isValidEmailAddress(email)) {
            throw new InvalidEmailAddressException();
        }

        if (!isValidPassword(password)) {
            throw new InvalidPasswordException();
        }

        if (!isUsernameAvailable(username)) {
            throw new UnavailableUsernameException();
        }

        if (!isEmailAddressAvailable(email)) {
            throw new UnavailableUsernameException();
        }

        if (!isPasswordAvailable(password)) {
            throw new UnavailableUsernameException();
        }

        userRepository.save(new User(username, email, password));
    }

    /**
     * Checks if an email address is available for registration.
     *
     * @param email The email address to check.
     * @return True if the email address has not been registered with any User entity, false otherwise.
     */
    private boolean isEmailAddressAvailable(String email) {
        return userRepository.findByEmailAddress(email).isEmpty();
    }

    /**
     * Checks if a password is available for registration.
     *
     * @param password The password to check.
     * @return True if the password has not been registered with any User entity, false otherwise.
     */
    private boolean isPasswordAvailable(String password) {
        return userRepository.findByPassword(password).isEmpty();
    }

    /**
     * Checks if a username is available for registration.
     *
     * @param username The username to check.
     * @return True if the username has not been registered with any User entity, false otherwise.
     */
    private boolean isUsernameAvailable(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }

    /**
     * Checks that a password is valid.
     *
     * @param password The password entered during registration.
     * @return True for valid password, false otherwise.
     */
    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        int numberOfUppercaseLetters = 0;
        int numberOfNumbers = 0;
        int numberOfSpecialCharacters = 0;
        for (int i = 0; i < password.length(); i++) {
            char character = password.charAt(i);
            if (Character.isUpperCase(character)) {
                numberOfUppercaseLetters++;
            }
            if (Character.isDigit(character)) {
                numberOfNumbers++;
            }
            if (!Character.isLetterOrDigit(character)) {
                numberOfSpecialCharacters++;
            }
        }

        return numberOfUppercaseLetters >= 1 && numberOfNumbers >=1 && numberOfSpecialCharacters >= 1;
    }

    /**
     * Checks that an email address is valid.
     *
     * @param email The email address entered during registration.
     * @return True for valid email address, false otherwise.
     */
    private boolean isValidEmailAddress(String email) {
        if (email == null) {
            return false;
        }
        String trimmedEmail = email.trim();
        return trimmedEmail.matches("[a-zA-Z][a-zA-Z0-9_]*@[a-zA-Z]+\\.com");
    }

    /**
     * Checks that a username is valid
     *
     * @param   username The username entered during registration.
     * @return  True if the username consists of only one word, false otherwise.
     */
    private boolean isValidUsername(String username) {
        String[] word = username.split(" ");
        return word.length == 1;
    }

}
