package edu.school21.app;


import edu.school21.models.Car;
import edu.school21.models.User;

public class Program {

    public static void main(String[] args) {

        Manager myDB = new OrmManager();
        User user1 = new User((long) 10, "Evgenii", "Isupov", 23);
        User user2 = new User((long) 90, "Nail", "Mukminov", 22);
        User user3 = new User(null, "Regina", "Valeeva", 21);
        User user4 = new User((long) 5, "Fidan", "Halikov", 23);
        User user5 = new User((long) 5, "Irina", "Isupova", 44);
        User user6 = new User((long) 5, "Konstantin", "Isupov", 45);

        Car car1 = new Car("Ford", 2015, 200);
        Car car2 = new Car("Ferrari", 2020, 350);
        Car car3 = new Car("Tatra", 1990, 100);
        Car car4 = new Car("Lada", 2010, 150);


        myDB.save(user1);
        myDB.save(car1);
        myDB.save(car2);
        myDB.save(user2);
        myDB.save(user3);
        myDB.save(car3);
        myDB.save(user4);
        myDB.save(user5);
        myDB.save(user6);
        myDB.save(car4);
        myDB.showTable("simple_user");
        myDB.showTable("car_table");

    }
}
