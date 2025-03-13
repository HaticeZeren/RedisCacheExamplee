package com.example.RedisCacheExample;

import com.example.RedisCacheExample.dto.Book;
import com.example.RedisCacheExample.dto.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BookController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Cacheable(value = "books", key = "#bookId", unless = "#result.pages < 300")
    @RequestMapping(value = "/get/{bookId}", method = RequestMethod.GET)
    public Book getBook(@PathVariable String bookId) {
        LOG.info("ID değerine ait kayıtı getirir. {}.", bookId);
        long i = Long.valueOf(bookId);
        return bookRepository.findById(i).orElse(null);
    }

    @CachePut(value = "books", key = "#book.id")
    @PutMapping("/add")
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @CacheEvict(value = "books",  key = "#bookId")
    @DeleteMapping("/delete/{bookId}")
    public void deleteUserByID(@PathVariable Long bookId) {
        LOG.info("ID değerine ait kaydı siler{}", bookId);
        Optional<Book> bk = bookRepository.findById(bookId);
        if (!bk.isEmpty()) {
            Book book = bk.get();
            bookRepository.delete(book);
        }
    }
}