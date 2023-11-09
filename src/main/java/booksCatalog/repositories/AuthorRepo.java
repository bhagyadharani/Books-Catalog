package booksCatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import booksCatalog.entities.Author;

public interface AuthorRepo extends JpaRepository<Author,String>{
	

}
