package pers.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pers.project.dao.BookDao;
import pers.project.domain.BookDO;
import pers.project.service.BookService;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Override
    @CachePut(cacheNames = "book", key = "#book.id")
    public BookDO save(BookDO book) {
        System.out.println("写入缓存");
        return bookDao.save(book);
    }

    @Override
    @CacheEvict(cacheNames = "book", key = "#book.id")
    public BookDO update(BookDO book) {
        System.out.println("更新数据，删除缓存");
        return bookDao.save(book);
    }

    @Override
    @CacheEvict(cacheNames = "book", key = "#id")
    public void delete(String id) {
        System.out.println("删除数据，删除缓存");
        bookDao.deleteById(id); // 注意这个方法会先检查数据库中是否有这个id对应的数据，若有则删，若无则抛出EmptyResultDataAccessException异常
    }

    @Override
    @Cacheable(cacheNames = "book", key = "#id")
    public BookDO getById(String id) {
        System.out.println("缓存中没有，写入缓存");
        return bookDao.findBookDOById(id); // 不要用getOne这个方法！！！这个不是用来查数据的
    }
}
