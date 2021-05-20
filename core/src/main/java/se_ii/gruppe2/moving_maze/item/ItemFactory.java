package se_ii.gruppe2.moving_maze.item;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.Random;

public class ItemFactory {

    private static final String[] itemPaths = getFileList();
    private static int itemPathCounter= 0;
    private static Random random=new Random();


    private ItemFactory(){}


    /**
            *Gets all components to create an item
     */
    private static ItemLogical buildItem(int x, int y){
        var position = new Position();
        position.setPosition(x, y);
        String path= itemPaths[itemPathCounter++];
        return new ItemLogical(path, position,false);
    }

    /**
     *LibGDX has the FileHandel Class. With this class you can give a Pathname. When u call then the
     *.list function, you get a List off all Files in the Folder, that were selectet.
     * List element if android:I/System.out: items/(image Element)
     * List element if desktop: android/assets/items/(image Element)
     *
     * The second for makes the item instances game per game random.
     */
    private static String[] getFileList(){
        FileHandle handle;
        if(Gdx.app.getType() == Application.ApplicationType.Android){
            handle= Gdx.files.internal("items");
        }
        else{
            handle= Gdx.files.internal("android/assets/items");
        }
        var fileNames= new String[handle.list().length];
        var i = 0;
        for (FileHandle file : handle.list()) {
            fileNames[i]=file.toString();
            i++;
        }
        fileNames=shuffleArray(fileNames);

        return fileNames;
    }

    private static String[] shuffleArray(String[] shuffleA){
        for (var j = 0; j < shuffleA.length; j++) {
            var swapIndex= random.nextInt(shuffleA.length);
            String temp = shuffleA[swapIndex];
            shuffleA[swapIndex]= shuffleA[j];
            shuffleA[j]=temp;
        }
        return shuffleA;
    }

}
