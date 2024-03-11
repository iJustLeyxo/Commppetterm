package commppetterm.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import commppetterm.entity.Entry;

public class Event{
    private Boolean fullDay;
    private long start;
    private long duration;
    private String title;
    private String description;

    public Event(Boolean fullDay, long start, long duration, String title, String description){
        this.fullDay = fullDay;
        this.start = start;
        this.duration = duration;
        this.title = title;
        this.description = description;
    }
    public Event(){}
    public Boolean save(){
        File f = new File("save/events.json");
        System.out.println("tetst");
        System.out.println(f.getAbsolutePath());
        try {
            f.getParentFile().mkdir();
            if (f.createNewFile()){
                FileWriter writer = new FileWriter(f);
                writer.write("{\n\t\"events\" : []\n}");
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Entry entry = new Entry("test", "info", LocalDateTime.now(), null, null, null);
        return true;
    }
}
