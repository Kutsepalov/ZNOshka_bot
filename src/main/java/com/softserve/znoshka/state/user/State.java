/**
 * Copyright 2021
 *
 * All rights reserved.
 *
 * Created on Nov 18, 2021 2:38:38 PM
 */
package com.softserve.znoshka.state.user;

import org.telegram.telegrambots.meta.api.objects.User;

import com.softserve.znoshka.Subject;

/**
 * @author Max Kutsepalov
 *
 */
public abstract class State {
    private ZnoshkaUser user;
    
    public State(ZnoshkaUser user2) {
	this.setUser(user2);
    }
    
    public abstract String selectSubject(Subject x, Buttons btns);
	
//	y.select(x);
//	y.getAmountSelected();
//	return "";
    
    public abstract String getSpecialties();

    public ZnoshkaUser getUser() {
	return user;
    }

    public void setUser(ZnoshkaUser user) {
	this.user = user;
    }
}
