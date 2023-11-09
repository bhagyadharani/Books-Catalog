package booksCatalog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import booksCatalog.entities.Sale;
import booksCatalog.entities.SalePk;

public interface SaleRepo extends JpaRepository<Sale,SalePk>{

	//15
	@Query("select s from Sale s where s.title.title like concat('%',:name,'%')")
	List<Sale> querySaleByTitle(@Param("name") String name);
	
}
