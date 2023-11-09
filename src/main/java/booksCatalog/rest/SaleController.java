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

import booksCatalog.entities.Sale;
import booksCatalog.entities.SalePk;
import booksCatalog.repositories.SaleRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class SaleController {

	@Autowired
	SaleRepo saleRepo;

	//6
	@GetMapping("/sales")
	@Operation(summary = "List all Sales", description = "Retrieve a list of all Sales.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved sales"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Sale> getAllSales() {
		return saleRepo.findAll();
	}

	@PostMapping("/add/sale")
	@Operation(summary = "Add a Sale", description = "Add a new Sale to the Books Catalog.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Sale added successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request - Sale Id is already present"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public Sale addSale(@Valid @RequestBody Sale sale) {
		try {
			var optSale = saleRepo.findById(sale.getKey());
			if (optSale.isPresent())
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sale Id is already present!");
			saleRepo.save(sale);
			return sale;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	@PutMapping("/update/sale")
	@Operation(summary = "Update a Sale", description = "Update an existing Sale in the Books Catalog.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Sale updated successfully"),
			@ApiResponse(responseCode = "400", description = "Not Found - Sale Id is not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public Sale updateSale(@Valid @RequestBody Sale sale) {
		try {
			var optSale = saleRepo.findById(sale.getKey());
			if (!optSale.isPresent())
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale Id is not found!");
			var sale1 = optSale.get();
			sale1.setQtySold(sale.getQtySold());
			saleRepo.save(sale1);
			return sale1;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	@DeleteMapping("/delete/sale")
	@Operation(summary = "Delete a Sale", description = "Delete a Sale from the Books Catalog.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Sale deleted successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request - Sale Id is not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public void deleteSale(@Valid @RequestBody SalePk saleId) {
		try {
			if (saleRepo.findById(saleId).isPresent())
				saleRepo.deleteById(saleId);
			else
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sale Id is not found!");
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	//15
	@GetMapping("/salesByTitleName/{titleName}")
	@Operation(summary = "List Sales by the title name", description = "Retrieve Sales based on the title name.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved sales by title name"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Sale> listSalesByTitleName(
			@Parameter(description = "Sales will be retrived by the Title Name ", allowEmptyValue = false) @PathVariable("titleName") String name) {
		return saleRepo.querySaleByTitle(name);
	}

}
