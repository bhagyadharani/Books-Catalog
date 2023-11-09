package booksCatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import booksCatalog.entities.TitleAuthor;
import booksCatalog.entities.TitleAuthorPk;

public interface TitleAuthorRepo extends JpaRepository<TitleAuthor,TitleAuthorPk>{

}
