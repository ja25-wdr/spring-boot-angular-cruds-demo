package org.aj.web.validators;

import org.aj.web.models.WebSiteStory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("beforeCreateWebSiteStoryValidator")
public class WebSiteStoryValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return WebSiteStory.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {

        WebSiteStory story = (WebSiteStory) obj;
        if (checkInputString(story.getTitle())) {
            errors.rejectValue("title", "title.empty");
        }

        if (checkInputString(story.getContents())) {
            errors.rejectValue("contents", "contents.empty");
        }
    }

    private boolean checkInputString(String input) {
        return (input == null || input.trim().length() == 0);
    }
}
