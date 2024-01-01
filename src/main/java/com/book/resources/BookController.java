package com.book.resources;

import com.book.dto.BookDTO;
import com.book.services.BookServices;
import com.book.services.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;


@RestController
@RequestMapping(value = "/book")
public class BookController {
    @Autowired
    private BookServices bookServices;

    private final RestTemplate restTemplate;

    public BookController(BookServices bookServices, RestTemplate restTemplate) {
        this.bookServices = bookServices;
        this.restTemplate = restTemplate;
    }


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

        URI uri = UriComponentsBuilder.newInstance().path("/{id}").buildAndExpand(dto.getId()).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<BookDTO> requestEntity = new HttpEntity<>(dto, headers);
        System.out.println(requestEntity);


        String externalApiUrl = "https://admin.kioxke.ao/api/v0.1/book/setBookUpload";

        ResponseEntity<BookDTO> externalApiResponse = restTemplate.postForEntity(externalApiUrl, requestEntity, BookDTO.class);

        return ResponseEntity.created(uri).body(externalApiResponse.getBody());

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
        try {

            BookDTO result = bookServices.uploadFile(id, file);

            String apiUrl = "https://admin.kioxke.ao/api/v0.1/book/setBookUpload";

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();


            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");

            String boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW";
            String lineEnd = "\r\n";
            String twoHyphens = "--";


            StringBuilder postData = new StringBuilder();
            postData.append(twoHyphens).append(boundary).append(lineEnd);
            postData.append("Content-Disposition: form-data; name=\"id\"").append(lineEnd);
            postData.append(lineEnd);
            postData.append(id).append(lineEnd);

            postData.append(twoHyphens).append(boundary).append(lineEnd);
            postData.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(file.getOriginalFilename()).append("\"").append(lineEnd);
            postData.append("Content-Type: ").append(file.getContentType()).append(lineEnd);
            postData.append(lineEnd);


            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(postData.toString());


            try (InputStream fileInputStream = file.getInputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }


            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            outputStream.flush();
            outputStream.close();


            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                return ResponseEntity.ok(result);
            } else {

                return ResponseEntity.status(responseCode).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
