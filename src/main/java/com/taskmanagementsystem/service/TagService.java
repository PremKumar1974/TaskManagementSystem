package com.taskmanagementsystem.service;

import com.taskmanagementsystem.entity.Tag;
import com.taskmanagementsystem.entity.Task;
import com.taskmanagementsystem.exception.ResourceNotFoundException;
import com.taskmanagementsystem.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    @Transactional
    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional(readOnly = true)
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Transactional
    public void deleteTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + tagId));
        for (Task task : tag.getTasks()) {
            task.getTags().remove(tag);
        }
        tagRepository.delete(tag);
    }
}
