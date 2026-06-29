package petstore;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserData implements FileEditing {

    public HashMap<String, String> admin = new HashMap<>();
    public HashMap<String, String> user = new HashMap<>();

    private static final String ADMIN_FILE = "AdminInformation.txt";
    private static final String USER_FILE = "UserInformation.txt";

    public UserData() {
        loadAdmins();
        addData();
        ensureDefaultAdmin();
    }


    private void loadAdmins() {
        File f = new File(ADMIN_FILE);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.trim().split(":");
                if (data.length == 2) {
                    admin.put(data[0].trim(), data[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ensureDefaultAdmin() {
        if (admin.isEmpty()) {
            admin.put("admin", "Admin123");
            saveAdminData();
            System.out.println("Default admin created → admin : Admin123");
        }
    }

    private void saveAdminData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ADMIN_FILE))) {
            for (Map.Entry<String, String> entry : admin.entrySet()) {
                bw.write(entry.getKey() + ":" + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addData() {
        File f = new File(USER_FILE);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.trim().split(":");
                if (data.length == 2) {
                    user.put(data[0].trim(), data[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUserData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (Map.Entry<String, String> entry : user.entrySet()) {
                bw.write(entry.getKey() + ":" + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> void deleteFromFile(T data) {
        if (!(data instanceof String username)) return;

        user.remove(username);
        saveUserData();
    }

    public boolean isAdmin(String username, String password) {
        return admin.containsKey(username) &&
                admin.get(username).equals(password);
    }

    public boolean isUser(String username, String password) {
        return user.containsKey(username) &&
                user.get(username).equals(password);
    }

    public boolean addUser(String username, String password) {
        if (user.containsKey(username)) return false;
        user.put(username, password);
        saveUserData();
        return true;
    }
}
