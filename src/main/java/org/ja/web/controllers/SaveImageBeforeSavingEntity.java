package org.ja.web.controllers;

import org.ja.web.models.WebSiteBlog;
import org.ja.web.repositories.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Base64;
import java.util.Hashtable;

@Component
public class SaveImageBeforeSavingEntity extends AbstractRepositoryEventListener<WebSiteBlog> {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public void onBeforeCreate(WebSiteBlog entity) {

        int width = 270, height = 200;
        BufferedImage bi = getBufferedImageWithWrappedTextAndBorder(entity, width, height);

        ByteArrayOutputStream byteArrayOutputStream = getByteArrayOutputStreamFromBufferedImage(bi);

        entity.setImage(Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray()));
        blogRepository.save(entity);
    }

    @Override
    public void onBeforeSave(WebSiteBlog entity) {

        int width = 270, height = 200;
        BufferedImage bi = getBufferedImageWithWrappedTextAndBorder(entity, width, height);

        ByteArrayOutputStream byteArrayOutputStream = getByteArrayOutputStreamFromBufferedImage(bi);

        entity.setImage(Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray()));
        blogRepository.save(entity);
    }

    private ByteArrayOutputStream getByteArrayOutputStreamFromBufferedImage(BufferedImage bi) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, "PNG", byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream;
    }

    private BufferedImage getBufferedImageWithWrappedTextAndBorder(WebSiteBlog entity, int width, int height) {
        // TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
        // into integer pixels
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.setColor(Color.RED);
        g2d.setBackground(Color.WHITE);

        Hashtable<TextAttribute, Object> map = new Hashtable<TextAttribute, Object>();
        map.put(TextAttribute.FAMILY, "微软雅黑");
        map.put(TextAttribute.SIZE, 18.0f);
        AttributedString contents = new AttributedString(entity.getContents(), map);

        AttributedCharacterIterator paragraph = contents.getIterator();
        int paragraphStart = paragraph.getBeginIndex();
        int paragraphEnd = paragraph.getEndIndex();
        FontRenderContext frc = g2d.getFontRenderContext();
        LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(paragraph, frc);
        float breakWidth = width - 20;
        float drawPosY = 20;
        float drawPosX = 20;
        lineMeasurer.setPosition(paragraphStart);
        while (lineMeasurer.getPosition() < paragraphEnd) {
            TextLayout layout = lineMeasurer.nextLayout(breakWidth);
            drawPosX = layout.isLeftToRight() ? 20 : breakWidth - layout.getAdvance();
            drawPosY += layout.getAscent();
            layout.draw(g2d, drawPosX, drawPosY);
            drawPosY += layout.getDescent() + layout.getLeading();
        }

        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.BLUE);
        g2d.drawRect(10, 10, bi.getWidth() - 10, bi.getHeight() - 10);
        return bi;
    }

}