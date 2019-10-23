package com.example.comicApp.controller;

import com.example.comicApp.model.Category;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.comicApp.repository.CategoryRepository;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.comicApp.util.ResponseUtil.returnListCategory;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping(value = "/category", produces = "application/json")
    public String getAll() throws JsonProcessingException {
        return returnListCategory(categoryRepository.findAll()).toString();
    }


    @GetMapping(value = "/category_by_name", produces = "application/json")
    public String getByName(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String name = httpServletRequest.getParameter("name");
        List<Category> categorys = categoryRepository.findByTitleJPQLQuery(name);
        String response = returnListCategory(categorys).toString();
        return response;
    }

    @PostMapping("/category")
    public Category create(@Valid @RequestBody Category category) {
        return categoryRepository.save(category);
    }


    @GetMapping("/crawler")
    public boolean crawlerTest() throws IOException{
        Document doc = Jsoup.connect("https://truyenfull.vn/").get();
        System.out.println("Title: "+ doc.title());
        Elements categories = doc.select("div.row > div.col-md-4 > ul > li > a");//"#nav > div.container > div.navbar-collapse.collapse.in > ul > li.dropdown.open > div > div > div:nth-child(1) > ul");
        for (Element category : categories){
            System.out.println("Category name: "+ category.text());
            System.out.println("Link: "+ category.attr("href"));
        }
        return  true;
    }


}

