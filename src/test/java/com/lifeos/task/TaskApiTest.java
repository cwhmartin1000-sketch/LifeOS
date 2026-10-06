package com.lifeos.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskApiTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper json;

    @Test
    void taskLifecycleAndFiltering() throws Exception {
        var created = mvc.perform(post("/api/v1/tasks").contentType("application/json")
                .content("{\"title\":\"Practice transactions\",\"dueDate\":\"2026-10-10\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("TODO")).andReturn();
        String id = json.readTree(created.getResponse().getContentAsString()).get("id").asText();
        mvc.perform(get("/api/v1/tasks/" + id)).andExpect(status().isOk());
        mvc.perform(put("/api/v1/tasks/" + id).contentType("application/json")
                .content("{\"title\":\"Review isolation\",\"description\":\"PostgreSQL exercise\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.title").value("Review isolation"));
        mvc.perform(patch("/api/v1/tasks/" + id + "/status").contentType("application/json")
                .content("{\"status\":\"DONE\"}")).andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DONE"));
        mvc.perform(get("/api/v1/tasks?status=DONE")).andExpect(status().isOk())
                .andExpect(jsonPath("$.items[?(@.id == '" + id + "')].status").value(org.hamcrest.Matchers.hasItem("DONE")));
        mvc.perform(delete("/api/v1/tasks/" + id)).andExpect(status().isNoContent());
        mvc.perform(get("/api/v1/tasks/" + id)).andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith("application/problem+json"));
    }

    @Test
    void rejectsInvalidRequests() throws Exception {
        mvc.perform(post("/api/v1/tasks").contentType("application/json").content("{\"title\":\" \"}"))
                .andExpect(status().isBadRequest());
        mvc.perform(get("/api/v1/tasks?size=101")).andExpect(status().isBadRequest());
        mvc.perform(get("/api/v1/tasks?status=UNKNOWN")).andExpect(status().isBadRequest());
        mvc.perform(patch("/api/v1/tasks/00000000-0000-0000-0000-000000000000/status")
                .contentType("application/json").content("{}")).andExpect(status().isBadRequest());
    }
}
