import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

class MusicPlayer {
    private final Map<String, Song> songDatabase;
    private final Map<String, TreeSet<Song>> artistSongs;
    private final TreeSet<Song> globalTopSongs;
    private final Map<LocalDate, TreeSet<Song>> dateTopSongs;
    private final Map<String, TreeSet<Song>> genreSongs;  
    
    private static final Comparator<Song> PLAY_COUNT_COMPARATOR = 
        Comparator.comparingInt(Song::getTotalPlayCount)
                 .reversed()
                 .thenComparing(Song::getName)
                 .thenComparing(Song::getArtist);

    public MusicPlayer() {
        songDatabase = new HashMap<>();
        artistSongs = new HashMap<>();
        dateTopSongs = new HashMap<>();
        globalTopSongs = new TreeSet<>(PLAY_COUNT_COMPARATOR);
        genreSongs = new HashMap<>();  
    }

    public void addSong(String songName, String artist, String genre) {
        String key = generateKey(songName, artist);
        if (!songDatabase.containsKey(key)) {
            Song newSong = new Song(songName, artist, genre);
            songDatabase.put(key, newSong);
            
            artistSongs.computeIfAbsent(artist, k -> new TreeSet<>(PLAY_COUNT_COMPARATOR))
                      .add(newSong);
            globalTopSongs.add(newSong);
            
            genreSongs.computeIfAbsent(genre, k -> new TreeSet<>(PLAY_COUNT_COMPARATOR))
                      .add(newSong);
        }
    }

    public void playSong(String songName, String artist) {
        String key = generateKey(songName, artist);
        Song song = songDatabase.get(key);
        if (song != null) {
            LocalDate today = LocalDate.now();
            updateSongCollections(song, today);
        }
    }

    private void updateSongCollections(Song song, LocalDate date) {
        globalTopSongs.remove(song);
        artistSongs.get(song.getArtist()).remove(song);
        genreSongs.get(song.getGenre()).remove(song);  // Remove from genre as well
        if (dateTopSongs.containsKey(date)) {
            dateTopSongs.get(date).remove(song);
        }
        
        song.incrementPlayCount(date);
        
        globalTopSongs.add(song);
        artistSongs.get(song.getArtist()).add(song);
        genreSongs.get(song.getGenre()).add(song);
        
        dateTopSongs.computeIfAbsent(date, k -> new TreeSet<>((s1, s2) -> 
            Comparator.comparingInt((Song s) -> s.getPlayCountForDate(date))
                     .reversed()
                     .thenComparing(Song::getName)
                     .thenComparing(Song::getArtist)
                     .compare(s1, s2)
        )).add(song);
    }

    public void removeSong(String songName, String artist) {
        String key = generateKey(songName, artist);
        Song song = songDatabase.get(key);
        if (song != null) {
            songDatabase.remove(key);
            artistSongs.get(song.getArtist()).remove(song);
            genreSongs.get(song.getGenre()).remove(song);
            globalTopSongs.remove(song);
        }
    }

    public List<String> getAllSongsByArtist(String artist) {
        return artistSongs.getOrDefault(artist, new TreeSet<>(PLAY_COUNT_COMPARATOR))
                         .stream()
                         .map(Song::getName)
                         .collect(Collectors.toList());
    }

    public List<String> getAllSongsByGenre(String genre) {
        return genreSongs.getOrDefault(genre, new TreeSet<>(PLAY_COUNT_COMPARATOR))
                         .stream()
                         .map(Song::getName)
                         .collect(Collectors.toList());
    }

    public List<Map<String, String>> getTopTenSongsEver() {
        return getSongInfoList(globalTopSongs);
    }

    public List<String> getTopTenSongsByArtist(String artist) {
        return artistSongs.getOrDefault(artist, new TreeSet<>(PLAY_COUNT_COMPARATOR))
                         .stream()
                         .limit(10)
                         .map(Song::getName)
                         .collect(Collectors.toList());
    }

    public List<Map<String, String>> getTopTenSongsByDate(LocalDate date) {
        return getSongInfoList(dateTopSongs.getOrDefault(date, new TreeSet<>()));
    }

    public List<Song> getSongsPlayedLessThan(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative");
        }
        return songDatabase.values()
                          .stream()
                          .filter(song -> song.getTotalPlayCount() < count)
                          .collect(Collectors.toList());
    }

    public void markAsFavorite(String songName, String artist) {
        String key = generateKey(songName, artist);
        Song song = songDatabase.get(key);
        if (song != null) {
            song.toggleFavorite();
        }
    }

    private String generateKey(String songName, String artist) {
        return songName + "_" + artist;
    }

    private List<Map<String, String>> getSongInfoList(TreeSet<Song> songs) {
        return songs.stream()
                   .limit(10)
                   .map(song -> {
                       Map<String, String> info = new HashMap<>();
                       info.put("name", song.getName());
                       info.put("artist", song.getArtist());
                       return info;
                   })
                   .collect(Collectors.toList());
    }
}
