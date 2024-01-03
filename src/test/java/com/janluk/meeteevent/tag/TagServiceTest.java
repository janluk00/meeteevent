package com.janluk.meeteevent.tag;

import com.janluk.meeteevent.tag.dto.TagCreateRequest;
import com.janluk.meeteevent.tag.dto.TagDTO;
import com.janluk.meeteevent.tag.mapper.TagMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    TagRepository tagRepository;

    @Mock
    TagMapper tagMapper;

    @InjectMocks
    TagService tagService;

    private Tag tag;
    private TagCreateRequest tagCreateRequest;
    private TagDTO tagDTO;

    @BeforeEach
    void setUp() {
        // TODO: create entity factory instead of this
        tag = Tag.builder()
                .name("name")
                .events(Set.of())
                .build();

        UUID tagId = tag.getId();


        tagDTO = TagDTO.builder()
                .id(tagId)
                .name("name")
                .build();

        tagCreateRequest = new TagCreateRequest("name");
    }

    @Test
    void findAllTagsShouldReturnOne() {
        when(tagRepository.findAll()).thenReturn(List.of(tag));
        when(tagMapper.toTagDTO(tag)).thenReturn(tagDTO);

        List<TagDTO> result = tagService.fetchAllTags();

        assertEquals(List.of(tagDTO), result);
        verify(tagRepository).findAll();
        verify(tagMapper).toTagDTO(tag);
    }

    @Test
    void createTagShouldSaveTag() {
        when(tagMapper.toTag(tagCreateRequest)).thenReturn(tag);

        tagService.createTag(tagCreateRequest);

        verify(tagRepository).save(tag);
    }
}
