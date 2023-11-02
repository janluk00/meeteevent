package com.janluk.meeteevent.tag;

import com.janluk.meeteevent.event.Event;
import com.janluk.meeteevent.event.EventRepository;
import com.janluk.meeteevent.tag.dto.TagCreateRequest;
import com.janluk.meeteevent.tag.mapper.TagMapper;
import com.janluk.meeteevent.utils.exception.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final EventRepository eventRepository;
    private final TagMapper tagMapper;

    @Autowired
    public TagService(TagRepository tagRepository, EventRepository eventRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.eventRepository = eventRepository;
        this.tagMapper = tagMapper;
    }

    public void createTag(TagCreateRequest tag) {
        Tag saveTag = tagMapper.toTag(tag);

        tagRepository.save(saveTag);
    }

    public void addTagToEvent(UUID tagId, UUID eventId) {
        Tag tag = tagRepository.fetchById(tagId)
                .orElseThrow(() -> new ResourceNotFound("Tag with id: " + tagId + " not found"));

        // TODO: ADD EXCEPTION ALREADY ADDED

        Event event = eventRepository.fetchById(eventId)
                .orElseThrow(() -> new ResourceNotFound("Event with id: " + eventId + " not found"));

        event.addTag(tag);
        eventRepository.save(event);
    }
}
