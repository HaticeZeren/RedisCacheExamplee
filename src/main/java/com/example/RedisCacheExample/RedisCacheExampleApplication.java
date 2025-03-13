package com.example.RedisCacheExample;

import com.example.RedisCacheExample.dto.Book;
import com.example.RedisCacheExample.dto.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisCacheExampleApplication implements CommandLineRunner {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private final BookRepository bookRepository;

	@Autowired
	public RedisCacheExampleApplication(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}


	public static void main(String[] args) {
		SpringApplication.run(RedisCacheExampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Uygulama ayağa kaldırılırken veritabanına örnek birkaç kayıt atılır.
		//Bu kayıtlar üzerinden @Cacheable, @CachePut ve @CacheEvict kontrol edeceğiz.
		LOG.info("Kayıt sayısı {}.", bookRepository.count());
		Book javaCoreBook = new Book("Java Core", 250);
		Book springBootBook = new Book("Spring Boot", 300);
		Book cacheBook = new Book("Caching", 550);

		bookRepository.save(javaCoreBook);
		bookRepository.save(springBootBook);
		bookRepository.save(cacheBook);
		LOG.info("Kayıt Sayısı {}.", bookRepository.findAll());
	}
}
