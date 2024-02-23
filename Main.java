import java.util.Scanner;

class Music_Player_02{

    static class SongNode {
        String name;
        SongNode next;
        SongNode prev;

        SongNode(String songName) {
            this.name = songName;
            this.next = null;
            this.prev = null;
        }
    }

    static class Playlist {
        private SongNode head;
        private SongNode currentSong; // Track the current song

        Playlist() {
            this.head = null;
            this.currentSong = null;
        }

        void insertLast(String songName) {
            SongNode newNode = new SongNode(songName);

            if (head == null) {
                head = newNode;
                currentSong = head; // Set the current song if the playlist was empty
            } else {
                SongNode current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
                newNode.prev = current;
            }
        }

        void insertFirst(String songName) {
            SongNode newNode = new SongNode(songName);

            if (head != null) {
                newNode.next = head;
                head.prev = newNode;
            }
            head = newNode;
            currentSong = head; // Set the current song after inserting at the beginning
        }

        boolean containsSong(String songName) {
            SongNode current = head;
            while (current != null) {
                if (current.name.equals(songName)) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }

        void displaySongs() {
            SongNode current = head;
            while (current != null) {
                System.out.println(current.name);
                current = current.next;
            }
        }

        void playNextSong() {
            if (currentSong != null && currentSong.next != null) {
                currentSong = currentSong.next;
                System.out.println("Now playing next song: " + currentSong.name);
            } else {
                System.out.println("No next song available.");
            }
        }

        void playPreviousSong() {
            if (currentSong != null && currentSong.prev != null) {
                currentSong = currentSong.prev;
                System.out.println("Now playing previous song: " + currentSong.name);
            } else {
                System.out.println("No previous song available.");
            }
        }

        SongNode getCurrentSong() {
            return currentSong;
        }

        void addToFavorites(Playlist favorites) {
            if (currentSong != null) {
                favorites.insertFirst(currentSong.name);
                System.out.println("Current song added to Favorites playlist.");
            } else {
                System.out.println("No song is currently playing.");
            }
        }

        void clear() {
            head = null;
            currentSong = null;
        }

    }

    static class MusicPlayer {
        private Playlist home;
        private Playlist favorites;
        private Playlist[] playlists;
        private int playlistCount;
        private Scanner scanner;

        MusicPlayer() {
            home = new Playlist();
            favorites = new Playlist();
            playlists = new Playlist[3]; // Assuming there are 3 playlists
            for (int i = 0; i < 3; i++) {
                playlists[i] = new Playlist();
            }
            playlistCount = 0;
            scanner = new Scanner(System.in); // Initialize the scanner

            // Initialize initial songs
            initializeSongs();
        }

        // Method to initialize initial songs
        private void initializeSongs() {
            home.insertLast("Song 1");
            home.insertLast("Song 2");
            home.insertLast("Song 3");
            home.insertLast("Song 4");
        }

        // Method to display all songs
        private void displayAllSongs() {
            System.out.println("All Songs:");
            System.out.println("Home Playlist:");
            home.displaySongs();
            System.out.println("Favorites Playlist:");
            favorites.displaySongs();
            for (int i = 0; i < playlistCount; i++) {
                System.out.println("Playlist " + (i + 1) + ":");
                playlists[i].displaySongs();
            }
        }

        void displayMenu() {
            System.out.println("Navigation Pages:");
            System.out.println("1. HOME");
            System.out.println("2. Favorites");
            System.out.println("3. Playlists");
            System.out.println("4. All Songs");
            System.out.print("Enter a number to navigate: ");
        }

        void homePage() {
            int choice;
            do {
                System.out.println("Home Page:");
                System.out.println("1. Add a new song");
                System.out.println("2. Listen to songs");
                System.out.println("3. Go to the previous menu");
                System.out.print("Enter a number to proceed: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addNewSong();
                        break;
                    case 2:
                        listenToSongs();
                        break;
                    case 3:
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid number.");
                }
            } while (choice != 3);
        }

        void addNewSong() {
            scanner.nextLine(); // Consume newline
            System.out.print("Enter the name of the new song: ");
            String songName = scanner.nextLine();

            home.insertLast(songName);
            System.out.println("Song added to HOME playlist.");
        }

        void listenToSongs() {
            System.out.println("Listen to Songs:");
            home.displaySongs();

            int choice;
            do {
                System.out.println("Now Playing: " + (home.getCurrentSong() != null ? home.getCurrentSong().name : "No song playing"));
                System.out.println("1. Play a song");
                System.out.println("2. Play next song");
                System.out.println("3. Play previous song");
                System.out.println("4. Add to Favorites");
                System.out.println("5. Add to Playlist");
                System.out.println("6. Go to the previous menu");
                System.out.print("Enter a number to proceed: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        playSong();
                        break;
                    case 2:
                        home.playNextSong();
                        break;
                    case 3:
                        home.playPreviousSong();
                        break;
                    case 4:

                        break;
                    case 5:
                        addSongToPlaylist();
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid number.");
                }
            } while (choice != 6);
        }

        void addSongToFavorites() {
            if (home.getCurrentSong() != null) {
                String currentSongName = home.getCurrentSong().name;
                if (!favorites.containsSong(currentSongName)) {
                    favorites.insertFirst(currentSongName);
                    System.out.println("Currently playing song added to Favorites playlist.");
                } else {
                    System.out.println("Song is already favorite.");
                }
            } else {
                System.out.println("No song is currently playing.");
            }
        }

        void displayPlaylists() {
            System.out.println("Available Playlists:");
            for (int i = 0; i < playlistCount; ++i) {
                System.out.println((i + 1) + ". Playlist " + (i + 1));
            }
        }

