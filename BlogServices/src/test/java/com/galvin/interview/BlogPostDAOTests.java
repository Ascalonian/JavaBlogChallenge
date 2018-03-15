package com.galvin.interview;

import com.galvin.interview.dao.BlogPostDAO;
import com.galvin.interview.entity.BlogPost;
import com.galvin.interview.utils.HibernateUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class BlogPostDAOTests {
    private BlogPostDAO dao;
    private String findUUID_UUID = null;
    private String save_UUID = null;

    @Before
    public void setup() {
        dao = new BlogPostDAO();
    }

    @After
    public void tearDown() {
        if (findUUID_UUID != null) {
            dao.remove(findUUID_UUID);
        }

        if (save_UUID != null) {
            dao.remove(save_UUID);
        }

        dao = null;
    }

    @Test
    public void findByUUID() {
        System.out.print("Create dummy post...");
        BlogPost dummyPost = generateDummyPost("findByUUID test");
        HibernateUtil.beginTransaction();
        findUUID_UUID = dao.save(dummyPost);
        HibernateUtil.endTransaction(false);

        Assert.assertNotNull("No UUID returned; Save failed", findUUID_UUID);
        System.out.println("DONE");

        System.out.print("Fetching Blog Post by UUID...");

        BlogPost post = dao.findByUUID(findUUID_UUID);

        Assert.assertNotNull("No post found", post);
        Assert.assertEquals("UUID do not match", findUUID_UUID, post.getUuid());
        System.out.println("DONE");
    }

    @Test
    public void save() {
        System.out.print("Creating example Blog Post...");

        BlogPost post = generateDummyPost("Save test");

        System.out.print("Saving...");
        HibernateUtil.beginTransaction();
        save_UUID = dao.save(post);
        HibernateUtil.endTransaction(false);

        Assert.assertNotNull("No UUID returned; Save failed", save_UUID);
        System.out.println("DONE");

        System.out.println("UUID: " + save_UUID);
    }

    @Test
    public void remove() {
        String uuid = "42635b12-4f02-4cd0-a78b-db82b5124e57"; // Need to run save() first to get this

        System.out.print("Confirm post is still in DB...");
        BlogPost post = dao.findByUUID(uuid);

        Assert.assertNotNull("No post found", post);
        Assert.assertEquals("UUID do not match", uuid, post.getUuid());
        System.out.println("DONE");

        System.out.print("Remove the post...");
        HibernateUtil.beginTransaction();
        dao.remove(uuid);
        HibernateUtil.endTransaction(false);
        System.out.println("DONE");

        System.out.print("Verifying the post was removed...");
        post = dao.findByUUID(uuid);

        Assert.assertNull("Post was not removed", post);
        System.out.println("DONE");
    }

    @Test
    public void findAllPosts() {
        System.out.println("Creating 5 posts...");

        System.out.print("    Post 1...");
        BlogPost post = generateDummyPost("Post 1 test");
        HibernateUtil.beginTransaction();
        String uuid1 = dao.save(post);

        Assert.assertNotNull("No UUID returned; Save failed", uuid1);
        System.out.println("DONE");

        System.out.print("    Post 2...");
        post = generateDummyPost("Post 2 test");
        String uuid2 = dao.save(post);

        Assert.assertNotNull("No UUID returned; Save failed", uuid2);
        System.out.println("DONE");

        System.out.print("    Post 3...");
        post = generateDummyPost("Post 3 test");
        String uuid3 = dao.save(post);

        Assert.assertNotNull("No UUID returned; Save failed", uuid3);
        System.out.println("DONE");

        System.out.print("    Post 4...");
        post = generateDummyPost("Post 4 test");
        String uuid4 = dao.save(post);

        Assert.assertNotNull("No UUID returned; Save failed", uuid4);
        System.out.println("DONE");

        System.out.print("    Post 5...");
        post = generateDummyPost("Post 5 test");
        String uuid5 = dao.save(post);
        HibernateUtil.endTransaction(false);

        Assert.assertNotNull("No UUID returned; Save failed", uuid5);
        System.out.println("DONE");


        System.out.print("Fetching all posts...");
        List<BlogPost> postList = dao.findAll();

        Assert.assertNotNull("Error getting all post", postList);
        System.out.println("DONE");

        System.out.print("Validating correct size...");
        Assert.assertEquals("Incorrect results found", 5, postList.size());
        System.out.println("DONE");

        System.out.print("Removing dummy posts...");
        HibernateUtil.beginTransaction();
        dao.remove(uuid1);
        dao.remove(uuid2);
        dao.remove(uuid3);
        dao.remove(uuid4);
        dao.remove(uuid5);
        HibernateUtil.endTransaction(false);

        BlogPost testPost = dao.findByUUID(uuid1);
        Assert.assertNull("UUID1 not deleted", testPost);

        testPost = dao.findByUUID(uuid2);
        Assert.assertNull("UUID2 not deleted", testPost);

        testPost = dao.findByUUID(uuid3);
        Assert.assertNull("UUID3 not deleted", testPost);

        testPost = dao.findByUUID(uuid4);
        Assert.assertNull("UUID4 not deleted", testPost);

        testPost = dao.findByUUID(uuid5);
        Assert.assertNull("UUID5 not deleted", testPost);

        System.out.println("DONE");
    }

    private BlogPost generateDummyPost(final String title) {
        BlogPost post = new BlogPost();

        if (title != null) {
            post.setTitle(title);
        }else {
            post.setTitle("Blog title");
        }

        post.setPubDate(System.currentTimeMillis());
        post.setAuthor("Mikey Judd");
        post.setAuthorEmail("judd.michael@gmail.com");
        post.setPullQuote("some quote");
        post.setPullQuoteAsHtml("<b>Some</b> <em>quote</em>");
        post.setBody("Interviewing at CACI");
        post.setBodyAsHtml("Interviewing at <strong>CACI</strong>");

        return post;
    }
}