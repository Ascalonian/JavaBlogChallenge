package interview.blog.service;

import com.galvin.interview.dao.BlogPostDAO;
import com.galvin.interview.entity.BlogPost;
import com.galvin.interview.utils.HibernateUtil;

import java.util.List;

public class BlogPostServiceImpl implements BlogPostService {
    private static BlogPostServiceImpl instance = null;
    private BlogPostDAO dao = new BlogPostDAO();

    public static BlogPostServiceImpl getInstance() {
        if (instance == null) {
            instance = new BlogPostServiceImpl();
        }

        return instance;
    }

    @Override
    public List<BlogPost> findAll () {
        return dao.findAll();
    }

    @Override
    public String save (BlogPost post) {
        HibernateUtil.beginTransaction();
        String uuid = dao.save(post);
        HibernateUtil.endTransaction(false);

        return uuid;
    }

    @Override
    public BlogPost findByUUID (String uuid) {
        return dao.findByUUID(uuid);
    }

    @Override
    public void remove (String uuid) {
        HibernateUtil.beginTransaction();
        dao.remove(uuid);
        HibernateUtil.endTransaction(false);
    }
}
