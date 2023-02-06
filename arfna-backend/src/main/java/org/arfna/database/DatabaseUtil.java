package org.arfna.database;

import org.arfna.database.entity.Post;
import org.arfna.database.entity.Subscriber;
import org.arfna.util.logger.ArfnaLogger;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseUtil {

    /**
     * Adds subscriber to table if unique
     * @param s subscriber
     * @return true if added subscriber to table
     */
    public boolean createSubscriber(Subscriber s) {
        if (!doesSubscriberExist(s.getEmailAddress())) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                session.beginTransaction();
                session.save(s);
                session.getTransaction().commit();
                return true;
            } catch (Exception e) {
                ArfnaLogger.exception(DatabaseUtil.class, "Exception occurred when creating subscriber", e);
            }
        }
        return false;
    }

    /**
     * Creates a post and returns the unique identifier of the post
     * @param p post to write to the table
     * @return the unique key for the post
     */
    public int createPost(Post p) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Serializable key = session.save(p);
            session.getTransaction().commit();
            if (key instanceof Integer)
                return (Integer) key;
        } catch (Exception e) {
            ArfnaLogger.exception(DatabaseUtil.class, "Exception occurred when creating post", e);
        }
        return -1;
    }

    /**
     * Given an already existing post, we fetch from the database and update with the new information
     * @param p existing post from the database
     */
    public void updatePost(Post p) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Post existingPost = session.get(Post.class, p.getId());
            p.copy(existingPost);
            session.getTransaction().commit();
        } catch (Exception e) {
            ArfnaLogger.exception(DatabaseUtil.class, "Exception occurred when creating post", e);
        }
    }

    /**
     * Given an already existing subscriber, we fetch from the database and update with the new information
     * @param s existing subscriber from the database
     */
    public void updateSubscriber(Subscriber s) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Subscriber existingSub;
            if (s.getId() == 0) {
                List<Subscriber> subscribers = getSubscribersFromEmail(s.getEmailAddress(), session);
                if (subscribers.size() == 0) {
                    ArfnaLogger.warn(this.getClass(), "Subscriber does not exist");
                    session.getTransaction().commit();
                    return;
                }
                existingSub = subscribers.get(0);
            } else {
                existingSub = session.get(Subscriber.class, s.getId());
            }
            s.copyNewInformation(existingSub);
            session.getTransaction().commit();
        } catch (Exception e) {
            ArfnaLogger.exception(DatabaseUtil.class, "Exception occurred when creating post", e);
        }
    }

    public Subscriber getSubscriberFromEmail(String emailAddress) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            List<Subscriber> s = getSubscribersFromEmail(emailAddress, session);
            session.getTransaction().commit();
            return !s.isEmpty() ? s.get(0) : null;
        } catch (Exception e) {
            ArfnaLogger.exception(DatabaseUtil.class, "Exception occurred when checking if subscriber exists", e);
        }
        return null;
    }

    public boolean doesSubscriberExist(String emailAddress) {
        Subscriber s = getSubscriberFromEmail(emailAddress);
        return s != null;
    }

    public List<Post> getSubmittedPostsNotPublished() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Post> query = criteriaBuilder.createQuery(Post.class);
            Root<Post> root = query.from(Post.class);
            query.select(root)
                    .where(criteriaBuilder.equal(root.get("isSubmitted"), true))
                    .where(criteriaBuilder.equal(root.get("isPublished"), false))
                    .orderBy(criteriaBuilder.asc(root.get("lastUpdated")));
            List<Post> posts = session.createQuery(query).getResultList();
            session.getTransaction().commit();
            return posts;
        } catch (Exception e) {
            ArfnaLogger.exception(DatabaseUtil.class, "Exception occurred when fetching submitted posts", e);
        }
        return null;
    }

    public List<Post> getPublishedPosts() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Post> query = criteriaBuilder.createQuery(Post.class);
            Root<Post> root = query.from(Post.class);
            query.select(root)
                    .where(criteriaBuilder.equal(root.get("isPublished"), true))
                    .orderBy(criteriaBuilder.desc(root.get("lastUpdated")));
            List<Post> posts = session.createQuery(query).getResultList();
            session.getTransaction().commit();
            return posts;
        } catch (Exception e) {
            ArfnaLogger.exception(DatabaseUtil.class, "Exception occurred when fetching published posts", e);
        }
        return new ArrayList<>();
    }

    /**
     * This is a unique logic method.
     * It will only get posts that are passed the submitted stage so the admin can look at it.
     * It will also only pull posts that aren't written by admin, assuming that the rest of the posts were retrieved
     * earlier.
     * @return List<Post> filtered appropriately as by above.
     */
    public List<Post> getAllPostsForAdminView(int subscriberId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Post> query = criteriaBuilder.createQuery(Post.class);
            Root<Post> root = query.from(Post.class);
            query.select(root)
                    .where(Arrays.asList(
                            criteriaBuilder.equal(root.get("isSubmitted"), true),
                            criteriaBuilder.notEqual(root.get("author"), subscriberId)
                    ).toArray(new Predicate[0]))
                    .orderBy(criteriaBuilder.desc(root.get("lastUpdated")));
            List<Post> posts = session.createQuery(query).getResultList();
            session.getTransaction().commit();
            return posts;
        } catch (Exception e) {
            ArfnaLogger.exception(DatabaseUtil.class, "Exception occurred when fetching all submitted posts for admin view", e);
        }
        return new ArrayList<>();
    }

    public Post getPost(int postId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Post p = session.get(Post.class, postId);
            session.getTransaction().commit();
            return p;
        } catch (Exception e) {
            ArfnaLogger.exception(DatabaseUtil.class, "Exception occurred when fetching the post " + postId, e);
        }
        return null;
    }

    public boolean deletePost(int postId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Post post = session.get(Post.class, postId);
            session.delete(post);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            ArfnaLogger.exception(DatabaseUtil.class, "Exception occurred when deleting the post " + postId, e);
            return false;
        }
    }

    public Subscriber getSubscriber(int subscriberId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Subscriber s = session.get(Subscriber.class, subscriberId);
            session.getTransaction().commit();
            return s;
        } catch (Exception e) {
            ArfnaLogger.exception(DatabaseUtil.class, "Exception occurred when fetching the subscriber " + subscriberId, e);
        }
        return null;
    }

    public List<Post> getPostsPerSubscriber(int subscriberId) {
        Subscriber s = getSubscriber(subscriberId);
        return s != null ? s.getPosts() : new ArrayList<>();
    }

    private List<Subscriber> getSubscribersFromEmail(String emailAddress, Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Subscriber> query = criteriaBuilder.createQuery(Subscriber.class);
        Root<Subscriber> root = query.from(Subscriber.class);
        query.select(root)
                .where(criteriaBuilder.equal(root.get("emailAddress"), emailAddress));
        return session.createQuery(query).getResultList();
    }

}
