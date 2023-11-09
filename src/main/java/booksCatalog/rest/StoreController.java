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

import booksCatalog.entities.Store;
import booksCatalog.repositories.StoreRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class StoreController {

	@Autowired
	StoreRepo storeRepo;

	@GetMapping("/stores")
	@Operation(summary = "List all Stores", description = "Retrieve a list of all Stores.")
	public List<Store> getAllStores() {
		return storeRepo.findAll();
	}

	@PostMapping("/add/store")
	@Operation(summary = "Add a new Store", description = "Add a new Store to the Books Catalog.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Store added successfully"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public Store addStore(@Valid @RequestBody Store store) {
		try {
			storeRepo.save(store);
			return store;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	@PutMapping("/update/store/{storeId}")
	@Operation(summary = "Update a Store", description = "Update an existing Store's information.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Store updated successfully"),
			@ApiResponse(responseCode = "404", description = "Store Id is not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public Store updateStore(
			@Parameter(description = "Store ID to be Updated an existing Store", allowEmptyValue = false) @PathVariable("storeId") Integer storeId,
			@RequestBody Store store) {
		try {
			var optStore = storeRepo.findById(storeId);
			if (!optStore.isPresent())
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Store Id is not found");
			var store1 = optStore.get();
			if (store.getLocation() != null)
				store1.setLocation(store.getLocation());
			if (store.getCity() != null)
				store1.setCity(store.getCity());
			if (store.getCountry() != null)
				store1.setCountry(store.getCountry());
			storeRepo.save(store1);
			return store1;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	@DeleteMapping("/delete/store/{storeId}")
	@Operation(summary = "Delete a Store", description = "Delete a Store from the Books Catalog by its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Store deleted successfully"),
			@ApiResponse(responseCode = "400", description = "Bad request, Store Id is not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public void deleteStore(
			@Parameter(description = "Store ID to be deleted", allowEmptyValue = false) @PathVariable("storeId") Integer storeId) {
		try {
			if (storeRepo.findById(storeId).isPresent())
				storeRepo.deleteById(storeId);
			else
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Store Id is not found");
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}
}
