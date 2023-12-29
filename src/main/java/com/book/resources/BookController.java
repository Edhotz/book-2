package com.book.resources;

import com.book.dto.BookDTO;
import com.book.services.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.client.RestTemplate;
import java.net.URI;

@RestController
@RequestMapping(value = "/book")
public class BookController {
    @Autowired
    private BookServices bookServices;



    @GetMapping
    public ResponseEntity<Page<BookDTO>> findall(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value= "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy",  defaultValue = "name") String orderBy
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.ASC.valueOf(direction), orderBy);
        Page<BookDTO> list = bookServices.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable Long id) {
        BookDTO dto = bookServices.findById(id);
        return  ResponseEntity.ok().body(dto);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<BookDTO> insert(@RequestBody BookDTO dto, @RequestParam("file") MultipartFile file) {
        dto = bookServices.insert(dto);

        bookServices.uploadFile(dto.getId(), file);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BookDTO> update(@PathVariable Long id, @RequestBody BookDTO dto) {
        dto = bookServices.update(id , dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookServices.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/upload")
    public ResponseEntity<BookDTO> uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        BookDTO result = bookServices.uploadFile(id, file);

        return ResponseEntity.ok(result);
    }
}
