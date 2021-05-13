package com.oaklea.urg.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;

import com.oaklea.urg.model.GameLogic;
import com.oaklea.urg.model.GameMetrics;
import com.oaklea.urg.model.GlobalSettings;
import com.oaklea.urg.model.Song;
import com.oaklea.urg.model.SongConductor;
import com.oaklea.urg.model.SongGroup;
import com.oaklea.urg.view.UI.PauseScreen;
import com.oaklea.urg.view.UI.SongGroupItem;
import com.oaklea.urg.view.UI.SongItem;
import com.oaklea.urg.view.UI.SongPreviewPlayer;
import com.oaklea.urg.view.scenes.MainMenu;
import com.oaklea.urg.view.scenes.ResultsScreen;
import com.oaklea.urg.view.scenes.SettingsScreen;
import com.oaklea.urg.view.scenes.SongPicker;
import com.oaklea.urg.view.scenes.SongPlayer;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The Controller for URG. Manages the interaction between the Model and View
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class Controller {

    Stage mainStage;
    MainMenu mainMenu;
    SongPicker songPicker;
    SongPlayer songPlayer;
    ResultsScreen resultsScreen;
    SettingsScreen settingsScreen;
    GlobalSettings gsettings;
    ArrayList<SongGroup> songGroups;
    GameLogic gameLogic;
    MediaPlayer musicPlayer;
    Song currentSong;

    /**
     * Creates a new Controller that's tied to a Stage. Reads in the songData and
     * initializes the MainMenu
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param stage the Stage to tie the Controller to
     */
    public Controller(Stage stage) {
        this.mainStage = stage;

        // init settings
        readInSettings();
        // init song data
        readInSongs();
        // initial screen is the main menu
        setupMainMenu();
        this.mainStage.setScene(this.mainMenu);

        // we're done with setting everything up, show it all!
        this.mainStage.setWidth(1920);
        this.mainStage.setHeight(1080);
        this.mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        this.mainStage.show();
    }

    private void readInSettings() {
        File settingsFile = new File("./bin/settings.gs");
        try {
            if (settingsFile.length() != 0) { // check if file empty first
                FileInputStream fis = new FileInputStream(settingsFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                this.gsettings = (GlobalSettings) ois.readObject();
                ois.close();
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupMainMenu() {
        // add functionality to buttons
        this.mainMenu = new MainMenu(new StackPane());
        this.mainMenu.getSinglePlayerButton().setOnMouseClicked(e -> {
            setupSongPicker();
            this.mainMenu.getVisualizer().pause();
            this.setScene(this.songPicker);
        });
        this.mainMenu.getSettingsButton().setOnMouseClicked(e -> {
            setupSettingsScreen();
            this.mainMenu.getVisualizer().pause();
            this.setScene(this.settingsScreen);
        });
        this.mainMenu.getQuitButton().setOnMouseClicked(e -> {
            System.exit(0);
        });

        // start the main menu visualizer
        this.mainMenu.getVisualizer().start(this.musicPlayer);
    }

    private void setupSettingsScreen() {
        this.settingsScreen = new SettingsScreen(new BorderPane(), this.gsettings);
        this.settingsScreen.getBackButton().setOnMouseClicked(e -> {
            this.mainMenu.getVisualizer().unpause();
            this.setScene(this.mainMenu);
        });
        this.settingsScreen.getMusicVolumeSlider().valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                gsettings.setMusicVolume(newValue.doubleValue() / 100);
                musicPlayer.setVolume(newValue.doubleValue() / 100);
            }
        });
        this.settingsScreen.getHitsoundVolumeSlider().valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                gsettings.setHitsoundVolume(newValue.doubleValue() / 100);
            }
        });
        this.settingsScreen.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ESCAPE:
                    this.mainMenu.getVisualizer().unpause();
                    this.setScene(this.mainMenu);
                    break;
                default:
                    break;
            }
        });
    }

    private void setupSongPicker() {
        this.songPicker = new SongPicker(this.songGroups, new BorderPane());
        this.songPicker.setBackgroundImage(this.currentSong.getBackgroundPath());
        ArrayList<SongGroupItem> songGroupItems = this.songPicker.getSongGroupItems();
        for (SongGroupItem group : songGroupItems) {
            group.getMainPane().setOnMouseClicked(e -> {
                for (SongGroupItem g2 : songGroupItems) {
                    g2.close();
                }
                group.toggleSongItems();
                this.currentSong = group.getSongItems().get(0).getSong();
                this.songPicker.setBackgroundImage(this.currentSong.getBackgroundPath());
                this.songPicker.renderScores(this.currentSong);
                this.playPreview();
            });
            if (group.getSongItems().get(0).getSong().getTitle().equals(this.currentSong.getTitle())) {
                group.toggleSongItems();
            }
            for (SongItem song : group.getSongItems()) {
                song.setOnMouseClicked(e -> {
                    setupSongPlayer(song.getSong(), e.getButton() == MouseButton.SECONDARY);
                    setScene(songPlayer);
                });
            }
        }
        this.songPicker.renderScores(this.currentSong);

        this.songPicker.getBackButton().setOnMouseClicked(e -> {
            this.mainMenu.getVisualizer().unpause();
            setScene(this.mainMenu);
        });

        // add key listener
        this.songPicker.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ESCAPE:
                    this.mainMenu.getVisualizer().unpause();
                    setScene(this.mainMenu);
                    break;
                default:
                    break;
            }
        });
    }

    private void setupGamePortion(Song song, boolean autoplay) {
        this.musicPlayer.stop();
        this.musicPlayer = null;
        this.musicPlayer = new MediaPlayer(new Media(new File(song.getAudioPath()).toURI().toString()));
        this.musicPlayer.setVolume(this.gsettings.getMusicVolume());
        this.gameLogic = new GameLogic(new SongConductor(this.musicPlayer), song, autoplay, this.gsettings) {
            @Override
            public void onSongEnd() {
                // save metrics
                this.getMetrics().save(song.getScorePath());
                song.addScore(this.getMetrics());
                setupResultsScreen(this.getMetrics(), song);
                setScene(resultsScreen);
            }
        };
    }

    private void setupSongPlayer(Song song, boolean autoplay) {

        setupGamePortion(song, autoplay);

        this.songPlayer = new SongPlayer(song, new StackPane(), autoplay, this.gameLogic, this.gsettings);

        // set up pause buttons
        PauseScreen pauseScreen = this.songPlayer.getPauseScreen();
        pauseScreen.getResumeText().setOnMouseClicked(e -> {
            this.songPlayer.togglePauseScreen();
        });
        pauseScreen.getExitText().setOnMouseClicked(e -> {
            this.setScene(this.songPicker);
            this.musicPlayer.play();
        });
    }

    private void setupResultsScreen(GameMetrics metrics, Song song) {
        this.resultsScreen = new ResultsScreen(new BorderPane(), metrics, song);
        this.resultsScreen.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ESCAPE:
                    this.setScene(this.songPicker);
                    this.playPreview();
                    this.songPicker.renderScores(song);
                    break;
                default:
                    break;
            }
        });
        this.resultsScreen.getBackButton().setOnMouseClicked(e -> {
            this.setScene(this.songPicker);
            this.playPreview();
            this.songPicker.renderScores(song);

        });
    }

    private void readInSongs() {
        File songDirectory = new File("./bin/songs");
        this.songGroups = new ArrayList<SongGroup>();
        for (File directory : songDirectory.listFiles()) {
            SongGroup group = new SongGroup(directory.getName());
            if (directory.listFiles() == null)
                continue;
            for (File file : directory.listFiles()) {
                if (file.getName().contains(".txt")) {
                    group.addSong(new Song(file));
                }
            }
            this.songGroups.add(group);
        }
        // set current song to random song
        this.currentSong = this.songGroups.get(new Random().nextInt(this.songGroups.size())).getSongs().get(0);
        playPreview();

    }

    private void playPreview() {
        if (this.musicPlayer != null) {
            this.musicPlayer.stop();
        }
        this.musicPlayer = new MediaPlayer(new Media(new File(currentSong.getAudioPath()).toURI().toString()));
        if (this.mainMenu != null) {
            this.mainMenu.getVisualizer().start(this.musicPlayer);
        }
        this.musicPlayer.setVolume(this.gsettings.getMusicVolume());
        this.musicPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                musicPlayer.seek(new Duration(currentSong.getPreviewTime()));
                musicPlayer.play();
                mainMenu.setAndShowSongPreviewPlayer(new SongPreviewPlayer(currentSong, musicPlayer));
                FontIcon pauseButton = mainMenu.getPreviewPlayer().getPauseButton();
                pauseButton.setOnMouseClicked(e -> {
                    if (musicPlayer != null) {
                        if (pauseButton.getIconLiteral().equals("mdi-pause")) {
                            pauseButton.setIconLiteral("mdi-play");
                            musicPlayer.pause();
                        } else {
                            pauseButton.setIconLiteral("mdi-pause");
                            musicPlayer.play();
                        }
                    }
                });
                FontIcon shuffleButton = mainMenu.getPreviewPlayer().getShuffleButton();
                shuffleButton.setOnMouseClicked(e -> {
                    if (musicPlayer != null) {
                        // set current song to random song
                        currentSong = songGroups.get(new Random().nextInt(songGroups.size())).getSongs().get(0);
                        playPreview();
                    }
                });
            }
        });
    }

    private void setScene(Scene newScene) {
        this.mainStage.setScene(newScene);
    }

}
