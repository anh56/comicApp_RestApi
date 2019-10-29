package com.example.comicApp.controller;
import com.example.comicApp.model.Chapter;
import com.example.comicApp.repository.ChapterRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
import static com.example.comicApp.util.ResponseUtil.returnListChapter;

@RestController
    @RequestMapping("/api")
    public class ChapterController {
    @Autowired
    ChapterRepository chapterRepository;

    @GetMapping(value = "/chapter", produces = "application/json")
    public String getAll() throws JsonProcessingException {
        return returnListChapter(chapterRepository.findAll()).toString();
    }


    @GetMapping(value = "/chapter_by_name", produces = "application/json")
    public String getByName(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String name = httpServletRequest.getParameter("name");
        List<Chapter> chapters = chapterRepository.findByTitleJPQLQuery(name);
        String response = returnListChapter(chapters).toString();
        return response;
    }

    @PostMapping("/chapters")
    public Chapter create(@Valid @RequestBody Chapter chapter) {
        return chapterRepository.save(chapter);
    }


    @GetMapping("/chapterCrawler")
    public boolean crawlerTest() throws IOException
    {
        int i = 1;
        int j =1;

             while (i != 0)
             {
                 String url = "https://truyenfull.vn/choc-tuc-vo-yeu-mua-mot-tang-mot-241019//trang-"+i+"/#list-chapter";
                 Document doc = Jsoup.connect(url).get();
                 System.out.println("Title: " + doc.title());
                 while (j != 0)
                 {

                     Elements chapters = doc.select("#list-chapter > div.row > div:nth-child(1) > ul > li> a");
                     ////#list-chapter > div.row > div:nth-child(1) > ul
                     //#list-chapter > div.row > div:nth-child(1) > ul > li:nth-child(" + i + ") > a
                     for (Element chapter : chapters)
                     {
                         System.out.println("Chapter name: " + chapter.text());
                         System.out.println("Link: " + chapter.attr("href"));
                     }
                     j++;
                 }
                 i++;
             }
        return true;
    }

}

//
//    Document doc = Jsoup.connect("https://truyenfull.vn/").get();
//        System.out.println("Title: "+ doc.title());
//    Elements categories = doc.select("div.row > div.col-md-4 > ul > li > a");//"#nav > div.container > div.navbar-collapse.collapse.in > ul > li.dropdown.open > div > div > div:nth-child(1) > ul");
//        for (Element category : categories){
//        System.out.println("Category name: "+ category.text());
//        System.out.println("Link: "+ category.attr("href"));
//    }
//        return  true;



