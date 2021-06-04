package com.example.sarafan.controller;

import com.example.sarafan.domain.Message;
import com.example.sarafan.domain.Views;
import com.example.sarafan.dto.EventType;
import com.example.sarafan.dto.MetaDto;
import com.example.sarafan.dto.ObjectType;
import com.example.sarafan.repositories.MessageRepository;
import com.example.sarafan.util.WsSender;
import com.fasterxml.jackson.annotation.JsonView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ivan Kurilov on 14.05.2021
 */

@RestController
@RequestMapping("message")
public class MessageController {
    private static final String URL_PATTERN = "https?://?[\\w\\d._\\-%/?=&#]+";
    private static final String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";

    private static Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static Pattern IMAGE_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);

    private final MessageRepository messageRepository;
    private final BiConsumer<EventType, Message> wsSender;

    public MessageController(MessageRepository messageRepository, WsSender wsSender) {
        this.messageRepository = messageRepository;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.IdText.class);
    }

    @GetMapping
    @JsonView(Views.IdText.class)
    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getOne(@PathVariable("id") Message message) {
        return messageRepository.getOne(message.getId());
    }

    @PostMapping
    public Message create(@RequestBody Message message) throws IOException {

        message.setCreationDate(LocalDateTime.now());
        fillMeta(message);
        Message createdMessage = messageRepository.save(message);
        wsSender.accept(EventType.CREATE, createdMessage);

        return createdMessage;
    }

    @PutMapping("{id}")
    public Message update(
            @PathVariable("id") Message messageIdFromDb,
            @RequestBody Message message) throws IOException {

        BeanUtils.copyProperties(message, messageIdFromDb, "id");
        fillMeta(messageIdFromDb);
        Message updatedMessage = messageRepository.save(messageIdFromDb);
        wsSender.accept(EventType.UPDATE, messageIdFromDb);

        return updatedMessage;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {

        wsSender.accept(EventType.REMOVE, message);
        messageRepository.delete(message);
    }

    private void fillMeta(Message message) throws IOException {
        String text = message.getText();
        Matcher matcher = URL_REGEX.matcher(text);

        if (matcher.find()) {
            String url = text.substring(matcher.start(), matcher.end());

            matcher = IMAGE_REGEX.matcher(url);
            message.setLink(url);

            if (matcher.find()) {
                message.setLinkCover(url);
            } else if (!url.contains("youtu")) {
                MetaDto metaDto = getMeta(url);

                message.setLinkCover(metaDto.getCover());
                message.setLinkTitle(metaDto.getTitle());
                message.setLinkDescription(metaDto.getDescription());
            }
        }
    }

    private MetaDto getMeta(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements cover = doc.select("meta[name$=image], meta[property$=image]");
        Elements title = doc.select("meta[name$=title], meta[property$=title]");
        Elements description = doc.select("meta[name$=description], meta[property$=description]");

        return new MetaDto(
                getContent(title.first()),
                getContent(description.first()),
                getContent(cover.first())
        );
    }

    private String getContent(Element element) {
        return element == null ? "" : element.attr("content");
    }
}
