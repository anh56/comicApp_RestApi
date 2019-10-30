package com.example.comicApp.controller;

import com.example.comicApp.exception.ResourceNotFoundException;
import com.example.comicApp.model.Comic;
import com.example.comicApp.util.ResponseUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.example.comicApp.repository.ComicRepository;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ComicController {
    @Autowired
    private ComicRepository comicRepository;

    @GetMapping("/comics")
    public List<Comic> getAllNotes() {
        return comicRepository.findAll();
    }

    @PostMapping("/comics")
    public String createNote(@Valid @RequestBody Comic comic) {
        try {
            if (StringUtils.isEmpty(comic.getTitle())) {
                return ResponseUtil.invalid();
            }
            comicRepository.save(comic);
            return ResponseUtil.success(ResponseUtil.returnComic(comic));
        } catch (Exception e) {
            return ResponseUtil.serverError();
        }
    }

    @GetMapping("/comics/{id}")
    public String getNoteById(@PathVariable(value = "id") Long comicId) {
        try {
            Optional<Comic> comic = comicRepository.findById(comicId);
            if (comic.isPresent()) {
                return ResponseUtil.success(ResponseUtil.returnComic(comic.get()));
            }
            return ResponseUtil.notfound();
        } catch (Exception e) {
            return ResponseUtil.serverError();
        }
    }

    @PutMapping("/comics/{id}")
    public Comic updateNote(@PathVariable(value = "id") Long comicId,
                           @Valid @RequestBody Comic comicDetails) {

        Comic comic = comicRepository.findById(comicId)
                .orElseThrow(() -> new ResourceNotFoundException("Comic", "id", comicId));

        comic.setTitle(comicDetails.getTitle());
        comic.setContent(comicDetails.getContent());

        Comic updatedComic = comicRepository.save(comic);
        return updatedComic;
    }

    @DeleteMapping("/comics/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long comicId) {
        Comic note = comicRepository.findById(comicId)
                .orElseThrow(() -> new ResourceNotFoundException("Comic", "id", comicId));

        comicRepository.delete(note);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/comicCrawler")
    public boolean crawlerTest() throws IOException
    {

        //#list-page > div.col-xs-12.col-sm-12.col-md-9.col-truyen-main.no-hover > div.list.list-truyen.col-xs-12 > div:nth-child(28) > div.col-xs-2.text-info > div > a
        //#list-page > div.col-xs-12.col-sm-12.col-md-9.col-truyen-main.no-hover > div.list.list-truyen.col-xs-12 > div:nth-child(2) > div.col-xs-7 > div
        //#list-page > div.col-xs-12.col-sm-12.col-md-9.col-truyen-main.no-hover > div.list.list-truyen.col-xs-12 > div:nth-child(2) > div.col-xs-7 > div > h3 > a
        //#wrap > div.container.text-center.pagination-container > div > ul > li.active > span
        //#wrap > div.container.text-center.pagination-container > div > ul > li.active > span
        //#wrap > div.container.text-center.pagination-container > div > ul > li:nth-child(7) > a
        //#wrap > div.container.text-center.pagination-container > div > ul > li:nth-child(7) > a
        //#wrap > div.container.text-center.pagination-container > div > ul > li:nth-child(10) > a > span.glyphicon.glyphicon-menu-right
        //#wrap > div.container.text-center.pagination-container > div > ul > li:nth-child(10) > a
        int i = 1;
        Document doc;
        do {
            String url = "https://truyenfull.vn/danh-sach/truyen-hot/trang-" + i + "/";
            System.out.println(url);
            doc = Jsoup.connect(url).get();
            //System.out.println("Title: "+ doc.title());
            Elements comics = doc.select("#list-page > div > div > div > div> div > h3 > a");
            for (Element comic : comics) {
                System.out.println("Comic name: " + comic.attr("title"));
                System.out.println("Link: " + comic.attr("href"));
                i++;
            }
        } while //(Integer.parseInt(doc.select("#wrap > div.container.text-center.pagination-container > div > ul > li > span").attr("span")) != 0);
        (doc.select("#wrap > div.container.text-center.pagination-container > div > ul > li > a") != null);


//        for (int i = 1; i < 723; i++)
//        {
//        String url = "https://truyenfull.vn/danh-sach/truyen-hot/trang-" + i + "/";
//        System.out.println(url);
//        Document doc = Jsoup.connect(url).get();
//        //System.out.println("Title: "+ doc.title());
//        Elements comics = doc.select("#list-page > div > div > div > div> div > h3 > a");
//        //#list-page > div.col-xs-12.col-sm-12.col-md-9.col-truyen-main.no-hover > div.list.list-truyen.col-xs-12 > div:nth-child(28) > div.col-xs-2.text-info > div > a
//        //#list-page > div.col-xs-12.col-sm-12.col-md-9.col-truyen-main.no-hover > div.list.list-truyen.col-xs-12 > div:nth-child(2) > div.col-xs-7 > div
//        //#list-page > div.col-xs-12.col-sm-12.col-md-9.col-truyen-main.no-hover > div.list.list-truyen.col-xs-12 > div:nth-child(2) > div.col-xs-7 > div > h3 > a
//        //#wrap > div.container.text-center.pagination-container > div > ul > li.active > span
//        //#wrap > div.container.text-center.pagination-container > div > ul > li.active > span
//        //#wrap > div.container.text-center.pagination-container > div > ul > li:nth-child(7) > a
//        //#wrap > div.container.text-center.pagination-container > div > ul > li:nth-child(7) > a
//        for (Element comic : comics)
//                {
//                System.out.println("Comic name: " + comic.attr("title"));
//                System.out.println("Link: " + comic.attr("href"));
//                }
//        }
            return true;
    }
}


