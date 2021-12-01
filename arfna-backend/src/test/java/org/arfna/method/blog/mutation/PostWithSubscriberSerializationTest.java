package org.arfna.method.blog.mutation;

import org.arfna.database.entity.Post;
import org.arfna.database.entity.Subscriber;
import org.arfna.util.gson.GsonHelper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PostWithSubscriberSerializationTest {

    @Test
    public void testSubscriberPostSerialization() {
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
        MutatePostResponse response = new MutatePostResponse();
        response.setAllPosts(Arrays.asList(p1, p2));
        String deserialized = GsonHelper.getGson().toJson(response);
        MutatePostResponse serialized = GsonHelper.getGson().fromJson(deserialized, MutatePostResponse.class);
        assertEquals(2, serialized.getAllPosts().size());
        assertNull(serialized.getAllPosts().get(0).getAuthor());
        assertNull(serialized.getAllPosts().get(1).getAuthor());
    }

}
