package com.janluk.meeteevent.tag;

import com.janluk.meeteevent.tag.dto.TagCreateRequest;
import com.janluk.meeteevent.tag.dto.TagDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TagDTO>> getAllTags() {
        List<TagDTO> tags = tagService.fetchAllTags();

        return ResponseEntity.status(HttpStatus.OK).body(tags);
    }

    @GetMapping(value = "/unassigned/event/{event_id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TagDTO>> getAllUnassignedEventTagsByEventId(@PathVariable("event_id") UUID eventId) {
        List<TagDTO> tags = tagService.fetchAllUnassignedEventTagsByEventId(eventId);

        return ResponseEntity.status(HttpStatus.OK).body(tags);
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<TagCreateRequest> registerTag(@Valid @RequestBody TagCreateRequest newTag) {
        tagService.createTag(newTag);

        return ResponseEntity.status(HttpStatus.CREATED).body(newTag);
    }

    @PostMapping(value = "/{id}/event/{event_id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addTagToEvent(@PathVariable UUID id, @PathVariable("event_id") UUID eventId) {
        tagService.addTagToEvent(id, eventId);

        return ResponseEntity.status(HttpStatus.CREATED).body(eventId.toString());
    }

    @DeleteMapping(value = "/{id}/event/{event_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTagFromEvent(@PathVariable UUID id, @PathVariable("event_id") UUID eventId) {
        tagService.deleteTagFromEvent(id, eventId);
    }
}
