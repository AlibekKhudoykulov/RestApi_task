package com.epam.esm.Controller;

import com.epam.esm.Entity.Tag;
import com.epam.esm.Payload.ApiResponse;
import com.epam.esm.Service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping("/all")
    public HttpEntity<?> getAllTags() throws SQLException, ClassNotFoundException {
        ApiResponse allTags = tagService.getAllTags();
        return ResponseEntity.status(allTags.isSuccess()?200:404).body(allTags);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getTagById(@PathVariable Integer id) throws SQLException, ClassNotFoundException {
        ApiResponse tagById = tagService.getTagById(id);
        return ResponseEntity.status(tagById.isSuccess()?200:404).body(tagById);
    }

    @PostMapping
    public HttpEntity<?> addTag(@RequestBody Tag tag) throws SQLException, ClassNotFoundException {
        ApiResponse apiResponse = tagService.addTag(tag);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteTagById(@PathVariable Integer id) throws SQLException, ClassNotFoundException {
        ApiResponse apiResponse = tagService.deleteTagById(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:404).body(apiResponse);
    }
}
