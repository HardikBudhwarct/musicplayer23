import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        MusicPlayer player = new MusicPlayer();

        try {
            player.addSong("Lose Yourself", "Eminem", "Hip-Hop");
            player.addSong("The Real Slim Shady", "Eminem", "Hip-Hop");
            player.addSong("God's Plan", "Drake", "Hip-Hop");
            player.addSong("Rolling in the Deep", "Adele", "Pop");
            player.addSong("Someone Like You", "Adele", "Pop");
            player.addSong("Set Fire to the Rain", "Adele", "Pop");
            System.out.println("Songs added successfully!");
        } catch (IllegalArgumentException e) {
            System.err.println("Error adding songs: " + e.getMessage());
        }

        System.out.println("\nPlaying songs...");
        for (int i = 0; i < 5; i++) player.playSong("Lose Yourself", "Eminem");
        for (int i = 0; i < 3; i++) player.playSong("The Real Slim Shady", "Eminem");
        for (int i = 0; i < 7; i++) player.playSong("Rolling in the Deep", "Adele");
        for (int i = 0; i < 2; i++) player.playSong("Set Fire to the Rain", "Adele");

        System.out.println("\nAll songs by Eminem:");
        System.out.println(player.getAllSongsByArtist("Eminem"));

        System.out.println("\nAll songs by Adele:");
        System.out.println(player.getAllSongsByArtist("Adele"));

        System.out.println("\nAll songs in Hip-Hop genre:");
        System.out.println(player.getAllSongsByGenre("Hip-Hop"));

        System.out.println("\nMarking 'Lose Yourself' as favorite...");
        player.markAsFavorite("Lose Yourself", "Eminem");

        System.out.println("\nTop 10 songs ever:");
        System.out.println(player.getTopTenSongsEver());

        System.out.println("\nTop 10 songs by Eminem:");
        System.out.println(player.getTopTenSongsByArtist("Eminem"));

        LocalDate today = LocalDate.now();
        System.out.println("\nTop 10 songs for today (" + today + "):");
        System.out.println(player.getTopTenSongsByDate(today));

        System.out.println("\nSongs played less than 3 times:");
        System.out.println(player.getSongsPlayedLessThan(3));

        System.out.println("\nTesting song removal:");
        player.removeSong("Set Fire to the Rain", "Adele");
        System.out.println(player.getAllSongsByArtist("Adele"));
    }
}
