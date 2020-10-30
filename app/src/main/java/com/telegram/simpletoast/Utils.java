package com.telegram.simpletoast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Utils {

    private static List<User> users = new ArrayList<>();

    public static List<User> getAccounts() {
        users.add(new User("https://uafootball.us/wp-content/uploads/2017/02/AMONRA-STBROWN.jpg", "Amon Brown", "Hello", timeStamped()));
        users.add(new User("https://upload.wikimedia.org/wikipedia/commons/1/11/Steve_Angello_2015.jpg", "Steve Angello", "Hi ,bro", timeStamped()));
        users.add(new User("https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Laidback_Luke_2012.jpg/274px-Laidback_Luke_2012.jpg", "Laidback Luke", "Whats up", timeStamped()));
        users.add(new User("https://djmagasia.com/wp-content/uploads/2019/07/dndkdkdk-636x400.jpg", "Pryda", "No", timeStamped()));
        users.add(new User("https://pbs.twimg.com/profile_images/600600398383157248/MaYY_AOC_400x400.jpg", "AN21", "Listen my new track", timeStamped()));
        users.add(new User("https://edmhousenetwork.com/wp-content/uploads/2019/06/zkaaze-1200x675.png", "Kaaze", "thanks bro)", timeStamped()));
        users.add(new User("https://uafootball.us/wp-content/uploads/2017/02/AMONRA-STBROWN.jpg", "Amon Brown", "Hello", timeStamped()));
        users.add(new User("https://upload.wikimedia.org/wikipedia/commons/1/11/Steve_Angello_2015.jpg", "Steve Angello", "Hi ,bro", timeStamped()));
        users.add(new User("https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Laidback_Luke_2012.jpg/274px-Laidback_Luke_2012.jpg", "Laidback Luke", "Whats up", timeStamped()));
        users.add(new User("https://djmagasia.com/wp-content/uploads/2019/07/dndkdkdk-636x400.jpg", "Pryda", "No", timeStamped()));
        users.add(new User("https://pbs.twimg.com/profile_images/600600398383157248/MaYY_AOC_400x400.jpg", "AN21", "Listen my new track", timeStamped()));
        users.add(new User("https://edmhousenetwork.com/wp-content/uploads/2019/06/zkaaze-1200x675.png", "Kaaze", "thanks bro)", timeStamped()));
        return users;
    }

    private static String timeStamped() {
        Random rnd = new Random();
        Date date = new Date(Math.abs(System.currentTimeMillis() - rnd.nextLong()));
        return new SimpleDateFormat("HH:mm", Locale.getDefault()).format(date);
    }
}
