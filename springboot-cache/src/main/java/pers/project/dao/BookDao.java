package pers.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pers.project.domain.BookDO;

@Repository
public interface BookDao extends JpaRepository<BookDO, String>, JpaSpecificationExecutor<BookDO> {
    BookDO findBookDOById(String id);

}
