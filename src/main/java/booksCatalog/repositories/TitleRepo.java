package booksCatalog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import booksCatalog.entities.Title;

public interface TitleRepo extends JpaRepository<Title,String>{
	//8
	@Query(value="select * from titles where title_id in(select title_id from titleauthors where au_id in(select au_id from authors where au_name like '%'+?+'%'))",nativeQuery=true)
	List<Title> getAllTitlesByAuthor(String author);
	
	//7
	@Query("select t from Title t where publisher.pubName like concat('%'+:name+'%')")
	List<Title> getAllTitlesByPublisher(@Param("name") String publisher);
	
	//9
	List<Title> findTitlesByTitleContaining(String str);
	
	//10
	@Query("select t from Title t where t.price < :max and t.price > :min ")
	List<Title> findTitlesByRangeOfMinAndMAxOfPrice(@Param("min") Integer min,@Param("max") Integer max);
	
	//11
	List<Title> findFirst5TitlesByOrderByYtdSalesDesc();
	
	//14
	@Query("select t from Title t where t.titleId in (select s.key.titleId from Sale s where s.key.storeId = :id)")
	List<Title> findSoldTitle(@Param("id") Integer id);
	
	//13
	@Query("select t.title as title ,t.price as price ,t.ytdSales as ytdSales,t.publisher.pubName as pubName ,a.auName as auName from Title t join t.authors a where titleId like :titleId ")
	List<TitleDTO> findTitleInfoById(@Param("titleId") String titleId);
	
}
