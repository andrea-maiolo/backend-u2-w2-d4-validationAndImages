package andream.validation.controllers;

import andream.validation.entities.Blog;
import andream.validation.exceptions.ValidationException;
import andream.validation.payloads.NewBlogDTO;
import andream.validation.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Blog addBlog(@RequestBody @Validated NewBlogDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException("da finire");
        }
        return this.blogService.saveBlog(payload);
    }

    @GetMapping
    public Page<Blog> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                             @RequestParam(defaultValue = "10") int pageSize,
                             @RequestParam(defaultValue = "id") String sort) {
        return this.blogService.getAll(pageNumber, pageSize, sort);
    }

    @GetMapping("/{blogId}")
    public Blog getBlogById(@PathVariable UUID blogId) {
        return this.blogService.findBlogById(blogId);
    }

    @PutMapping("/{blogId}")
    public Blog modifyBlog(@RequestBody @Validated NewBlogDTO payload, @PathVariable UUID blogId,
                           BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException("da finire");
        }
        return this.blogService.modifyBlog(payload, blogId);
    }

    @DeleteMapping("/{blogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlog(@PathVariable UUID blogId) {
        this.blogService.deleteBlog(blogId);
    }

}
