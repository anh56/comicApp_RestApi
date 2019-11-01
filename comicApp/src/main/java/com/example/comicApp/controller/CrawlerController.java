/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.comicApp.controller;


import com.example.comicApp.model.Category;
import com.example.comicApp.model.Comic;
import com.example.comicApp.repository.CategoryRepository;
import com.example.comicApp.repository.ChapterRepository;
import com.example.comicApp.repository.ComicRepository;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ANH
 */
@RestController
@RequestMapping("/api")
public class CrawlerController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ComicRepository comicRepsitory;
    @Autowired
    ChapterRepository chapterRepository;
    
        public Set<Category> getListCategory() throws IOException {
        Set<Category> categories = categoryRepository.findAll().stream().collect(Collectors.toSet());

        Document document = Jsoup.connect("https://truyenfull.vn/").get();
        Elements elements = document.select("div.row > div.col-md-4 > ul > li > a");
        for (Element element : elements) {
            Category newCategory = new Category();
            String[] temp = (element.attr("href").split("/"));
            String name = element.text();
            String urlName = temp[temp.length - 1];
            newCategory.setName(name);
            newCategory.setUrlName(urlName);
            categories.add(newCategory);
        }
        categoryRepository.saveAll(categories);
        return categories;
    }
        
//        public Set<Comic> getListComic() throws IOException {
//        Set<Comic> comics = comicRepsitory.findAll().stream().collect(Collectors.toSet());
//
//        Document document = Jsoup.connect("https://truyenfull.vn/danh-sach/truyen-hot/trang-" + i + "/").get();
//        Elements elements = document.select("#list-page > div.col-xs-12.col-sm-12.col-md-9.col-truyen-main > div.list.list-truyen.col-xs-12 > div.row");
//        for (Element element : elements) {
//            Comic newComic = new Comic();
//            String[] temp = (element.attr("href").split("/"));
//            String name = element.text();
//            
//            newComic.setName(name);
//            newComic.setCategory( ? );
//            comics.add(newComic);
//        }
//        comicRepository.saveAll(comics);
//        return comics;
//    }
        
        
    @GetMapping("/crawler")
    public boolean crawlComic() throws IOException {
        Set<Category> categories = getListCategory();
        for (int i = 1; i < 5; i++) {
            System.out.println("Page: " + i);
            Document document = Jsoup.connect("https://truyenfull.vn/danh-sach/truyen-hot/trang-" + i + "/").get();
            Elements elements = document.select("#list-page > div.col-xs-12.col-sm-12.col-md-9.col-truyen-main > div.list.list-truyen.col-xs-12 > div.row");
            for (Element element : elements) {
            
                System.out.println("Tên truyện: " + element.select("h3.truyen-title > a").text());
                
                crawlChapterOfComic(element.select("h3.truyen-title > a").attr("href"));
            }
        }

        return true;
    }

    public void crawlChapterOfComic(String comicUrl) throws IOException {
        boolean hasNext = false;
        do {
            Document document = Jsoup.connect(comicUrl).get();
            Elements elements = document.select("#list-chapter > div.row > div > ul > li > a");
            for (Element element : elements) {
                System.out.println(element.attr("href"));
            }
            Element nextPageButton = document.select("#list-chapter > ul > li.active + li > a").first();
            comicUrl = nextPageButton != null
                    ? nextPageButton.attr("href")
                    : "javascript:void(0)";
            if (!comicUrl.equals("javascript:void(0)")) {
                hasNext = true;
                comicUrl = nextPageButton.attr("href");
                System.out.println("Chapter link:" + comicUrl);
            } else {
                hasNext = false;
            }
        } while (hasNext);
    }


}
