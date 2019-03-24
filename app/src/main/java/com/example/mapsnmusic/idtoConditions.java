package com.example.mapsnmusic;
import java.util.HashMap;
import java.util.Map;

public class idtoConditions {

    public void loadDictionary(HashMap<Integer,String> dictionary){
        dictionary.put(200,"thunderstorm with light rain");
        dictionary.put(201,"thunderstorm with rain");
        dictionary.put(202,"thunderstorm with heavy rain");
        dictionary.put(210,"light thunderstorm");
        dictionary.put(211,"thunderstorm");
        dictionary.put(212,"heavy thunderstorm");
        dictionary.put(221,"ragged thunderstorm");
        dictionary.put(230,"thunderstorm with light drizzle");
        dictionary.put(231,"thunderstorm with drizzle");
        dictionary.put(232,"thunderstorm with heavy drizzle");

        dictionary.put(300,"drizzle");
        dictionary.put(301,"heavy intensity drizzle");
        dictionary.put(302,"light intensity drizzle rain");
        dictionary.put(311,"drizzle rain");
        dictionary.put(312,"heavy intensity drizzle rain");
        dictionary.put(313,"shower rain and drizzle");
        dictionary.put(314,"heavy shower rain and drizzle");
        dictionary.put(321,"shower drizzle");

        dictionary.put(500,"light rain");
        dictionary.put(501,"moderate rain");
        dictionary.put(502,"heavy intensity rain");
        dictionary.put(503,"very heavy rain");
        dictionary.put(504,"extreme rain");
        dictionary.put(511,"freezing rain");
        dictionary.put(520,"light intensity shower rain");
        dictionary.put(521,"shower rain");
        dictionary.put(522,"heavy intensity shower rain");
        dictionary.put(531,"ragged shower rain");

        dictionary.put(600,"light snow");
        dictionary.put(601,"snow");
        dictionary.put(602,"heavy snow");
        dictionary.put(611,"sleet");
        dictionary.put(612,"shower sleet");
        dictionary.put(615,"light rain and snow");
        dictionary.put(616,"rain and snow");
        dictionary.put(620,"light shower snow");
        dictionary.put(621,"shower snow");
        dictionary.put(622,"heavy shower snow");

        dictionary.put(701,"mist");
        dictionary.put(711,"smoke");
        dictionary.put(721,"haze");
        dictionary.put(731,"sand, dust whirls");
        dictionary.put(741,"fog");
        dictionary.put(751,"sand");
        dictionary.put(761,"dust");
        dictionary.put(762,"volcanic ash");
        dictionary.put(771,"squalls");
        dictionary.put(781,"tornado");

        dictionary.put(800,"clear sky");
        dictionary.put(801,"few clouds");
        dictionary.put(802,"scattered clouds");
        dictionary.put(803,"broken clouds");
        dictionary.put(804,"overcast clouds");
    }

    public String getWeather(int id,HashMap<Integer,String> dictionary){
        return dictionary.get(id);
    }
}