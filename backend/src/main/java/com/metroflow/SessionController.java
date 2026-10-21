
package com.metroflow;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/sessions")
@CrossOrigin(origins = "http://localhost:5173")
public class SessionController {

    private final PassengerSessionService sessionService;

    public SessionController(PassengerSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public PassengerSession createSession() {
        return sessionService.createSession();
    }
}
