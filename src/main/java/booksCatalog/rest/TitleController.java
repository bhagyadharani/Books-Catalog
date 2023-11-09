package booksCatalog.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import booksCatalog.entities.Title;
import booksCatalog.repositories.StoreAndTitlesSoldDTO;
import booksCatalog.repositories.StoreRepo;
import booksCatalog.repositories.TitleDTO;
import booksCatalog.repositories.TitleRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class TitleController {

	@Autowired
	TitleRepo titleRepo;
	
	@Autowired
	StoreRepo storeRepo;
	
	@CrossOrigin
	@GetMapping("/titles")
	@Operation(summary = "List All Titles", description = "Retrieves all Titles in Books Catalog")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation"), })
	public List<Title> getAllTitles() {
		return titleRepo.findAll();
	}

	@PostMapping("/add/title")
	@Operation(summary = "Add Title", description = "Adds a new title to the Books Catalog")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "400", description = "Title Id is already exist") })
	public Title addTitle(@Valid @RequestBody Title title) {
		try {
			var optTitle = titleRepo.findById(title.getTitleId());
			if (optTitle.isPresent())
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title Id is already present!");
			titleRepo.save(title);
			return title;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	@PutMapping("/update/title/{titleId}")
	@Operation(summary = "Update Title", description = "Updates an existing title")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "404", description = "No Title Id found") })
	public Title updateTitle(
			@Parameter(description = "Title Id to be Updated existing title", allowEmptyValue = false) @PathVariable("titleId") String titleId,
			@RequestBody Title title) {

		try {
			var optTitle = titleRepo.findById(titleId);
			if (!optTitle.isPresent())
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Title Id is not found!");
			var title1 = optTitle.get();
			if (title.getTitle() != null)
				title1.setTitle(title.getTitle());
			if (title.getPrice() != null)
				title1.setPrice(title.getPrice());
			if (title.getPubId() != null)
				title1.setPubId(title.getPubId());
			if (title.getReleaseYear() != null)
				title1.setReleaseYear(title.getReleaseYear());
			titleRepo.save(title1);
			return title1;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	@DeleteMapping("/delete/title/{titleId}")
	@Operation(summary = "Delete Title", description = "Delete a Title by it's ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "400", description = "Invalid Title Id") })
	public void deleteTitle(
			@Parameter(description = "Id of Title that is to be deleted!", allowEmptyValue = false) @PathVariable("titleId") String titleId) {
		
		try {
			if (titleRepo.findById(titleId).isPresent())
				titleRepo.deleteById(titleId);
			else
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title Id is not Found!");
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	@GetMapping("/titlesByPublisherName/{name}")
	@Operation(summary = "List Titles by Publisher Name", description = "Retrieves Titles published by a specific Publisher")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "404", description = "No Publisher name found") })
	public List<Title> ListAllTitlesByPublisher(
			@Parameter(description = "Titles to be retrived by the publisher name", allowEmptyValue = false) @PathVariable("name") String name) {

		var titles = titleRepo.getAllTitlesByPublisher(name);

		if (titles.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no titles published by " + name);
		}
		return titles;
	}

	@GetMapping("/titleByAuthorName/{name}")
	@Operation(summary = "List Titles by Author Name", description = "Retrieves Titles authored by a specific Author")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "404", description = "No Author name found") })
	public List<Title> ListAllTitlesByAuthor(@PathVariable("name") String name) {

		var titles = titleRepo.getAllTitlesByAuthor(name);

		if (titles.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Titles written by" + name);
		}
		return titles;
	}

	@GetMapping("/titlesByRangeOfPrice")
	@Operation(summary = "List Titles by Price of specified range", description = "Retrieves Titles within a specified range of price")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	public List<Title> listTitlesByRangeOfPrice(
			@Parameter(description = "Titles to be retrived by the given minimum price", allowEmptyValue = false) @Param("min") Integer min,
			@Parameter(description = "Titles to be retrived by the given maximum price", allowEmptyValue = false) @Param("max") Integer max) {
		return titleRepo.findTitlesByRangeOfMinAndMAxOfPrice(min, max);
	}

	@GetMapping("/top5Titles")
	@Operation(summary = "List First 5 Titles", description = "Retrieves the first 5 titles ordered by YTD sales")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation"), })
	public List<Title> firstFiveTitles() {
		var values = titleRepo.findFirst5TitlesByOrderByYtdSalesDesc();
		if (values.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no titles found");
		}
		return values;
	}

	@GetMapping("/top5Stores")
	@Operation(summary = "List Top 5 Stores by Titles Sold", description = "Retrieves the top 5 stores based on titles sold")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	public List<StoreAndTitlesSoldDTO> listStoresByTitlesSold() {
		return storeRepo.queryTop5StoresByTitlesSold();
	}

	@GetMapping("/titleDetails/{id}")
	@Operation(summary = "List Title Details", description = "Retrieves details of title with the given title Id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	public List<TitleDTO> titleDetails(
			@Parameter(description = "Title to be retrived by the given title Id", allowEmptyValue = false) @PathVariable("id") String id) {
		
		return titleRepo.findTitleInfoById(id);
		
	}

	@GetMapping("titlesSoldInStore/{storeId}")
	@Operation(summary = "List Titles Sold in Store", description = "Retrieves titles sold in the selected store")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "404", description = "No Store Id found") })
	public List<Title> listTitlesBySoldInStore(
			@Parameter(description = "Titles to be retrived by the given Store Id", allowEmptyValue = false) @PathVariable("storeId") Integer id) {
		var check = storeRepo.findById(id);
		if (!check.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such Id found in Store");
		}
		var titles = titleRepo.findSoldTitle(id);
		return titles;
	}
	
}
