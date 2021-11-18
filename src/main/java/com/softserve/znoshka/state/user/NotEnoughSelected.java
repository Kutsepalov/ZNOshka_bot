/**
 * Copyright 2021
 *
 * All rights reserved.
 *
 * Created on Nov 18, 2021 2:42:42 PM
 */
package com.softserve.znoshka.state.user;

import com.softserve.znoshka.Subject;

/**
 * @author Max Kutsepalov
 *
 */
public class NotEnoughSelected extends State {
    
    public NotEnoughSelected(ZnoshkaUser user) {
	super(user);
    }

    @Override
    public String selectSubject(Subject x, Buttons btns) {
	btns.select(x);
	int amount = btns.getAmountSelected();
	if(amount >= 3) {
	    super.getUser().setState(new EnoughSelected());
	}
	return null;
    }

    @Override
    public String getSpecialties() {
	// TODO Auto-generated method stub
	return null;
    }

}
