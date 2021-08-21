package com.epam.esm.controller;


import com.epam.esm.exceptionHandling.exceptions.NoContentException;
import com.epam.esm.exceptionHandling.exceptions.NotFoundException;
import com.epam.esm.model.Tag;
import com.epam.esm.payload.response.ApiResponse;
import com.epam.esm.service.TagService;
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

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/all")
    public HttpEntity<?> getAllTags() throws SQLException, ClassNotFoundException {
        ApiResponse allTags = tagService.getAllTags();
        if (allTags.isSuccess()){
            return ResponseEntity.status(200).body(allTags);
        }else throw new NoContentException();
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getTagById(@PathVariable Integer id) throws SQLException, ClassNotFoundException {
        ApiResponse tagById = tagService.getTagById(id);
        if (tagById.isSuccess()){
            return ResponseEntity.status(200).body(tagById);
        }else throw new NotFoundException(id);
    }

    @PostMapping
    public HttpEntity<?> addTag(@RequestBody Tag tag) throws SQLException, ClassNotFoundException {
        ApiResponse apiResponse = tagService.addTag(tag);
        if (apiResponse.isSuccess()){
            return ResponseEntity.status(201).body(apiResponse);
        }else throw new NoContentException();
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteTagById(@PathVariable Integer id) throws SQLException, ClassNotFoundException {
        ApiResponse apiResponse = tagService.deleteTagById(id);
        if (apiResponse.isSuccess()){
            return ResponseEntity.status(200).body(apiResponse);
        }else throw new NotFoundException(id);
    }
}
