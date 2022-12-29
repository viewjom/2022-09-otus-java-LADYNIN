package ru.otus.dao;

import ru.otus.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class InMemoryUserDao implements UserDao {

    private final Map<Long, User> users;
    //   private final SessionFactory sessionFactory;

    public InMemoryUserDao() {
        users = new HashMap<>();
        users.put(1L, new User(1L, "Крис Гир", "user1", "11111"));
        users.put(2L, new User(2L, "Ая Кэш", "user2", "11111"));
        users.put(3L, new User(3L, "Десмин Боргес", "user3", "11111"));
        users.put(4L, new User(4L, "Кетер Донохью", "user4", "11111"));
        users.put(5L, new User(5L, "Стивен Шнайдер", "user5", "11111"));
        users.put(6L, new User(6L, "Джанет Вэрни", "user6", "11111"));
        users.put(7L, new User(7L, "Брэндон Смит", "user7", "11111"));

  /*     this.sessionFactory = sessionFactory;

        var client = new Client(null, "Vasya", new Address(null, "AnyStreet"),
                List.of(new Phone(null, "13-555-22"), new Phone(null, "14-666-333")));
        try (var session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.persist(client);
            session.getTransaction().commit();
       //     session.clear();


   */
    }
    @Override
    public Map<String, Object> getAll(){

        Map<String, Object> paramsMap = new HashMap<>();
        String body = "";

        for(Map.Entry<Long, User> entry: users.entrySet()) {

            body = body +
                   "<tr>" +
                     "<td>" + entry.getValue().getId() + "</td>" +
                     "<td>" + entry.getValue().getName() + "</td>" +
                     "<td>" + entry.getValue().getLogin() + "</td>" +
                     "<td>" + entry.getValue().getPassword() + "</td>" +
                   "</tr>";
        }
        paramsMap.put("body", body);

        return paramsMap;
    }


        @Override
        public Optional<User> findById ( long id){
            return Optional.ofNullable(users.get(id));

            //   return transactionManager.doInReadOnlyTransaction(session -> {


            //   var res =  clientTemplate.findById(session, savedClient.getId());


        }

        @Override
        public Optional<User> findRandomUser () {
            Random r = new Random();
            return users.values().stream().skip(r.nextInt(users.size() - 1)).findFirst();
        }

        @Override
        public Optional<User> findByLogin (String login){
            return users.values().stream().filter(v -> v.getLogin().equals(login)).findFirst();


        }
    }