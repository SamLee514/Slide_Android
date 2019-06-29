package com.roodac.slide_android;

import android.graphics.Color;
import android.widget.EditText;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class TextFieldParser {

    // sets the placeholder text to red and displays an appropriate error message
    static void emptyError(EditText field, String error) {
        field.setHintTextColor(Color.RED);
        field.setHint(error);
    }

    // verifies the format and completion of a text field entry
    static boolean validate(Map<String, EditText> textFields) {

        boolean completeForm = true;

        // iterates through each textField and checks if it is empty
        for (HashMap.Entry<String, EditText> entry : textFields.entrySet()) {
            if (entry.getValue().getText().toString().isEmpty()) {
                emptyError(entry.getValue(), String.format("please enter your %s", entry.getKey()));
                completeForm = false;
            }

            // regex email matching
            boolean valid = TextFieldParser.isValidEmail(entry.getValue().getText().toString());
            if (entry.getKey().equals("email") && !valid) {
                entry.getValue().setError("Please enter a valid email address");
                completeForm = false;
            }
        }
        return completeForm;
    }

    // Checks that an email string conforms to styling and format standards
    static boolean isValidEmail(String input) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);

        return input != null && pat.matcher(input.trim()).matches();
    }
}
