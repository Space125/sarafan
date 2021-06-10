package com.example.sarafan.service;

import com.example.sarafan.domain.Message;
import com.example.sarafan.domain.User;
import com.example.sarafan.domain.Views;
import com.example.sarafan.dto.EventType;
import com.example.sarafan.dto.MessagePageDto;
import com.example.sarafan.dto.MetaDto;
import com.example.sarafan.dto.ObjectType;
import com.example.sarafan.repositories.MessageRepository;
import com.example.sarafan.util.WsSender;
import org.codehaus.jackson.map.annotate.JsonView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ivan Kurilov on 09.06.2021
 */

@Service
public class MessageService {
    private static final String URL_PATTERN = "https?://?[\\w\\d._\\-%/?=&#]+";
    private static final String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";

    private static Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static Pattern IMAGE_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);

    private final MessageRepository messageRepository;
    private final BiConsumer<EventType, Message> wsSender;


    public MessageService(MessageRepository messageRepository, WsSender wsSender) {
        this.messageRepository = messageRepository;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.FullMessage.class);
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

    public void delete(Message message) {
        wsSender.accept(EventType.REMOVE, message);
        messageRepository.delete(message);
    }
    @JsonView(Views.FullMessage.class)
    public Message update(Message messageIdFromDb, Message message) throws IOException {
        BeanUtils.copyProperties(message, messageIdFromDb, "id");
        fillMeta(messageIdFromDb);
        Message updatedMessage = messageRepository.save(messageIdFromDb);
        wsSender.accept(EventType.UPDATE, messageIdFromDb);

        return updatedMessage;
    }

    public Message create(Message message, User user) throws IOException {
        message.setCreationDate(LocalDateTime.now());
        message.setAuthor(user);
        fillMeta(message);
        Message createdMessage = messageRepository.save(message);
        wsSender.accept(EventType.CREATE, createdMessage);

        return createdMessage;
    }

    public Message getMessageById(Message message) {
        return messageRepository.getOne(message.getId());
    }

    public MessagePageDto findAll(Pageable pageable) {
        Page<Message> page = messageRepository.findAll(pageable);
        return new MessagePageDto(
                page.getContent(),
                pageable.getPageNumber(),
                page.getTotalPages()
        );
    }
}
