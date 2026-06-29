package petstore;
import java.io.File;
import java.io.IOException;
class Testing {
    public static void main(String[] args) throws IOException {
        File invFile = new File("Inventory.txt");
        if (!invFile.exists()) {
            try {
                invFile.createNewFile();
            } catch (
                    IOException e) {
                e.printStackTrace();
            }
        }
    }
}