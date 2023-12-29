package com.book.services;

import com.book.dto.BookDTO;
import com.book.entities.Book;
import com.book.repository.BookRepository;
import com.book.services.Exceptions.DatabaseException;
import com.book.services.Exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BookServices {
    @Autowired
    private BookRepository bookRepository;

    @Transactional(readOnly = true)
    public Page<BookDTO> findAllPaged(PageRequest pageRequest) {
        Page<Book> list  = bookRepository.findAll(pageRequest);
        return list.map(x -> new BookDTO(x));
    }


    @Transactional(readOnly = true)
    public BookDTO findById(Long id) {
        Optional<Book> obj = bookRepository.findById(id);
        Book entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new BookDTO(entity);
    }

    @Transactional
    public BookDTO insert(BookDTO dto) {
        Book entity = new Book();
        entity.setTitulo(dto.getTitulo());
        entity.setPreco(dto.getPreco());
        entity.setDescricao(dto.getDescricao());
        entity.setCapa(dto.getCapa());
        entity.setAutor(dto.getAutor());
        entity.setCategoria(dto.getCategoria());
        entity.setUrlpath(dto.getUrlpath());
        entity.setSubcategoria(dto.getSubcategoria());
        entity.setCookie(dto.getCookie());
        entity.setEscritor(dto.getEscritor());
        entity.setDesenhista(dto.getDesenhista());
        entity.setExtencao(dto.getExtencao());
        entity.setISBN(dto.getISBN());
        entity.setRate(dto.getRate());
        entity.setSize(dto.getSize());
        entity.setNumContrato(dto.getNumContrato());
        entity.setRoyalities(dto.getRoyalities());
        entity.setTotalPage(dto.getTotalPage());
        entity.setCuponCode(dto.getCuponCode());
        entity = bookRepository.save(entity);
        return new BookDTO(entity);
    }

    @Transactional
    public BookDTO update(Long id, BookDTO dto) {

        try {
            Book entity = bookRepository.getOne(id);
            entity.setTitulo(dto.getTitulo());
            entity.setPreco(dto.getPreco());
            entity.setDescricao(dto.getDescricao());
            entity.setCapa(dto.getCapa());
            entity.setAutor(dto.getAutor());
            entity.setCategoria(dto.getCategoria());
            entity.setUrlpath(dto.getUrlpath());
            entity.setSubcategoria(dto.getSubcategoria());
            entity.setCookie(dto.getCookie());
            entity.setEscritor(dto.getEscritor());
            entity.setDesenhista(dto.getDesenhista());
            entity.setExtencao(dto.getExtencao());
            entity.setISBN(dto.getISBN());
            entity.setRate(dto.getRate());
            entity.setSize(dto.getSize());
            entity.setNumContrato(dto.getNumContrato());
            entity.setRoyalities(dto.getRoyalities());
            entity.setTotalPage(dto.getTotalPage());
            entity.setCuponCode(dto.getCuponCode());
            entity = bookRepository.save(entity);
            return new BookDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found");
        }
    }

    public void delete(Long id){
        try {
           bookRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
}
