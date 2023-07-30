package org.olegalimov.examples.social.network.dialogs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.olegalimov.examples.social.network.dialogs.constant.ApplicationConstants.APP_PACKAGE_NAME;
import static org.olegalimov.examples.social.network.dialogs.constant.ApplicationConstants.CORE_PACKAGE_NAME;

@SpringBootApplication(scanBasePackages = {CORE_PACKAGE_NAME, APP_PACKAGE_NAME})
public class SocialNetworkDialogsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SocialNetworkDialogsApplication.class, args);
    }
}