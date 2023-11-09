package booksCatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import booksCatalog.entities.Publisher;

public interface PublisherRepo extends JpaRepository<Publisher,String>{

}
