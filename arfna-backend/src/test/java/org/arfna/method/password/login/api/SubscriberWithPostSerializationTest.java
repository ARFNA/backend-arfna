package org.arfna.method.password.login.api;

import org.arfna.database.entity.Post;
import org.arfna.database.entity.Subscriber;
import org.arfna.util.gson.GsonHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SubscriberWithPostSerializationTest {

    @Test
    public void testSubscriberDeserialization() {
        Subscriber s = new Subscriber();
        Post p1 = new Post();
        p1.setAuthor(s);
        p1.setTitle("My title here");
        p1.setMarkdown("My markdown stuff here");
        s.addPost(p1);
        Post p2 = new Post();
        p2.setAuthor(s);
        p2.setTitle("My second title here");
        p2.setMarkdown("My markdown stuff here");
        s.addPost(p2);
        s.setName("Rosh");
        s.setEmailAddress("test@gmail.com");
        s.setId(1);
        s.setRole("maint");
        MutateSubscribersResponse response = new MutateSubscribersResponse();
        response.setSubscriber(s);
        String deserialized = GsonHelper.getGson().toJson(response);
        Subscriber serialized = GsonHelper.getGson().fromJson(deserialized, Subscriber.class);
        assertTrue(serialized.getPosts().isEmpty());
    }

}
