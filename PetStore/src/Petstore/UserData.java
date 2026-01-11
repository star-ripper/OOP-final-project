package Petstore;
import java.io.*;
import java.util.*;
public class UserData implements FileEditing {
    HashMap<String, String> admin = new HashMap<>();
    HashMap<String, String> user = new HashMap<>();
    void addAdminData(){
        File f = new File("AdminInformation.txt");
        if(!f.exists()){return;}
        try {
            FileReader fr = new FileReader("AdminInformation.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null){
                String[] data = line.trim().split(":");
                admin.put(data[0],data[1]);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void addData(){
        File f = new File("UserInformation.txt");
        if(!f.exists()){return;}
        try {
            FileReader fr = new FileReader("UserInformation.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null){
                String[] data = line.trim().split(":");
                user.put(data[0].trim(), data[1].trim());
            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }
    void saveUserData(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("UserInformation.txt"))) {
            for (Map.Entry<String, String> entry : user.entrySet()) {
                bw.write(entry.getKey() + ":" + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public <T> void deleteFromFile(T data){
        File f = new File("CustomerInformation.txt");

        if(!f.exists()){return;}

    }

}
