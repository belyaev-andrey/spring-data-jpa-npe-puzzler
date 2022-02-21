package com.example.puzzler.npe;

import com.example.puzzler.npe.entities.Book;
import com.example.puzzler.npe.entities.BookId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, BookId> {

    Book findByBookId_Author_Id(Long id);

}