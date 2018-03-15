package interview.blog.service;

import com.galvin.interview.entity.BlogPost;

import java.util.List;

public interface BlogPostService {
    List<BlogPost> findAll();
    String save(BlogPost post);
    BlogPost findByUUID(String uuid);
    void remove(String uuid);
}
