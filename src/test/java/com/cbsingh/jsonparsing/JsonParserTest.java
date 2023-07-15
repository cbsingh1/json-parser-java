package com.cbsingh.jsonparsing;

import com.cbsingh.jsonparsing.pojo.AuthorPOJO;
import com.cbsingh.jsonparsing.pojo.DayPOJO;
import com.cbsingh.jsonparsing.pojo.JsonPOJO;
import com.cbsingh.jsonparsing.pojo.WelcomePOJO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {
    private final String simpleTestCaseJsonSource = "{ \"title\": \"Hello CBS\", \"author\": \"CBS\" }";

    @Test
    void parse() throws IOException {
        JsonNode node = JsonParser.parse(simpleTestCaseJsonSource);
        assertEquals(node.get("title").asText(), "Hello CBS");
    }

    @Test
    void fromJson() throws IOException {
        JsonNode node = JsonParser.parse(simpleTestCaseJsonSource);
        JsonPOJO pojo = JsonParser.fromJson(node, JsonPOJO.class);
        assertEquals(pojo.getTitle(), "Hello CBS");
    }

    @Test
    void toJson() {
        JsonPOJO pojo = new JsonPOJO();
        pojo.setTitle("New title from toJson");
        JsonNode node = JsonParser.toJson(pojo);
        assertEquals(node.get("title").asText(), "New title from toJson");
    }

    @Test
    void stringify() throws JsonProcessingException {
        JsonPOJO pojo = new JsonPOJO();
        pojo.setTitle("New title from toJson");
        JsonNode node = JsonParser.toJson(pojo);
        System.out.println(JsonParser.stringify(node));
        System.out.println(JsonParser.prettyPrint(node));
    }

    @Test
    void parseFromFile() throws URISyntaxException, IOException {
        File jsonFile = JsonParser.getFileFromResource("Welcome.json");
        JsonNode welcomePojoNode = JsonParser.parseFromFile(jsonFile);
        WelcomePOJO welcomePOJO = JsonParser.fromJson(welcomePojoNode, WelcomePOJO.class);

        assertEquals(welcomePOJO.getGreeting(), "Welcome to jackson");
    }

    @Test
    void dayTest() throws URISyntaxException, IOException {
        File jsonFile = JsonParser.getFileFromResource("day.json");
        JsonNode dayPojoNode = JsonParser.parseFromFile(jsonFile);
        DayPOJO dayPOJO = JsonParser.fromJson(dayPojoNode, DayPOJO.class);
        assertEquals("2023-12-25", dayPOJO.getDate().toString());
    }

    @Test
    void authorBookTest() throws URISyntaxException, IOException {
        File jsonFile = JsonParser.getFileFromResource("author.json");
        JsonNode authorJsonNode = JsonParser.parseFromFile(jsonFile);

        AuthorPOJO authorPOJO = JsonParser.fromJson(authorJsonNode, AuthorPOJO.class);
        assertEquals("Chandra", authorPOJO.getAuthorName());
    }
}