        void playSong() {
            scanner.nextLine(); // Consume newline
            System.out.print("Enter the name of the song to play: ");
            String songName = scanner.nextLine();

            if (home.containsSong(songName)) {
                System.out.println("Now playing: " + songName);
            } else {
                System.out.println("Song is not in the HOME playlist.");
            }
        }

        void displayFavorites() {
            int choice;
            do {
                System.out.println("Favorites Page:");
                System.out.println("1. Display all favorited songs and additional options:");
                System.out.println("2. Go to the previous menu");
                System.out.print("Enter a number to proceed: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        do {
                            System.out.println("Now Playing: " + (favorites.getCurrentSong() != null ? favorites.getCurrentSong().name : "No song playing"));
                            System.out.println("All favorited songs:");
                            favorites.displaySongs();
                            System.out.println("Additional options:");
                            System.out.println("1. Play a song from Favorites");
                            System.out.println("2. Play next song from Favorites");
                            System.out.println("3. Play previous song from Favorites");
                            System.out.println("4. Remove a song from Favorites");
                            System.out.println("5. Go back to the main options");
                            System.out.print("Enter a number to proceed: ");
                            int subChoice = scanner.nextInt();
                            switch (subChoice) {
                                case 1:
                                    playSongFromFavorites();
                                    break;
                                case 2:
                                    favorites.playNextSong();
                                    break;
                                case 3:
                                    favorites.playPreviousSong();
                                    break;
                                case 4:
                                    removeSongFromFavorites();
                                    break;
                                case 5:
                                    break; // Go back to the main options
                                default:
                                    System.out.println("Invalid choice. Please enter a valid number.");
                                    break;
                            }
                        } while (choice != 5); // Stay in this section until the user chooses to go back
                        break;
                    case 2:
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a valid number.");
                        break;
                }
            } while (choice != 2);
        }

        void playSongFromFavorites() {
            scanner.nextLine(); // Consume newline
            System.out.print("Enter the name of the song to play from Favorites: ");
            String songName = scanner.nextLine();

            if (favorites.containsSong(songName)) {
                System.out.println("Now playing from Favorites: " + songName);
            } else {
                System.out.println("Song is not in the Favorites playlist.");
            }
        }

        void removeSongFromFavorites() {
            if (favorites.getCurrentSong() != null) {
                String currentSongName = favorites.getCurrentSong().name;
                if (favorites.containsSong(currentSongName)) {
                    SongNode current = favorites.getHead();
                    while (current != null) {
                        if (current.name.equals(currentSongName)) {
                            // Adjust the previous and next pointers
                            if (current.prev != null) {
                                current.prev.next = current.next;
                            }
                            if (current.next != null) {
                                current.next.prev = current.prev;
                            }
                            // Handle head removal
                            if (current == favorites.getHead()) {
                                favorites.head = current.next;
                            }
                            // Handle tail removal
                            if (current.next == null) {
                                favorites.head = current.prev;
                            }
                            System.out.println("Currently playing song removed from Favorites: " + currentSongName);
                            return;
                        }
                        current = current.next;
                    }
                } else {
                    System.out.println("Currently playing song is not in the Favorites playlist.");
                }
            } else {
                System.out.println("No song is currently playing.");
            }
        }

        void addSongToPlaylist() {
            displayPlaylists();

            System.out.print("Enter the number of the playlist to add the song: ");
            int playlistChoice = scanner.nextInt();

            if (playlistChoice >= 1 && playlistChoice <= playlistCount) {
                scanner.nextLine(); // Consume newline
                System.out.print("Enter the name of the song to add to the playlist: ");
                String songName = scanner.nextLine();

                if (home.containsSong(songName)) {
                    if (!playlists[playlistChoice - 1].containsSong(songName)) {
                        playlists[playlistChoice - 1].insertFirst(songName);
                        System.out.println("Song added to Playlist " + playlistChoice + ".");
                    } else {
                        System.out.println("Song is already in the playlist.");
                    }
                } else {
                    System.out.println("Song is not available in the Listen to Songs playlist.");
                }
            } else {
                System.out.println("Invalid playlist choice.");
            }
        }

        void displayPlaylists() {
            System.out.println("Available Playlists:");
            for (int i = 0; i < playlistCount; ++i) {
                System.out.println((i + 1) + ". Playlist " + (i + 1));
            }
        }





        void playlistPage() {
            System.out.println("Playlists Page:");
            System.out.println("1. Create a new playlist");
            System.out.println("2. Display all playlists");
            System.out.println("3. View songs in a playlist");
            System.out.println("4. Go to the previous menu");
            System.out.print("Enter a number to proceed: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    createNewPlaylist();
                    break;
                case 2:
                    displayPlaylists();
                    break;
                case 3:
                    // Implement viewPlaylistSongs() method or remove this case
                    System.out.println("Functionality not implemented yet.");
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid number.");
                    break;
            }
        }

        void createNewPlaylist() {
            if (playlistCount < 3) { // Assuming there is a limit of 3 playlists
                playlists[playlistCount].insertLast("Playlist " + (playlistCount + 1));
                System.out.println("Playlist created: Playlist " + (playlistCount + 1));
                playlistCount++;
            } else {
                System.out.println("Maximum number of playlists reached (3).");
            }
        }
    }

    public static void main(String[] args) {
        MusicPlayer player = new MusicPlayer();

        int choice;
        do {
            player.displayMenu();
            choice = player.scanner.nextInt();

            switch (choice) {
                case 1:
                    player.homePage();
                    break;
                case 2:
                    player.displayFavorites();
                    break;
                case 3:
                    player.playlistPage();
                    break;
                case 4:
                    player.displayAllSongs();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid number.");
            }
        } while (choice >= 1 && choice <= 3);
    }
}
