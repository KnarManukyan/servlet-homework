package com.synisys;

import java.util.HashMap;
import java.util.Map;

class UsersService {
    private Map<User, Integer> users;
    private int token = 1;
    UsersService() {
        this.users = new HashMap<User, Integer>();
        this.users.put(new User("admin", "admin"), token++);
        this.users.put(new User("Robin", "Graham"), token++);
        this.users.put(new User("Carlton", "Casey"), token++);
        this.users.put(new User("Josephine", "Lawrence"), token++);
        this.users.put(new User("Henry", "Stanley"), token++);
        this.users.put(new User("Taylor", "Campbell"), token++);
        this.users.put(new User("Patricia", "Ingram"), token++);
        this.users.put(new User("Kenneth", "Page"), token++);
    }

    Integer findUser(User user) {
        return users.getOrDefault(user, -1);
    }

    public Map<User, Integer> getUsers() {
        return users;
    }

    void addUser(User u){
        this.users.put(u, token++);
    }
}
