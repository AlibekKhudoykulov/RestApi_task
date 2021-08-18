package com.epam.esm.Service;

import com.epam.esm.Entity.Tag;
import com.epam.esm.Payload.ApiResponse;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TagServiceTest {

    @Test
    void getAllTags() throws SQLException, ClassNotFoundException {
        TagService tagService=new TagService();
        ApiResponse allTags = tagService.getAllTags();
        Object allTag = allTags.getObject();
        ArrayList<Object> allTagsToCheck = new ArrayList(Arrays.asList(allTag));

        assertTrue(allTagsToCheck.size()>0);
        assertTrue(allTags.isSuccess());
    }

    @Test
    void getTagById() throws SQLException, ClassNotFoundException {
        TagService tagService=new TagService();
        tagService.deleteTagById(1);

        ApiResponse tagById = tagService.getTagById(1);

        assertNull(tagById.getObject());
        assertFalse(tagById.isSuccess());
    }

    @Test
    void deleteTagById() throws SQLException, ClassNotFoundException {
        int id=5;

        TagService tagService=new TagService();
        tagService.deleteTagById(id);

        ApiResponse tagById = tagService.getTagById(id);
        assertFalse(tagById.isSuccess());
        assertNull(tagById.getObject());

    }

    @Test
    void addTag() throws SQLException, ClassNotFoundException {
        TagService tagService=new TagService();
        ApiResponse addedTagMessage = tagService.addTag(new Tag("salom"));

        assertTrue(addedTagMessage.isSuccess());
    }
}