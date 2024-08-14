package com.ust.controller;



import com.ust.model.Author;
import com.ust.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping
    public List<Author> getAllAuthors() {
        return libraryService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Optional<Author> author = libraryService.getAuthorById(id);
        return author.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        return libraryService.saveAuthor(author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        if (!libraryService.getAuthorById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        author.setId(id);
        return ResponseEntity.ok(libraryService.saveAuthor(author));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        if (!libraryService.getAuthorById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        libraryService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
