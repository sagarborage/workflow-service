package com.sowermate.user.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

@Component
public class ConfirmationCodeUtils {
        private static final int CODE_LENGTH = 6;
        private static final String CODE_CHARACTERS = "0123456789";
        private static Set<String> generatedCodes = new HashSet<>();

        public String generateConfirmationCode() {
            SecureRandom random = new SecureRandom();
            StringBuilder code = new StringBuilder(CODE_LENGTH);

            do {
                for (int i = 0; i < CODE_LENGTH; i++) {
                    int randomIndex = random.nextInt(CODE_CHARACTERS.length());
                    code.append(CODE_CHARACTERS.charAt(randomIndex));
                }
            } while (!isUnique(code.toString()));

            generatedCodes.add(code.toString());
            return code.toString();
        }

        private static boolean isUnique(String code) {
            return !generatedCodes.contains(code);
        }

    }



