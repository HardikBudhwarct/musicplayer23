import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        MusicPlayer player = new MusicPlayer();

        try {
            player.addSong("Shape of You", "Ed Sheeran", "Pop");
            player.addSong("Perfect", "Ed Sheeran", "Pop");
            player.addSong("Bad Habits", "Ed Sheeran", "Pop");
            player.addSong("Blinding Lights", "The Weeknd", "R&B");
            player.addSong("Starboy", "The Weeknd", "R&B");
            player.addSong("Save Your Tears", "The Weeknd", "R&B");
            System.out.println("Songs added successfully!");
        } catch (IllegalArgumentException e) {
            System.err.println("Error adding songs: " + e.getMessage());
        }

        System.out.println("\nPlaying songs...");
        for (int i = 0; i < 5; i++) player.playSong("Shape of You", "Ed Sheeran");
        for (int i = 0; i < 3; i++) player.playSong("Perfect", "Ed Sheeran");
        for (int i = 0; i < 7; i++) player.playSong("Blinding Lights", "The Weeknd");
        for (int i = 0; i < 2; i++) player.playSong("Starboy", "The Weeknd");

        System.out.println("\nAll songs by Ed Sheeran:");
        System.out.println(player.getAllSongsByArtist("Ed Sheeran"));

        System.out.println("\nAll songs by The Weeknd:");
        System.out.println(player.getAllSongsByArtist("The Weeknd"));

        System.out.println("\nAll songs in Pop genre:");
        System.out.println(player.getAllSongsByGenre("Pop"));

        System.out.println("\nMarking 'Shape of You' as favorite...");
        player.markAsFavorite("Shape of You", "Ed Sheeran");

        System.out.println("\nTop 10 songs ever:");
        System.out.println(player.getTopTenSongsEver());

        System.out.println("\nTop 10 songs by Ed Sheeran:");
        System.out.println(player.getTopTenSongsByArtist("Ed Sheeran"));

        LocalDate today = LocalDate.now();
        System.out.println("\nTop 10 songs for today (" + today + "):");
        System.out.println(player.getTopTenSongsByDate(today));

        System.out.println("\nSongs played less than 3 times:");
        System.out.println(player.getSongsPlayedLessThan(3));

        System.out.println("\nTesting song removal:");
        player.removeSong("Starboy", "The Weeknd");
        System.out.println(player.getAllSongsByArtist("The Weeknd"));
    }
}
