package com.example.puzzler.npe;

import com.example.puzzler.npe.entities.Author;
import com.example.puzzler.npe.entities.Book;
import com.example.puzzler.npe.entities.BookId;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class SpringDataJpaNpePuzzlerApplicationTests {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private BookRepository bookRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    void contextLoads() {
    }


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "insert-products.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "delete-products.sql")
    void TestFetchByCompositeKeyHibernateTransaction(){
        Author author = new Author();
        author.setId(1L);

        BookId id = new BookId();
        id.setId(1L);
        id.setAuthor(author);

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Book book = session.find(Book.class, id);
        assertNotNull(book);
        assertEquals("From 800m to marathon", book.getTitle());
        assertEquals("Jack Daniels", book.getBookId().getAuthor().getName());
        transaction.commit();
    }


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "insert-products.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "delete-products.sql")
    void TestFetchByCompositeKeyEm(){
        Author author = new Author();
        author.setId(1L);

        BookId id = new BookId();
        id.setId(1L);
        id.setAuthor(author);

        Book book = em.find(Book.class, id);
        assertNotNull(book);
        assertEquals("From 800m to marathon", book.getTitle());
        assertEquals("Jack Daniels", book.getBookId().getAuthor().getName());
    }


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "insert-products.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "delete-products.sql")
    void TestFetchByCompositeKeyJpql(){
        Author author = new Author();
        author.setId(1L);

        BookId id = new BookId();
        id.setId(1L);
        id.setAuthor(author);

        Book book = em.createQuery("select b from Book b where b.bookId = :id", Book.class)
                .setParameter("id", id)
                .getSingleResult();
        assertNotNull(book);
        assertEquals("From 800m to marathon", book.getTitle());
        assertEquals("Jack Daniels", book.getBookId().getAuthor().getName());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "insert-products.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "delete-products.sql")
    @Transactional
    void TestFetchByFullCompositeKey(){
        Author author = new Author();
        author.setId(1L);

        BookId id = new BookId();
        id.setId(1L);
        id.setAuthor(author);

        Book book = bookRepository.findByBookId(id);
        assertNotNull(book);
        assertEquals("From 800m to marathon", book.getTitle());
        assertEquals("Jack Daniels", book.getBookId().getAuthor().getName());
    }


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "insert-products.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "delete-products.sql")
    void TestNpeOnCompositeKey(){
        Author author = new Author();
        author.setId(1L);

        BookId id = new BookId();
        id.setId(1L);
        id.setAuthor(author);

        Book book = bookRepository.findById(id).orElseThrow();
        assertNotNull(book);
        assertEquals("From 800m to marathon", book.getTitle());
        assertEquals("Jack Daniels", book.getBookId().getAuthor().getName());
    }

}