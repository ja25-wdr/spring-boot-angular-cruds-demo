package org.ja.web.validators;

import org.ja.web.models.WebSiteBlog;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("beforeCreateWebSiteBlogValidator")
public class WebSiteBlogValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return WebSiteBlog.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {

        WebSiteBlog blog = (WebSiteBlog) obj;
        if (checkInputString(blog.getTitle())) {
            errors.rejectValue("title", "title.empty");
        }

        if (checkInputString(blog.getContents())) {
            errors.rejectValue("contents", "contents.empty");
        }
    }

    private boolean checkInputString(String input) {
        return (input == null || input.trim().length() == 0);
    }
}
