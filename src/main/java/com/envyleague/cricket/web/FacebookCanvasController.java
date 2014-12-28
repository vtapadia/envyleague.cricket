package com.envyleague.cricket.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/social/facebook/canvas")
public class FacebookCanvasController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * TODO Canvas for Facebook to be implemented
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView provideFacebookCanvas() {
        log.info("Facebook canvas called");
        return null;
    }

}
