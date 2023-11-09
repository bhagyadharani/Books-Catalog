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

import booksCatalog.entities.Publisher;
import booksCatalog.repositories.PublisherRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class PublisherController {

	@Autowired
	PublisherRepo publisherRepo;

	@GetMapping("/publishers")
	@Operation(summary = "List all publishers", description = "Retrieve a list of all Publishers.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved Publishers"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Publisher> getAllPublishers() {
		return publisherRepo.findAll();
	}

	@PostMapping("/add/publisher")
	@Operation(summary = "Add a publisher", description = "Add a new publisher to the catalog.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Publisher added successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request - Publisher Id is already present"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public Publisher addPublisher(@Valid @RequestBody Publisher publisher) {
		try {
			var optPublisher = publisherRepo.findById(publisher.getPubId());
			if (optPublisher.isPresent())
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Publisher Id is already present!");
			publisherRepo.save(publisher);
			return publisher;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	@PutMapping("/update/publisher/{id}")
	@Operation(summary = "Update a publisher", description = "Update an existing publisher in the Books Catalog.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Publisher updated successfully"),
			@ApiResponse(responseCode = "404", description = "Not Found - Publisher Id is not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public Publisher updatePublisher(
			@Parameter(description = "Publisher Id to be Updated existing Publisher", allowEmptyValue = false) @PathVariable("id") String id,
			@RequestBody Publisher publisher) {

		try {
			var optPublisher = publisherRepo.findById(id);
			if (!optPublisher.isPresent())
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher Id is not found!");
			var publisher1 = optPublisher.get();
			if (publisher.getPubName() != null)
				publisher1.setPubName(publisher.getPubName());
			if (publisher.getEmail() != null)
				publisher1.setEmail(publisher.getEmail());
			if (publisher.getCity() != null)
				publisher1.setCity(publisher.getCity());
			if (publisher.getCountry() != null)
				publisher1.setCountry(publisher.getCountry());
			publisherRepo.save(publisher1);
			return publisher1;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	@DeleteMapping("/delete/publisher/{pubId}")
	@Operation(summary = "Deletes a Publisher", description = "Deletes a Publisher from the Books Catalog.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Publisher deleted successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request - Publisher Id is not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public void deletePublisher(@PathVariable("pubId") String pubId) {
		try {
			if (publisherRepo.findById(pubId).isPresent())
				publisherRepo.deleteById(pubId);
			else
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Publisher Id is not Found!");
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

}
