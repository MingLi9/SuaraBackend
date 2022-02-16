package Suara.Suara.Logic;

import Suara.Suara.Model.Album;
import Suara.Suara.Model.Playlist;
import Suara.Suara.Model.Song;
import Suara.Suara.Model.SuaraUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
    //Source code: https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation/3802238
    private static Pattern regexPattern;
    private static Matcher regMatcher;

    public static boolean Song(Song result) {
        return fileMP3(result.getFile()) && title(result.getTitle());
    }

    private static boolean title(String title) {
        regexPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{1,}$");
        regMatcher = regexPattern.matcher(title);
        return regMatcher.matches();
    }

    private static boolean fileMP3(String file) {
        return true;
    }

    private static boolean password(String password) {
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!&*_=+?]).{8,}$";
        regexPattern = Pattern.compile(regex);
        regMatcher = regexPattern.matcher(password);
        return regMatcher.matches();
    }

    private static boolean username(String username) {
        regexPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?!.*[@#$%!^&*()_=+{}';:/?.>,<])(?=\\S+$).{1,30}$");
        regMatcher = regexPattern.matcher(username);
        return regMatcher.matches();
    }

    public static boolean Playlist(Playlist playlist) {
        return title(playlist.getName());
    }

    public static boolean Album(Album album) {
        return title(album.getName());
    }

    public static boolean User(SuaraUser suaraUser) {
        if (!username(suaraUser.getUsername()))
            return false;
        return password(suaraUser.getPassword());
    }
}
