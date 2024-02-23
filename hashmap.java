import java.util.HashMap;
import java.util.Map;

public class hashmap
{

    private Map<String, Integer> playCountMap;

    public hashmap() {
        playCountMap = new HashMap<>();
    }

    public void playSong2(String songName) {
        playCountMap.put(songName, playCountMap.getOrDefault(songName, 0) + 1);

    }

    public void listMostPlayedSongs(int n) {
        System.out.println("Most Played Songs:");

        playCountMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(n)
                .forEach(entry -> System.out.println(entry.getKey() + " - Plays: " + entry.getValue()));
    }

}
