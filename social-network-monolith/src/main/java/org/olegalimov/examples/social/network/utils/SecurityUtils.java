package org.olegalimov.examples.social.network.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class SecurityUtils {
    public static String getUserFromContext() {
        var context = SecurityContextHolder.getContext();
        if(context == null || context.getAuthentication() == null) {
            throw new IllegalStateException("Информация о текущем пользователе не найдена!");
        }
        return context.getAuthentication().getName();
    }
}
