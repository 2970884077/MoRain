/*
 * Copyright (c) 2018-2020 Karlatemp. All rights reserved.
 * @author Karlatemp <karlatemp@vip.qq.com> <https://github.com/Karlatemp>
 * @create 2020/03/18 12:24:53
 *
 * MiraiPlugins/MiraiPlugins/Console.java
 */

package cn.mcres.karlatemp.mirai;

import cn.mcres.karlatemp.mxlib.tools.Toolkit;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.EventCancelledException;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.*;
import org.apache.hc.core5.concurrent.CompletedFuture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.util.concurrent.Future;

public abstract class Console extends Contact {
    static Console INSTANCE;
    static OnlineMessageSource.Outgoing source;

    static {
        ClassWriter writer = new ClassWriter(0);
        writer.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "cn/mcres/karlatemp/mirai/Console$MS$PACKET", null, "net/mamoe/mirai/message/data/OnlineMessageSource$Outgoing", null);
        source = Toolkit.Reflection.allocObject(Toolkit.Reflection.defineClass(
                Console.class.getClassLoader(), writer, null
        ).asSubclass(OnlineMessageSource.Outgoing.class));
    }

    public static Console getInstance() {
        return INSTANCE;
    }

    @NotNull
    @Override
    public Bot getBot() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        return o == this;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @NotNull
    @Override
    public String toString() {
        return "ConsoleContact";
    }

    @NotNull
    @Override
    public CoroutineContext getCoroutineContext() {
        throw new UnsupportedOperationException();
    }

    protected abstract void write(String message);

    @NotNull
    @Override
    public MessageReceipt<Contact> sendMessage(@NotNull String message) throws EventCancelledException, IllegalStateException {
        MessageReceipt<Contact> receipt = new MessageReceipt(source, ConsolePacket.INSTANCE.getSender(), null);
        write(message);
        return receipt;
    }

    @NotNull
    @Override
    public Future<MessageReceipt<Contact>> sendMessageAsync(@NotNull String message) {
        return new CompletedFuture<>(sendMessage(message));
    }

    @NotNull
    @Override
    public Future<MessageReceipt<Contact>> sendMessageAsync(@NotNull Message message) {
        return new CompletedFuture<>(sendMessage(message));
    }

    @NotNull
    @Override
    public MessageReceipt<Contact> sendMessage(@NotNull Message message) throws EventCancelledException, IllegalStateException {
        return sendMessage(message.toString());
    }
}
