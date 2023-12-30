package com.book.dto;

import com.book.entities.Book;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

public class BookDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private Float preco;
    private String descricao;
    private String capa;
    private String autor;
    private String categoria;
    private String subCategoria;
    private String rate;
    private String size;
    private String extencao;
    private String urlPath;
    private String totalPage;
    private String cookie;
    private String desenhista;
    private String escritor;

    private String ISBN;
    private String numContrato;
    private String royalities;
    private String cuponCode;

    public BookDTO() {
    }

    public BookDTO(Long id, String titulo, Float preco, String descricao, String capa, String autor, String categoria, String subCategoria, String rate, String size, String extencao, String urlPath, String totalPage, String cookie, String desenhista, String escritor, String ISBN, String numContrato, String royalities, String cuponCode) {
        this.id = id;
        this.titulo = titulo;
        this.preco = preco;
        this.descricao = descricao;
        this.capa = capa;
        this.autor = autor;
        this.categoria = categoria;
        this.subCategoria = subCategoria;
        this.rate = rate;
        this.size = size;
        this.extencao = extencao;
        this.urlPath = urlPath;
        this.totalPage = totalPage;
        this.cookie = cookie;
        this.desenhista = desenhista;
        this.escritor = escritor;
        this.ISBN = ISBN;
        this.numContrato = numContrato;
        this.royalities = royalities;
        this.cuponCode = cuponCode;
    }


    public BookDTO(Book entity) {
        this.id = entity.getId();
        this.titulo = entity.getTitulo();
        this.preco = entity.getPreco();
        this.descricao = entity.getDescricao();
        this.capa = entity.getCapa();
        this.autor = entity.getAutor();
        this.categoria = entity.getCategoria();
        this.subCategoria = entity.getSubcategoria();
        this.rate = entity.getRate();
        this.size = entity.getSize();
        this.extencao = entity.getExtencao();
        this.urlPath = entity.getUrlpath();
        this.totalPage = entity.getTotalPage();
        this.cookie = entity.getCookie();
        this.desenhista = entity.getDesenhista();
        this.escritor = entity.getEscritor();
        this.ISBN = entity.getISBN();
        this.numContrato = entity.getNumContrato();
        this.royalities = entity.getRoyalities();
        this.cuponCode = entity.getCuponCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubcategoria() {
        return subCategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subCategoria = subcategoria;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getExtencao() {
        return extencao;
    }

    public void setExtencao(String extencao) {
        this.extencao = extencao;
    }

    public String getUrlpath() {
        return urlPath;
    }

    public void setUrlpath(String urlpath) {
        this.urlPath = urlpath;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getDesenhista() {
        return desenhista;
    }

    public void setDesenhista(String desenhista) {
        this.desenhista = desenhista;
    }

    public String getEscritor() {
        return escritor;
    }

    public void setEscritor(String escritor) {
        this.escritor = escritor;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getNumContrato() {
        return numContrato;
    }

    public void setNumContrato(String numContrato) {
        this.numContrato = numContrato;
    }

    public String getRoyalities() {
        return royalities;
    }

    public void setRoyalities(String royalities) {
        this.royalities = royalities;
    }

    public String getCuponCode() {
        return cuponCode;
    }

    public void setCuponCode(String cuponCode) {
        this.cuponCode = cuponCode;
    }
}
