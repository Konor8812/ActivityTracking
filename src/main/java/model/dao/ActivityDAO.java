package model.dao;

public class ActivityDAO {
    private static ActivityDAO instance;


    public static synchronized ActivityDAO getInstance() {
        if (instance == null) {
            instance = new ActivityDAO();
        }
        return instance;
    }


    public void insertActivity(String name){

    }


}
