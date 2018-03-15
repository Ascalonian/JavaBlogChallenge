package com.galvin.interview.dao;

import com.galvin.interview.entity.BlogPost;
import com.galvin.interview.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class BlogPostDAO {
    private static BlogPostDAO instance = null;

    /**
     * Get the singleton of this class
     * @return the singleton
     */
    public static BlogPostDAO getInstance() {
        if (instance == null) {
            instance = new BlogPostDAO();
        }

        return instance;
    }

    /**
     * Find a Blog Post by the UUID
     * @param uuid the UUID to search by
     * @return the BlogPost using that UUID. If none is found, NULL is returned
     */
    public BlogPost findByUUID(final String uuid) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        BlogPost blogPost = (BlogPost) session.get(BlogPost.class, uuid);

        return blogPost;
    }

    /**
     * Remove the BlogPost from the database
     * @param uuid the UUID of the BlogPost to remove
     */
    public void remove(final String uuid) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        BlogPost blogPost = findByUUID(uuid);

        if (blogPost != null) {
            session.delete(blogPost);
        }
    }

    /**
     * Save a new BlogPost to the database
     * @param blogPost the BlogPost to save
     * @return the UUID of the new BlogPost
     */
    public String save(final BlogPost blogPost) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.save(blogPost);

        return blogPost.getUuid();
    }

    /**
     * Return a List of all the BlogPosts available
     * @return the list of all BlogPosts
     */
    public List<BlogPost> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<BlogPost> postList = new ArrayList<>();

        Query query = session.getNamedQuery("BlogPost.findAll");
        postList = query.list();

        return postList;
    }
}