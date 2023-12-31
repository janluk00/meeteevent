package com.janluk.meeteevent.tag;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.janluk.meeteevent.event.dto.EventCreateRequest;
import com.janluk.meeteevent.tag.dto.TagCreateRequest;
import com.janluk.meeteevent.tag.dto.TagDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TagController.class)
public class TagControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TagService tagService;

    private TagDTO first;
    private TagDTO second;
    private TagCreateRequest tagRequest;
    private UUID TAG_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        // TODO: create entity factory instead of this

        first = TagDTO.builder()
                .id(TAG_ID)
                .name("Tag")
                .build();

        second = TagDTO.builder()
                .id(UUID.randomUUID())
                .name("Tag2")
                .build();

        tagRequest = new TagCreateRequest("Tag Create");
    }

    @Test
    @WithMockUser
    void findAllTagsShouldFindTwo() throws Exception {
        when(tagService.fetchAllTags()).thenReturn(List.of(first, second));

        mvc.perform(
                        get("/api/v1/tags/all")
                                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void createTagShouldNotAllowCreationForUnauthenticatedUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String request = objectMapper.writeValueAsString(tagRequest);

        mvc.perform(
                        post("/api/v1/tags")
                                .contentType(APPLICATION_JSON)
                                .content(request)
                                .with(csrf()))
                .andExpect(status().isUnauthorized());

        verify(tagService, never()).createTag(any(TagCreateRequest.class));
    }

    @Test
    @WithMockUser
    void createTagShouldAllowCreation() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String request = objectMapper.writeValueAsString(tagRequest);

        mvc.perform(
                        post("/api/v1/tags")
                                .contentType(APPLICATION_JSON)
                                .content(request)
                                .with(csrf()))
                .andExpect(status().isCreated());

        verify(tagService).createTag(any(TagCreateRequest.class));
    }
}
