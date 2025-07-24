package andream.validation.services;

import andream.validation.entities.Author;
import andream.validation.exceptions.BadRequestException;
import andream.validation.exceptions.NotFoundException;
import andream.validation.payloads.NewAuthorDTO;
import andream.validation.repositories.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepo authorRepo;

    public Author saveAuthor(NewAuthorDTO payload) {
        this.authorRepo.findByEmail(payload.email()).ifPresent(a -> {
            throw new BadRequestException("user already registred");
        });

        Author authorToSave = new Author(payload.name(), payload.surname(), payload.email(),
                ("https://ui-avatars.com/api/?name=" + payload.name() + "+" + payload.surname()), payload.dob());
        authorRepo.save(authorToSave);
        return authorToSave;
    }

    public Page<Author> getAll(int pageNumber, int pageSize, String sort) {
        if (pageSize > 10) pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sort).descending());
        return authorRepo.findAll(pageable);
    }

    public Author getByID(UUID authorId) {
        return this.authorRepo.findById(authorId).orElseThrow(() -> new NotFoundException(
                "author not found"
        ));
    }

    public Author modifyAuthor(NewAuthorDTO payload, UUID authorId) {
        Author found = getByID(authorId);
        if (!found.getEmail().equals(payload.email()))
            this.authorRepo.findByEmail(payload.email()).ifPresent(a -> {
                throw new BadRequestException("invalid email");
            });
        found.setName(payload.name());
        found.setSurname(payload.surname());
        found.setEmail(payload.email());
        found.setDob(payload.dob());
        return found;
    }

    public void deleteAuthor(UUID authorId) {
        Author found = getByID(authorId);
        this.authorRepo.delete(found);
    }
}

