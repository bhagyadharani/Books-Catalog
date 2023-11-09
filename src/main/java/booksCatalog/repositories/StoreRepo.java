package booksCatalog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import booksCatalog.entities.Store;

public interface StoreRepo extends JpaRepository<Store,Integer>{

	//12
	@Query("select s.key.storeId as storeId, sum(s.qtySold) as titlesSold from Sale s group by s.key.storeId order by titlesSold desc limit 5")
	List<StoreAndTitlesSoldDTO> queryTop5StoresByTitlesSold();
}
