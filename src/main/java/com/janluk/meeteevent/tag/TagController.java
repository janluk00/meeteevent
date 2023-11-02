package com.janluk.meeteevent.tag;

import com.janluk.meeteevent.tag.dto.TagCreateRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/tag")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<TagCreateRequest> registerTag(@Valid @RequestBody TagCreateRequest newTag) {
        tagService.createTag(newTag);

        return ResponseEntity.status(HttpStatus.CREATED).body(newTag);
    }

    @PostMapping(value = "/{id}/add/event/{event_id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addTagToEvent(@PathVariable UUID id, @PathVariable("event_id") UUID eventId) {
        tagService.addTagToEvent(id, eventId);

        return ResponseEntity.status(HttpStatus.CREATED).body(eventId.toString());
    }
}
