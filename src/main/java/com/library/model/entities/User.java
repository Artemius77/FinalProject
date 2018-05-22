package com.library.model.entities;

import com.library.controller.utils.enums.Role;

/**
 * The type User.
 */
public class User implements Identified<String>{

    private String id;
    private String password;
    private String email;
    private String phonenumber;
    private Role role;

    /**
     * Instantiates a new User.
     *
     * @param name the name
     * @param role the role
     */
    public User(String name, Role role) {
        this.id = name;
        this.role = role;
    }

    /**
     * Instantiates a new User.
     *
     * @param id       the id
     * @param password the password
     */
    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    public String getId() {
        return id;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.id = name;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets phonenumber.
     *
     * @return the phonenumber
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     * Sets phonenumber.
     *
     * @param phonenumber the phonenumber
     */
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
