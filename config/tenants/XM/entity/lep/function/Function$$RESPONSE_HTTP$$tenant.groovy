package function

import org.springframework.http.ResponseEntity;

// return HTTP response with status code 201, additional header and new body
return ResponseEntity.status(201)
        .header("X-B3-TraceId", java.util.UUID.randomUUID().toString())
        .body("Custom response")