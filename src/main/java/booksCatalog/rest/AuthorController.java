package booksCatalog.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import booksCatalog.entities.Author;
import booksCatalog.repositories.AuthorRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class AuthorController {

	@Autowired
	AuthorRepo authorRepo;

	@GetMapping("/authors")
	@Operation(summary = "List all authors", description = "Retrieve a list of all Authors.")
	public List<Author> getAllAuthors() {
		return authorRepo.findAll();
	}

	@PostMapping("/add/author")
	@Operation(summary = "Add a new Author", description = "Add a new Author to the Books Catalog.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Author added successfully"),
			@ApiResponse(responseCode = "400", description = "Bad request, Author Id is already present"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public Author addAuthor(@Valid @RequestBody Author author) {
		try {
			var optAuthor = authorRepo.findById(author.getAuId());
			if (optAuthor.isPresent())
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author Id is already present");
			authorRepo.save(author);
			return author;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	@PutMapping("/update/author/{authorId}")
	@Operation(summary = "Update an Author", description = "Update an existing Author's information.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Author updated successfully"),
			@ApiResponse(responseCode = "400", description = "Bad request, Author Id is not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public Author updateAuthor(
			@Parameter(description = "Author Id to be Updated existing Author", allowEmptyValue = false) @PathVariable("authorId") String id,
			@RequestBody Author author) {
		try {
			var optAuthor = authorRepo.findById(id);
			if (!optAuthor.isPresent())
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author Id is not found");
			var author1 = optAuthor.get();
			if (author.getAuName() != null)
				author1.setAuName(author.getAuName());
			if (author.getEmail() != null)
				author1.setEmail(author.getEmail());
			if (author.getMobile() != null)
				author1.setMobile(author.getMobile());
			if (author.getCity() != null)
				author1.setCity(author.getCity());
			if (author.getCountry() != null)
				author1.setCountry(author.getCountry());
			authorRepo.save(author1);
			return author1;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	@DeleteMapping("/delete/author/{auId}")
	@Operation(summary = "Delete an author", description = "Delete an Author from the Books Catalog by their ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Author deleted successfully"),
			@ApiResponse(responseCode = "400", description = "Bad request, Author Id is not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public void deleteAuthor(
			@Parameter(description = "Author Id to be deleted", allowEmptyValue = false) @PathVariable("auId") String auId) {
		try {
			if (authorRepo.findById(auId).isPresent())
				authorRepo.deleteById(auId);
			else
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author Id is not found");
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

}
