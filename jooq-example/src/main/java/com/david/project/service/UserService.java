package com.david.project.service;

import com.david.project.dto.UserPayload;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.david.project.jooq.Tables.USERS;

@Service
public class UserService {

    private final DSLContext dslContext;

    public UserService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    // CREATE
    @Transactional
    public void createUser(UserPayload payload) {
        dslContext.insertInto(USERS)
                .set(USERS.NAME, payload.getName())
                .set(USERS.GENDER, payload.getGender())
                .set(USERS.PHONE_NUMBER, payload.getPhoneNumber())
                .set(USERS.EMAIL, payload.getEmail())
                .set(USERS.ADDRESS, payload.getAddress())
                .execute();
//        dslContext.insertInto(USERS)
//                .columns(USERS.NAME, USERS.GENDER, USERS.PHONE_NUMBER, USERS.EMAIL, USERS.ADDRESS)
//                .values(name, gender, phoneNumber, email, address)
//                .execute();
    }

    // READ
    public UserPayload getUserById(Integer id) {
        return dslContext.selectFrom(USERS)
                .where(USERS.ID.eq(id))
                .fetchOneInto(UserPayload.class);
    }

    public List<UserPayload> getAllUsers() {
        return dslContext.selectFrom(USERS)
                .fetchInto(UserPayload.class);
    }

    // UPDATE
    public void updateUser(UserPayload user) {
        dslContext.update(USERS)
                .set(USERS.NAME, user.getName())
                .set(USERS.GENDER, user.getGender())
                .set(USERS.PHONE_NUMBER, user.getPhoneNumber())
                .set(USERS.EMAIL, user.getEmail())
                .set(USERS.ADDRESS, user.getAddress())
                .where(USERS.ID.eq(user.getId()))
                .execute();
    }

    // DELETE
    public void deleteUserById(Integer id) {
        dslContext.deleteFrom(USERS)
                .where(USERS.ID.eq(id))
                .execute();
    }
}
