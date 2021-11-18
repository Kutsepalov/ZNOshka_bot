/**
 * Copyright 2021
 *
 * All rights reserved.
 *
 * Created on Nov 18, 2021 2:53:53 PM
 */
package com.softserve.znoshka.state.user;

import org.telegram.telegrambots.meta.api.objects.User;

/**
 * @author Max Kutsepalov
 *
 */
public class ZnoshkaUser {
    private State state;
    private User user;
    
    /**
     * @param user
     */
    public ZnoshkaUser(User user) {
	this(new NotEnoughSelected(new ZnoshkaUser(user)), user);
    }
    
    /**
     * @param state
     * @param user
     */
    public ZnoshkaUser(State state, User user) {
	this.state = state;
	this.user = user;
    }

    /**
     * @return the state
     */
    public State getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(State state) {
        this.state = state;
    }
}
