package com.softserve.bot.view.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Handler {
    SendMessage handle(Update update);
}
