package control.initializers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class LevelInitializer {
    private int playerID;

    public LevelInitializer(int playerID){
        this.playerID = playerID;
    }
    public void initLevel(String levelPath){
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(levelPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(String line : lines){
            for(char c : line.toCharArray()){
                switch(c) {
                    case '.':
                        // create empty tile
                        break;
                    case '#':
                        // create wall tile
                        break;
                    case '@':
                        // create player tile
                        break;
                    default:
                        // create enemy tile
                        break;
                }
            }
        }
    }
}
