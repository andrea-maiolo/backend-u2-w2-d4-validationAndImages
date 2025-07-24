package andream.validation.services;

import andream.validation.entities.Author;
import andream.validation.entities.Blog;
import andream.validation.exceptions.NotFoundException;
import andream.validation.payloads.BlogPayload;
import andream.validation.repositories.BlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BlogService {
    @Autowired
    private BlogRepo blogRepo;

    @Autowired
    private AuthorService authorService;

    public Blog saveBlog(BlogPayload payloadb) {
        Author blogAuthor = this.authorService.getByID(payloadb.getAuthorId());
        Blog blogToSave = new Blog(payloadb.getCategory(), payloadb.getTitle(), payloadb.getContent(),
                "https://picsum.photos/200/300", blogAuthor);
        this.blogRepo.save(blogToSave);
        return blogToSave;
    }

    public Page<Blog> getAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 10) pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber,
                pageSize, Sort.by(sortBy).descending());
        return this.blogRepo.findAll(pageable);
    }


    public Blog findBlogById(UUID blogId) {
        return this.blogRepo.findById(blogId).orElseThrow(() ->
                new NotFoundException("blog not found"));

    }

    public Blog modifyBlog(BlogPayload payload, UUID blogId) {
        Blog found = this.findBlogById(blogId);
        found.setCategory(payload.getCategory());
        found.setTitle(payload.getTitle());
        found.setContent(payload.getContent());
        return found;
    }

    public void deleteBlog(UUID blogId) {
        Blog found = findBlogById(blogId);
        blogRepo.delete(found);
    }

}
