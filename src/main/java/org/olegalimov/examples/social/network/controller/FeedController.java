package org.olegalimov.examples.social.network.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Slf4j
@RequestMapping(value = "/feed", method = RequestMethod.POST)
public class FeedController {
    @GetMapping
    public String getFeedPage() {
        return "feed";
    }
}
