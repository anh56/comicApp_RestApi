package com.example.comicApp.util;

import com.example.comicApp.constant.StatusCode;
import com.example.comicApp.model.Category;
import com.example.comicApp.model.Comic;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public class ResponseUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String success(JsonNode body) {
        ObjectNode node = mapper.createObjectNode();
        node.put("StatusCode", StatusCode.SUCCESS.getValue());
        node.put("Message", StatusCode.SUCCESS.getValue());
        node.set("Response", body);
        return node.toString();
    }

    public static String notfound() {
        ObjectNode node = mapper.createObjectNode();
        node.put("StatusCode", StatusCode.NOT_FOUND.getValue());
        node.put("Message", StatusCode.NOT_FOUND.getValue());
        node.put("Response", "");
        return node.toString();
    }

    public static String invalid() {
        ObjectNode node = mapper.createObjectNode();
        node.put("StatusCode", StatusCode.PARAMETER_INVALID.getValue());
        node.put("Message", StatusCode.PARAMETER_INVALID.getValue());
        node.put("Response", "");
        return node.toString();
    }

    public static String serverError() {
        ObjectNode node = mapper.createObjectNode();
        node.put("StatusCode", StatusCode.SERVER_ERROR.getValue());
        node.put("Message", StatusCode.SERVER_ERROR.getValue());
        node.put("Response", "");
        return node.toString();
    }

    public static ObjectNode returnComic(Comic comic) {
        ObjectNode node = mapper.createObjectNode();
        node.put("id", comic.getId());
        node.put("title", comic.getTitle());
        node.put("content", comic.getContent());
        return node;
    }

    public static ArrayNode returnListNode(List<Comic> comics) {
        ArrayNode node = mapper.createArrayNode();
        for (Comic comic : comics) {
            node.add((returnComic(comic)));
        }
        return node;
    }

    public static ObjectNode returnCategory(Category category) {
        ObjectNode node = mapper.createObjectNode();
        node.put("id", category.getId());
        node.put("title", category.getTitle());
        node.put("content", category.getContent());
        node.set("notes", returnListNode(category.getComics()));
        return node;
    }

    public static ArrayNode returnListCategory(List<Category> categorys) {
        ArrayNode node = mapper.createArrayNode();
        for (Category category : categorys) {
            node.add((returnCategory(category)));
        }
        return node;
    }
}
