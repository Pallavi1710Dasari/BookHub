package com.example.BookHub.Service;

import com.example.BookHub.model.Book;
import com.example.BookHub.Repository.BookJpaRepository;
import com.example.BookHub.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookJpaService implements BookRepository {

    @Autowired
    private BookJpaRepository bookJpaRepository;

    @Override
    public ArrayList<Book> getBooks() {
        List<Book> bookList = bookJpaRepository.findAll();
        ArrayList<Book> books = new ArrayList<>(bookList);
        return books;
    }

    @Override
    public Book getBookById(int bookId) {
        try {
            Book book = bookJpaRepository.findById(bookId).get();
            return book;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Book addBook(Book book) {
        bookJpaRepository.save(book);
        return book;
    }

    @Override
    public Book updateBook(int bookId, Book book) {
        try {
            Book newBook = bookJpaRepository.findById(bookId).get();
            if (book.getName() != null) {
                newBook.setName(book.getName());
            }
            if (book.getImageUrl() != null) {
                newBook.setImageUrl(book.getImageUrl());
            }
            bookJpaRepository.save(newBook);
            return newBook;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteBook(int bookId) {
        try {
            bookJpaRepository.deleteById(bookId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }
}