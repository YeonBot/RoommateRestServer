package com.yeonbot.roommate.roomPreView;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Iterator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomControllerTest {

    @Autowired
    RoomRepository roomRepository;

    @Test
    public void jsoup(){
        try {
            int count = 0;
            Document doc = Jsoup.connect("https://www.thecomenstay.com/brand/sharehouse-woozoo").get();

            Elements elements = doc.select("div.brand-house-info");

            for (Element element: elements) {
                Iterator<Element> iterElem =element.select("div div.cell3").iterator();

//                String[] items = new String[] { "위치", "교통", "월세", "보증금", "입주조건"};
//                StringBuilder builder = new StringBuilder();
//                for (String item : items) {
//                    builder.append(item + ": " + iterElem.next().text() + " \t");
//                }
//                System.out.println(builder.toString());
                Room room = Room.builder()
                        .roomNumber(""+count)
                        .location(iterElem.next().text())
                        .trafic(iterElem.next().text())
                        .montlyRent(iterElem.next().text())
                        .desposit(iterElem.next().text())
                        .residencyCondition(iterElem.next().text())
                        .build();

                roomRepository.save(room);
                count++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}