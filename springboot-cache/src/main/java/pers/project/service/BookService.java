package pers.project.service;

import pers.project.domain.BookDO;

public interface BookService {
    BookDO save(BookDO book);
    BookDO update(BookDO book);
    void delete(String id);
    BookDO getById(String id);
}
