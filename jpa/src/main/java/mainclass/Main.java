package mainclass;

import jakarta.persistence.*;

import util.JPAUtil;

import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) {
        boolean isRunning = true;
        while (isRunning) {
            Menu.showMain();
            int menuChoice = UserInputHandler.menuInput(5);
            switch (menuChoice) {
                case 0 -> isRunning = false;
                case 1 -> Menu.create();
                case 2 -> Menu.read();
                case 3 -> Menu.count();
                case 4 -> Menu.update();
                case 5 -> Menu.delete();
            }
        }
    }

    public static void inTransaction(Consumer<EntityManager> work) {
        try (EntityManager entityManager = JPAUtil.getEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                work.accept(entityManager);
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            }
        }
    }

}