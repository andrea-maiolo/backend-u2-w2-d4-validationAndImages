package andream.validation.controllers;


import andream.validation.entities.Author;
import andream.validation.exceptions.ValidationException;
import andream.validation.payloads.NewAuthorDTO;
import andream.validation.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author addAuthor(@RequestBody @Validated NewAuthorDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream()
                    .map(e -> e.getDefaultMessage())
                    .toList());
        }
        return this.authorService.saveAuthor(payload);
    }

    @GetMapping
    public Page<Author> getAllAuthors(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sortBy) {
        return this.authorService.getAll(page, size, sortBy);
    }

    @GetMapping("/{authorId}")
    public Author getById(@PathVariable UUID authorId) {
        return this.authorService.getByID(authorId);
    }

    @PutMapping("/{authorId}")
    public Author modifyAuthor(@RequestBody @Validated NewAuthorDTO payload, @PathVariable UUID authorId,
                               BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream()
                    .map(e -> e.getDefaultMessage())
                    .toList());
        }
        return this.authorService.modifyAuthor(payload, authorId);
    }

    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable UUID authorId) {
        this.authorService.deleteAuthor(authorId);
    }
}
