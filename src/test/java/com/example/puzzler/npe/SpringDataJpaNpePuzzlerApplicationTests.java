package com.example.puzzler.npe;

import com.example.puzzler.npe.entities.Author;
import com.example.puzzler.npe.entities.Book;
import com.example.puzzler.npe.entities.BookId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class SpringDataJpaNpePuzzlerApplicationTests {


    @Autowired
    private BookRepository bookRepository;

    @Test
    void contextLoads() {
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "insert-products.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "delete-products.sql")
    void TestNpeOnCompositeKey(){
        BookId id = new BookId();

        id.setId(1L);
        id.setAuthorId(1L);

        Book book = bookRepository.findById(id).orElseThrow();
        assertNotNull(book);
        assertEquals("From 800m to marathon", book.getTitle());
        assertEquals("Jack Daniels", book.getAuthor().getName());
    }

}