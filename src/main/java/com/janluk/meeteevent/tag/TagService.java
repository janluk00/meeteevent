package com.janluk.meeteevent.tag;

import com.janluk.meeteevent.event.Event;
import com.janluk.meeteevent.event.EventRepository;
import com.janluk.meeteevent.tag.dto.TagCreateRequest;
import com.janluk.meeteevent.tag.dto.TagDTO;
import com.janluk.meeteevent.tag.mapper.TagMapper;
import com.janluk.meeteevent.exception.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<TagDTO> fetchAllTags() {
        List<Tag> tags = tagRepository.findAll();

        return tags.stream()
                .map(tagMapper::toTagDTO)
                .collect(Collectors.toList());
    }

    public void createTag(TagCreateRequest tag) {
        Tag saveTag = tagMapper.toTag(tag);

        tagRepository.save(saveTag);
    }

    public List<TagDTO> fetchAllUnassignedEventTagsByEventId(UUID eventId) {
        List<Tag> tags = tagRepository.findAllUnassignedEventTagsByEventId(eventId);

        return tags.stream()
                .map(tagMapper::toTagDTO)
                .collect(Collectors.toList());
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

    public void deleteTagFromEvent(UUID tagId, UUID eventId) {
        Tag tag = tagRepository.fetchById(tagId)
                .orElseThrow(() -> new ResourceNotFound("Tag with id: " + tagId + " not found"));

        Event event = eventRepository.fetchById(eventId)
                .orElseThrow(() -> new ResourceNotFound("Event with id: " + eventId + " not found"));

        event.deleteTag(tag);
        eventRepository.save(event);
    }
}
