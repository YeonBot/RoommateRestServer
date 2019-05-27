package com.yeonbot.roommate.roomPreView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/room")
public class RoomController {

    @Autowired
    RoomRepository roomRepository;

    @RequestMapping("/test")
    public String hello(){
        return "Hello Wrold !";
    }

    @GetMapping("/Rooms")
    public String getRoomsInfo(){
        Gson gson = new Gson();
        List<Room> rooms = roomRepository.findAll();
        JsonArray jsonArray = new JsonArray();

        rooms.forEach((item)->{
            String str = gson.toJson(item);
            jsonArray.add(str);
        });
        return jsonArray.toString();
    }

    @RequestMapping("/crawling")
    public void crawlingTest(){
        int count = 1;
        try {
            Document doc = Jsoup.connect("https://www.thecomenstay.com/brand/sharehouse-woozoo").get();

            Elements elements = doc.select("div.brand-house-info");

            for (Element element: elements) {
                Iterator<Element> iterElem =element.select("div div.cell3").iterator();

                Room room = Room.builder()
                        .roomNumber(""+count)
                        .location(iterElem.next().text())
                        .trafic(iterElem.next().text())
                        .montlyRent(iterElem.next().text())
                        .desposit(iterElem.next().text())
                        .residencyCondition(iterElem.next().text())
                        .build();

                roomRepository.save(room);

//                String[] items = new String[] { "위치", "교통", "월세", "보증금", "입주조건"};
//                StringBuilder builder = new StringBuilder();
//                for (String item : items) {
//                    builder.append(item + ": " + iterElem.next().text() + " \t");
//                }
//                System.out.println(builder.toString());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
