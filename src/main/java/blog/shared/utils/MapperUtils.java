package blog.shared.utils;

import com.google.gson.Gson;
public class MapperUtils {
    private static final Gson gson = new Gson();
    public static Gson getGson(){
        return gson;
    }
}
