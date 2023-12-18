package com.janluk.meeteevent.event.mapper;

import com.janluk.meeteevent.event.mapper.annotations.TagsMapping;
import com.janluk.meeteevent.tag.Tag;
import com.janluk.meeteevent.tag.TagRepository;
import com.janluk.meeteevent.exception.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OptionalTagsMapper {

    private final TagRepository tagRepository;

    @TagsMapping
    public Set<Tag> fetchTags(List<UUID> ids) {
        Set<Tag> tags = new HashSet<>();
        ids.forEach(id -> {
            Tag tag = tagRepository.fetchById(id)
                    .orElseThrow(() -> new ResourceNotFound("Tag with id: " + id + " not found"));
            tags.add(tag);
        });

        return tags;
    }
}
